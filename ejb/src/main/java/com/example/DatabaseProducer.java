package com.example;

import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public class DatabaseProducer {


    @Produces
    @PersistenceContext(unitName = "DerbyMy")
    private EntityManager em;
}