package io.github.cepr0.demo;

import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import org.jdbi.v3.core.argument.Argument;
import org.jdbi.v3.core.statement.StatementContext;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * @author Sergei Poznanski, 2018-02-25
 */
@RequiredArgsConstructor(staticName = "toJson")
public class JsonArgument<T> implements Argument {

	private final T obj;

	@Override
	public void apply(int position, PreparedStatement statement, StatementContext ctx) throws SQLException {
		statement.setString(position, new Gson().toJson(obj));
	}
}
