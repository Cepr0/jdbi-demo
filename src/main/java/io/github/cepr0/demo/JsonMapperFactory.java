package io.github.cepr0.demo;

import com.google.gson.Gson;
import org.jdbi.v3.core.config.ConfigRegistry;
import org.jdbi.v3.core.mapper.ColumnMapper;
import org.jdbi.v3.core.mapper.ColumnMapperFactory;

import java.lang.reflect.Type;
import java.sql.ResultSetMetaData;
import java.util.Optional;

/**
 * @author Sergei Poznanski, 2018-02-25
 */
public class JsonMapperFactory implements ColumnMapperFactory {

	@Override
	public Optional<ColumnMapper<?>> build(Type type, ConfigRegistry config) {

		ColumnMapper<Object> mapper = (r, columnNumber, ctx) -> {
			ResultSetMetaData metaData = r.getMetaData();
			String typeName = metaData.getColumnTypeName(columnNumber);
			if ("json".equals(typeName) || "jsonb".equals(typeName)) {
				return new Gson().fromJson(r.getString(columnNumber), type);
			} else {
				return null;
			}
		};
		return Optional.of(mapper);
	}
}
