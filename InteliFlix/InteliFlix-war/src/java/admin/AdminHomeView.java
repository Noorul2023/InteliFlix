/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package admin;

import entities.InteliflixOptions;
import session.InteliflixOptionsFacade;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.ejb.EJB;
import java.io.Serializable;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

/**
 *
 * @author Intern2018
 */
@Named(value = "adminHomeView")
@ViewScoped
public class AdminHomeView  implements Serializable {

    private static final long serialVersionUID = 1;

    @EJB
    private InteliflixOptionsFacade inteliflixOptionsFacade;
    
    
    private InteliflixOptions options;
    private boolean visibility;
    private String message;

//      AdminHomeView Components
    public AdminHomeView() {
    }

    public InteliflixOptions getOptions() {
        return options;
    }

    public void setOptions() {
        setMessage("You are on the Admin Page");
        this.options = this.inteliflixOptionsFacade.find(1);

        setVisibility(true);
    }

    public List<InteliflixOptions> findAll() {
        return this.inteliflixOptionsFacade.findAll();
    }

    //    Visibilty Options 
    public boolean isVisibility() {
        return visibility;
    }

    public void setVisibility(boolean visibility) {
        this.visibility = visibility;
    }
 
    //      Main Functions
    public void updatePricing() {
        this.inteliflixOptionsFacade.edit(options);
        setVisibility(false);
    }
    
    public void cancelUpdate() {
        setVisibility(false);
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

}
