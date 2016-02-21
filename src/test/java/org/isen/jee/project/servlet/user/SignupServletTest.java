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

public class SignupServletTest extends JettyHarness {

	/**
     * Dans params, list de tous les users
     *
     * GET http://localhost:9090/user
     *
     * @throws Exception
     */
	private String getServletUri() {
		return getBaseUri() + "/user/signup";
	}
	
    @Test
    public void singupValidUser() throws Exception {
    	UserDao userDao = new UserDao();
    	String email = "bar@foo.com";
    	if(userDao.findByEmail(email) != null){
    		userDao.delete(email);
    	}
    	Map<String, String> params = new HashMap<>();
    	params.put("email", email);
    	params.put("firstname", "john");
    	params.put("lastname", "doe");
        params.put("username", "john_tester");
        params.put("password", "hackMe");
        params.put("public_key", "securePK");
        assertEquals(200, postAndGetStatusCode(getServletUri(), params));
    }
    

}
