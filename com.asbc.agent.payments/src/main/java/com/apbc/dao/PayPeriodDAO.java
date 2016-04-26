
package com.apbc.dao;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.apbc.dto.PayPeriod;

public class PayPeriodDAO {
	private JdbcTemplate template;

	public void setTemplate(JdbcTemplate template) {
		this.template = template;
	}

	public PayPeriodDAO() {
	}
	/**
	 * RETURNS THE RANGE OF DATES
	 * @param payPeriodId
	 * @return
	 */
	public PayPeriod getRangePayperiod(String payPeriodId)
	{
			//return template.queryForObject("select * from pay_period where payperiodId = '"+ payPeriodId +"'",
			return template.queryForObject("select * from pay_period where payperiodId = "+ payPeriodId,
					new RowMapper<PayPeriod>() {
						@Override
						public PayPeriod mapRow(ResultSet rs, int rownumber)throws SQLException 
						{
							PayPeriod e = new PayPeriod();
							e.setFromDate(rs.getDate("payperiodFrom"));
							e.setToDate(rs.getDate("payperiodTo"));
							e.setUptoExtractDate(rs.getDate("extractUptoDate"));
							e.setPayperiodId(rs.getString("payperiodId"));
							return e;
						}
					});
	}
	}
	

