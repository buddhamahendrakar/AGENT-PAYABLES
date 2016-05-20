package com.apbc.dto;

import java.util.Date;

/**
 * POJO TO STORE PAYBLES RESULTS AND STORE THEM INTO DB.
 * @author T54JU52
 *
 */
public class ExtractedAgentPaymentDetails {
/**`agent_id` int(11) NOT NULL,
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
	
	private Integer agent_id;
	private String	agent_name;
	private String 	agentlicnum;
	private Integer	payperiod;
	private Date	payperiodupto;
	private String 	policyid;
	private Integer carrierid;
	private String 	productcode;
	private Long	agentcommission;
	private String 	commissiontype;
	private Date	paymentdate;
	private Integer	trasactiontype;
	private Date	creationdate;
	private String	desc;
	public ExtractedAgentPaymentDetails(Integer agent_id, String agent_name,
			String agentlicnum, Integer payperiod, Date payperiodupto,
			String policyid, Integer carrierid, String productcode,
			Long agentcommission, String commissiontype, Date paymentdate,
			Integer trasactiontype, Date creationdate, String desc) {
		super();
		this.agent_id = agent_id;
		this.agent_name = agent_name;
		this.agentlicnum = agentlicnum;
		this.payperiod = payperiod;
		this.payperiodupto = payperiodupto;
		this.policyid = policyid;
		this.carrierid = carrierid;
		this.productcode = productcode;
		this.agentcommission = agentcommission;
		this.commissiontype = commissiontype;
		this.paymentdate = paymentdate;
		this.trasactiontype = trasactiontype;
		this.creationdate = creationdate;
		this.desc = desc;
	}
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
	public String getPolicyid() {
		return policyid;
	}
	public void setPolicyid(String policyid) {
		this.policyid = policyid;
	}
	public Integer getCarrierid() {
		return carrierid;
	}
	public void setCarrierid(Integer carrierid) {
		this.carrierid = carrierid;
	}
	public String getProductcode() {
		return productcode;
	}
	public void setProductcode(String productcode) {
		this.productcode = productcode;
	}
	public Long getAgentcommission() {
		return agentcommission;
	}
	public void setAgentcommission(Long agentcommission) {
		this.agentcommission = agentcommission;
	}
	public String getCommissiontype() {
		return commissiontype;
	}
	public void setCommissiontype(String commissiontype) {
		this.commissiontype = commissiontype;
	}
	public Date getPaymentdate() {
		return paymentdate;
	}
	public void setPaymentdate(Date paymentdate) {
		this.paymentdate = paymentdate;
	}
	public Integer getTrasactiontype() {
		return trasactiontype;
	}
	public void setTrasactiontype(Integer trasactiontype) {
		this.trasactiontype = trasactiontype;
	}
	public Date getCreationdate() {
		return creationdate;
	}
	public void setCreationdate(Date creationdate) {
		this.creationdate = creationdate;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public ExtractedAgentPaymentDetails() {
		// TODO Auto-generated constructor stub
	}
	
	

	

}
