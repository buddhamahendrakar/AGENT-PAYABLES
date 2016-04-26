
package com.apbc.dao;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.apbc.dto.ExtractedAgentPaymentDetails;
import com.apbc.dto.ExtractedAgentPaymentSummary;
import com.apbc.dto.PayPeriod;

public class AgentPayablesDAO {
	private JdbcTemplate template;

	public void setTemplate(JdbcTemplate template) {
		this.template = template;
	}

	public AgentPayablesDAO() {
	}

	/*public List<ProcessLog> insertAgentCommission(CommissionFeedDetails cd, Long commissionValue, PolicyData pd) 
	{

		String commissionQuery = "INSERT INTO agent_payables " +
				"(agent_id,"			
				+ "policy_Id,"
				+ "carrier_id,"
				+ "product_code,"
				+ "agency_earned_commission,"
				+ "agent_earned_commission,"
				+ "commission_type,"
				+ "payment_date,"
				+ "transaction_type,"
				+ "creation_date,"
				+ "extraction_date,"
				+ "status"				
				+ ")"
				+"VALUES (?, ?,?,?, ?,?,?, ?,?,?, ?,?)";
		
		try
		{
			//HOW MUCH TO PAY? BASED ON PRODUCT FOR EACH AGENT.
			template.update(commissionQuery, new Object[] 
			{
					pd.getAgentId(), cd.getPolicyId(),pd.getCarrierId(), pd.getProductId(),
					cd.getCommissionAmount(), cd.getCommissionAmount()*commissionValue/100,
					"FULL", //TODO FULL HOW BASED ON NUMBER OF AGENTS
					cd.getPaidtodate(),
					cd.getTransactionType(), //TODO DO A LOOKUP FOR TYPE, TO GET 2=ADVANCE PAY ETC
					new Date(),
					cd.getPaidtodate(),
					"CREATED"});
			
	    }
		catch(Exception eee)
		{
			System.out.println("Can not Insert data in Agent Commissions Table :"+eee.getMessage());
		}
		 System.out.println("Inserted into Agent Commission Table Successfully");
	
		return null;
	}
	
	*//**
	 * 
select agentId, sum(commissionAmount) as 'commtotal'from asbckodbnew.agent_commissions
where month(paidDate) = 09 and year(paidDate) = 2015
GROUP BY agentId;
	 * @param i
	 * @param j
	 * @return
	 *//*
	public List<AgentTotalsDTO> getAgentTotalsByPayPeriodRowMapper(Integer i, Integer j) 
	{
		return template.query("select agentId, sum(commissionAmount) "
				+ "from agent_commissions"
				+"where month(paidDate) = "+i+" and year(paidDate) = "+j+
				"GROUP BY agentId ",
				new RowMapper<AgentTotalsDTO>() {
					@Override
					public AgentTotalsDTO mapRow(ResultSet rs, int rownumber)throws SQLException {
						AgentTotalsDTO e = new AgentTotalsDTO();
						e.setAgentId(rs.getInt("agentId"));
						e.setCommissionTotals(rs.getDouble("commissionAmount"));
						return e;
					}
				});
	}
	
	public Boolean isCommissionPaidForAgent(String policyId)
	{
		String sql = "SELECT policy_Id From  agent_payables where policy_id= '"+policyId+"'";
		List<Map<String, Object>> listofIds = template.queryForList(sql);
		if (listofIds.size() > 0)
			return true;
		return false;
	}*/
	
	/**
	 * select * from asbckodbnew.agent_payables
where payment_date
BETWEEN CAST('2016-01-01' AS DATE) AND CAST('2016-01-15' AS DATE)
;

	 * @param i
	 * @param j
	 * @return
	 */
	
	
	public List<ExtractedAgentPaymentDetails> getPayablesRow(PayPeriod pp) 
	{
		Date d1 = pp.getFromDate();
		Date d2 = pp.getToDate();
		final Date d3 = pp.getUptoExtractDate();
		final Integer payperiodid = Integer.valueOf(pp.getPayperiodId());
		return template.query("select *  from agent_payables "
				+"where payment_date BETWEEN CAST('"+d1+"'"+" AS DATE) and CAST('"+ d2 +"' "+ "AS DATE) and status !="+"'Paid'",
				
				new RowMapper<ExtractedAgentPaymentDetails>() {
					@Override
					public ExtractedAgentPaymentDetails mapRow(ResultSet rs, int rownumber)throws SQLException {
						ExtractedAgentPaymentDetails e = new ExtractedAgentPaymentDetails();
						e.setAgent_id(rs.getInt("agent_id"));
						e.setPolicyid(rs.getString("policy_id"));
						e.setCarrierid(rs.getInt("carrier_id"));
						e.setProductcode(rs.getString("product_code"));
						e.setAgentcommission(rs.getLong("agency_earned_commission"));
						e.setCommissiontype(rs.getString("commission_type"));
						e.setPaymentdate(rs.getDate("payment_date"));
						e.setCreationdate(rs.getDate("creation_date"));
						e.setPayperiodupto(d3); //PAYPERIOD UP TO COMING FROM STATIC TABLE FOR GIVEN PAY PERIOD ID TODO
						e.setPayperiod(Integer.valueOf(payperiodid)); //TODO FROM PAYPERIOD INPUT VALUE
						e.setAgent_name("TEST NAME"); //TODO GET AGENT NAME FROM AGENT TABLE BY PROVIDING ID;
						e.setAgentlicnum("LIC"); // TODO get from STATIC TABLE;
						e.setTrasactiontype(rs.getInt("transaction_type"));
						e.setDesc("NORMAL");
						return e;
					}
				});
	}

	/*
	 * select *, agent_id, transaction_type, sum(agent_earned_commission) from asbckodbnew.agent_payables
where payment_date BETWEEN CAST('2016-01-01' AS DATE) AND CAST('2016-01-15' AS DATE) group by transaction_type;
	 */
	
	public List<ExtractedAgentPaymentSummary> getPayablesTotalRow(PayPeriod pp) 
	{
		Date d1 = pp.getFromDate();
		Date d2 = pp.getToDate();
		
		String datequeryPart  = " where payment_date BETWEEN CAST('"+d1+"'"+" AS DATE) and CAST('"+ d2 +"' "+ "AS DATE)";
		
		String query = "select   agent_id,transaction_type, creation_date," +
				"(select sum(agent_earned_commission) 	from asbckodbnew.agent_payables" +
				datequeryPart +
				"	and transaction_type= "+ 2  +
				"	and agent_id = p1.agent_id" +
				"	group by agent_id		)as 'normal'," +
				"(select sum(agent_earned_commission) 		from asbckodbnew.agent_payables" +
				datequeryPart +
				"	and transaction_type= " +1+
				"		and agent_id = p1.agent_id		group by agent_id		)as 'chargeback'," +
				
				"(select sum(agent_earned_commission) 		from asbckodbnew.agent_payables" +
						datequeryPart +
						"	and transaction_type= " +3+
						"		and agent_id = p1.agent_id		group by agent_id		)as 'adjustments'," +
				"	(select sum(agent_earned_commission) 	from asbckodbnew.agent_payables" +
				datequeryPart +
				"	and agent_id = p1.agent_id 	group by agent_id) as 'allTotal'" +
				"	from asbckodbnew.agent_payables p1 		" +
				datequeryPart +
				"group by agent_id" ;
		
		
		
		
		final Date d3 = pp.getUptoExtractDate();
		final Integer payperiodid = Integer.valueOf(pp.getPayperiodId());
		return template.query(query,
				
				new RowMapper<ExtractedAgentPaymentSummary>() {
					@Override
					public ExtractedAgentPaymentSummary mapRow(ResultSet rs, int rownumber)throws SQLException {
						ExtractedAgentPaymentSummary e = new ExtractedAgentPaymentSummary();
						e.setAgent_id(rs.getInt("agent_id"));
						e.setPayadjustments(rs.getLong("adjustments"));
						e.setPaynormal(rs.getLong("normal"));
						e.setPaychargeback(rs.getLong("chargeback"));
						e.setPaymenttotal(rs.getLong("allTotal"));
						e.setPayperiod(payperiodid);
						e.setAgent_name("SNEHIL_IDLIE");
						e.setPayperiodupto(d3);
						e.setCreationdate(rs.getDate("creation_date"));
						return e;
					}
				});
	}
	
	
	//UPDATE PAYBLES RECORDS 
	public void updatePaymentStatus(Integer agentId, String policyId)
	{
		String sql = "update agent_payables set status =" + "'Paid'"				
				+" WHERE policy_Id = '"+ policyId +"'"
				+ " AND agent_id = " + agentId;
		template.execute(sql);
	}
	
}
