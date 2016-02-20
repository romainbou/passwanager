package org.isen.jee.project.dao;

import java.util.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;

import org.isen.jee.project.model.Folder;
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
    
    public User createNewUser(String email, String firstname, String lastname, String username, String password, String publicKey){
        User user = new User();
        try {
        	user.setEmail(email);
        	user.setFirstname(firstname);
        	user.setLastname(lastname);
        	user.setUsername(username);
        	user.setPassword(password);
        	user.setPublicKey(publicKey);
        	em.getTransaction().begin();
        	em.persist(user);
        	em.getTransaction().commit();

        } catch (SecurityException | IllegalStateException e) {
            return null;
        }
        return user;
    }
    
    public User findById(int id){
    	try{
    		User foundUser = (User) em.createQuery("SELECT u FROM User u WHERE u.id = :id")
    				.setParameter("id", id).getSingleResult();
    		return foundUser;
    	} catch (SecurityException | IllegalStateException | NoResultException e) {
    		return null;
    	}
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
    
    public Boolean delete(String email){
    	boolean done = false;
    	try{
    		em.getTransaction().begin();
    		User foundUser = (User) em.createQuery("SELECT u FROM User u WHERE u.email = :email")
    			.setParameter("email", email).getSingleResult();
    		if(foundUser != null){
    			List<Folder> ownedFolder = foundUser.getOwnedFolders();
    			for (Folder folder : ownedFolder) {
					em.remove(folder);
				}
    			em.remove(foundUser);
    			done = true;
    		}
    		em.getTransaction().commit();
    	}  catch (SecurityException | IllegalStateException | NoResultException e) {
            return done;
        }
    	return done;
    }
    
    public List<User> getAll(){
    	
    	List<User> users = (List<User>) em.createQuery("SELECT u FROM User u").getResultList();

    	return users;
    }

}
