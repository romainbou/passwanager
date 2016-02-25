package org.isen.jee.project.servlet;

import java.io.IOException;
import java.util.Optional;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;

import org.isen.jee.project.model.User;

@WebServlet("/passwanager")
public class PasswanagerServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	
	
	 public void setHeaders(HttpServletResponse resp){
		resp.addHeader("Access-Control-Allow-Origin", "http://localhost");
		resp.addHeader("Content-Type", "application/json");
		resp.addHeader("Access-Control-Allow-Credentials", "true");
	}
	
	
	public User loginUser(HttpServletRequest req, HttpServletResponse resp){
		User currentUser = (User) req.getSession().getAttribute("user");
		if(currentUser == null){
    		resp.setStatus(401);
    		try {
				resp.getWriter().print("{ \"error\": \"Unauthorized\" }");
			} catch (IOException e) {
				e.printStackTrace();
			}
    		return null;
		}
		return currentUser;
	}

	
	
}
