package ru.appline.users;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import ru.appline.users.logic.Model;
import ru.appline.users.logic.User;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.atomic.AtomicInteger;

@WebServlet(urlPatterns = "/add")
public class ServletAdd extends HttpServlet {
	private AtomicInteger counter = new AtomicInteger(5);
	private Model model = Model.getInstance();
	private Gson gson = new GsonBuilder().setPrettyPrinting().create();

	/*protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("UTF-8");

		PrintWriter pw = response.getWriter();
		String name = request.getParameter("name");
		String surname = request.getParameter("surname");
		Double salary = Double.parseDouble(request.getParameter("salary"));

		User user = new User(name, surname, salary);
		model.add(user, counter.getAndIncrement());
		pw.println("<html><h3> Пользователь: " + name + " " + surname + " с зарплатой: " + salary + " создан! </h3>"
				+ "<a href=\"addUser.html\">Создать нового пользователя</a></br>"
				+ "<a href=\"index.jsp\">Домой</a></html>");
	}*/

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("application/json;charset=utf-8");
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
		String name = jsonObject.get("name").getAsString();
		String surname = jsonObject.get("surname").getAsString();
		Double salary = jsonObject.get("salary").getAsDouble();

		User user = new User(name, surname, salary);
		model.add(user, counter.getAndIncrement());
		PrintWriter pw = response.getWriter();
		pw.print(gson.toJson(model.getFromList()));
	}
}
