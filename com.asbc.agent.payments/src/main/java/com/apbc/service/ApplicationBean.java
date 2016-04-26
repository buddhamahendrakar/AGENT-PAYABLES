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
import com.apbc.dao.AgentPayablesDAO;
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
		

		
		PayPeriod pp = getPayPeriodDuration(payperiod , payPeriod);
		
		List<ExtractedAgentPaymentDetails> payablesList = agentDao.getPayablesRow(pp);
		List<ExtractedAgentPaymentSummary> payblesTotals = agentDao.getPayablesTotalRow(pp);
		System.out.println("Total Payables Records for this Run : "+payablesList.size());
		
		//NOW INSERT INTO AGENT EXTRACTED PAYMENT DETAILS TABLE AND SUMMARY TWO TABLES.
		for (ExtractedAgentPaymentDetails eap : payablesList)
		{
			extrDetails.insertAgentExtractedDetails(eap);
			agentDao.updatePaymentStatus(eap.getAgent_id(), eap.getPolicyid());			
		}
		// NOW INSERT INTO TOTALS RECORDS
		for (ExtractedAgentPaymentSummary pd : payblesTotals)
		{
			extrTotals.insertAgentExtractedTotals(pd);
		}
		//check the 3 parameter is it Test run or main run??
		//agentDao.
		
		/*//FileCommissionFeedDAO commissionfeedDAO	= (FileCommissionFeedDAO)ctx.getBean("commissionfeed");
		ProcessLogDAO logDao = (ProcessLogDAO)ctx.getBean("processlogdao");
		CommissionRulesDAO rulesDao = (CommissionRulesDAO)ctx.getBean("commissionrulesdao");
		AgentCommissionDAO agentDao = (AgentCommissionDAO)ctx.getBean("agentcommissiondao");
		//AgentTotalsCommissionDAO agentTotalsDao = (AgentTotalsCommissionDAO)ctx.getBean("agenttotalsdao");
		KnownCarrierDAO carDao	= (KnownCarrierDAO)ctx.getBean("carrierdao");
		AgentTotalsCommissionDAO agentTotalsDao = null;
		PdfStorageDAO storageDAO = (PdfStorageDAO)ctx.getBean("pdfstorage");
		KnownAgentDAO agentDAO = (KnownAgentDAO)ctx.getBean("agentdao");
		agencycommissionexpectedDAO agentCommExpected = (agencycommissionexpectedDAO)ctx.getBean("agencycommissionexpecteddao");
		PoliciesDAO policyDAO = (PoliciesDAO)ctx.getBean("policiesDao");


		List<CommissionFeedDetails> commissionsList = new ArrayList<CommissionFeedDetails>();
		Integer fileID = 0;
		try
		{
			// read from process log where we stopped.
			fileID = logDao.findLastProcessedId();
			logger.info("Completed Reading most recent policy file Id : "+ fileID);
			System.out.println("Completed Reading most recent policy file Id : "+ fileID);
			commissionsList = commissionfeedDAO.getCommissionFeedRowMapper(fileID);
			logger.info("Total commissions received for this Run : "+ commissionsList.size());
			System.out.println("Total commissions received for this Run : "+ commissionsList.size());
		}
		catch (Exception exp)
		{
			System.out.println(exp.getMessage());
			logger.error("Exception occured in retrieving feed from filecommissionfeed Table : "+exp.getMessage());
		}
		ICommissionManager commManager = ctx.getBean("commissionManager",FinancialManagerImpl.class);		
		if (commissionsList.size() <= 0)
		{
			logger.info("No Data available from filecommissionfeed Table : "+new Date());
			return;
		}
		logger.info("Looping thru retrieved feed from filecommissionfeed Table : "+commissionsList.size());

		for(CommissionFeedDetails commision : commissionsList)
		{
			try
			{
				CommissionService comService = new CommissionService();
				Boolean isProcessSuccess = comService.processInserts(commision, commManager);
				if (!isProcessSuccess)
				{
					logDao.create(commision, "No Policy found", "Rejected");
					continue;
				}
				//Retreive the policy's product code, effectivedate to calculate Agency Commission Rate
				PolicyData polData = policyDAO.getPolicy(commision.getPolicyId());
				// NEW table created for this set of rules commissionagency
				Long rate = agentCommExpected.findExpectedAgencyCommissionRate(polData);
				if ( null != rate)
				{
					// compare received rate v/s this RATE if not equal then raise the warning.
					if (rate != commision.getCommissionPercentage().longValue())
						logDao.create(commision, "Commissions not match", "Warning");
					else
						logDao.create(commision, "Success","Success");
				}
				
				//DO THIS LATER
				AgentCommission agentComm = new AgentCommission();
				agentComm.processAgentCommissions(commision, rulesDao, agentDao, agentTotalsDao, polData);
				
			}
			catch(Exception e)
			{
				logger.info("Could not able to insert into Commissions Table"+e.getMessage());
			}					
		}
		List<ProcessLog> goodRecords = logDao.findAllSuccessRecords(fileID);
		List<ProcessLog> rejectedRecords = logDao.findAllRejectedRecords(fileID);
		List<ProcessLog> warningRecords = logDao.findAllWarningRecords(fileID);
		List<Carrier> carriers = carDao.getCarrierIdsAndNames(); 
		List<Agent> agents = agentDAO.getAgentIdsAndNames(); 

		System.out.println("Writing PDF");
		PdfServiceBean pdfBean =  new PdfServiceBean();
		pdfBean.createPDF("TransLog"+convertDateToString(), goodRecords, rejectedRecords, warningRecords, carriers, agents, storageDAO, configFilePath);
		System.out.println("PDF Completed");*/
		((AbstractApplicationContext) ctx).close();
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
