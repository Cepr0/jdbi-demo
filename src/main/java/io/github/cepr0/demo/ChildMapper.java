package io.github.cepr0.demo;

import com.google.gson.Gson;
import org.jdbi.v3.core.mapper.ColumnMapper;
import org.jdbi.v3.core.statement.StatementContext;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Sergei Poznanski, 2018-02-25
 */
public class ChildMapper implements ColumnMapper<Child> {
	@Override
	public Child map(ResultSet r, int columnNumber, StatementContext ctx) throws SQLException {
		return new Gson().fromJson(r.getString(columnNumber), Child.class);
	}
}
