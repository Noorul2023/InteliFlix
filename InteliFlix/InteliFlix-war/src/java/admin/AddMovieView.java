/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package admin;

import entities.Genre;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import entities.Movie;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import org.primefaces.model.UploadedFile;
import session.GenreFacade;
import session.MovieFacade;

/**
 *
 * @author Intern2018
 */
@Named(value = "addMovieView")
@ViewScoped
public class AddMovieView implements Serializable {

    @EJB
    private MovieFacade movieFacade;
    @EJB
    private GenreFacade genreFacade;

    private Movie movie;
    private Genre genre;
    private String rating;
    private boolean visibility;
    private String message;
    private UploadedFile uploadFile;
    
    private List<Movie> movies;

    @PostConstruct
    private void init() {
        this.movie = new Movie();
        this.genre = new Genre();
        movies = movieFacade.findAll();
        genrefindAll();
    }

    public AddMovieView() {
    }

    //      Function - Add Movie and Add Genre
    public void addMovie() {
        handleFileUpload();
        this.movieFacade.create(movie);
        this.movie = new Movie();
    }

    public void addGenre() {
        this.genreFacade.create(genre);
        this.genre = new Genre();
    }

    public void handleFileUpload() {
        String fileName = uploadFile.getFileName();
        String contentType = uploadFile.getContentType();
        byte[] contents = uploadFile.getContents();
        movie.setImage(contents);
    }

    //      On Screen Message Methods
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void saveMessage() {
        FacesContext context = FacesContext.getCurrentInstance();

        context.addMessage(null, new FacesMessage("Successful", "Your message: " + message));
        context.addMessage(null, new FacesMessage("Second Message", "Additional Message Detail"));
    }

    public void outputMessage() {
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage("Successful", "Your message: " + message));
    }

    /*//////////////////////////////////////////////////////////////////////////////
                       Getters and Setters                                                                                            
     *///////////////////////////////////////////////////////////////////////////////
    //      Getters and Setters - Movie
    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public List<Movie> moviefindAll() {
        return this.movieFacade.findAll();
    }

    //      Getters and Setters - Genre
    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    public List<Genre> genrefindAll() {
        return this.genreFacade.findAll();
    }

    //      Getters and Setters - Visibilitiy
    public boolean isVisibility() {
        return visibility;
    }

    public void setVisibility(boolean visibility) {
        this.visibility = visibility;
    }

    //      Getters and Setters - Rating
    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    //      Getters and Setters - UploadFile
    public UploadedFile getUploadFile() {
        return uploadFile;
    }

    public void setUploadFile(UploadedFile uploadFile) {
        this.uploadFile = uploadFile;
    }

    public List<Movie> getMovies() {
        return movies;
    }

    public void setMovies(List<Movie> movies) {
        this.movies = movies;
    }
    
    

}
