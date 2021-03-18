package ru.appline.compas.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import ru.appline.compas.logic.Range;

import java.util.HashMap;
import java.util.Map;

@RestController
public class CompassController {
	private Map<String, Range> coordinates = new HashMap<>();

	@PostMapping(value = "/putRanges", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody
	Map<String, String> putRanges(@RequestBody Map<String, String> ranges) {
		Map<String, String> result = new HashMap<>();
		try {
			for (Map.Entry<String, String> entry : ranges.entrySet()) {
				String side = entry.getKey();
				String rangeString = entry.getValue();
				String[] splitedRanges = StringUtils.split(rangeString, "-");
				Range range;
				int minRange = Integer.parseInt(splitedRanges[0]);
				int maxRange = Integer.parseInt(splitedRanges[1]);
				if (minRange < 0 || minRange > 359 || maxRange < 0 || maxRange > 359) {
					result.put("Сообщение", String.format("Диапазон стороны %1s вне диапазона 0-359", side));
					return result;
				}
				range = new Range(minRange, maxRange);

				coordinates.put(side, range);
			}
			result.put("Сообщение", "Стороны света успешно сохранены");
		} catch (Exception ex) {
			result.put("Сообщение", String.format("Ошибка выполнения: %1s", ex.getMessage()));
		}
		return result;
	}

	@GetMapping(value = "/getSide", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public Map<String, String> getSide(@RequestBody Map<String, Integer> degrees) {
		Integer degree = degrees.get("Degree");
		Map<String, String> result = new HashMap<>();
		if (degree == null) {
			result.put("Сообщение", "Должна быть задана переменная Degree");
			return result;
		}
		String resultSide = null;

		for (Map.Entry<String, Range> entry : coordinates.entrySet()) {
			String side = entry.getKey();
			Range range = entry.getValue();
			if (range.getMaxRange() < range.getMinRange()) {
				if ((range.getMinRange() <= degree && degree <= 360) || (degree >= 0 && range.getMaxRange() >= degree)) {
					resultSide = side;
					break;
				}
			} else if (range.contains(degree)) {
				resultSide = side;
				break;
			}
		}
		if (resultSide != null) {
			result.put("Side", resultSide);
		} else {
			result.put("Сообщение", "Направление не найдено");
		}
		return result;
	}
}
