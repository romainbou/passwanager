package org.isen.jee.project.servlet.folder;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import javax.json.Json;

import org.json.JSONObject;
import org.eclipse.persistence.internal.oxm.record.json.JSONReader;
import org.isen.jee.project.dao.FolderDao;
import org.isen.jee.project.dao.UserDao;
import org.isen.jee.project.harness.JettyHarness;
import org.isen.jee.project.model.Folder;
import org.isen.jee.project.model.User;
import org.isen.jee.project.servlet.user.SigninServlet;
import org.isen.jee.project.servlet.user.SigninServletTest;
import org.junit.Test;
import org.mindrot.jbcrypt.BCrypt;

import com.cedarsoftware.util.io.JsonObject;
import com.cedarsoftware.util.io.JsonReader;
import com.google.common.collect.Iterables;


public class FolderServletTest extends JettyHarness {

	public String getServletUri() {
		return getBaseUri() + "/folder";
	}
	
	public String slice_range(String s, int startIndex, int endIndex) {
	    if (startIndex < 0) startIndex = s.length() + startIndex;
	    if (endIndex < 0) endIndex = s.length() + endIndex;
	    return s.substring(startIndex, endIndex);
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

 
	@Test
	public void tryToGetWithoutSession() throws Exception {
		assertEquals("{ \"error\": \"Unauthorized\" }", get(getServletUri()));  
	}
	
	@Test
	public void tryToGetExistingFolders() throws Exception {
		
		String sessionId = setupUserSession("bar@foo.com");
		Map<String, String> params = new HashMap<>();
		params.put("session_id", sessionId);
		
		FolderDao folderDao = new FolderDao();
		UserDao userDao = new UserDao();
		User owner = userDao.findByEmail("bar@foo.com");
		Folder newFolder = folderDao.createNewFolder("top_secret", owner);
		
		assertEquals(200, getWithParamsAndGetStatusCode(getServletUri(), params));  
	}
	
	@Test
	public void tryToGetExistingFolderFromId() throws Exception {
		
		String sessionId = setupUserSession("bar@foo.com");
		Map<String, String> params = new HashMap<>();
		params.put("session_id", sessionId);
		
		FolderDao folderDao = new FolderDao();
		UserDao userDao = new UserDao();
		User owner = userDao.findByEmail("bar@foo.com");
		Folder newFolder = folderDao.createNewFolder("top_secret", owner);
		params.put("id", Integer.toString(newFolder.getId()));
		JSONObject jobj = new JSONObject(getWithParams(getServletUri(), params));
		String jsonFolder = jobj.get("folder").toString();
		jsonFolder = jsonFolder.replaceAll(Pattern.quote("\\"), "");
		jsonFolder = slice_range(jsonFolder, 2, jsonFolder.length() - 2);
		Folder folder = (Folder) JsonReader.jsonToJava(jsonFolder);
        assertEquals(newFolder.getId(), folder.getId());  
	}
	
	
	@Test
	public void createANewFolder() throws Exception {
		
		String sessionId = setupUserSession("bar@foo.com");
		Map<String, String> params = new HashMap<>();
		params.put("session_id", sessionId);
		params.put("name", "super_secret");
		params.put("colaborators", "bar@foo.com, foo@bar.com");
		
		assertEquals(200, postAndGetStatusCode(getServletUri(), params));  
	}
	
	@Test
	public void createANewFolderAndReturnIt() throws Exception {
		
		String sessionId = setupUserSession("bar@foo.com");
		Map<String, String> params = new HashMap<>();
		params.put("session_id", sessionId);
		params.put("name", "super_secret");
		params.put("colaborators", "bar@foo.com, foo@bar.com");
		Folder folder = (Folder) JsonReader.jsonToJava(post(getServletUri(), params));
		assertEquals("super_secret", folder.getName());
	}
	
	@Test
	public void createANewFolderWith2Colabs() throws Exception {
		
		String sessionId = setupUserSession("bar@foo.com");
		setupUserSession("foo@bar.com");
    	Map<String, String> params = new HashMap<>();
        params.put("session_id", sessionId);
        params.put("name", "super_secret2");
        params.put("colaborators", "bar@foo.com, foo@bar.com");
        String folderJson = post(getServletUri(), params);
        Folder folder = (Folder) JsonReader.jsonToJava(folderJson);
        assertEquals(2, folder.getUsers().size());
        assertEquals("super_secret2", folder.getName());
        assertEquals("bar@foo.com", folder.getUsers().get(0).getEmail());
        assertEquals("foo@bar.com", folder.getUsers().get(1).getEmail());
	}
	
	

}
