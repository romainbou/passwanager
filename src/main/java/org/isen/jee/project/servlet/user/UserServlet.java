package org.isen.jee.project.servlet.user;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.isen.jee.project.dao.UserDao;
import org.isen.jee.project.model.User;
import org.isen.jee.project.servlet.PasswanagerServlet;

import com.cedarsoftware.util.io.JsonWriter;


@WebServlet("/user")
public class UserServlet extends PasswanagerServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		setHeaders(resp);
		User currentUser = loginUser(req, resp);
		String userString = JsonWriter.objectToJson(currentUser);
		resp.getWriter().print(userString);
		
	}
}