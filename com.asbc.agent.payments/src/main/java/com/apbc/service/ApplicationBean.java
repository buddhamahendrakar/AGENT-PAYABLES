package com.apbc.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.apbc.dao.AgentExtractedDetailsDAO;
import com.apbc.dao.AgentExtractedTotalsDAO;
import com.apbc.dao.AgentLicenceDAO;
import com.apbc.dao.AgentPayablesDAO;
import com.apbc.dao.KnownAgentDAO;
import com.apbc.dao.PayPeriodDAO;
import com.apbc.dto.ExtractedAgentPaymentDetails;
import com.apbc.dto.ExtractedAgentPaymentSummary;
import com.apbc.dto.PayPeriod;

public class ApplicationBean 
{
	final static Logger logger = Logger.getLogger(ApplicationBean.class);
	
	public void processPayroll(String filePath, String configFilePath,  String mainRun, String payPeriod)
	{
		logger.info("Inside Process method of payroll wise payments, Reading appcontext");
		ApplicationContext ctx = new FileSystemXmlApplicationContext(filePath);
		logger.info("Inside Process Policy record method, Reading appcontext");
		logger.info("Completed Reading appcontext");	
		AgentPayablesDAO agentDao	= (AgentPayablesDAO)ctx.getBean("agentpayables");
		PayPeriodDAO payperiod	= (PayPeriodDAO)ctx.getBean("payperiod"); 
		AgentExtractedDetailsDAO extrDetails	= (AgentExtractedDetailsDAO)ctx.getBean("extractedpayables");
		AgentExtractedTotalsDAO extrTotals	= (AgentExtractedTotalsDAO)ctx.getBean("extracttotals");
		KnownAgentDAO agentDAO = (KnownAgentDAO)ctx.getBean("agentdao"); 
		AgentLicenceDAO agentLicDAO = (AgentLicenceDAO)ctx.getBean("agentlicdao"); 
		PayPeriod pp = getPayPeriodDuration(payperiod , payPeriod);
		List<ExtractedAgentPaymentDetails> payablesList = agentDao.getPayablesRow(pp, agentDAO, agentLicDAO);
		List<ExtractedAgentPaymentSummary> payblesTotals = agentDao.getPayablesTotalRow(pp, agentDAO, agentLicDAO);
		System.out.println("Total Payables Records for this Run : "+payablesList.size());
		
		//NOW INSERT INTO AGENT EXTRACTED PAYMENT DETAILS TABLE AND SUMMARY TWO TABLES.
		// CLEAN THE RECORDS EVERYTIME AND DO A FRESH INSERT
		cleanExtractedData(extrDetails, pp.getUptoExtractDate());
		cleanExtractedTotals(extrTotals, pp.getUptoExtractDate());
		
		for (ExtractedAgentPaymentDetails eap : payablesList)
		{
			extrDetails.insertAgentExtractedDetails(eap);
			// IF STATUS IS DRY RUN THEN DO NOT CHANGE IT TO PAID FROM CREATED.
			if (!mainRun.equalsIgnoreCase("test"))
				agentDao.updatePaymentStatus(eap.getAgent_id(), eap.getPolicyid());			
		}
		// NOW INSERT INTO TOTALS RECORDS
		for (ExtractedAgentPaymentSummary pd : payblesTotals)
		{
			extrTotals.insertAgentExtractedTotals(pd);
		}
		//check the 3 parameter is it Test run or main run??
		//agentDao.

		((AbstractApplicationContext) ctx).close();
	}
	private void cleanExtractedTotals(AgentExtractedTotalsDAO extrTotals,Date uptoExtractDate) {
		extrTotals.AgentExtractionTotalsClear(uptoExtractDate);
		
	}
	private void cleanExtractedData(AgentExtractedDetailsDAO agentDao, Date upto) {
		agentDao.AgentExtractionClear(upto);
	}
	/**
	 * THIS WILL COLLECT PAY PERIOD DATES FROM STATIC CALENDER TABLE
	 * @param pp 
	 * @param payPeriod
	 */
	private PayPeriod getPayPeriodDuration(PayPeriodDAO pp, String payPeriod) 
	{
		return pp.getRangePayperiod(payPeriod);
	}

	Date convertToDate(String receivedDate) throws ParseException{
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        Date date = formatter.parse(receivedDate);
        return date;
    }
	
	String convertDateToString()
	{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
		//SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String date = sdf.format(new Date()); 
		System.out.println(date); 
		return date;
	}
	
	
}
