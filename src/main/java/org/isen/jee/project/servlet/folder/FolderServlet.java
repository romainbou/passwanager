package org.isen.jee.project.servlet.folder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonString;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.isen.jee.project.dao.FolderDao;
import org.isen.jee.project.dao.UserDao;
import org.isen.jee.project.model.Folder;
import org.isen.jee.project.model.User;
import org.isen.jee.project.servlet.PasswanagerServlet;

import com.cedarsoftware.util.io.JsonWriter;


@WebServlet("/folder")
public class FolderServlet extends PasswanagerServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	

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
		
		
		String folderId = req.getParameter("id");
		if(folderId != null && !folderId.isEmpty()){
			boolean found = false;
			String jsonFolder = null;
			for (Folder folder : folders) {
				if(folderId.equals(Integer.toString(folder.getId()))){
					// @TODO add all users public keys
					ArrayList<String> entries = new ArrayList();
					for (Iterator iterator = folder.getEntries().iterator(); iterator.hasNext();) {
						Entry entry = (Entry) iterator.next();
						entries.add(JsonWriter.objectToJson(entry));
					}
					String folderEntries = JsonWriter.objectToJson(entries);
					jsonFolder = JsonWriter.objectToJson(folder);
					jsonFolder = "{ \"folder\":"+ jsonFolder +", \"entries\":" + folderEntries + " }";

					found = true;
				}
			}
			if(found){
				resp.getWriter().print(jsonFolder);
				return;
			}
		}
		
		ArrayList<String> al = new ArrayList();
		for (Iterator iterator = folders.iterator(); iterator.hasNext();) {
			Folder folder = (Folder) iterator.next();
			al.add(JsonWriter.objectToJson(folder));
		}
		String usersString = JsonWriter.objectToJson(al);
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