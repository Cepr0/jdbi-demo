package io.github.cepr0.demo;

import lombok.extern.slf4j.Slf4j;
import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.postgres.PostgresPlugin;

/**
 * @author Sergei Poznanski, 2018-02-23
 */
@Slf4j
public class Application {

	public static void main(String[] args) {
		LOG.info("App started...");

		Jdbi jdbi = Jdbi.create("jdbc:postgresql://localhost:5432/jdbi-demo?user=postgres&password=postgres")
				.installPlugin(new PostgresPlugin());

		Integer result = jdbi.withHandle(handle -> handle.createQuery("select 1").mapTo(Integer.class).findOnly());
		LOG.info("Result: {}", result);

		LOG.info("App ended...");
	}
}
