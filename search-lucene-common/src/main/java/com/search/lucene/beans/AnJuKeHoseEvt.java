package com.search.lucene.beans;
public class AnJuKeHoseEvt {
	private String houseName;
	private String price;
	private String lng;
	private String lat;
	private String aroundPrice;
	private String address;
	private String content;
	private String url;
	public String getHouseName() {
		return houseName;
	}
	public void setHouseName(String houseName) {
		this.houseName = houseName;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getLng() {
		return lng;
	}
	public void setLng(String lng) {
		this.lng = lng;
	}
	public String getLat() {
		return lat;
	}
	public void setLat(String lat) {
		this.lat = lat;
	}
	public String getAroundPrice() {
		return aroundPrice;
	}
	public void setAroundPrice(String aroundPrice) {
		this.aroundPrice = aroundPrice;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	@Override
	public String toString() {
		return "AnJuKeHoseEvt [houseName=" + houseName + ", price=" + price + ", lng=" + lng + ", lat=" + lat
				+ ", aroundPrice=" + aroundPrice + ", address=" + address + ", content=" + content + ", url=" + url
				+ "]";
	}
	
	
}