package com.example.bean;


import com.example.entity.Customer;
import com.example.entity.LoggingInterceptor;

import java.io.Serializable;
import java.util.List;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.inject.Named;
import javax.interceptor.Interceptors;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

@Named
@Stateless
@LocalBean
@Interceptors(LoggingInterceptor.class)
public class CustomerEJB implements Serializable {
    @Inject
    private EntityManager em;

    public List<Customer> findAllCustomers() {

        TypedQuery<Customer> query = em.createNamedQuery("Customer.findAll", Customer.class);

        return query.getResultList();
    }

    public Customer findCustomerById(Integer id) {


        return em.find(Customer.class, id);
    }

    public Customer findCustomerByEmail(String email) {

        TypedQuery<Customer> query = em.createNamedQuery("Customer.findByEmail", Customer.class)
                .setParameter("email", email);

        if (query.getResultList().size() > 0) {
            return query.getSingleResult();
        } else {
            return null;
        }

    }

    public Customer save(Customer customer) {
        em.persist(customer);
        return customer;
    }

    public Customer edit(Customer customer) {

        em.merge(customer);
        return customer;
    }


}