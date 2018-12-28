/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package session;

import entities.User;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;


/**
 *
 * @author Intern2018
 */
@Stateless
public class UserFacade extends AbstractFacade<User> {

    @PersistenceContext(unitName = "InteliFlix-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public UserFacade() {
        super(User.class);
    }
    
    public User findByEmail(String eMail) {
        try {
            return em.createNamedQuery("User.findByEMail", User.class).setParameter("email", eMail).getSingleResult();
        } catch (NoResultException ex) {
            //log.log(Level.INFO, "No user found for: {0}", eMail);
            return null;
        } catch (NonUniqueResultException ex) {
            // CAN'T GET HERE, THERE IS A UNIQUE INDEX ON THE TABLE!
            //log.log(Level.INFO, "Non Unique users found for: {0}", eMail);
            return null;
        }
    }
    
}
