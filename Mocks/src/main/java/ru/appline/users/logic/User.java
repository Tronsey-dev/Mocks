package ru.appline.users.logic;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class User {
	private String name;
	private String surname;
	private Double salary;
}
