package com.wellshang.data.entity;

import java.util.List;

class Item {
	private String article;
	private String source;
	private String icon;	
	private String detailurl;
	private String trainnum;
	private String start;
	private String terminal;
	private String starttime;
	private String endtime;
	private String flight;
	private String name;
	private String info;

	public String getArticle() {
		return article;
	}
	public void setArticle(String article) {
		this.article = article;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	public String getDetailurl() {
		return detailurl;
	}
	public void setDetailurl(String detailurl) {
		this.detailurl = detailurl;
	}
	public String getTrainnum() {
		return trainnum;
	}
	public void setTrainnum(String trainnum) {
		this.trainnum = trainnum;
	}
	public String getStart() {
		return start;
	}
	public void setStart(String start) {
		this.start = start;
	}
	public String getTerminal() {
		return terminal;
	}
	public void setTerminal(String terminal) {
		this.terminal = terminal;
	}
	public String getStarttime() {
		return starttime;
	}
	public void setStarttime(String starttime) {
		this.starttime = starttime;
	}
	public String getEndtime() {
		return endtime;
	}
	public void setEndtime(String endtime) {
		this.endtime = endtime;
	}
	public String getFlight() {
		return flight;
	}
	public void setFlight(String flight) {
		this.flight = flight;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getInfo() {
		return info;
	}
	public void setInfo(String info) {
		this.info = info;
	}

}

public class TulingResult {
	public static final int text_code = 100000;
	public static final int link_code = 200000;
	public static final int news_code = 302000;
	public static final int train_code = 305000;
	public static final int flight_code = 306000;
	public static final int dish_code = 308000;
	
	private int code;
	private String text;
	private String url;
	private List<Item> list;

	public TulingResult() {
	}

	public TulingResult(int resultCode, String msg) {
		this.code = resultCode;
		this.text = msg;
	}

	public TulingResult(int resultCode) {
		this.code = resultCode;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getText() {
		if (code > 400000 || text == null || text.trim().equals("")) {
			return "正在开发...";
		}
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public void setList(List<Item> list) {
		this.list = list;
	}
	
	public String getResult() {
		String str = "";
		switch (code)
		{
			case TulingResult.text_code:
				str = text;
				break;
			case TulingResult.link_code:
				str = text + ", <a href=\"" + url + "\">访问页面</a>";
				break;
			case TulingResult.news_code:
				str += "新闻:";
				int i = 1;
				for (Item it : list) {
					str += " \r\n" + i++ + ". <a href=\"" + it.getDetailurl() + "\">" + it.getArticle() + "</a>";
				}
				break;
			case TulingResult.train_code:
				str += "列车";
				for (Item it : list) {
					str += ", 车次: " + it.getTrainnum() + ", 始发站: " + it.getStart() +
							", 终点站: " + it.getTerminal() + ", 始发时间: " + it.getStarttime() +
							", 到达时间: " + it.getEndtime() + ".";	
				}
				break;
			case TulingResult.dish_code:
				str += "美食";
				i = 1;
				for (Item it : list) {
					str += " \r\n" + i++ + ". <a href=\"" + it.getDetailurl() + "\">" + it.getName() + "</a>";
				}
				break;
			default:
				str = "正在开发...";
		}

		return str;
	}

}
