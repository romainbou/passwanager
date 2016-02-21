package org.isen.jee.project.servlet.folder;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.isen.jee.project.dao.UserDao;
import org.isen.jee.project.model.Folder;
import org.isen.jee.project.model.User;

import com.cedarsoftware.util.io.JsonWriter;


@WebServlet("/folder")
public class FolderServlet extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		String sessionId = req.getParameter("session_id");
		if(sessionId == null || sessionId.isEmpty()){
    		resp.setStatus(401);
    		resp.getWriter().print("{ \"error\": \"Unauthorized\" }");
    		return;
		}
		User currentUser = (User) req.getSession().getAttribute("user");
		if(currentUser == null){
    		resp.setStatus(401);
    		resp.getWriter().print("{ \"error\": \"Unauthorized\" }");
    		return;
		}
		
		UserDao userDao = new UserDao();
		currentUser = userDao.findById(currentUser.getId());
		List<Folder> folders = currentUser.getFolders();
		String usersString = JsonWriter.objectToJson(folders.toArray());
		resp.getWriter().print(usersString);
		
	}
}