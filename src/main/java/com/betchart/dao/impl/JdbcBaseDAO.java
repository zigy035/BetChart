package com.betchart.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;

import com.betchart.enumeration.ScheduledItemStatus;

public abstract class JdbcBaseDAO {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	protected JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}
	
	protected PreparedStatementCreator getPreparedStatement(String sql, Object... args) {
		return new PreparedStatementCreator() {
			
			@Override
			public PreparedStatement createPreparedStatement(Connection conn) throws SQLException {
				PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
				int i=1;
				for (Object arg : args) {
					//Integer, Long, String, Date, Boolean, Enum
					if (arg instanceof Integer) {
						ps.setInt(i++, (Integer)arg);
					} else if (arg instanceof Double) {
						ps.setDouble(i++, (Double)arg);
					} else if (arg instanceof Long) {
						ps.setLong(i++, (Long)arg);
					} else if (arg instanceof String) {
						ps.setString(i++, (String)arg);
					} else if (arg instanceof Date) {
						ps.setDate(i++, new java.sql.Date(((Date)arg).getTime()));
					} else if (arg instanceof Boolean) {
						ps.setInt(i++, ((Boolean)arg) ? 1 : 0);
					} /*else if (arg.getClass().isEnum()) {
						if (arg.getClass().isAssignableFrom(ScheduledItemStatus.class)) {
							ps.setInt(i++, ((ScheduledItemStatus)arg).getCode());
						} else {
							throw new SQLException("Incorrect enum type!");
						}
					} else if (arg == null) {
						ps.setNull(i++, arg);
					}*/ else {
						throw new SQLException("Incorrect data type!");
					}
				}
				
				return ps;
			}
		};
	}

	protected String clauseIn(List<Integer> list) {
		StringBuilder sb = new StringBuilder("(");
		boolean first = true;
		for (Integer item : list) {
			if (!first) {
				sb.append(", ");
			}
			sb.append(item);
			first = false;
		}
		sb.append(") ");

		return sb.toString();
	}
	
	
}
