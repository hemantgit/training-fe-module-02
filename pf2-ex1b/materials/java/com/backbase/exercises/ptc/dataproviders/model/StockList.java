package com.backbase.exercises.ptc.dataproviders.model;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Wrapper class for to marshall a java list object
 *
 */
@XmlRootElement(name="stocks")
public class StockList {
	protected List<Stock> list;
	
	public StockList(){
		this.list = new ArrayList<Stock>();
	}

    public void add( Stock stock ){
        this.list.add(stock);
    }

    @XmlElement(name="stock")
    public List<Stock> getList(){
        return list;
    }

}
