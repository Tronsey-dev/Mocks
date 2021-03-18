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

@WebServlet(urlPatterns = "/get")
public class ServletList extends HttpServlet {
	private Model model = Model.getInstance();
	private Gson gson = new GsonBuilder().setPrettyPrinting().create();

	/*protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		PrintWriter pw = response.getWriter();
		int id = Integer.parseInt(request.getParameter("id"));
		if (id == 0) {
			pw.print("<html><h3>Доступные пользователи:</h3></br>"
					+ "ID пользователя: <ul>");

			for (Map.Entry<Integer, User> entry : model.getFromList().entrySet()) {
				pw.print("<li>" + entry.getKey() + "</li>"
						+ "<ul>"
						+ "<li>Имя: " + entry.getValue().getName() + "</li>"
						+ "<li>Фамилия: " + entry.getValue().getSurname() + "</li>"
						+ "<li>Зарплата: " + entry.getValue().getSalary() + "</li>"
						+ "</ul>"
				);
			}
			pw.print("</ul>" + "<a href=\"index.jsp\">Домой</a></html>");
		} else if (id > 0) {
			if (id > model.getFromList().size()) {
				pw.print("<html><h3>Такого пользователя нет</h3></br><a href=\"index.jsp\">Домой</a></html>");
			} else {
				pw.print("<html><h3>Запрошенный пользователь:</h3></br>"
						+ "Имя: " + model.getFromList().get(id).getName() + "</br>"
						+ "Фамилия: " + model.getFromList().get(id).getSurname() + "</br>"
						+ "Зарплата: " + model.getFromList().get(id).getSalary() + "</br>"
						+ "<a href=\"index.jsp\">Домой</a></html>");
			}
		} else {
			pw.print("<html><h3>ID должен больше 0</h3></br><a href=\"index.jsp\">Домой</a></html>");
		}
	}*/
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("application/json;charset=utf-8");
		PrintWriter pw = response.getWriter();
		StringBuffer stringBuffer = new StringBuffer();
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

		if (id == 0) {
			pw.print(gson.toJson(model.getFromList()));
		} else if (id > 0) {
			if (id > model.getFromList().size()) {
				Map<String, String> result = new HashMap<>();
				result.put("Error", "Такого пользователя нет");
				pw.print(gson.toJson(result));
			} else {

				pw.print(gson.toJson(model.getFromList().get(id)));
			}
		} else {
			Map<String, String> result = new HashMap<>();
			result.put("Error", "ID должен больше 0");
			pw.print(gson.toJson(result));
		}
	}
}
