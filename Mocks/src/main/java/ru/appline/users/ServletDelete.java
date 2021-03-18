package ru.appline.users;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import ru.appline.users.logic.Model;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@WebServlet(urlPatterns = "/delete")
public class ServletDelete extends HttpServlet {
	private AtomicInteger counter = new AtomicInteger(5);
	private Model model = Model.getInstance();
	private Gson gson = new GsonBuilder().setPrettyPrinting().create();

	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {
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
		int id = jsonObject.get("id").getAsInt();

		Map<String, String> result = new HashMap<>();
		if (model.getFromList().containsKey(id)) {
			model.getFromList().remove(id);
			result.put("Result", "Пользователь успешно удален");
		} else {
			result.put("Error", "Такого пользователя нет");
		}
		pw.print(gson.toJson(result));
	}
}
