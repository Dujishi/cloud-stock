package com.xiaoka.cloud.stock.service.epc.dto;

/**
 * Created by sabo on 25/11/2017.
 */
public class CarModelPicMarkItem {

	/*
	标记序号
	 */
	private String picSequence;

	/*
	标记序号
	 */
	private Double xAxis;

	/*
	标记序号
	 */
	private Double yAxis;

	/**
	 * 矩形高度
	 */
	private Integer recHeight;

	/*
	矩形宽度
	 */
	private Integer recWidth;

	private Integer markedPicHeight;

	private Integer markedPicWidth;

	public String getPicSequence() {
		return picSequence;
	}

	public void setPicSequence(String picSequence) {
		this.picSequence = picSequence;
	}

	public Double getxAxis() {
		return xAxis;
	}

	public void setxAxis(Double xAxis) {
		this.xAxis = xAxis;
	}

	public Double getyAxis() {
		return yAxis;
	}

	public void setyAxis(Double yAxis) {
		this.yAxis = yAxis;
	}

	public Integer getRecHeight() {
		return recHeight;
	}

	public void setRecHeight(Integer recHeight) {
		this.recHeight = recHeight;
	}

	public Integer getRecWidth() {
		return recWidth;
	}

	public void setRecWidth(Integer recWidth) {
		this.recWidth = recWidth;
	}

	public Integer getMarkedPicHeight() {
		return markedPicHeight;
	}

	public void setMarkedPicHeight(Integer markedPicHeight) {
		this.markedPicHeight = markedPicHeight;
	}

	public Integer getMarkedPicWidth() {
		return markedPicWidth;
	}

	public void setMarkedPicWidth(Integer markedPicWidth) {
		this.markedPicWidth = markedPicWidth;
	}
}
