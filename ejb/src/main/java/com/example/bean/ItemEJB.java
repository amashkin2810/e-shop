package com.example.bean;

import com.example.entity.Item;
import com.example.entity.LoggingInterceptor;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.inject.Named;
import javax.interceptor.Interceptors;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.validation.constraints.NotNull;
import java.util.List;

@Named
@Stateless
@LocalBean
@Interceptors(LoggingInterceptor.class)
public class ItemEJB {

    @Inject
    private EntityManager em;

    public List<Item> findAllItems() {
        TypedQuery<Item> query = em.createNamedQuery("findAllItems", Item.class);
        return query.getResultList();
    }

    public @NotNull
    Item createItem(@NotNull Item item) {
        em.persist(item);
        return item;
    }

    public Item findItemById(Integer id) {
        return em.find(Item.class, id);
    }

}
