/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package session;

import entities.Genre;
import entities.Movie;
import entities.RatingsEnum;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 *
 * @author Intern2018
 */
@Stateless
public class MovieFacade extends AbstractFacade<Movie> {  

    @PersistenceContext(unitName = "InteliFlix-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public MovieFacade() {
        super(Movie.class);
    }
    
    public List<Movie> searchInteliflix(String sTitle, String sActor, Genre sGenre, RatingsEnum sRatings) {
        String qStr = "SELECT m FROM Movie m "
                + "WHERE ";
        boolean bGenre = false;
        boolean bRating = false;
        boolean bActor = false;
        boolean bTitle = false;
        int counter = 0;
        String w = "AND ";
        if (sTitle != null && sTitle.length() != 0){
            qStr = qStr + "m.title LIKE :sTitle ";    
            bTitle = true;
            counter ++;
        }
        if (sActor.length() != 0){
            if (counter > 0){
                qStr = qStr + w + "m.actors LIKE :sActor ";
            }
            else{
                qStr = qStr + "m.actors LIKE :sActor ";
            }
            bActor = true;
            counter ++;
        }
        if (sRatings != null){
            if (counter > 0){
                qStr = qStr + w + "m.ratings = :sRatings ";
            }
            else{
                System.out.println("HITTT");
                qStr = qStr + "m.ratings = :sRatings ";
            }
            bRating = true;
            counter ++;
        }
        //convert the genre name to its corresponding genre_key
       
        if (sGenre != null){
            if (counter > 0){
                qStr = qStr + w + "m.genre = :sGenre ";
            }
            else{
                qStr = qStr + "m.genre = :sGenre ";
            }
            bGenre = true;
            counter ++;
        }
        qStr = qStr.trim();
        sTitle = "%" + sTitle + "%";
        sActor = "%" + sActor + "%";
        if (sTitle == null && sActor == null && sGenre == null && sRatings == null){
            return null;
        }
        TypedQuery<Movie> query = em.createQuery(qStr, Movie.class);

        if (bTitle == true){
            query.setParameter("sTitle", sTitle);
        }
        if (bActor == true){
            query.setParameter("sActor", sActor);
        }
        if (bGenre == true){
            query.setParameter("sGenre", sGenre);
        }
        if (bRating == true){
            query.setParameter("sRatings", sRatings);
        }
        
        
        
        return query.getResultList();

    }
    
    public List<String> searchMovieTitle(String searchQuery){
        List<String> result = new ArrayList<String>();
        for (Movie m : findAll()){
            if (m.getTitle().toLowerCase().contains(searchQuery.toLowerCase())){
                result.add(m.getTitle());
            }
        }
        return result;
    }
    
}
