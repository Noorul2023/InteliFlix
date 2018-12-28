/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Intern2018
 */
@Entity
@Table(name = "users")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "User.findAll", query = "SELECT u FROM User u")
    , @NamedQuery(name = "User.findBySerialKey", query = "SELECT u FROM User u WHERE u.serialKey = :serialKey")
    , @NamedQuery(name = "User.findByName", query = "SELECT u FROM User u WHERE u.name = :name")
    , @NamedQuery(name = "User.findByEMail", query = "SELECT u FROM User u WHERE u.email = :email")
    , @NamedQuery(name = "User.findByPassword", query = "SELECT u FROM User u WHERE u.password = :password")
    , @NamedQuery(name = "User.findByCreditCard", query = "SELECT u FROM User u WHERE u.creditCard = :creditCard")
    , @NamedQuery(name = "User.findByAdminUser", query = "SELECT u FROM User u WHERE u.isAdmin = :adminUser")})
public class User extends InteliflixEntity {

    private static final long serialVersionUID = 1L;
    @Basic(optional = false)
    @NotNull
    @Column(name = "name")
    private String name;
    
    // @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message="Invalid email")//if the field contains email address consider using this annotation to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Column(name = "email")
    private String email;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "password")
    private String password;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "address")
    private String address;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "credit_card")
    private String creditCard;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "admin_user")
    private boolean isAdmin;
    
    @ManyToMany(mappedBy = "users")
    private List<Movie> movies;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user") 
    private List<MovieRented> moviesRented;

    public User() {
    }

    public User(Integer serialKey) {
        this.serialKey = serialKey;
    }

    public User(Integer serialKey, String name, String email, String password, String address, String creditCard, boolean admin) {
        this.serialKey = serialKey;
        this.name = name;
        this.email = email;
        this.password = password;
        this.address = address;
        this.creditCard = creditCard;
        this.isAdmin = admin;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public boolean getAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        this.isAdmin = admin;
    }

    public List<Movie> getMovies() {
        return movies;
    }

    public void setMovie(List<Movie> movies) {
        this.movies = movies;
    }

    public List<MovieRented> getMovieRented() {
        return moviesRented;
    }

    public void setMovieRented(List<MovieRented> movieRented) {
        this.moviesRented = movieRented;
    }
}
