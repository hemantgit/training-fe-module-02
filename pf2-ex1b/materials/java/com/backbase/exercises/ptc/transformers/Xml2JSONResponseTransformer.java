package com.backbase.exercises.ptc.transformers;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.json.JSON;
import net.sf.json.JSONObject;
import net.sf.json.xml.XMLSerializer;

import com.backbase.portal.ptc.common.Parameter;
import com.backbase.portal.ptc.context.MutableProxyContext;
import com.backbase.portal.ptc.request.ProxyRequest;
import com.backbase.portal.ptc.response.MutableProxyResponse;
import com.backbase.portal.ptc.transform.response.AbstractResponseTransformer;

/**
 * This class converts a XML response to a JSON response. Have a look at {@link http://www.json.org/}.
 * 
 * By default the Portal Dashboard used the json-lib dependency so we can also use it in this code.
 * See also the M2 Dependency Hierachy Tab.
 *
 */
public class Xml2JSONResponseTransformer extends AbstractResponseTransformer{
	
	private static final Logger log = LoggerFactory.getLogger(Xml2JSONResponseTransformer.class);
	private static final int INDENT_FACTOR = 2;
	private static final String UTF_8 = "UTF-8";
    private static final String APPLICATION_JSON = "application/json";
	private static final String DEFAULT_OBJECTNAME = "response";
	
	private XMLSerializer xmlSerializer;
	
	/**
	 * Default contructor
	 */
	public Xml2JSONResponseTransformer(){
		xmlSerializer = new XMLSerializer(); 
	}
	
	/**
	 * Transform xml code to a JSON object. 
	 */
	public void transform(MutableProxyContext proxyContext, ProxyRequest proxyRequest,
			MutableProxyResponse proxyResponse) {
		
		Boolean wrapResponse = false;
		if( isValidParameter("wrap") ){
			String wrap = this.getParameterValue("wrap");
			wrapResponse = Boolean.parseBoolean( wrap );
			log.info("wrapResponse={}", wrapResponse);
		}
		
		String objectName = DEFAULT_OBJECTNAME;
		if( isValidParameter("objectName") ){
			objectName = this.getParameterValue("objectName");
			log.info("objectName={}", objectName);
		}
		
		// The xml is inside the response
		String xml = proxyResponse.getBody();
		
		// Output it. 
		proxyResponse.setBody( xml2json(xml, wrapResponse, objectName) );
        proxyResponse.addContentTypeHeader(APPLICATION_JSON, UTF_8);
		//proxyResponse.setStatusCode(200);
		
	}
	
	/**
	 * 
	 * @param The xml string
	 * @param wrapResponse Boolean if the response should be wrapped in an object
	 * @param objectName The object name if wrapResponse is set to True.
	 * @return The JSON object represented as an String value.
	 */
	private String xml2json( String xml, Boolean wrapResponse, String objectName ){
		
		String output = StringUtils.EMPTY;
		
		// Only transform is there is something to transform
		if( StringUtils.isNotEmpty(xml.trim()) ){
			
			// Give the xml to the JSON serializer
			JSON response = xmlSerializer.read( xml );
			// Wrap the response in a 'response' object if needed
			if( wrapResponse ){
				JSONObject jsonObject = new JSONObject().element( objectName, response );
			
				// We want to have some readable json
				output = jsonObject.toString( INDENT_FACTOR );
			}
			else{
				// We want to have some readable json
				output = response.toString( INDENT_FACTOR );
			}
			
			// Log 
			log.info( output );
		}
		else{
			// Warn the developer
			log.warn("Nothing to transform.");
		}
		
		return output;
	}
	
	/**
	 * Check if the parameter exists and has values
	 * 
	 * @param The name of the parameter
	 * @return True if the parameter exists and has values
	 */
	private boolean isValidParameter( String name ){
		Parameter parameter = this.getParameter( name );
		if( parameter != null && parameter.getValues() != null && parameter.getValues().size() > 0){
			return true;
		}
		return false;
	}
	
}
