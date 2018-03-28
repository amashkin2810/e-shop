package com.example.web;

import com.example.bean.CustomerEJB;
import com.example.entity.Customer;
import com.example.entity.LoggingInterceptor;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.interceptor.Interceptors;
import java.util.logging.Level;
import java.util.logging.Logger;

@Named
@ManagedBean
@RequestScoped
@Interceptors(LoggingInterceptor.class)
public class CustomerController {

    @Inject
    private CustomerEJB customerEJB;

    @Inject
    UserController userController;

    private Customer customer;
    private static final Logger LOGGER = Logger.getLogger(CustomerController.class.getName());

    @PostConstruct
    private void init (){
        this.customer = new Customer();
    }

    public String doCreateCustomer() {
        if(customerEJB.findCustomerByEmail(customer.getEmail())!=null) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "User already exists with such email", ""));
            return "";
        }
        else {
            customerEJB.save(customer);
            userController.setUser(customer);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Customer created",
                    "The customer" + customer.getFirstname() + " has been created with id=" + customer.getId()));
            LOGGER.log(Level.INFO, "Adding customer {0}", customer);

        }
        return "personalPage";
    }



    public void doFindCustomerById() {
        this.customer = customerEJB.findCustomerById(customer.getId());
    }

    public void doFindCustomerByEmail() {
        customer = customerEJB.findCustomerByEmail(customer.getEmail());
    }

    public Customer getCustomer() {
        return customer;
    }


    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
}