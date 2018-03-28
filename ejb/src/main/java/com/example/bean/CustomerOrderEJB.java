package com.example.bean;


import com.example.entity.Customer;
import com.example.entity.CustomerOrder;
import com.example.entity.LoggingInterceptor;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.inject.Named;
import javax.interceptor.Interceptors;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.io.Serializable;
import java.util.List;


@Named
@Stateless
@LocalBean
@Interceptors(LoggingInterceptor.class)
public class CustomerOrderEJB implements Serializable {
    @Inject
    private EntityManager em;

    public List<Customer> findAllCustomerOrders() {

        TypedQuery<Customer> query = em.createNamedQuery("CustomerOrder.findAll", Customer.class);

        return query.getResultList();
    }

    public Customer findCustomerById(Integer id) {

        return em.find(Customer.class, id);
    }

    public List<CustomerOrder> findByCustomerId(Integer customerId) {
        TypedQuery<CustomerOrder> query = em.createNamedQuery("CustomerOrder.findByCustomerId", CustomerOrder.class)
                .setParameter("id", customerId);
        return query.getResultList();
    }


    public CustomerOrder save(CustomerOrder customerOrder) {
        em.persist(customerOrder);
        return customerOrder;
    }

    public CustomerOrder edit(CustomerOrder customerOrder) {
        em.merge(customerOrder);
        return customerOrder;
    }


}