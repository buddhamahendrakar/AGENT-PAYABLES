package com.apbc.dto;

import java.util.Date;

public class PayPeriod 
{
	private Date fromDate;
	private String payperiodId;
	private Date toDate;
	private Date uptoExtractDate;
	public PayPeriod(Date fromDate, Date toDate) {
		super();
		this.fromDate = fromDate;
		this.toDate = toDate;
	}
	public PayPeriod(Date fromDate, Date toDate, Date uptoExtractDate) {
		super();
		this.fromDate = fromDate;
		this.toDate = toDate;
		this.uptoExtractDate = uptoExtractDate;
	}
	
	public PayPeriod() {
		super();
	}
	public Date getFromDate() {
		return fromDate;
	}
	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}
	public Date getToDate() {
		return toDate;
	}
	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}
	public Date getUptoExtractDate() {
		return uptoExtractDate;
	}
	public void setUptoExtractDate(Date uptoExtractDate) {
		this.uptoExtractDate = uptoExtractDate;
	}
	public String getPayperiodId() {
		return payperiodId;
	}
	public void setPayperiodId(String payperiodId) {
		this.payperiodId = payperiodId;
	}
	
	

}
