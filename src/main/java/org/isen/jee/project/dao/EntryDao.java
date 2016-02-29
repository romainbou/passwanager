package org.isen.jee.project.dao;

import java.util.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;

import org.isen.jee.project.model.Entry;
import org.isen.jee.project.model.Folder;
import org.isen.jee.project.model.User;
import org.isen.jee.project.model.Value;


public class EntryDao {  
    EntityManagerFactory emf = Persistence.createEntityManagerFactory("passwanager");
    EntityManager em = emf.createEntityManager( );
    
    public Entry createNewEntry() {
    	
    	Entry entry = new Entry();
    	try {
    		em.getTransaction().begin();
        	em.persist(entry);
            em.flush();
            em.refresh(entry); // To get the id
    		em.getTransaction().commit();
    		
    	} catch (SecurityException | IllegalStateException e) {
    		return null;
    	}
    	return entry;
    }
    
    public Entry createNewEntry(String title, String url, String notes, String username, Folder folder, User creator, List<Value> values){
    	Entry entry = new Entry();
    	try {
    		entry.setTitle(title);
    		entry.setUrl(url);
    		entry.setNotes(notes);
    		entry.setUsername(username);
    		entry.setUser(creator);
    		entry.setValues(values);
    		if(folder.getEntries().size() < 1){
    			List<Entry> entries = new ArrayList<Entry>();
    			entries.add(entry);
    			folder.setEntries(entries);
    			entry.setFolder(folder);
    		} else {
    			folder.addEntry(entry);
    		}
    		Date now = new Date();
    		entry.setCreatedAt(now);
    		
    		em.getTransaction().begin();
    		em.persist(entry);
    		em.flush();
    		em.merge(folder); // To get the id
            em.refresh(entry); // To get the id
    		em.getTransaction().commit();
    		
    	} catch (SecurityException | IllegalStateException e) {
    		return null;
    	}
    	return entry;
    }
    
    
    public Entry findById(int id){
    	try{
    		Entry foundEntry = (Entry) em.createQuery("SELECT e FROM Entry e WHERE e.id= :id")
    			.setParameter("id", id).getSingleResult();
    		return foundEntry;
    	} catch (SecurityException | IllegalStateException | NoResultException e) {
            return null;
        }
    }
    
    public Boolean delete(int id){
    	boolean done = false;
    	try{
    		em.getTransaction().begin();
    		Entry foundEntry = (Entry) em.createQuery("SELECT e FROM Entry e WHERE e.id = :id")
    			.setParameter("id", id).getSingleResult();
    		if(foundEntry != null){
    			em.remove(foundEntry);
    			done = true;
    		}
    		em.getTransaction().commit();
    	}  catch (SecurityException | IllegalStateException | NoResultException e) {
            return done;
        }
    	return done;
    }
    
    public List<Entry> getAll(){
    	List<Entry> Entrys = (List<Entry>) em.createQuery("SELECT e FROM Entry e").getResultList();
    	return Entrys;
    }

}
