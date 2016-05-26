package com.apbc.dao;

import org.springframework.jdbc.core.JdbcTemplate;

public class AgentLicenceDAO {
	private JdbcTemplate template;

	public void setTemplate(JdbcTemplate template) {
		this.template = template;
	}

	public AgentLicenceDAO() {
	}

	public String findByAgentId(Integer agentId) 
	{
		String licenceNumber = "unknown";
		String sql = "select licensenumber from agentlicensed where agentid = "+agentId;
		licenceNumber = template.queryForObject(sql,String.class);
		return licenceNumber;
	}
	
}
