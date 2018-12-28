/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package session;

import entities.InteliflixOptions;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Intern2018
 */
@Stateless
public class InteliflixOptionsFacade extends AbstractFacade<InteliflixOptions> { 

    @PersistenceContext(unitName = "InteliFlix-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public InteliflixOptionsFacade() {
        super(InteliflixOptions.class);
    }
    
}
