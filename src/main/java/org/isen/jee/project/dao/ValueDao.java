package org.isen.jee.project.dao;

import java.util.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;

import org.isen.jee.project.model.Entry;
import org.isen.jee.project.model.Value;
import org.isen.jee.project.model.User;

public class ValueDao {  
    EntityManagerFactory emf = Persistence.createEntityManagerFactory("passwanager");
    EntityManager em = emf.createEntityManager( );
    
    public Value createNewValue() {
    	
    	Value value = new Value();
    	try {
    		em.getTransaction().begin();
        	em.persist(value);
            em.flush();
            em.refresh(value); // To get the id
    		em.getTransaction().commit();
    		
    	} catch (SecurityException | IllegalStateException e) {
    		return null;
    	}
    	return value;
    }
    
    public Value updateEntry(Value value, Entry entry){
    	value.setEntry(entry);
    	try {
    		em.getTransaction().begin();
    		em.merge(value);
    		em.refresh(value); // To get the id
    		em.getTransaction().commit();
    		
    	} catch (SecurityException | IllegalStateException e) {
    		return null;
    	}
    	return value;
    }
    
    public Value createNewValue(String value, User user){
    	Value newValue = new Value();
    	try {
    		newValue.setUser(user);
    		newValue.setValue(value);
    		
    		em.getTransaction().begin();
    		em.persist(newValue);
    		em.flush();
    		em.merge(user);
    		em.refresh(newValue); // To get the id
    		em.getTransaction().commit();
    		
    	} catch (SecurityException | IllegalStateException e) {
    		return null;
    	}
    	return newValue;
    }
    
    public Value createNewValue(String value, Entry entry, User user){
    	Value newValue = new Value();
    	try {
    		newValue.setEntry(entry);
    		newValue.setUser(user);
    		newValue.setValue(value);
    		
    		em.getTransaction().begin();
    		em.persist(newValue);
    		em.flush();
    		em.merge(entry);
    		em.merge(user);
            em.refresh(newValue); // To get the id
    		em.getTransaction().commit();
    		
    	} catch (SecurityException | IllegalStateException e) {
    		return null;
    	}
    	return newValue;
    }
    
    
    public Value findById(int id){
    	try{
    		Value foundValue = (Value) em.createQuery("SELECT v FROM Value v WHERE v.id= :id")
    			.setParameter("id", id).getSingleResult();
    		return foundValue;
    	} catch (SecurityException | IllegalStateException | NoResultException e) {
            return null;
        }
    }
    
    public Boolean delete(int id){
    	boolean done = false;
    	try{
    		em.getTransaction().begin();
    		Value foundValue = (Value) em.createQuery("SELECT v FROM Value v WHERE v.id = :id")
    			.setParameter("id", id).getSingleResult();
    		if(foundValue != null){
    			em.remove(foundValue);
    			done = true;
    		}
    		em.getTransaction().commit();
    	}  catch (SecurityException | IllegalStateException | NoResultException e) {
            return done;
        }
    	return done;
    }
    
    public List<Value> getAll(){
    	List<Value> Values = (List<Value>) em.createQuery("SELECT v FROM Value v").getResultList();
    	return Values;
    }

}
