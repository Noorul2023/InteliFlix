/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Intern2018
 */
@Entity
@Table(name = "movies_rented")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MovieRented.findAll", query = "SELECT m FROM MovieRented m")
    , @NamedQuery(name = "MovieRented.findBySerialKey", query = "SELECT m FROM MovieRented m WHERE m.serialKey = :serialKey")
    , @NamedQuery(name = "MovieRented.findByRentedPrice", query = "SELECT m FROM MovieRented m WHERE m.rentedPrice = :rentedPrice")
    , @NamedQuery(name = "MovieRented.findByExpiredDate", query = "SELECT m FROM MovieRented m WHERE m.expiredDate = :expiredDate")})
public class MovieRented extends InteliflixEntity {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Column(name = "rented_price")
    private BigDecimal rentedPrice;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "expired_date")
    @Temporal(TemporalType.DATE)
    private Date expiredDate;
    
    @JoinColumn(name = "movies_key", referencedColumnName = "serial_key")
    @ManyToOne(optional = false)
    private Movie movie;
    
    @JoinColumn(name = "users_key", referencedColumnName = "serial_key")
    @ManyToOne(optional = false)
    private User user;

    public MovieRented() {
    }

    public BigDecimal getRentedPrice() {
        return rentedPrice;
    }

    public void setRentedPrice(BigDecimal rentedPrice) {
        this.rentedPrice = rentedPrice;
    }

    public Date getExpiredDate() {
        return expiredDate;
    }

    public void setExpiredDate(Date expiredDate) {
        this.expiredDate = expiredDate;
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
