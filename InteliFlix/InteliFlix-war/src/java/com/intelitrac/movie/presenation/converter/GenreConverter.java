/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.intelitrac.movie.presenation.converter;

import entities.Genre;
import javax.ejb.EJBException;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author Intern2018
 */
@FacesConverter("genreConverter")
public class GenreConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        if (value == null || value.isEmpty()) {
            return null;
        }
        
        try {
            System.out.println("value is: " + value);
            if (value.equalsIgnoreCase("Genres")){
                return null;
            }
            return EntityFinder.getInstance(context).find(
                Integer.valueOf(value), Genre.class);
        } catch (EJBException ex) {
            throw new ConverterException(ex);
        }     
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (value == null) {
            return null;
        }
        if (value instanceof Genre) {
            return ((Genre) value).getSerialKey().toString();
        }
        throw new ConverterException("Not a " + Genre.class.getName() + ": " + value);
    }

}
