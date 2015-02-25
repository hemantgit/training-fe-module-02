package com.backbase.exercises.ptc.dataproviders;

import com.backbase.portal.ptc.config.element.DataProviderConfig;
import com.backbase.portal.ptc.context.MutableProxyContext;
import com.backbase.portal.ptc.provider.DataProvider;
import com.backbase.portal.ptc.request.ProxyRequest;
import com.backbase.portal.ptc.response.MutableProxyResponse;

import java.io.*;
import java.util.Properties;
import org.apache.commons.io.FileUtils;

/**
 * This class is a dummy stock information data provider. It can be used for
 * creating a stub for real stock information service.
 *
 * 	<data-provider class="com.backbase.exercises.ptc.dataproviders.PropertiesDataProvider" id="propertiesDataProvider"/>
 * 	<pipe name="propertiesPipe">
 * 	    <data-provider referenceId="propertiesDataProvider"/>
 * 	</pipe>
 */
public class PropertiesDataProvider implements DataProvider {

    private static final String UTF_8 = "UTF-8";
    private static final String TEXT_PLAIN = "text/plain";
    private static final int HTTP_OK = 200;
    private static final int HTTP_ERROR = 500;

    private static final String FILE_NAME = "test.properties";

    private Properties properties = new Properties();

    public MutableProxyResponse executeRequest(DataProviderConfig config,
                                               MutableProxyContext context,
                                               ProxyRequest request) throws IOException {

        // load properties form the file
        try{
            properties.load(new FileInputStream(FILE_NAME));
        } catch (FileNotFoundException e) {
            new File(FILE_NAME).createNewFile();
        }

        // get request parameters
        String action = context.getInternalParameterValue("action");
        String key = context.getInternalParameterValue("key");
        String value = context.getInternalParameterValue("value");

        // local variables defining the status code and the string content of the response
        int statusCode = HTTP_OK;
        String responseBody = "#OK\n";

        // the CRUD service
        if ("create".equalsIgnoreCase(action) || "update".equalsIgnoreCase(action)) {
            if (key == null || value == null) {
                responseBody = "#Error: you have to specify key and value parameters\n";
                statusCode = HTTP_ERROR;
            } else {
                properties.put(key, value);
            }
        } else if ("delete".equalsIgnoreCase(action)) {
            if (key == null) {
                responseBody = "#Error: you have to specify the key parameter\n";
                statusCode = HTTP_ERROR;
            } else {
                properties.remove(key);
            }
        } else if ("read".equalsIgnoreCase(action)) {
            if (key == null) {
                responseBody = "#Error: you have to specify the key parameter\n";
                statusCode = HTTP_ERROR;
            } else {
                responseBody = "#" + properties.get(key).toString() + "\n";
            }
        } else {
            responseBody = "";
        }

        // store properties to the file
        properties.store(new FileOutputStream(FILE_NAME), "");

        // this object has to be populated with data to be send back
        MutableProxyResponse response = new MutableProxyResponse();
        // set the response body, mime type, and status code
        response.setBody(responseBody + FileUtils.readFileToString(new File(FILE_NAME), null));
        response.addContentTypeHeader(TEXT_PLAIN, UTF_8);
        response.setStatusCode(statusCode);

        return response;
    }
}
