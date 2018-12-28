/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.math.BigDecimal;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Intern2018
 */
@Entity
@Table(name = "inteliflix_options")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "InteliflixOptions.findAll", query = "SELECT i FROM InteliflixOptions i")
    , @NamedQuery(name = "InteliflixOptions.findBySerialKey", query = "SELECT i FROM InteliflixOptions i WHERE i.serialKey = :serialKey")
    , @NamedQuery(name = "InteliflixOptions.findByCost", query = "SELECT i FROM InteliflixOptions i WHERE i.cost = :cost")})
public class InteliflixOptions extends InteliflixEntity {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @Column(name = "cost")
    private BigDecimal cost;

    public InteliflixOptions() {
    }

    public BigDecimal getCost() {
        return cost;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }
}
