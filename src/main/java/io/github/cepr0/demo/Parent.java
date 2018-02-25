package io.github.cepr0.demo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jdbi.v3.sqlobject.config.RegisterColumnMapper;

import java.util.List;

/**
 * @author Sergei Poznanski, 2018-02-24
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Parent {
	private Long id;
	private String name;
	private List<Child> children;

	@RegisterColumnMapper(ChildListMapper.class)
	public List<Child> getChildren() {
		return children;
	}
}
