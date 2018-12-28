package template;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import entities.User;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import session.UserFacade;

/**
 *
 * @author Intern2018
 */
@Named(value = "inteliflixTemplateView")
@ViewScoped
public class InteliflixTemplateView  implements Serializable {

    private static final long serialVersionUID = 1;

    @EJB
    private UserFacade userFacade;

    private User user;
    private User selectedUser;
    private List<User> users;
    private boolean visibility;
    private String message;

    @PostConstruct
    private void init() {
        this.user = new User();
        visibility = false;

        setMessage("User History");
        outputMessage();
    }

    /**
     * Creates a new instance of InteliFlixTemplateView
     */
    public InteliflixTemplateView() {
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getSelectedUser() {
        return selectedUser;
    }

    public void setSelectedUser(User selectedUser) {
        this.selectedUser = selectedUser;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
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

}
