package io.github.cepr0.demo;

import org.jdbi.v3.sqlobject.config.RegisterColumnMapper;
import org.jdbi.v3.sqlobject.config.RegisterConstructorMapper;
import org.jdbi.v3.sqlobject.statement.SqlQuery;

import java.util.List;

/**
 * @author Sergei Poznanski, 2018-02-25
 */
public interface ParentDao {
	@SqlQuery("select * from parents")
	@RegisterConstructorMapper(Parent.class)
	@RegisterColumnMapper(ChildListMapper.class)
	List<Parent> listUsers();
}
