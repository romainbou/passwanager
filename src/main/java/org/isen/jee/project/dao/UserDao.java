package org.isen.jee.project.dao;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceUnit;
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;

import org.isen.jee.project.model.User;


public class UserDao {
	
//    @Inject
//    EntityManager em;
//    @Resource
//    UserTransaction ut;
//	
//    private UserTransaction ut;
//    @PersistenceContext( unitName = "passwanager" )
//    private EntityManager em;
//    
    EntityManagerFactory emf = Persistence.createEntityManagerFactory("passwanager");
//    @PersistenceUnit
//    EntityManagerFactory emf;
    EntityManager em = emf.createEntityManager( );
    
    public User createNewUser() {

        User user = new User();
        try {
        	
//            ut.begin();
//        	em.getTransaction().begin();
//            em.persist(user);
//            em.getTransaction().commit();
//            ut.commit();

//        } catch ( NotSupportedException | SystemException | SecurityException
//        		| IllegalStateException | RollbackException
//        		| HeuristicMixedException | HeuristicRollbackException e) {
//        	return null;
//        }
        } catch (SecurityException | IllegalStateException e) {
            return null;
        }
        return user;
    }

}
