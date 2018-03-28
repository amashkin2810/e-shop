package com.example;

import com.example.bean.CustomerEJB;
import com.example.bean.CustomerOrderEJB;
import com.example.entity.*;
import com.example.web.CustomerController;
import com.example.web.UserController;

import javax.annotation.PostConstruct;
import javax.ejb.LocalBean;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


@Named
@ConversationScoped
@LocalBean
public class ShoppingCart implements Serializable {
    private static final long serialVersionUID = 3313992336071349028L;
    @Inject
    Conversation conversation;
    private Item item;
    @Inject
    CustomerOrderEJB customerOrderEJB;
    @Inject
    CustomerEJB customerEJB;
    @Inject
    UserController userController;
    @Inject
    CustomerController customerController;
    private Customer customer;
    private CustomerOrder curentCustomerOrder;

    private static final Logger LOGGER = Logger.getLogger(ShoppingCart.class.getName());
    private List<Item> cartItems;


    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }


    public List<Item> getCartItems() {
        return cartItems;
    }

    public void setCartItems(List<Item> cartItems) {
        this.cartItems = cartItems;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    @PostConstruct
    private void init() {
        this.curentCustomerOrder = new CustomerOrder();
        this.item = new Item();
        this.customer = new Customer();
    }

    public CustomerOrder getCurentCustomerOrder() {
        return curentCustomerOrder;
    }

    public void setCurentCustomerOrder(CustomerOrder curentCustomerOrder) {
        this.curentCustomerOrder = curentCustomerOrder;
    }

    public String addItem(final Item item) {
        if (cartItems == null) {
            cartItems = new ArrayList<>();
            if (conversation.isTransient()) {
                conversation.begin();
            }
        }

        if (!cartItems.contains(item)) {
            cartItems.add(item);
            for (Item it : cartItems) {
            }
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Product added",
                    "The Item" + item.getName() + " has been added to Cart"));

            LOGGER.log(Level.INFO, "Adding product {0}", item.getName());
            LOGGER.log(Level.INFO, "Cart Size: {0}", cartItems.size());

        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Product was added early", ""));
        }
        return "newOrderContactInfo";
    }

    public void removeItem(Item item) {
        if (cartItems.contains(item))
            cartItems.remove(item);
    }

    public Integer getNumberOfItems() {
        if (cartItems == null || cartItems.isEmpty())
            return 0;
        return cartItems.size();
    }

    public Float getTotal() {
        if (cartItems == null || cartItems.isEmpty())
            return 0f;

        Float total = 0f;
        for (Item cartItem : cartItems) {
            total += (cartItem.getPrice());
        }
        return total;
    }

    public void empty() {
        cartItems.clear();
    }

    public CustomerOrder confirmOrder() {
        curentCustomerOrder.setDateCreated(Calendar.getInstance().getTime());
        curentCustomerOrder.setAmount(getTotal());
        return curentCustomerOrder;
    }

    public String checkout() {
        customer = userController.getUser();
        confirmOrder();
        curentCustomerOrder.setCustomer(customer);

        List<OrderDetail> details = new ArrayList<>();
        customerEJB.edit(customer);
        customerOrderEJB.save(curentCustomerOrder);
        for (Item i : getCartItems()) {
            OrderDetail detail = new OrderDetail();

            OrderDetailPK pk = new OrderDetailPK(curentCustomerOrder.getId(), i.getId());
            detail.setQty(1);
            detail.setItem(i);
            detail.setOrderDetailPK(pk);

            details.add(detail);
        }
        curentCustomerOrder.setOrderDetailList(details);
        customerOrderEJB.edit(curentCustomerOrder);
        LOGGER.log(Level.INFO, "Edit customer {0}", customer);
        LOGGER.log(Level.INFO, "Adding order {0}", curentCustomerOrder.getOrderDetailList());
        cartItems.clear();
        this.conversation.end();
        return "personalPage";
    }
}