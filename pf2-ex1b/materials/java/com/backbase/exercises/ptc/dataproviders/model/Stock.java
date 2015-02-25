package com.backbase.exercises.ptc.dataproviders.model;

import java.util.Random;

import javax.xml.bind.annotation.XmlAttribute;

import org.apache.commons.lang.RandomStringUtils;

/**
 * A Stock object is used to encapsulate stock update information
 * that is sent in an update notification.
 */
public class Stock {
	
	private String symbol;
	private String company;
	private String exchange;
	
	private float last;
	private float high;
	private float low;
	private float current;
	
	private static Random random = new Random();
	private final static float MAX_VALUE = 67;
	
	/**
     * Constructs a stock with the given name with a initial random
     * stock price.
     */
	public Stock(){
		current = getRandomizedValue();
		last = getRandomizedValue();
		high = getRandomizedValue();
		low = getRandomizedValue();
		symbol = RandomStringUtils.randomAlphabetic(5);
	}
	
	public Stock( String symbol ){
		this();
		this.symbol = symbol;
	}
	
	/**
	 * Generate random stock price between 20 and 60
	 * @return A random stock
	 */
	private float getRandomizedValue(){
		return (float)(Math.abs(random.nextInt()) % 40 + 20);
	}
	
	/**
     * Update the stock price (generates a random change).
     */
    public float update() {
		float change = ((float)(random.nextGaussian() * 1.0));
		if (symbol.equals("Sun") && current < MAX_VALUE - 5)
		    change = Math.abs(change);		// what did you expect?
	
		float newCurrent = current + change;
	
		// don't allow stock price to step outside range
		if (newCurrent < 0 || newCurrent > MAX_VALUE)
		    change = 0;
	
		current += change;
		
		return change;
    }
	
    @XmlAttribute
	public String getSymbol() {
		return symbol;
	}
	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	public String getExchange() {
		return exchange;
	}
	public void setExchange(String exchange) {
		this.exchange = exchange;
	}
	public float getLast() {
		return last;
	}
	public void setLast(float last) {
		this.last = last;
	}
	public float getHigh() {
		return high;
	}
	public void setHigh(float high) {
		this.high = high;
	}
	public float getLow() {
		return low;
	}
	public void setLow(float low) {
		this.low = low;
	}

}
