package com.example.web;

import com.example.bean.CustomerEJB;
import com.example.entity.Customer;

import java.io.IOException;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.ServletException;

@Named
@SessionScoped
public class UserController implements Serializable {
    private Customer user;
    private String username;
    private String password;
    @Inject
    private CustomerEJB customerEJB;
    @Inject
    CustomerController customerController;
    private static final Logger LOGGER = Logger.getLogger(UserController.class.getName());
    public Customer getUser() {
        return user;
    }

    public void setUser(Customer user) {
        this.user = user;
    }

    public String login() throws IOException {
        String result;
        request(username, password);
        this.username = username;
        this.password = password;
        this.user = customerEJB.findCustomerByEmail(username);
        if (customerController.getCustomer() == null) {
            customerController.setCustomer(this.user);
        }
        if (this.user.getPassword().equals(this.password)) {
            LOGGER.log(Level.INFO, "Login user {0}", user);
            result = "personalPage";
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Incorrect password", ""));
            LOGGER.log(Level.INFO, "Incorrect user password  {0}", user);
            result = "";
        }
        return result;
    }

    private void request(String username, String password) {
    }

    public String logout() throws ServletException {
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        LOGGER.log(Level.INFO, "Logout user {0}", user);
        return "index";
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


}
