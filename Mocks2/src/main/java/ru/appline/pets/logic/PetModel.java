package ru.appline.pets.logic;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class PetModel implements Serializable {
	private static final PetModel instanse = new PetModel();

	private final Map<Integer, Pet> model;

	public PetModel() {
		this.model = new HashMap<>();
	}

	public static PetModel getInstanse() {
		return instanse;
	}

	public void add(Pet pet, Integer id) {
		model.put(id, pet);
	}

	public Pet getFromList(Integer id) {
		return model.get(id);
	}

	public Map<Integer, Pet> getAll() {
		return model;
	}

	public void deletePet(Integer id) {
		model.remove(id);
	}
}
