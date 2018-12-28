/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package user;

import com.intelitrac.movie.presentation.LoginBeanFlix;
import entities.Genre;
import entities.Movie;
import entities.MovieRented;
import entities.RatingsEnum;
import entities.User;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Intern2018
 */
@Named
@ViewScoped
public class RentalsView implements Serializable {

    private static final long serialVersionUID = 1;

    private Movie movie;
    private String title, actors;//, description;
    private RatingsEnum rating;
    private Genre genre;
    private User user;

    private List<Movie> movieList = new ArrayList<>();

    private List<MovieRented> movieRentedList;

    @PostConstruct
    private void init() {
        User loggedUser = LoginBeanFlix.getUser();
        //this.setUser(loggedUser);

        //dNow.setHours(0);
        this.setUser(LoginBeanFlix.getUser());
        //List<Movie> tempMovieList;
        dateCheck();
//        for (MovieRented r : this.getMovieRentedList()) {
//            getMovieList().add(r.getMovie());
//        }

    }

    public void dateCheck() {
        Date dNow = new Date();
        List<MovieRented> tempML = this.getUser().getMovieRented();
        List<MovieRented> tempML2 = new ArrayList<>();
        for (int i = 0; i < tempML.size(); i++) {
            if (dNow.before(tempML.get(i).getExpiredDate())) {
                tempML2.add(tempML.get(i));
                //this.getMovieList().add(this.getMovieRentedList().get(i).getMovie());
            }
        }
        this.setMovieRentedList(tempML2);

    }

    public String gotoMovie(int key) {
        writeToSession("movie_key", key);
        return "streaming.xhtml?faces-redirect=true";
    }

    private void writeToSession(String label, Object value) {
        FacesContext context = FacesContext.getCurrentInstance();
        final HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
        final HttpSession session = request.getSession(true);
        session.setAttribute(label, value);
    }

    public List<MovieRented> getMovieRentedList() {
        return movieRentedList;
    }

    public void setMovieRentedList(List<MovieRented> movieRentedList) {
        this.movieRentedList = movieRentedList;
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getActors() {
        return actors;
    }

    public void setActors(String actors) {
        this.actors = actors;
    }

    public RatingsEnum getRating() {
        return rating;
    }

    public void setRating(RatingsEnum rating) {
        this.rating = rating;
    }

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Movie> getMovieList() {
        return movieList;
    }

    public void setMovieList(List<Movie> movieList) {
        this.movieList = movieList;
    }

}
