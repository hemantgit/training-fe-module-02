package com.backbase.exercises.ptc.dataproviders;

import java.io.IOException;
import java.io.StringWriter;

import com.backbase.exercises.ptc.dataproviders.model.Stock;
import com.backbase.exercises.ptc.dataproviders.model.StockList;
import com.backbase.portal.ptc.config.element.DataProviderConfig;
import com.backbase.portal.ptc.context.MutableProxyContext;
import com.backbase.portal.ptc.provider.impl.AbstractDataProviderImpl;
import com.backbase.portal.ptc.request.ProxyRequest;
import com.backbase.portal.ptc.response.MutableProxyResponse;

import javax.xml.bind.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class is a dummy stock information data provider. It can be used for 
 * creating a stub for real stock information service. 
 *
 */
public class RandomStockInfoDataProvider extends AbstractDataProviderImpl{
	
	private static final Logger log = LoggerFactory.getLogger(RandomStockInfoDataProvider.class);
	
	private static final String UTF_8 = "UTF-8";
	private static final String TEXT_XML = "text/xml";
	private static final String[] SYMBOLS = new String[]{
		"AAC", "Apple", "BANC", "CBKN", "IBM", "Oracle", "MS", "SYM"
	};
	
	private Marshaller marshaller;
	
	/**
	 * Default constructor
	 */
	public RandomStockInfoDataProvider(){
		try {
			JAXBContext context = JAXBContext.newInstance(StockList.class);
			marshaller = context.createMarshaller();
	        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		} 
		catch (JAXBException e) {
			log.error(e.getMessage(), e);
		}
		
	}
	
	/**
	 * Provide and XML feed of stock information. 
	 */
	public MutableProxyResponse executeRequest(DataProviderConfig config, MutableProxyContext proxyContext, ProxyRequest proxyRequest) throws IOException {
		
		MutableProxyResponse mutableProxyResponse = new MutableProxyResponse();
		mutableProxyResponse.addContentTypeHeader( TEXT_XML, UTF_8);
		mutableProxyResponse.setStatusCode(200);
		
		String serviceResponse = createResponse();
		mutableProxyResponse.setBody( serviceResponse );
		
		return mutableProxyResponse;
	}
	
	/**
	 * Create some random socks.
	 * 
	 * @return A xml string
	 */
	private String createResponse(){
		StringWriter writer = new StringWriter();
		try {
	        StockList stocks = createRandomStocks();
	        marshaller.marshal(stocks, writer);     
		} 
		catch (JAXBException e) {
			log.error(e.getMessage(), e);
		}
		
		return writer.toString();
	}
	
	/**
	 * Create a random stock list.
	 * 
	 * @return A stocklist with random socks
	 */
	private StockList createRandomStocks(){
		StockList stocks = new StockList();
		for(int x = 0; x < SYMBOLS.length; x++){
			Stock stock = new Stock(SYMBOLS[x]);
			stocks.add(stock);
		}
		return stocks;
	}

}
