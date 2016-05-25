
package com.apbc.dao;
import java.util.Date;

import org.springframework.jdbc.core.JdbcTemplate;

import com.apbc.dto.ExtractedAgentPaymentDetails;

public class AgentExtractedDetailsDAO {
	private JdbcTemplate template;

	public void setTemplate(JdbcTemplate template) {
		this.template = template;
	}

	public AgentExtractedDetailsDAO() {
	}
	// Clean upto extracted date everytime
	public void AgentExtractionClear(Date upto)
	{
		//pay_period_upto  '" + d3  payment_date <= '" + d3
		try
		{
		String sql = "delete from from extracted_agent_payables_details where pay_period_upto <= '"+upto;
		template.execute(sql);
		}
		catch (Exception e)
		{
			System.out.println("CAN NOT ABLE TO DELETE EXTRACTED PAYMENTS SUMMARY BEFORE INSERTING ...THIS IS A CLEANING STEP");
		}
	}
	

	/**
	 * RETURNS THE RANGE OF DATES
	 * @param payPeriodId
	 * @return
	 * 
	 * /**`agent_id` int(11) NOT NULL,
  `agent_name` varchar(45) DEFAULT NULL,
  `agent_lic_num` varchar(45) DEFAULT NULL,
  `pay_period` varchar(10) NOT NULL,
  `pay_period_upto` date DEFAULT NULL,
  `policy_id` varchar(45) NOT NULL,
  `carrier_id` int(11) NOT NULL,
  `product_code` varchar(10) NOT NULL,
  `agent_earned_commission` decimal(25,2) NOT NULL,
  `commission_type` varchar(10) DEFAULT NULL,
  `payment_date` date DEFAULT NULL,
  `transaction_type` int(11) DEFAULT NULL,
  `creation_date` date DEFAULT NULL,
  `description` varchar(45) NOT NULL,
 */
	 
	public void  insertAgentExtractedDetails(ExtractedAgentPaymentDetails pd) 
	{

		String commissionQuery = "INSERT INTO extracted_agent_payables_details " +
				"(agent_id,"	
				+ "agent_name,"
				+ "agent_lic_num,"
				+"pay_period,"
				+"pay_period_upto,"
				+ "policy_Id,"
				+ "carrier_id,"
				+ "product_code,"
				+ "agent_earned_commission,"
				+ "commission_type,"
				+ "payment_date,"
				+ "transaction_type,"
				+ "creation_date,"
				+ "description"				
				+ ")"
				+"VALUES (?, ?,?,?, ?,?,?, ?,?,?, ?,?,?,?)";
		
		try
		{
			//HOW MUCH TO PAY? BASED ON PRODUCT FOR EACH AGENT.
			template.update(commissionQuery, new Object[] 
			{
					pd.getAgent_id(), pd.getAgent_name(), pd.getAgentlicnum(), pd.getPayperiod(), pd.getPayperiodupto(),
					pd.getPolicyid(),pd.getCarrierid(), pd.getProductcode(),
					pd.getAgentcommission(),pd.getCommissiontype(), pd.getPaymentdate(),pd.getTrasactiontype(), pd.getCreationdate(),
					pd.getDesc()
					}
			);
			// NOW UPDATE THE PAYABLES RECORD AS PAID SO NEXT TIME YOU DONT TAKE
			
			
	    }
		catch(Exception eee)
		{
			System.out.println("Can not Insert data in Agent Extract Commissions Individual Table :"+eee.getMessage());
		}
		 System.out.println("Inserted into Agent Extract Commissions Individual Table Successfully");
	}
	public void  insertAgentExtractedTotals(ExtractedAgentPaymentDetails pd) 
	{

		String commissionQuery = "INSERT INTO extracted_agent_payables_details " +
				"(agent_id,"	
				+ "agent_name,"
				+ "agent_lic_num,"
				+"pay_period,"
				+"pay_period_upto,"
				+ "policy_Id,"
				+ "carrier_id,"
				+ "product_code,"
				+ "agent_earned_commission,"
				+ "commission_type,"
				+ "payment_date,"
				+ "transaction_type,"
				+ "creation_date,"
				+ "description"				
				+ ")"
				+"VALUES (?, ?,?,?, ?,?,?, ?,?,?, ?,?,?,?)";
		
		try
		{
			//HOW MUCH TO PAY? BASED ON PRODUCT FOR EACH AGENT.
			template.update(commissionQuery, new Object[] 
			{
					pd.getAgent_id(), pd.getAgent_name(), pd.getAgentlicnum(), pd.getPayperiod(), pd.getPayperiodupto(),
					pd.getPolicyid(),pd.getCarrierid(), pd.getProductcode(),
					pd.getAgentcommission(),pd.getCommissiontype(), pd.getPaymentdate(),pd.getTrasactiontype(), pd.getCreationdate(),
					pd.getDesc()
					}
			);
			
	    }
		catch(Exception eee)
		{
			System.out.println("Can not Insert data in Agent Extract Commissions Individual Table :"+eee.getMessage());
		}
		 System.out.println("Inserted into Agent Extract Commissions Individual Table Successfully");
	}
	}
	

