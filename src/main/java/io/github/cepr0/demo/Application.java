package io.github.cepr0.demo;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.flywaydb.core.Flyway;
import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.core.mapper.reflect.ConstructorMapper;
import org.jdbi.v3.postgres.PostgresPlugin;
import org.jdbi.v3.sqlobject.SqlObjectPlugin;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;

/**
 * @author Sergei Poznanski, 2018-02-23
 */
@Slf4j
public class Application {

	private static final String DB_URL = "jdbc:postgresql://localhost:5432/jdbi-demo"; //:log4jdbc

	public static void main(String[] args) {
		LOG.debug("App started...");

		DbConnection conn = DbConnection.of(DB_URL, "postgres", "postgres");
		// "net.sf.log4jdbc.sql.jdbcapi.DriverSpy"
		LOG.debug("Connection taken.");

		int migrateCount = migrate(conn);
		LOG.debug("Migrate completed with {} scripts.", migrateCount);

		Jdbi jdbi = Jdbi.create(conn::get)
				.installPlugin(new PostgresPlugin())
				.installPlugin(new SqlObjectPlugin());

		LOG.debug("JDBI created.");

//		jdbi.registerColumnMapper(new JsonMapperFactory());

		List<Parent> parents = jdbi.withHandle(handle -> {
			handle.registerRowMapper(ConstructorMapper.factory(Parent.class));
			return handle.createQuery("select * from parents")
					.registerColumnMapper(new ChildListMapper())
					.mapTo(Parent.class)
					.list();
		});

		LOG.info("Result: {}", parents);

		String child = jdbi.withHandle(handle -> handle.createQuery("select :child")
				.bind("child", JsonArgument.toJson(new Child("child!")))
				.mapTo(String.class)
				.findOnly());

		LOG.info("Result: {}", child);

		parents = jdbi.withExtension(ParentDao.class, ParentDao::listUsers);
		LOG.info("Result: {}", parents);

		LOG.debug("App ended...");
	}

	private static int migrate(DbConnection conn) {
		Flyway flyway = new Flyway();
		flyway.setDataSource(conn.getUrl(), conn.getUser(), conn.getPassword());
		flyway.setTable("schema_versions");
		flyway.clean();
		return flyway.migrate();
	}

	@Getter
	public static class DbConnection {
		private final String url;
		private final String user;
		private final String password;
		private final String driver;
		private final Properties props;

		@java.beans.ConstructorProperties({"url", "user", "password", "driver"})
		private DbConnection(String url, String user, String password, String driver) {
			this.url = url;
			this.user = user;
			this.password = password;
			this.driver = driver;

			props = new Properties();
			props.setProperty("user", "postgres");
			props.setProperty("password", "postgres");
			if (driver != null) props.setProperty("driver", driver);
		}

		public static DbConnection of(String url, String user, String password, String driver) {
			return new DbConnection(url, user, password, driver);
		}

		public static DbConnection of(String url, String user, String password) {
			return new DbConnection(url, user, password, null);
		}

		public Connection get() throws SQLException {
			return DriverManager.getConnection(url, props);
		}
	}
}
