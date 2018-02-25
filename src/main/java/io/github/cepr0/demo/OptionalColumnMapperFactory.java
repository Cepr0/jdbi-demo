package io.github.cepr0.demo;

import org.jdbi.v3.core.config.ConfigRegistry;
import org.jdbi.v3.core.generic.GenericTypes;
import org.jdbi.v3.core.mapper.ColumnMapper;
import org.jdbi.v3.core.mapper.ColumnMapperFactory;
import org.jdbi.v3.core.mapper.ColumnMappers;
import org.jdbi.v3.core.mapper.NoSuchMapperException;

import java.lang.reflect.Type;
import java.util.Optional;

/**
 * @author Sergei Poznanski, 2018-02-25
 */
public class OptionalColumnMapperFactory implements ColumnMapperFactory {
	public Optional<ColumnMapper<?>> build(Type type, ConfigRegistry config) {
		if (!Optional.class.equals(GenericTypes.getErasedType(type))) {
			return Optional.empty();
		}

		Type t = GenericTypes.resolveType(Optional.class.getTypeParameters()[0], type);

		ColumnMapper<?> tMapper = config.get(ColumnMappers.class)
				.findFor(t)
				.orElseThrow(() -> new NoSuchMapperException(
						"No column mapper registered for parameter " + t + " of type " + type));

		ColumnMapper<?> optionalMapper = (rs, col, ctx) ->
				Optional.ofNullable(tMapper.map(rs, col, ctx));

		return Optional.of(optionalMapper);
	}
}
