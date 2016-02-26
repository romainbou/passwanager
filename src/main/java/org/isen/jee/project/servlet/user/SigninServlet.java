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
import org.isen.jee.project.servlet.PasswanagerServlet;
import org.mindrot.jbcrypt.BCrypt;

import com.cedarsoftware.util.io.JsonWriter;


@WebServlet("/user/signin")
public class SigninServlet extends PasswanagerServlet {
	
	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		setHeaders(resp);
    	String email = req.getParameter("email");
    	String password = req.getParameter("password");
    	if(email == null || password == null || email.isEmpty() || password.isEmpty()){
    		resp.setStatus(422);
    		resp.getWriter().print("{ \"error\": \"Missing credentials\" }");
    		return;
    	}
    	UserDao userDao = new UserDao();
    	System.out.println(password);
    	User foundUser = userDao.signin(email, password);
    	if(foundUser != null){
    		req.getSession().setAttribute("user", foundUser);
    		String sessionId = req.getSession().getId();
    		resp.getWriter().print("{ \"session_id\": \"" + sessionId + "\" }");
    		return;
    	} else {
    		resp.setStatus(422);
    		resp.getWriter().print("{ \"error\": \"Wrong credentials\" }");
    		return;	
    	}
	}
}