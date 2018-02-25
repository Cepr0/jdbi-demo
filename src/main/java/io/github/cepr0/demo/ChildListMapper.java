package io.github.cepr0.demo;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.jdbi.v3.core.mapper.ColumnMapper;
import org.jdbi.v3.core.statement.StatementContext;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * @author Sergei Poznanski, 2018-02-25
 */
public class ChildListMapper implements ColumnMapper<List<Child>> {
	@Override
	public List<Child> map(ResultSet r, int columnNumber, StatementContext ctx) throws SQLException {
		return new Gson().fromJson(r.getString(columnNumber), new TypeToken<List<Child>>() {}.getType());
	}
}
