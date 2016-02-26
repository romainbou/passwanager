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
import org.isen.jee.project.model.User;

import com.cedarsoftware.util.io.JsonWriter;


@WebServlet("/user/signup")
public class SignupServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		String email = req.getParameter("email");
		String firstname = req.getParameter("firstname");
		String lastname = req.getParameter("lastname");
		String username = req.getParameter("username");    	
		String public_key = req.getParameter("public_key");
    	String password = req.getParameter("password");
    	
    	
    	if(email == null || public_key == null || password == null 
    	|| email.isEmpty() || public_key.isEmpty() || password.isEmpty()){
    		resp.setStatus(422);
    		resp.getWriter().print("{ \"error\": \"Missing credentials\" }");
    		return;
    	}
    	UserDao userDao = new UserDao();
    	User foundUser = userDao.findByEmail(email);
    	if(foundUser != null){
    		resp.setStatus(422);
    		resp.getWriter().print("{ \"error\": \"Email already taken\" }");
    		return;
    	}
    	User newUser = userDao.createNewUser(email, firstname, lastname, username, password, public_key);
    	if(newUser != null){
    		req.getSession().setAttribute("user", newUser);
    		String sessionId = req.getSession().getId();
    		resp.getWriter().print("{ \"session_id\": \"" + sessionId + "\" }");
    		return;
    	} else {
    		resp.setStatus(422);
    		resp.getWriter().print("{ \"error\": \"Server error\" }");
    		return;	
    	}
	}
}