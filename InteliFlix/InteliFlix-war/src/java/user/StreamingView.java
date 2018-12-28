/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package user;

import com.intelitrac.movie.presentation.LoginBeanFlix;
import entities.InteliflixOptions;
import entities.Movie;
import entities.MovieRented;
import entities.User;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import session.InteliflixOptionsFacade;
import session.MovieFacade;
import session.MovieRentedFacade;
import session.UserFacade;

/**
 *
 * @author Intern2018
 */
@Named
@ViewScoped
public class StreamingView implements Serializable {

    private static final long serialVersionUID = 1;
    @EJB
    private MovieFacade movieFacade;

    @EJB
    private UserFacade userFacade;

    @EJB
    private MovieRentedFacade movieRentedFacade;

    @EJB
    private InteliflixOptionsFacade inteliflixOptionsFacade;

    //use @EJB everytime you use a Facade
    private Movie selectedMovie;
    private MovieRented movieRented;
    private Movie currentMovie;
    private User currentUser;
    private InteliflixOptions inteliflixOptions;
    boolean favoriteSwitch = false;
    boolean rentAvailablity = true;
    boolean movieAvailable = false;
    int key = 0;

    @PostConstruct
    private void init() {
        Movie temp = getMovieFromSession();

        //log.log(Level.INFO, "init: opportunity->{0}", movie);
        if (temp == null) {
            try {
                FacesContext.getCurrentInstance().getExternalContext().redirect("/faces/loginError.xhtml?faces-redirect=true");
                return;
            } catch (IOException ex) {
                Logger.getLogger(StreamingView.class.getName()).log(Level.SEVERE, null, ex);
                return;
            }
        } else {
            setSelectedMovie(temp);
            key = temp.getSerialKey();
            writeToSession("movie_key", key);
        }
        movieAvailable = false;

        User loggedUser = LoginBeanFlix.getUser();

//        rentAvailablity = checkPlay();
        if (selectedMovie.getUser() == null) {
            return;
        }

        for (int i = 0; i < selectedMovie.getUser().size(); i++) {
            if (selectedMovie.getUser().get(i).equals(loggedUser)) {
                favoriteSwitch = !favoriteSwitch;
                return;
            }
        }

        //allSteps = pipelineStepFacade.findAll();
        //activeSteps = pipelineStepFacade.findAllActiveForPipeline(pipelineType);
    }

    public boolean checkPlay() {
        currentMovie = selectedMovie;
        currentUser = LoginBeanFlix.getUser();
//        return currentUser.getMovieRented().stream().anyMatch(mr -> mr.getMovie().equals(currentMovie));
        for (MovieRented mr : currentUser.getMovieRented()) {
            if (mr.getMovie().equals(currentMovie)) {
                return dateCheck(mr);
            }
        }
        return false;
    }

    public void showMovie() {
        movieAvailable = true;
    }

    public boolean dateCheck(MovieRented activeMovie) {
        Date currentDate = new Date();

        if (currentDate.before(activeMovie.getExpiredDate())) {
            return true;
        } else {
            return false;
        }
    }

    private Movie getMovieFromSession() {

        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest servletRequest = (HttpServletRequest) context.getExternalContext().getRequest();

        final HttpSession session = servletRequest.getSession(true);
        Integer serialKey = (Integer) session.getAttribute("movie_key");
        if (serialKey != null) {
            /**
             * Make sure to remove attribute after it is read *
             */
            session.removeAttribute("movie_key");
            return movieFacade.find(serialKey);
        }
        return null;
    }

    public boolean isFavoriteSwitch() {
        return favoriteSwitch;
    }

    public void setFavoriteSwitch(boolean favoriteSwitch) {
        this.favoriteSwitch = favoriteSwitch;
    }

    public boolean isRentAvailablity() {
        return rentAvailablity;
    }

    public void setRentAvailablity(boolean rentAvailablity) {
        this.rentAvailablity = rentAvailablity;
    }

    public InteliflixOptions getInteliflixOptions() {
        return inteliflixOptions;
    }

    public void setInteliflixOptions(InteliflixOptions inteliflixOptions) {
        this.inteliflixOptions = inteliflixOptions;
    }

    public Movie getSelectedMovie() {
        return selectedMovie;
    }

    public void setSelectedMovie(Movie selectedMovie) {
        this.selectedMovie = selectedMovie;
    }

    public MovieRented getMovieRented() {
        return movieRented;
    }

    public void setMovieRented(MovieRented movieRented) {
        this.movieRented = movieRented;
    }

    private void writeToSession(String label, Object value) {
        FacesContext context = FacesContext.getCurrentInstance();
        final HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
        final HttpSession session = request.getSession(true);
        session.setAttribute(label, value);
    }

    public void rent() {

        User loggedUser = LoginBeanFlix.getUser();
        inteliflixOptions = inteliflixOptionsFacade.find(1);
        //check if movie is already rented
        Date dNow = new Date();
        MovieRented tempMR = new MovieRented();

        tempMR.setMovie(selectedMovie);
        if (loggedUser.getMovieRented() != null) {
            boolean checkedOut = true;
            for (int i = 0; i < loggedUser.getMovieRented().size(); i++) {
                //check if the movie is the same
                //if it is then check if the date is after today's date
                if (loggedUser.getMovieRented().get(i).getMovie().equals(selectedMovie)) {
                    if (loggedUser.getMovieRented().get(i).getExpiredDate().after(dNow)) {
                        ownedMessage();
                        return;
                    }
                }
            }
        }

        tempMR.setUser(loggedUser);
        //calculate the date a week from now
        Date dWeek = addDays(dNow, 7);
        tempMR.setExpiredDate(dWeek);
        tempMR.setRentedPrice(inteliflixOptions.getCost());
        this.setMovieRented(tempMR);
        this.insertMovieRented();
        // add record to user list. 
        loggedUser.getMovieRented().add(tempMR);

    }

    //in init method determine if the selected movie has been favorited
    //if it has then display the remove button
    //if not then display the favorite button
    public void addFavorite() {
        User loggedUser = LoginBeanFlix.getUser();
        //Movie tempM = new Movie();

        selectedMovie.getUser().add(loggedUser);
        loggedUser.getMovies().add(selectedMovie);
        movieFacade.edit(selectedMovie);

        this.favoriteSwitch = !this.favoriteSwitch;
        favoritedMessage();
    }

    public void removeFavorite() {
        User loggedUser = LoginBeanFlix.getUser();

        for (int i = 0; i < selectedMovie.getUser().size(); i++) {
            if (selectedMovie.getUser().get(i).equals(loggedUser)) {
                selectedMovie.getUser().remove(i);
                loggedUser.getMovies().remove(selectedMovie);
                i = 9999;
            }
        }
        movieFacade.edit(selectedMovie);
        this.favoriteSwitch = !this.favoriteSwitch;
        removeFavoriteMessage();

    }

    public void favoritedMessage() {
        FacesContext context = FacesContext.getCurrentInstance();

        context.addMessage(null, new FacesMessage("Added to Favorites List", ""));
    }

    public void removeFavoriteMessage() {
        FacesContext context = FacesContext.getCurrentInstance();

        context.addMessage(null, new FacesMessage("Removed from Favorites List", ""));
    }

    public void ownedMessage() {
        FacesContext context = FacesContext.getCurrentInstance();

        context.addMessage(null, new FacesMessage("Playing Movie", ""));
    }

    public void insertMovieRented() {
        this.movieRentedFacade.create(movieRented);
        this.movieRented = new MovieRented();
    }

    private static Date addDays(Date date, int days) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, days); //minus number would decrement the days
        return cal.getTime();
    }

    public Movie getCurrentMovie() {
        return currentMovie;
    }

    public void setCurrentMovie(Movie currentMovie) {
        this.currentMovie = currentMovie;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }

    public boolean isMovieAvailable() {
        return movieAvailable;
    }

    public void setMovieAvailable(boolean movieAvailable) {
        this.movieAvailable = movieAvailable;
    }

}
