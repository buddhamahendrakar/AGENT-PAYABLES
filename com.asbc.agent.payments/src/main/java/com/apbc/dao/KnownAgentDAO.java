package com.apbc.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.apbc.dto.Agent;

public class KnownAgentDAO {
	private JdbcTemplate template;

	public void setTemplate(JdbcTemplate template) {
		this.template = template;
	}

	public KnownAgentDAO() {
	}

	public Boolean findByAgentId(String agentId) {
		String sql = "select id from agent";
		List<String> agentIds = (List<String>) template.queryForList(sql,String.class);
		if (agentIds.contains(agentId))
			return true;
		return false;
	}
	
	public List<Agent> getAgentIdsAndNames() {
		return template.query("select id,firstname,lastname from agent", new RowMapper<Agent>() {
			@Override
			public Agent mapRow(ResultSet rs, int rownumber) throws SQLException {
				Agent e = new Agent();
				e.setAgentId(rs.getString("id"));
				e.setAgentFirstName(rs.getString("firstname"));
				e.setAgentLastName(rs.getString("lastname"));
				return e;
			}
		});
	}
}
