package org.isen.jee.project.dao;

import java.util.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;

import org.isen.jee.project.model.Folder;
import org.isen.jee.project.model.User;


public class FolderDao {  
    EntityManagerFactory emf = Persistence.createEntityManagerFactory("passwanager");
    EntityManager em = emf.createEntityManager( );
    
    public Folder createNewFolder() {
    	
    	Folder folder = new Folder();
    	try {
    		em.getTransaction().begin();
        	em.persist(folder);
            em.flush();
            em.refresh(folder); // To get the id
    		em.getTransaction().commit();
    		
    	} catch (SecurityException | IllegalStateException e) {
    		return null;
    	}
    	return folder;
    }
    
    public Folder createNewFolder(String name, User owner, List<User> users){
    	Folder folder = new Folder();
    	try {
    		folder.setName(name);
    		folder.setOwner(owner);
    		Date now = new Date();
    		folder.setCreatedAt(now);
    		users.add(owner);
    		folder.setUsers(users);
    		List<Folder> ownerFolders = owner.getFolders();
    		if(ownerFolders == null){
    			ownerFolders = new ArrayList<Folder>();
    		}
    		ownerFolders.add(folder);
    		owner.setFolders(ownerFolders);
    		
    		em.getTransaction().begin();
    		em.persist(folder);
            em.flush();
            em.refresh(folder); // To get the id
    		em.merge(owner);
    		em.getTransaction().commit();
    		
    	} catch (SecurityException | IllegalStateException e) {
    		return null;
    	}
    	return folder;
    }
    
    public Folder createNewFolder(String name, User owner){
        Folder folder = new Folder();
        try {
        	folder.setName(name);
        	folder.setOwner(owner);
        	Date now = new Date();
        	folder.setCreatedAt(now);
        	List<User> users = new ArrayList<>();
        	users.add(owner);
        	folder.setUsers(users);
        	List<Folder> ownerFolders = owner.getFolders();
        	if(ownerFolders == null){
        		ownerFolders = new ArrayList<Folder>();
        	}
        	ownerFolders.add(folder);
        	owner.setFolders(ownerFolders);
        	
        	em.getTransaction().begin();
        	em.persist(folder);
            em.flush();
            em.refresh(folder); // To get the id
        	em.merge(owner);
        	em.getTransaction().commit();

        } catch (SecurityException | IllegalStateException e) {
            return null;
        }
        return folder;
    }
    
    public Folder findById(int id){
    	try{
    		Folder foundFolder = (Folder) em.createQuery("SELECT f FROM Folder f WHERE f.id= :id")
    			.setParameter("id", id).getSingleResult();
    		return foundFolder;
    	} catch (SecurityException | IllegalStateException | NoResultException e) {
            return null;
        }
    }
    
    public Boolean delete(int id){
    	boolean done = false;
    	try{
    		em.getTransaction().begin();
    		Folder foundFolder = (Folder) em.createQuery("SELECT f FROM Folder f WHERE f.id = :id")
    			.setParameter("id", id).getSingleResult();
    		if(foundFolder != null){
    			em.remove(foundFolder);
    			done = true;
    		}
    		em.getTransaction().commit();
    	}  catch (SecurityException | IllegalStateException | NoResultException e) {
            return done;
        }
    	return done;
    }
    
    public List<Folder> getAll(){
    	List<Folder> Folders = (List<Folder>) em.createQuery("SELECT f FROM Folder f").getResultList();
    	return Folders;
    }

}
