/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package user;

import entities.Movie;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import session.MovieFacade;

/**
 *
 * @author jayala 
 */
@ManagedBean
@SessionScoped
public class MoviePhotoLoader{

    @EJB
    private MovieFacade movieFacade;

    
    public MoviePhotoLoader() {
    }
    
    public Movie getMovie() {
        String keyStr = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("movieKey");
        if (keyStr == null || keyStr.isEmpty())  {
            return null;
        }
        try {
            Integer movieKey = Integer.parseInt(keyStr);
            return movieFacade.find(movieKey);

        } catch (NumberFormatException e) {
            return null;
        }
    }

    public StreamedContent getImage() {

        Movie movie = getMovie();

        if (movie == null || movie.getImage() == null) {
            return getDefaultFrontPhoto();
        }

        InputStream is = new ByteArrayInputStream(movie.getImage());
        return new DefaultStreamedContent(is);
    }

    public StreamedContent getDefaultFrontPhoto() {
        FacesContext fc = FacesContext.getCurrentInstance();
        InputStream stream = fc.getExternalContext().getResourceAsStream(
                "/resources/images/NA.jpg");
        return new DefaultStreamedContent(stream, "image/png");
    }

 
}