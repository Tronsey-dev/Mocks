package ru.appline.compas.logic;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Range {
	private int minRange;
	private int maxRange;

	public boolean contains(int number) {
		return (number >= minRange && number <= maxRange);
	}
}
