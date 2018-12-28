/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package user;

import com.intelitrac.movie.presentation.LoginBeanFlix;
import entities.MovieRented;
import entities.User;
import java.io.ByteArrayInputStream;
import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Formatter;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseId;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import session.MovieRentedFacade;
import session.UserFacade;

/**
 *
 * @author Intern2018
 */
@Named(value = "accountView")
@ViewScoped
public class AccountView implements Serializable {

    @EJB
    private UserFacade userFacade; 
    
    @EJB
    private MovieRentedFacade movieRentedFacade;

    private User selectedUser;
    private MovieRented movieRented;
    private boolean visibility;
    private String message;
    private List<User> users;
    private String passwordFirstEntry = null;
    private String passwordSecondEntry = null;
    private String passwordHash;
    private StreamedContent graphicText;
    private float totalCost = 0;

    @PostConstruct
    private void init() {
        this.selectedUser = LoginBeanFlix.getUser();
        this.calculateCost();
    }

    public AccountView() {
    }

    public StreamedContent getImage(MovieRented movieRented) {
        if (movieRented.getMovie().getImage() != null) {
            return new DefaultStreamedContent(new ByteArrayInputStream(movieRented.getMovie().getImage()), "image/png");
        }
        return null;

    }

    public void updateInfo() {
        this.selectedUser = LoginBeanFlix.getUser();
        if (passwordCheck()) {
            this.selectedUser.setPassword(this.getPasswordHash());
            this.userFacade.edit(selectedUser);
            setMessage("YOUR INFORMATION HAS BEEN UPDATED");
            outputMessage();
        }
    }

    public boolean passwordCheck() {
        return (passwordSameEntry() && passwordCheckOld()); //Assuming both above cases, this should return true
    }

    public boolean passwordSameEntry() {
        boolean compareEntries = passwordFirstEntry.equals(passwordSecondEntry);
//        if (passwordFirstEntry.equals("")){
//            compareEntries = false;
//            setMessage("ERROR: You must enter a password");
//            outputMessage();
//        }
        if (!compareEntries) {
            setMessage("ERROR: Make sure entered passwords are the same");
            outputMessage();
        }
        return compareEntries;
    }

    public boolean passwordCheckOld() {
        encodePassword(passwordFirstEntry);
        boolean checkOld = (this.passwordHash).equals(this.selectedUser.getPassword());
        checkOld = !checkOld;
        if (!checkOld) {
            setMessage("ERROR: You may not use the same password as before");
            outputMessage();
        }
        return checkOld;
    }

    public void cancelUpdate() {
    }

    public float getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(float totalCost) {
        this.totalCost = totalCost;
    }

    
    
    public User getSelectedUser() {
        return selectedUser;
    }

    public void setSelectedUser() {
        this.selectedUser = LoginBeanFlix.getUser();
    }

    public MovieRented getMovieRented() {
        return movieRented;
    }

    public void setMovieRented(MovieRented movieRented) {

        this.movieRented = movieRented;
        if (this.movieRented != null) {
            if (this.movieRented.getMovie().getImage() != null) {
                graphicText = new DefaultStreamedContent(new ByteArrayInputStream(this.movieRented.getMovie().getImage()), "image/png");
            }
        }
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

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public String getPasswordFirstEntry() {
        return passwordFirstEntry;
    }

    public void setPasswordFirstEntry(String passwordFirstEntry) {
        this.passwordFirstEntry = passwordFirstEntry;
    }

    public String getPasswordSecondEntry() {
        return passwordSecondEntry;
    }

    public void setPasswordSecondEntry(String passwordSecondEntry) {
        this.passwordSecondEntry = passwordSecondEntry;
    }

    public StreamedContent getGraphicText() {
        FacesContext context = FacesContext.getCurrentInstance();

        if (context.getCurrentPhaseId() == PhaseId.RENDER_RESPONSE) {
            // So, we're rendering the HTML. Return a stub StreamedContent so that it will generate right URL.
            return new DefaultStreamedContent();
        }
        else {
            // So, browser is requesting the image. Return a real StreamedContent with the image bytes.
            String movieRentedId = context.getExternalContext().getRequestParameterMap().get("id");
            MovieRented rented= movieRentedFacade.find(movieRentedId);
            if(rented.getMovie().getImage() != null){
            return new DefaultStreamedContent(new ByteArrayInputStream(rented.getMovie().getImage()));
            }
        }
        return new DefaultStreamedContent();
    }

    public void setGraphicText(StreamedContent graphicText) {
        this.graphicText = graphicText;
    }

    public void outputMessage() {
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage(message));
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String hash) {
        this.passwordHash = hash;
    }

    public void encodePassword(String password) {
        try {
            byte[] digest = MessageDigest.getInstance("SHA-256").digest(
                    password.getBytes(StandardCharsets.UTF_8));

            Formatter formatter = new Formatter();
            for (byte b : digest) {
                formatter.format("%02x", b);
            }
            setPasswordHash(formatter.toString());
        } catch (NoSuchAlgorithmException e) {
            // We will never get here, because every Java implementation
            // is required to support SHA-256.  See contract of MessageDigest
            // class for details.
            throw new RuntimeException(e);
        }
    }
    
    public void calculateCost (){
        float tempCost = 0;
        if (this.getSelectedUser().getMovieRented() == null){
            return;
        }
        
        for (int i = 0; i < this.getSelectedUser().getMovieRented().size(); i++){
            tempCost += this.getSelectedUser().getMovieRented().get(i).getRentedPrice().floatValue();
        }
        this.setTotalCost(tempCost);
    }

}
