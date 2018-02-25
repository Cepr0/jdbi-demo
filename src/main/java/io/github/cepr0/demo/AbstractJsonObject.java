package io.github.cepr0.demo;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.Serializable;
import java.lang.reflect.Type;

/**
 * @author Sergei Poznanski, 2018-02-25
 */
public abstract class AbstractJsonObject<T> implements Serializable {

	public String toJson() {
		return new Gson().toJson(this, new TypeToken<T>() {}.getType());
	}

	public T fromJson(String json) {
		return new Gson().fromJson(json, (Type) this.<T>getClass());
	}
}
