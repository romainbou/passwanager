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
     * affiche le score de jeu courant pour la requête suivante :
     *
     * GET http://localhost:9090/passwanager
     *
     * @throws Exception
     */
    @Test
    public void une_partie_doit_etre_intialisee() throws Exception {
        assertEquals("Coule", get(getServletUri()));
    }
    
    private String getServletUri() {
        return getBaseUri() + "/passwanager";
    }

}
