package com.backbase.exercises.ptc.transformers;

import com.backbase.portal.ptc.context.MutableProxyContext;
import com.backbase.portal.ptc.request.ProxyRequest;
import com.backbase.portal.ptc.response.MutableProxyResponse;
import com.backbase.portal.ptc.transform.response.AbstractResponseTransformer;

import java.io.ByteArrayOutputStream;
import java.io.StringReader;
import java.util.Properties;

/**
 * This class converts the string representation of properties to the XML representation
 *
 * 	<pipe name="propertiesXmlPipe">
 * 	    <data-provider referenceId="propertiesDataProvider"/>
 * 	    <response-transform>
 * 	        <transform class="com.backbase.exercises.ptc.transformers.PropertiesResponseTransformer"/>
 * 	    </response-transform>
 * 	</pipe>
 */
public class PropertiesResponseTransformer extends AbstractResponseTransformer {

    public void transform(MutableProxyContext context,
                          ProxyRequest request,
                          MutableProxyResponse response) {

        try {
            // get the original response body
            String responseBody = response.getBody();

            // transform: store the properties object as XML encoded byte array
            Properties properties = new Properties();
            properties.load(new StringReader(responseBody));
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            properties.storeToXML(byteArrayOutputStream, "properties");

            // set new transformed response body
            response.setBody(new String(byteArrayOutputStream.toByteArray(), "UTF-8"));
            response.addContentTypeHeader("text/xml", "UTF-8");
            //response.setStatusCode(200);
       } catch (Exception e) {
            response.setBody("Error: " + e.getMessage());
            response.setStatusCode(500);
        }
    }
}
