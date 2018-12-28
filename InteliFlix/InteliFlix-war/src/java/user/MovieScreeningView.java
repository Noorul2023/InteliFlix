/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package user;

import java.io.Serializable;
import javax.inject.Named;
import javax.faces.view.ViewScoped;

/**
 *
 * @author Intern2018
 */
@Named(value = "movieScreeningView")
@ViewScoped
public class MovieScreeningView   implements Serializable {

    private static final long serialVersionUID = 1;

    /**
     * Creates a new instance of MovieScreeningView
     */
    public MovieScreeningView() {
    }
    
}
