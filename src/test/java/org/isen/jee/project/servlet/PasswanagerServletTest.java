package org.isen.jee.project.servlet;
import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.isen.jee.project.harness.JettyHarness;
import org.junit.Test;

public class PasswanagerServletTest extends JettyHarness {

	/**
     * En implémentant la méthode `doGet()` faire en sorte que la servlet
     * affiche l'application
     *
     * @throws Exception
     */
    @Test
    public void getHomepage() throws Exception {
        assertEquals((int)200, (int)getAndGetStatusCode(getServletUri()));
    }
    
    private String getServletUri() {
        return getBaseUri() + "/";
    }

}
