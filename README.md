# BookShop
Lab Project for Distribution and Integration Technologies - FEUP


Setup
=====

Warehouse database
------------------

In Netbeans create a Java DB database:

 **name:** WarehouseDB
 **username:** warehouseUser
**password:** warehousePassword

```sql
CREATE TABLE BOOKORDER (
    ID INTEGER NOT NULL PRIMARY KEY 
        GENERATED ALWAYS AS IDENTITY
        (START WITH 1, INCREMENT BY 1),  
    ISBN VARCHAR(20) NOT NULL,
    BOOKNAME VARCHAR(100),
    QUANTITY INTEGER NOT NULL,
    ORDERDATE VARCHAR(23),
    DISPATCHDATE VARCHAR(23),
    STATUS VARCHAR(10) NOT NULL
);
```

Then go to the [glassfish page](http://localhost:4848) and then in JDBC inside Resources add a new JDBC Resource with:

**name:** WarehouseDB
**pool name:** DerbyPool

