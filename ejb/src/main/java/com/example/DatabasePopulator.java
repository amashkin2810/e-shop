package com.example;

import com.example.bean.ItemEJB;
import com.example.entity.Item;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.sql.DataSourceDefinition;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;


@Singleton
@Startup
@DataSourceDefinition(
        className = "org.apache.derby.jdbc.EmbeddedDataSource",
        name = "java:global/jdbc/DerbyMy",
        user = "app",
        password = "app",
        databaseName = "DerbyMy",
        properties = {"connectionAttributes=;create=true"}
)
public class DatabasePopulator {


    @Inject
    private ItemEJB itemEJB;

    private Item tv;


    @PostConstruct
    private void populateDB() {
        tv = new Item("TV 7", 999F, "Best of the best");


        itemEJB.createItem(tv);

    }

    @PreDestroy
    private void clearDB() {

    }
}