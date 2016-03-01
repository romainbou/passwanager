package org.isen.jee.project.servlet.entry;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

import org.isen.jee.project.dao.FolderDao;
import org.isen.jee.project.dao.UserDao;
import org.isen.jee.project.harness.JettyHarness;
import org.isen.jee.project.model.Folder;
import org.isen.jee.project.servlet.folder.FolderServletTest;
import org.isen.jee.project.servlet.user.SigninServletTest;
import org.junit.Test;
import org.mindrot.jbcrypt.BCrypt;

import com.cedarsoftware.util.io.JsonObject;
import com.cedarsoftware.util.io.JsonReader;
import com.google.common.collect.Iterables;

public class EntryServletTest extends JettyHarness {
	
	public String getServletUri() {
		return getBaseUri() + "/entry";
	}
	
	private String setupUserSession(String email) {
		UserDao userDao = new UserDao();
    	String hashedPassword = BCrypt.hashpw("hackMe", BCrypt.gensalt());
    	if(userDao.findByEmail(email) == null){
    		userDao.createNewUser(email, "john", "doe", "john_tester", hashedPassword, "securePK");
    	}
    	Map<String, String> params = new HashMap<>();
        params.put("email", email);
        params.put("password", "hackMe");

        SigninServletTest singinServlet = new SigninServletTest();
        String jsonSession = post(singinServlet.getServletUri(), params);
        JsonObject session = (JsonObject) JsonReader.jsonToJava(jsonSession);
        String session_id = Iterables.get(session.values(), 0).toString();
        return session_id;
	}
	
	private Integer createFolder(){
		String sessionId = setupUserSession("bar@foo.com");
		Map<String, String> params = new HashMap<>();
		params.put("session_id", sessionId);
		params.put("name", "super_secret");
		params.put("colaborators", "bar@foo.com, foo@bar.com");
        FolderServletTest folderServlet = new FolderServletTest();
		Folder folder = (Folder) JsonReader.jsonToJava(post(folderServlet.getServletUri(), params));
		return folder.getId();
	}

	
	@Test
	public void createANewEntry() throws Exception {
		
		String sessionId = setupUserSession("bar@foo.com");
		Map<String, String> params = new HashMap<>();
		params.put("session_id", sessionId);
		params.put("folder", createFolder().toString());
		params.put("title", "Cool password");
		params.put("url", "http://example.com");
		params.put("username", "jenny");
		params.put("values", "xy, yz");
		
		assertEquals(200, postAndGetStatusCode(getServletUri(), params));  
	}
	
	@Test
	public void createANewEntryAndRefreshFolder() throws Exception {
		
		String sessionId = setupUserSession("bar@foo.com");
		Map<String, String> params = new HashMap<>();
		Integer folderId = createFolder();
		params.put("session_id", sessionId);
		params.put("folder", folderId.toString());
		params.put("title", "Cool password");
		params.put("url", "http://example.com");
		params.put("username", "jenny");
		params.put("values", "xy, yz");
		
		FolderDao folderDao = new FolderDao();
		Folder folder = folderDao.findById(folderId);
		
		assertEquals(0, folder.getEntries().size());
		assertEquals(200, postAndGetStatusCode(getServletUri(), params));
	}

}
