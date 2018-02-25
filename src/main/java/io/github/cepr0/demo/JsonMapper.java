package io.github.cepr0.demo;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.jdbi.v3.core.mapper.ColumnMapper;
import org.jdbi.v3.core.statement.StatementContext;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Sergei Poznanski, 2018-02-25
 */
public class JsonMapper<T> implements ColumnMapper<T> {
	@Override
	public T map(ResultSet r, int columnNumber, StatementContext ctx) throws SQLException {
		return new Gson().fromJson(r.getString(columnNumber), new TypeToken<T>() {}.getType());
	}
}
