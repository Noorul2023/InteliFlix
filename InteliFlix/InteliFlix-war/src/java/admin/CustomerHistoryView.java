/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package admin;

//import entities.InteliflixEntity;
import com.intelitrac.movie.presentation.LoginBeanFlix;
import entities.InteliflixOptions;
import entities.Movie;
import entities.MovieRented;
import entities.User;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.imageio.ImageIO;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import session.InteliflixOptionsFacade;
import session.MovieFacade;
import session.MovieRentedFacade;
import session.UserFacade;

/**
 *
 * @author Intern2018
 */
@Named(value = "customerHistoryView")
@ViewScoped
public class CustomerHistoryView implements Serializable {

    private static final long serialVersionUID = 1;

    @EJB
    private UserFacade userFacade;
    @EJB
    private MovieFacade movieFacade;
    @EJB
    private MovieRentedFacade movieRentedFacade;
    @EJB
    private InteliflixOptionsFacade optionsFacade;

    private User user;
    private User selectedUser;
    private Movie movie;
    private MovieRented movieRented;
    private InteliflixOptions options;
    private boolean visibility;
    private String message;
    private StreamedContent graphicText;

    boolean costSwitch = false;
    
    private List<User> users;
    
    private float totalCost;

    @PostConstruct
    private void init() {

        this.movie = new Movie();
        this.user = new User();
        this.movieRented = new MovieRented();
        this.options = new InteliflixOptions();
        visibility = false;
        //this.calculateCost();
    }
    
    public void calculateCost (){
        //this.selectedUser = LoginBeanFlix.getUser();
        if (this.getSelectedUser().getMovieRented() == null){
            return;
        }
        float tempCost = 0;
        
        for (int i = 0; i < this.getSelectedUser().getMovieRented().size(); i++){
            tempCost += this.getSelectedUser().getMovieRented().get(i).getRentedPrice().floatValue();
        }
        this.setTotalCost(tempCost);
    }

    public CustomerHistoryView() {
    }

    public float getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(float totalCost) {
        this.totalCost = totalCost;
    }

    public boolean isCostSwitch() {
        return costSwitch;
    }

    public void setCostSwitch(boolean costSwitch) {
        this.costSwitch = costSwitch;
    }

    
    
    
    public List<User> getUsers() {
        return this.userFacade.findAll();
    }

    public void viewInfo(User user) {
        this.selectedUser = user;
        calculateCost();
        this.setCostSwitch(true);
        setVisibility(true);
    }

    public void viewCustomers() {
        this.setCostSwitch(false);
        setVisibility(false);
    }

    public void activeCustomer(User user) {
        setMessage("You are on the Admin Page");
        setVisibility(true);
        this.movieRented = this.movieRentedFacade.find(user);
    }

    /*Getters and Setters*/
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public MovieRented getMovieRented() {
        return movieRented;
    }

    public void setMovieRented(MovieRented movieRented) {
        this.movieRented = movieRented;
    }

    public InteliflixOptions getOptions() {
        return options;
    }

    public void setOptions(InteliflixOptions options) {
        this.options = options;
    }

    public boolean isVisibility() {
        return visibility;
    }

    public void setVisibility(boolean visibility) {
        this.visibility = visibility;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void outputMessage() {
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage("Successful", "Your message: " + message));
    }

    public User getSelectedUser() {
        return selectedUser;
    }

    public void setSelectedUser(User selectedUser) {
        this.selectedUser = selectedUser;
    }

    public StreamedContent getGraphicText() {
        return graphicText;
    }

}
