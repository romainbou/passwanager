package org.isen.jee.project.servlet.user;
import static org.junit.Assert.assertEquals;

import java.awt.List;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.persistence.oxm.JSONWithPadding;
import org.isen.jee.project.dao.UserDao;
import org.isen.jee.project.harness.JettyHarness;
import org.junit.Test;

public class SigninServletTest extends JettyHarness {

	/**
     * Dans params, list de tous les users
     *
     * GET http://localhost:9090/user
     *
     * @throws Exception
     */
   
	@Test
	public void singinWithoutEmail() throws Exception {
		Map<String, String> params = new HashMap<>();
		params.put("password", "1234");
		assertEquals("{ \"error\": \"Missing credentials\" }", post(getServletUri(), params));  
	}
	@Test
	public void singinUnknownUser() throws Exception {
		Map<String, String> params = new HashMap<>();
		params.put("email", "foo@bar.com");
		params.put("password", "1234");
		assertEquals("{ \"error\": \"Wrong credentials\" }", post(getServletUri(), params));  
	}
    @Test
    public void singinValidUser() throws Exception {
    	UserDao userDao = new UserDao();
    	String email = "bar@foo.com";
    	if(userDao.findByEmail(email) == null){
    		userDao.createNewUser(email, "john", "doe", "john_tester", "hackMe", "securePK");
    	}
    	Map<String, String> params = new HashMap<>();
        params.put("email", email);
        params.put("password", "hackMe");
        assertEquals(200, postAndGetStatusCode(getServletUri(), params));
    }
    
    private String getServletUri() {
        return getBaseUri() + "/user/signin";
    }

}
