package org.isen.jee.project.dao;

import java.util.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
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
        	em.flush(); // not to id 0
        	em.refresh(user);
    		em.getTransaction().commit();
    		
    	} catch (SecurityException | IllegalStateException e) {
    		return null;
    	}
    	return user;
    }
    
    public User createNewUser(String email, String firstname, String lastname, String username, String password) {

        User user = new User();
        try {
        	user.setEmail(email);
        	user.setFirstname(firstname);
        	user.setLastname(lastname);
        	user.setUsername(username);
        	user.setPassword(password);
        	System.out.println("user id: " + user.getId());
        	em.getTransaction().begin();
        	em.persist(user);
        	em.flush(); // not to id 0
        	em.refresh(user);
        	em.getTransaction().commit();

        } catch (SecurityException | IllegalStateException e) {
            return null;
        }
        return user;
    }
    
    public User findByEmail(String email){
    	try{
    		User foundUser = (User) em.createQuery("SELECT u FROM User u WHERE u.email = :email")
    			.setParameter("email", email).getSingleResult();
    		return foundUser;
    	} catch (SecurityException | IllegalStateException | NoResultException e) {
            return null;
        }
    }
    
    public User signin(String email, String password){
    	try{
    		User foundUser = (User) em.createQuery("SELECT u FROM User u WHERE u.email = :email AND u.password = :password")
    			.setParameter("email", email)
    			.setParameter("password", password).getSingleResult();
    		return foundUser;
    	} catch (SecurityException | IllegalStateException | NoResultException e) {
            return null;
        }
    }
    
    public List<User> getAll(){
    	
    	List<User> users = (List<User>) em.createQuery("SELECT u FROM User u").getResultList();

    	return users;
    }

}
