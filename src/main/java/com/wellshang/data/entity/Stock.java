package com.wellshang.data.entity;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "stocks")
@CompoundIndexes({ @CompoundIndex(name = "stock_idx", def = "{ 'stockcode' : 1, 'stockname' : 1 }") })
public class Stock implements java.io.Serializable {

	private static final long serialVersionUID = 3868988044634966660L;
	@Id
	private ObjectId _id;
	@Indexed(unique = true)
	private String stockcode;
	@Indexed
	private String stockname;
	private float openingPrice;
	private float closingPrice;
	private int volume;
	private float turnover;

	public Stock() {
		this.stockcode = "-1";
	}

	public Stock(String stockcode) {
		this.stockcode = stockcode;
	}

	public Stock(String stockcode, String stockname) {
		this.stockcode = stockcode;
		this.stockname = stockname;
	}
	
	public Stock(String stockcode, String stockname, float openingPrice,
			float closingPrice) {
		this.stockcode = stockcode;
		this.stockname = stockname;
		this.openingPrice = openingPrice;
		this.closingPrice = closingPrice;
	}

	public ObjectId getId() {
		return _id;
	}

	public void setId(ObjectId id) {
		this._id = id;
	}

	public String getStockcode() {
		return stockcode;
	}

	public void setStockcode(String stockcode) {
		this.stockcode = stockcode;
	}

	public String getStockname() {
		return stockname;
	}

	public void setStockname(String stockname) {
		this.stockname = stockname;
	}

	public float getOpeningPrice() {
		return openingPrice;
	}

	public void setOpeningPrice(float openingPrice) {
		this.openingPrice = openingPrice;
	}

	public float getClosingPrice() {
		return closingPrice;
	}

	public void setClosingPrice(float closingPrice) {
		this.closingPrice = closingPrice;
	}

	public int getVolume() {
		return volume;
	}

	public void setVolume(int volume) {
		this.volume = volume;
	}

	public float getTurnover() {
		return turnover;
	}

	public void setTurnover(float turnover) {
		this.turnover = turnover;
	}

	public String toString() {
		return stockname + " - stockcode: " + stockcode + ", openingPrice: " + openingPrice
				+ ", closingPrice: " + closingPrice + ", volume: " + volume + ", turnover: " + turnover + ".";
	}
}
