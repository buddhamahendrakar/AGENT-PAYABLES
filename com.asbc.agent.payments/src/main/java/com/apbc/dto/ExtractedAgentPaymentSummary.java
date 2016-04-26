package com.apbc.dto;

import java.util.Date;

/**
 * POJO TO STORE PAYBLES RESULTS AND STORE THEM INTO DB.
 * @author T54JU52
 *
 */
public class ExtractedAgentPaymentSummary {

	/**CREATE TABLE `extracted_agent_payables_totals` (
	  `agent_id` int(11) NOT NULL,
	  `agent_name` varchar(45) DEFAULT NULL,
	  `agent_lic_num` varchar(45) DEFAULT NULL,
	  `pay_period` int(11) NOT NULL,
	  `pay_period_upto` date DEFAULT NULL,
	  `payment_normal` decimal(25,2) NOT NULL,
	  `payment_chargeback` decimal(25,2) NOT NULL,
	  `payment_adjustments` decimal(25,2) NOT NULL,
	  `payment_total` decimal(25,2) NOT NULL,
	  `creation_date` date DEFAULT NULL,
	  PRIMARY KEY (`agent_id`,`pay_period`)
	) ENGINE=InnoDB DEFAULT CHARSET=latin1$$
	
	select  agent_id, pay_period_upto, creation_date, transaction_type, sum(agent_earned_commission) from asbckodbnew.agent_payables
where payment_date
BETWEEN CAST('2016-01-01' AS DATE) AND CAST('2016-01-15' AS DATE)
group by transaction_type;
*/

	
	private Integer agent_id;
	private String	agent_name;
	private String 	agentlicnum;
	private Integer	payperiod;
	private Date	payperiodupto;
	private Long 	paynormal;
	private Long	paychargeback;
	private Long 	payadjustments;
	private Long	paymenttotal;
	private Date	creationdate;
	public Integer getAgent_id() {
		return agent_id;
	}
	public void setAgent_id(Integer agent_id) {
		this.agent_id = agent_id;
	}
	public String getAgent_name() {
		return agent_name;
	}
	public void setAgent_name(String agent_name) {
		this.agent_name = agent_name;
	}
	public String getAgentlicnum() {
		return agentlicnum;
	}
	public void setAgentlicnum(String agentlicnum) {
		this.agentlicnum = agentlicnum;
	}
	public Integer getPayperiod() {
		return payperiod;
	}
	public void setPayperiod(Integer payperiod) {
		this.payperiod = payperiod;
	}
	public Date getPayperiodupto() {
		return payperiodupto;
	}
	public void setPayperiodupto(Date payperiodupto) {
		this.payperiodupto = payperiodupto;
	}
	public Long getPaynormal() {
		return paynormal;
	}
	public void setPaynormal(Long paynormal) {
		this.paynormal = paynormal;
	}
	public Long getPaychargeback() {
		return paychargeback;
	}
	public void setPaychargeback(Long paychargeback) {
		this.paychargeback = paychargeback;
	}
	public Long getPayadjustments() {
		return payadjustments;
	}
	public void setPayadjustments(Long payadjustments) {
		this.payadjustments = payadjustments;
	}
	public Long getPaymenttotal() {
		return paymenttotal;
	}
	public void setPaymenttotal(Long paymenttotal) {
		this.paymenttotal = paymenttotal;
	}
	public Date getCreationdate() {
		return creationdate;
	}
	public void setCreationdate(Date creationdate) {
		this.creationdate = creationdate;
	}
	public ExtractedAgentPaymentSummary() {
		super();
	}
	
	

}
