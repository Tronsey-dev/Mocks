package ru.appline.users;

import java.io.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

@WebServlet(urlPatterns = "/servlet")
public class HelloServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		PrintWriter pw = response.getWriter();
		pw.println("Hello World!!");
	}

	public void destroy() {
	}
}