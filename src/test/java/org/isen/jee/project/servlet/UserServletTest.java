package org.isen.jee.project.servlet;
import static org.junit.Assert.assertEquals;

import java.awt.List;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.persistence.oxm.JSONWithPadding;
import org.isen.jee.project.harness.JettyHarness;
import org.junit.Test;

public class UserServletTest extends JettyHarness {

	/**
     * Dans params, list de tous les users
     *
     * GET http://localhost:9090/user
     *
     * @throws Exception
     */
    @Test
    public void getUser() throws Exception {
        System.out.println(get(getServletUri()));
    }
    
    private String getServletUri() {
        return getBaseUri() + "/user";
    }

}
