package ru.appline.pets.logic;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UpdatePet {
	private Integer id;
	private String name;
	private String type;
	private Integer age;
}
