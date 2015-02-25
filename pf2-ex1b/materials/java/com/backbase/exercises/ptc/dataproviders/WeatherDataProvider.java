package com.backbase.exercises.ptc.dataproviders;

import com.backbase.portal.ptc.config.element.DataProviderConfig;
import com.backbase.portal.ptc.context.MutableProxyContext;
import com.backbase.portal.ptc.provider.DataProvider;
import com.backbase.portal.ptc.request.ProxyRequest;
import com.backbase.portal.ptc.response.MutableProxyResponse;
import com.cdyne.ws.weatherws.Weather;
import com.cdyne.ws.weatherws.WeatherReturn;
import com.cdyne.ws.weatherws.WeatherSoap;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: bartv
 * Date: 15-11-12
 * Time: 15:33
 */
public class WeatherDataProvider implements DataProvider {

    private static final String APPLICATION_JSON = "application/json";
    private static final String UTF_8 = "UTF-8";

    public MutableProxyResponse executeRequest(DataProviderConfig dataProviderConfig, MutableProxyContext mutableProxyContext, ProxyRequest proxyRequest) throws IOException {

        MutableProxyResponse response = new MutableProxyResponse();
        response.addContentTypeHeader(APPLICATION_JSON, UTF_8);
        response.setStatusCode(200);

        String zip = mutableProxyContext.getInternalParameterValue("zip");

        Weather weather = new Weather();
        WeatherSoap weatherSoap = weather.getWeatherSoap();
        WeatherReturn weatherReturn = weatherSoap.getCityWeatherByZIP(zip);

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.writeValue(byteArrayOutputStream, weatherReturn);

        response.setBodyBytes(byteArrayOutputStream.toByteArray());

        return response;

    }
}
