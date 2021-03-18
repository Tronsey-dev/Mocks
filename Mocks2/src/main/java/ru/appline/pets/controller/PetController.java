package ru.appline.pets.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import ru.appline.pets.logic.Pet;
import ru.appline.pets.logic.PetModel;
import ru.appline.pets.logic.UpdatePet;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@RestController
public class PetController {
	private static final PetModel petModel = PetModel.getInstanse();
	private static final AtomicInteger newId = new AtomicInteger(1);

	@PostMapping(value = "/createPet", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody
	Map<String, String> createPet(@RequestBody Pet pet) {
		petModel.add(pet, newId.getAndIncrement());
		Map<String, String> result = new HashMap<>();

		if (petModel.getAll().size() == 1) {
			result.put("Сообщение", "Поздравляем, вы создали своего первого питомца!");
		} else {
			result.put("Сообщение", "Поздравляем, вы создали еще одного питомца!");
		}
		return result;
	}

	@GetMapping(value = "/getAll", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public Map<Integer, Pet> getAll() {
		return petModel.getAll();
	}

	@GetMapping(value = "/getPet", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public Pet getPet(@RequestBody Map<String, Integer> id) {
		return petModel.getFromList(id.get("id"));
	}

	@DeleteMapping(value = "/deletePet", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public Map<String, String> deletePet(@RequestBody Map<String, Integer> id) {
		Map<String, String> result = new HashMap<>();
		if (id.containsKey("id")) {
			if (petModel.getFromList(id.get("id")) != null) {
				petModel.deletePet(id.get("id"));
				result.put("Сообщение", String.format("Удаление питомца %1d прошло успешно", id.get("id")));
			} else {
				result.put("Сообщение", String.format("Питомец с id = %1d не найден", id.get("id")));
			}
		} else {
			result.put("Сообщение", "Не задан id удаляемого питомца");
		}

		return result;
	}

	@PutMapping(value = "/updatePet", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public Map<String, String> deletePet(@RequestBody UpdatePet updatePet) {
		Map<String, String> result = new HashMap<>();
		Pet pet = petModel.getFromList(updatePet.getId());

		if (pet != null) {
			pet.setName(updatePet.getName());
			pet.setType(updatePet.getType());
			pet.setAge(updatePet.getAge());

			result.put("Сообщение", String.format("Обновление питомца %1d прошло успешно", updatePet.getId()));
		} else {
			result.put("Сообщение", String.format("Питомец с id = %1d не найден", updatePet.getId()));
		}

		return result;
	}

}
