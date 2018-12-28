/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 *
 * @author Intern2018
 */
@MappedSuperclass
public abstract class InteliflixEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "serial_key")
    protected Integer serialKey;
    

    public Integer getSerialKey() {
        return serialKey;
    }

    public void setSerialKey(Integer serialKey) {
        this.serialKey = serialKey;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(serialKey);
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof InteliflixEntity)) {
            return false;
        }

        if (!Objects.equals(getEntityClass(this), getEntityClass(object))) {
            return false;
        }

        InteliflixEntity other = (InteliflixEntity) object;
        return Objects.equals(this.serialKey, other.serialKey);
    }

    private static Class<?> getEntityClass(Object obj) {
        Class<?> cls = obj.getClass();
        while (cls != null && !cls.isAnnotationPresent(Entity.class)) {
            cls = cls.getSuperclass();
        }
        return cls;
    }

    protected boolean isNullOrEmpty(String value) {
        return value == null || value.length() == 0;
    }

    @Override
    public String toString() {
        return this.getClass().getName() + "[ serialKey=" + serialKey + " ]";
    }

}
