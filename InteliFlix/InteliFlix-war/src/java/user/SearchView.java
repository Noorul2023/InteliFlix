/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package user;

import com.intelitrac.movie.presentation.SelectItemComparator;
import entities.Genre;
import entities.Movie;
import entities.RatingsEnum;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import session.GenreFacade;
import session.MovieFacade;

/**
 *
 * @author Intern2018
 */
@Named
@ViewScoped
public class SearchView implements Serializable {

    private static final long serialVersionUID = 1;

    @EJB
    private MovieFacade movieFacade;
    @EJB
    private GenreFacade genreFacade;

    private boolean viewResults = false;
    private boolean viewFeatured = true;
    private boolean viewSearchButton = true; // What visiblity is shown
    private String ratingSearch, genreSearch, titleSearch, actorSearch;
    private RatingsEnum ratingSelection = RatingsEnum.PG13;

    private List<Movie> movieList;
    private List<Movie> allMovies;
    private List<Movie> featuredMoviesList;

    private Genre genre;
    private final Comparator<SelectItem> byLabel = new SelectItemComparator();

    @PostConstruct
    public void init() {
        movieList = movieFacade.findAll();
        allMovies = movieFacade.findAll();
        featuredMoviesList = new ArrayList<>();
        this.getFeaturedMoviesList().add(movieFacade.find(5));
        this.getFeaturedMoviesList().add(movieFacade.find(31));
        this.getFeaturedMoviesList().add(movieFacade.find(7));
        this.getFeaturedMoviesList().add(movieFacade.find(12));
        this.getFeaturedMoviesList().add(movieFacade.find(10));
        this.getFeaturedMoviesList().add(movieFacade.find(16));
        
        
        
        viewSearchButton = true; // New Search-True, Search-False
        viewResults = false; // Search Results
        viewFeatured = true; // Featured Films
    }

    //CONSTRUCTOR
    public SearchView() {

    }

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    public List<Movie> getFeaturedMoviesList() {
        return featuredMoviesList;
    }

    public void setFeaturedMoviesList(List<Movie> featuredMoviesList) {
        this.featuredMoviesList = featuredMoviesList;
    }

    private void fillFeatures() {
        for (int i = 0; i < 6; i++) {
            this.getFeaturedMoviesList().add(allMovies.get(i));
        }
    }

    public void searchIF() {
        System.out.println("Run: " + genre);
//        System.out.println(g.getName());

        if (ratingSearch == null) {
            ratingSelection = null;
        } else {

            switch (ratingSearch) {
                case "G":
                    ratingSelection = RatingsEnum.G;
                    break;
                case "PG":
                    ratingSelection = RatingsEnum.PG;
                    break;
                case "PG13":
                    ratingSelection = RatingsEnum.PG13;
                    break;
                case "R":
                    ratingSelection = RatingsEnum.R;
                    break;
                case "NR":
                    ratingSelection = RatingsEnum.NR;
                    break;
            }
        }
        System.out.println("RATING IS " + ratingSearch);

        movieList = movieFacade.searchInteliflix(titleSearch, actorSearch, genre, ratingSelection);

        viewSearchButton = true;
        viewResults = true;
        viewFeatured = false; // Featured Film
    }

    public List<String> completeText(String searchQuery) {
        return movieFacade.searchMovieTitle(searchQuery);
    }

    public void showSearchFields() {
        viewSearchButton = !viewSearchButton; // New Search-True, Search-False
        
        if(!viewFeatured){
            viewResults = true;
        }// Search Results
    }

    public List<Movie> getMovieList() {
        return movieList;
    }

    public void setMovieList(List<Movie> movieList) {
        this.movieList = movieList;
    }

    public String getRatingSearch() {
        return ratingSearch;
    }

    public void setRatingSearch(String ratingSearch) {
        this.ratingSearch = ratingSearch;
    }

    public String getGenreSearch() {
        return genreSearch;
    }

    public void setGenreSearch(String genreSearch) {
        this.genreSearch = genreSearch;
    }

    public String getTitleSearch() {
        return titleSearch;
    }

    public void setTitleSearch(String titleSearch) {
        this.titleSearch = titleSearch;
    }

    public String getActorSearch() {
        return actorSearch;
    }

    public void setActorSearch(String actorSearch) {
        this.actorSearch = actorSearch;
    }

    public boolean isViewSearchButton() {
        return viewSearchButton;
    }

    public void setViewSearchButton(boolean viewSearchButton) {
        this.viewSearchButton = viewSearchButton;
    }

    public boolean isViewResults() {
        return viewResults;
    }

    public void setViewResults(boolean viewResults) {
        this.viewResults = viewResults;
    }

    public List<Movie> getAllMovies() {
        return allMovies;
    }

    public boolean isViewFeatured() {
        return viewFeatured;
    }

    public void setViewFeatured(boolean viewFeatured) {
        this.viewFeatured = viewFeatured;
    }

    public void setAllMovies(List<Movie> allMovies) {
        this.allMovies = allMovies;
    }

    public List<SelectItem> getGenres() {

        List<SelectItem> retval = new ArrayList<>();

        for (Genre e : genreFacade.findAll()) {
            retval.add(new SelectItem(e, e.getName()));
        }

        Collections.sort(retval, byLabel);
        return retval;
    }
    
    

    public String gotoMovie(int key) {
        writeToSession("movie_key", key);
        return "streaming.xhtml?faces-redirect=true";
    }

    private void writeToSession(String label, Object value) {
        FacesContext context = FacesContext.getCurrentInstance();
        final HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
        final HttpSession session = request.getSession(true);
        session.setAttribute(label, value);
    }

}
