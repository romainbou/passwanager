package org.isen.jee.project.servlet.folder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.isen.jee.project.dao.FolderDao;
import org.isen.jee.project.dao.UserDao;
import org.isen.jee.project.model.Folder;
import org.isen.jee.project.model.User;

import com.cedarsoftware.util.io.JsonObject;
import com.cedarsoftware.util.io.JsonWriter;


@WebServlet("/folder")
public class FolderServlet extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private void setHeaders(HttpServletResponse resp){
		resp.addHeader("Access-Control-Allow-Origin", "*");
		resp.addHeader("Content-Type", "application/json");
	}
	
	private User loginUser(HttpServletRequest req, HttpServletResponse resp){
		String sessionId = req.getParameter("session_id");
		if(sessionId == null || sessionId.isEmpty()){
    		resp.setStatus(401);
    		try {
				resp.getWriter().print("{ \"error\": \"Unauthorized\" }");
			} catch (IOException e) {
				e.printStackTrace();
			}
    		return null;
		}
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

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		setHeaders(resp);
		User currentUser = loginUser(req, resp);
		if(currentUser == null){
			return;
		}
		
		UserDao userDao = new UserDao();
		currentUser = userDao.findById(currentUser.getId());
		List<Folder> folders = currentUser.getFolders();
		String usersString = JsonWriter.objectToJson(folders.toArray());
		resp.getWriter().print(usersString);
		
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		setHeaders(resp);
		FolderDao folderDao = new FolderDao();
		UserDao userDao = new UserDao();
		User currentUser = loginUser(req, resp);
		if(currentUser == null){
			return;
		}
		
		String name = req.getParameter("name");
    	String colaboratorsString = req.getParameter("colaborators");
    	String[] colaborators = colaboratorsString.split(",", -1);
    	
    	List<User> users = new ArrayList<User>();
    	for (String colaborator : colaborators) {
    		User currentColab = userDao.findByEmail(colaborator);
    		if(currentColab != null){
    			users.add(currentColab);
    		}
		}
    	Folder newFolder = folderDao.createNewFolder(name, currentUser, users);
    	
    	String folderJson = JsonWriter.objectToJson(newFolder);
		resp.getWriter().print(folderJson);
    	
    	return;
	}
}