package org.isen.jee.project.servlet.folder;

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


@WebServlet("/folder")
public class FolderServlet extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		FolderDao folderDao = new FolderDao();
		List folders = folderDao.getAll();
		String usersString = JsonWriter.objectToJson(folders);
		resp.getWriter().print(usersString);
		
	}
}