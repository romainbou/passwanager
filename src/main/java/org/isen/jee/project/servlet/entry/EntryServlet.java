package org.isen.jee.project.servlet.entry;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.isen.jee.project.dao.EntryDao;
import org.isen.jee.project.dao.FolderDao;
import org.isen.jee.project.dao.UserDao;
import org.isen.jee.project.model.Entry;
import org.isen.jee.project.model.Folder;
import org.isen.jee.project.model.User;
import org.isen.jee.project.model.Value;
import org.isen.jee.project.servlet.PasswanagerServlet;

import com.cedarsoftware.util.io.JsonObject;
import com.cedarsoftware.util.io.JsonWriter;


@WebServlet("/entry")
public class EntryServlet extends PasswanagerServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		setHeaders(resp);
		EntryDao entryDao = new EntryDao();
		FolderDao folderDao = new FolderDao();
		UserDao userDao = new UserDao();
		User currentUser = loginUser(req, resp);
		if(currentUser == null){
			return;
		}
		
		String title = req.getParameter("title");		
		String url = req.getParameter("url");
		String notes = req.getParameter("notes");
		String username = req.getParameter("username");
		String folderId = req.getParameter("folder");
    	String valuesString = req.getParameter("values");
    	String[] valuesStringArray = valuesString.split(",", -1);
    	
    	
    	List<Value> values = new ArrayList<>();
    	for (String value : valuesStringArray) {
    		// TODO Json parse
//    		values.add(new org.isen.jee.project.model.Value());
		}
    	Folder folder = folderDao.findById(Integer.parseInt(folderId));
    	Entry newEntry = entryDao.createNewEntry(title, url, notes, username, folder, currentUser, values);
    	
    	String entryJson = JsonWriter.objectToJson(newEntry);
		resp.getWriter().print(entryJson);
    	
    	return;
	}
}