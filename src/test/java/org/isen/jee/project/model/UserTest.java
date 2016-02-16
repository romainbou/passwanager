package org.isen.jee.project.model;

import org.isen.jee.project.dao.UserDao;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class UserTest {

    private User user;

//    @Before
//    public void doBefore() {
//        user = new User();
//    }

    /**
     * Dans ce test, on vérifiera que le jeu dans lequel le joueur lancer toutes
     * ses boules dans la goutière (c'est à dire qu'il ne fait tomber aucune
     * quille) rapporte un score nul.
     *
     * In that test, you'll verigy that a game in which the player rolls all of
     * its balls in the gutter is null.
     *
     * @throws Exception
     */
    @Test
    public void testCreateUser() throws Exception {
    	System.out.println("create user");
    	UserDao userDao = new UserDao();
    	User user = userDao.createNewUser();
    	System.out.println(user.getEmail());
    	
    }
    
}