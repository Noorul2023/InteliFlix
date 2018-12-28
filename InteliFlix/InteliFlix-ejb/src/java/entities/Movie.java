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
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
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
@Table(name = "movies")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Movie.findAll", query = "SELECT m FROM Movie m")
    , @NamedQuery(name = "Movie.findBySerialKey", query = "SELECT m FROM Movie m WHERE m.serialKey = :serialKey")
    , @NamedQuery(name = "Movie.findByTitle", query = "SELECT m FROM Movie m WHERE m.title = :title")
    , @NamedQuery(name = "Movie.findByActors", query = "SELECT m FROM Movie m WHERE m.actors = :actors")
    , @NamedQuery(name = "Movie.findByRatings", query = "SELECT m FROM Movie m WHERE m.ratings = :ratings")})
public class Movie extends InteliflixEntity {

    private static final long serialVersionUID = 1L;
    @Basic(optional = false)
    @Column(name = "title")
    private String title;

    @Column(name = "image")
    private byte[] image;
    
    @Basic(optional = false)
    @Column(name = "actors")
    private String actors;
    
    @Basic(optional = false)
    @Enumerated(EnumType.STRING)
    @Column(name = "ratings")
    private RatingsEnum ratings;

    @Lob
    @Column(name = "description")
    private String description;
    
    @JoinTable(name = "favorites", joinColumns = {
        @JoinColumn(name = "movies_key", referencedColumnName = "serial_key")}, inverseJoinColumns = {
        @JoinColumn(name = "users_key", referencedColumnName = "serial_key")})
    @ManyToMany
    private List<User> users;
    
    @JoinColumn(name = "genre_key", referencedColumnName = "serial_key")
    @ManyToOne(optional = false)
    private Genre genre;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "movie")
    private List<MovieRented> moviesRented;

    public Movie() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getActors() {
        return actors;
    }

    public void setActors(String actor) {
        this.actors = actor;
    }

    public RatingsEnum getRatings() {
        return ratings;
    }

    public void setRatings(RatingsEnum ratings) {
        this.ratings = ratings;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<User> getUser() { 
        return users;
    }

    public void setUser(List<User> user) {
        this.users = user;
    }

    public Genre getGenre() { 
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    public List<MovieRented> getMovieRented() {
        return moviesRented;
    }

    public void setMovieRented(List<MovieRented> moviesRented) {
        this.moviesRented = moviesRented;
    }
}
