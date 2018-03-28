package com.example.entity;

import javax.inject.Named;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Objects;

@Named
@Entity
@NamedQuery(name = "findAllItems", query = "SELECT i FROM Item i")
public class Item implements Serializable{
    @Id
    @GeneratedValue
    private Integer id;
    @NotNull
    @Size(min = 4, max = 20)
    @Column(nullable = false)
    private String name;
    @NotNull
    private Float price;
    @Column(length = 2000)
    private String description;

    public Item(String name, Float price, String description) {
        this.name = name;
        this.price = price;
        this.description = description;
    }

    public Item() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Item{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", description='" + description + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Item item = (Item) o;
        return Objects.equals(id, item.id);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id);
    }
}
