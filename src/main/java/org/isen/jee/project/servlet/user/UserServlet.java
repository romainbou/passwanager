package org.isen.jee.project.servlet.user;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.persistence.sessions.serializers.JSONSerializer;
import org.isen.jee.project.dao.UserDao;

import com.cedarsoftware.util.io.JsonWriter;


@WebServlet("/user")
public class UserServlet extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private void setHeaders(HttpServletResponse resp){
		resp.addHeader("Access-Control-Allow-Origin", "*");
		resp.addHeader("Content-Type", "application/json");
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		setHeaders(resp);
		UserDao userDao = new UserDao();
		List users = userDao.getAll();
		String usersString = JsonWriter.objectToJson(users);
		resp.getWriter().print(usersString);
		
	}
}