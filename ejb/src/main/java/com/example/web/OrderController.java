package com.example.web;

import com.example.bean.CustomerOrderEJB;
import com.example.entity.Customer;
import com.example.entity.CustomerOrder;
import com.example.entity.LoggingInterceptor;

import javax.enterprise.context.RequestScoped;
import javax.faces.bean.ManagedBean;
import javax.inject.Inject;
import javax.inject.Named;
import javax.interceptor.Interceptors;
import java.util.ArrayList;
import java.util.List;

@Named
@ManagedBean
@RequestScoped
@Interceptors(LoggingInterceptor.class)
public class OrderController {

    @Inject
    private CustomerOrderEJB customerOrderEJB;

    @Inject
    private UserController userController;
    private Customer customer;

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }


    public List<CustomerOrder> dofindOrderByCustomerId() {
        List<CustomerOrder> customerOrders = new ArrayList<>();
        customer = userController.getUser();
        Integer customerId = customer.getId();
        customerOrders = customerOrderEJB.findByCustomerId(customerId);

        return customerOrders;
    }

}