/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package user;

import entities.User;
import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Formatter;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import session.UserFacade;

/**
 *
 * @author Intern2018
 */

@Named
@ViewScoped
public class RegistrationView  implements Serializable {

    private static final long serialVersionUID = 1;
    
    @EJB
    private UserFacade userFacade;
    
    private User user;
    
    private String email, password, name, address, creditCard, rePassword, passwordHash;

    private boolean billSwitch = false;
    
    
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
    
    public String accountCreation(){
        User temp = new User();
        temp.setEmail(this.getEmail());
        this.encodePassword(this.getPassword());
        temp.setPassword(this.getPasswordHash());
        temp.setName(this.getName());
        temp.setAddress(this.getAddress());
        temp.setCreditCard(this.getCreditCard());
        this.userFacade.create(temp);
        return "/login?faces-redirect=true";
        
    }
    
    public void switchBill(){
        if (!this.getPassword().equalsIgnoreCase(this.getRePassword())){
            mismatchMessage();
            return;
        }
        this.billSwitch = !this.billSwitch;
    }
    
    public void mismatchMessage() {
        FacesContext context = FacesContext.getCurrentInstance();
        
        context.addMessage(null, new FacesMessage("ERROR: Password Mismatch",  "") );
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    /**
     * Sets the hash of this authentication object's password.
     *
     * @see #setPassword(String)
     */
    public void setPasswordHash(String hash) {
        this.passwordHash = hash;
    }
    
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
    
    public boolean isBillSwitch() {
        return billSwitch;
    }

    public void setBillSwitch(boolean billSwitch) {
        this.billSwitch = billSwitch;
    }
    
    public String getRePassword() {
        return rePassword;
    }

    public void setRePassword(String rePassword) {
        this.rePassword = rePassword;
    }
    
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCreditCard() {
        return creditCard;
    }

    public void setCreditCard(String creditCard) {
        this.creditCard = creditCard;
    }
    
    
    
}
