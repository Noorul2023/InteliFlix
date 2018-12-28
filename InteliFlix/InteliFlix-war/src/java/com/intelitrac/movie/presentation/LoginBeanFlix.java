package com.intelitrac.movie.presentation;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import entities.User;
import java.io.IOException;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.application.ViewHandler;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import session.UserFacade;

/**
 *
 * @author hkhalid
 */
@SessionScoped
@Named
public class LoginBeanFlix implements Serializable {

    private static final Logger log = Logger.getLogger(LoginBeanFlix.class.getName());
    private static final long serialVersionUID = 1;
    private static final String USER_SESSION_KEY = "DemoPortalUser";
    private static final String ADMIN_ROLE = "ADMIN";
    private static final String USER_ROLE = "USER";

    @EJB
    private UserFacade userFacade;
//
//    @EJB
//    private RoleFacade roleFacade;

    private Integer selectedRoleKey;
    private String username;
    private String password;
    private boolean firstLogin;
    private String newPassword1;
    private String newPassword2;
    private boolean changeSuccess;

    @PostConstruct
    private void init() {

        FacesContext context = FacesContext.getCurrentInstance();
        final HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
        final HttpSession session = request.getSession(true);

        if (isUserLoggedIn()) {
            if (isUserAdmin()) {
                redirect("/user/homePage.xhtml?faces-redirect=true");
                //REDIRECT TO ADMIN PAGE
            } else {
                redirect("/user/homePage.xhtml?faces-redirect=true");
            }
        }
    }

    public void redirect(String viewId) {
        try {
            FacesContext context = FacesContext.getCurrentInstance();
            ViewHandler handler = context.getApplication().getViewHandler();
            String redirectUrl = handler.getRedirectURL(context, viewId, null, true);
            context.getExternalContext().redirect(redirectUrl);
        } catch (IOException ex) {
            Logger.getLogger(LoginBeanFlix.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public String goHome() {
        changeSuccess = false;
        return "index.xhtml?faces-redirect=true";
    }

    public static String getUserSessionKey() {
        return USER_SESSION_KEY;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isFirstLogin() {
        return firstLogin;
    }

    public void setFirstLogin(boolean firstLogin) {
        this.firstLogin = firstLogin;
    }

    public String logout() {
        String result = "/login?faces-redirect=true";

        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
        try {
            log.log(Level.FINE, "Logging out");
            request.getSession().invalidate();
            request.logout();
            log.log(Level.FINE, "Logged out.");

        } catch (ServletException e) {
            log.log(Level.SEVERE, "Failed to log out.");
            result = "/loginError?faces-redirect=true";
        }

        return result;
    }

    public String login() {
        try {

            FacesContext context = FacesContext.getCurrentInstance();
            HttpServletRequest servletRequest = (HttpServletRequest) context.getExternalContext().getRequest();

            if (isUserLoggedIn()) {
                servletRequest.getSession().invalidate();
                servletRequest.logout();
            }

            HttpSession session = (HttpSession) context.getExternalContext().getSession(true);
            servletRequest.login(username, password);
            User user = userFacade.findByEmail(servletRequest.getRemoteUser());
            if (user != null) {

                session.setAttribute(USER_SESSION_KEY, user);


                userFacade.edit(user);
                username = null;
                password = null;
                if (isUserAdmin()) {
                    return "/user/homePage.xhtml?faces-redirect=true";
                } else {
                    return "/user/homePage.xhtml?faces-redirect=true";
                }
                //return "/users/index.xhtml?faces-redirect=true";
            }

        } catch (ServletException ex) {
            log.log(Level.SEVERE, "Login Failed");
            log.log(Level.SEVERE, "{0}", ex);
            return "loginError.xhtml?faces-redirect=true";
        }
        return "loginError.xhtml?faces-redirect=true";
    }

    public static User getUser() {
        try {
            FacesContext context = FacesContext.getCurrentInstance();
            HttpServletRequest servletRequest = (HttpServletRequest) context.getExternalContext().getRequest();

            final HttpSession session = servletRequest.getSession(true);
            User user = (User) session.getAttribute(USER_SESSION_KEY);
            return user;
        } catch (Exception e) {
            log.log(Level.SEVERE, "Error: getUser()", e);
            return null;
        }

    }

    public boolean isUserLoggedIn() {
        return (getUser() != null);
    }

    public boolean isLoggedIn() {
        return (getUser() != null);
    }

    public boolean isUser() {
        try {
            FacesContext context = FacesContext.getCurrentInstance();
            HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
            return request.isUserInRole(USER_ROLE);
        } catch (Exception e) {
            log.log(Level.SEVERE, "Error: isUser()", e);
            return false;
        }
    }

    public boolean isUserAdmin() {
        try {
            FacesContext context = FacesContext.getCurrentInstance();
            HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
            return request.isUserInRole(ADMIN_ROLE);
        } catch (Exception e) {
            log.log(Level.SEVERE, "Error: isUserAdmin()", e);
            return false;
        }
    }

    public static void reloadCurrentPage() {
        try {

            FacesContext context = FacesContext.getCurrentInstance();
            String viewId = context.getViewRoot().getViewId();
            ViewHandler handler = context.getApplication().getViewHandler();
            UIViewRoot root = handler.createView(context, viewId);
            root.setViewId(viewId);
            context.setViewRoot(root);
        } catch (Exception e) {
            log.log(Level.SEVERE, "Error: reloadCurrentPage()", e);
        }
    }

    public String getCurrentPage() {
        try {
            FacesContext context = FacesContext.getCurrentInstance();
            String viewId = context.getViewRoot().getViewId();
            return viewId;
        } catch (Exception e) {
            log.log(Level.SEVERE, "Error: getCurrentPage()", e);
            return null;
        }
    }

    public static boolean isUserInGroup(String group) {
        try {
            FacesContext context = FacesContext.getCurrentInstance();
            HttpServletRequest servletRequest = (HttpServletRequest) context.getExternalContext().getRequest();
            boolean inGroup = servletRequest.isUserInRole(group);
            log.log(Level.FINEST, "isUserInRole(\"{0}\")={1}",
                    new Object[]{group, inGroup});
            return inGroup;
        } catch (Exception e) {
            log.log(Level.SEVERE, "Error: isUserInGroup()", e);
            return false;
        }
    }

    public String getNewPassword1() {
        return newPassword1;
    }

    public void setNewPassword1(String newPassword1) {
        this.newPassword1 = newPassword1;
    }

    public String getNewPassword2() {
        return newPassword2;
    }

    public void setNewPassword2(String newPassword2) {
        this.newPassword2 = newPassword2;
    }

    public boolean isChangeSuccess() {
        return changeSuccess;
    }

    public void setChangeSuccess(boolean changeSuccess) {
        this.changeSuccess = changeSuccess;
    }

    public String changePassword() {
        if (newPassword1 == null
                || newPassword2 == null
                || !newPassword1.equals(newPassword2)) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Reset Error: ", "Passwords do not match."));
            changeSuccess = false;
            return "";
        }

        User selectedUser = LoginBeanFlix.getUser();
        try {
            // Try to save
            selectedUser.setPassword(newPassword1);
            userFacade.edit(selectedUser);
            clearPasswords();
            setChangeSuccess(true);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Reset Successful: ", "Password changed."));
        } catch (Exception ex) {
            log.log(Level.SEVERE, "Error reseting passwords: {0}", ex.getMessage());
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Save Error : ", ex.getMessage()));
        }
        return "/users/index.xhtml?faces-redirect=true";
    }

    private void clearPasswords() {
        setNewPassword1(null);
        setNewPassword2(null);
    }

}
