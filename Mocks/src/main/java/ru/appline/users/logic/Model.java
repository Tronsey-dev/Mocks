package ru.appline.users.logic;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;


public class Model implements Serializable {
	private static final Model instance = new Model();

	private final Map<Integer, User> model;

	public static Model getInstance() {
		return instance;
	}

	private Model() {
		model = new HashMap<>();
		model.put(1, new User("Ivan", "Petrov", 55555D));
		model.put(2, new User("Petr", "Ivanov", 44444D));
		model.put(3, new User("Zoui", "Ivanov", 3333D));
		model.put(4, new User("Nick", "Ivanov", 3333D));
	}

	public void add(User user, Integer id) {
		model.put(id, user);
	}

	public Map<Integer, User> getFromList() {
		return model;
	}
}
