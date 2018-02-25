package io.github.cepr0.demo;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Sergei Poznanski, 2018-02-25
 */
public class ChildTest {

	@Test
	public void toJson() {
		String childJson = new Child("child1").toJson();
		System.out.println(childJson);
		assertThat(childJson).isEqualTo("{\"name\":\"child1\"}");
	}

	@Test
	public void fromJson() {
		Child child = new Child().fromJson("{\"name\":\"child1\"}");
		System.out.println(child);
		assertThat(child.getName()).isEqualTo("child1");
	}
}