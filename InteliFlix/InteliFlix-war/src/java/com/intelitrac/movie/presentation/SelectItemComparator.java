/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.intelitrac.movie.presentation;

/**
 *
 * @author Intern2018
 */


import java.io.Serializable;
import java.text.Collator;
import java.util.Comparator;
import java.util.Locale;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

/**
 * Compares {@code SelectItem}s using their label strings, allowing them to be
 * sorted lexicographically.
 *
 * @author Craig Pell
 */
public class SelectItemComparator implements Comparator<SelectItem>, Serializable {

    private static final long serialVersionUID = 1;

    /**
     * @serial
     */
    private final Locale locale;

    private transient Collator collator;

    /**
     * Creates a new instance which uses the locale of the current
     * {@code FacesContext} for sorting.
     */
    public SelectItemComparator() {
        this(FacesContext.getCurrentInstance().getViewRoot().getLocale());
    }

    /**
     * Creates a new instance which uses the specified {@code Locale} for
     * sorting.
     *
     * @throws IllegalArgumentException if argument is {@code null}
     */
    public SelectItemComparator(Locale locale) {
        if (locale == null) {
            throw new IllegalArgumentException("Locale cannot be null");
        }
        this.locale = locale;
    }

    @Override
    public int compare(SelectItem item1,
            SelectItem item2) {
        if (collator == null) {
            collator = Collator.getInstance(locale);
        }

        String label1 = item1.getLabel();
        String label2 = item2.getLabel();
        return collator.compare(label1 == null ? "" : label1,
                label2 == null ? "" : label2);
    }
}
