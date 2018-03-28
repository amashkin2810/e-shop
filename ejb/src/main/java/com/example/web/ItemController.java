package com.example.web;

import com.example.bean.ItemEJB;
import com.example.ShoppingCart;
import com.example.entity.Item;
import com.example.entity.LoggingInterceptor;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.interceptor.Interceptors;

@Named
@ManagedBean
@RequestScoped
@Interceptors(LoggingInterceptor.class)
public class ItemController {

    @Inject
    private ItemEJB itemEJB;
    @Inject
    private Item item;
    @Inject
    private ShoppingCart shoppingCart;


    public String doCreateItem() {
        itemEJB.createItem(item);
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Item created",
                "The Item" + item.getName() + " has been created with id=" + item.getId()));
        return "";
    }

    public String doAddItem() {
        shoppingCart.addItem(item);
        return "newOrderContactInfo";
    }


    public void doFindItemById() {
        item = itemEJB.findItemById(item.getId());
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }
}