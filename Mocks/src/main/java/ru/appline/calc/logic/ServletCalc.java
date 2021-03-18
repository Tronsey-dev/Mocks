package ru.appline.calc.logic;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

@WebServlet(urlPatterns = "/calc")
public class ServletCalc extends HttpServlet {
	private Gson gson = new GsonBuilder().setPrettyPrinting().create();

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("application/json;charset=utf-8");
		StringBuffer stringBuffer = new StringBuffer();
		PrintWriter pw = response.getWriter();
		String line;
		try {
			BufferedReader br = request.getReader();
			while ((line = br.readLine()) != null) {
				stringBuffer.append(line);
			}
		} catch (Exception ex) {
			System.out.println(String.format("Error: %1s", ex.getMessage()));
		}

		JsonObject jsonObject = gson.fromJson(stringBuffer.toString(), JsonObject.class);
		Double a = jsonObject.get("a").getAsDouble();
		Double b = jsonObject.get("b").getAsDouble();
		String math = jsonObject.get("math").getAsString();
		Map<String, String> result = new HashMap<>();

		switch (math) {
			case "+":
				result.put("Результат", Double.toString(a + b));
				break;
			case "-":
				result.put("Результат", Double.toString(a - b));
				break;
			case "*":
				result.put("Результат", Double.toString(a * b));
				break;
			case "/":
				if (b.equals(0D)) {
					result.put("Ошибка", "Деление на ноль!");
				} else {
					result.put("Результат", Double.toString(a / b));
				}
				break;
			default:
				result.put("Ошибка", String.format("Нераспознанная операция: %1s", math));
		}
		pw.print(gson.toJson(result));
	}
}
