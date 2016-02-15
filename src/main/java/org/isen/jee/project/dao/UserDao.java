package org.isen.jee.project.dao;

import java.util.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.isen.jee.project.model.User;


public class UserDao {  
    EntityManagerFactory emf = Persistence.createEntityManagerFactory("passwanager");
    EntityManager em = emf.createEntityManager( );
    
    public User createNewUser() {

        User user = new User();
        try {
        	em.getTransaction().begin();
        	em.persist(user);
        	em.getTransaction().commit();

        } catch (SecurityException | IllegalStateException e) {
            return null;
        }
        return user;
    }
    
    public List<User> getAll(){
    	
    	List<User> users = (List<User>) em.createQuery("SELECT u FROM User u").getResultList();

    	return users;
    }

}
