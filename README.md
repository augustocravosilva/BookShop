Lab Project for Distribution and Integration Technologies - FEUP


#Setup

For this project to run you must do the following configurations. 

  
Warehouse database
------------------

**In Netbeans create a Java DB database:**

*name:* WarehouseDB  
*username:* warehouseUser  
*password:* warehousePassword  

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

*name:* WarehouseDB  
*pool name:* DerbyPool  

Message Queue
------------------

Go to the [glassfish page](http://localhost:4848) and then in JMS Resources  inside Resources do the following:

 - **Create a destination resource:**  
   *JNDI Name:* jms/EAppQueue  
   *Physical Destination Name:* name  
   *Resource Type:* javax.jsm.Queue  
 - **Create a connection factory:**  
   *JNDI Name:* jms/EAppQueueFactory  
   *Resource Type:* javax.jms.QueueConnectionFactory  

or run the following commands

```sh
asadmin create-jms-resource --restype=javax.jms.Queue jms/EAppQueue
asadmin create-jms-resource --restype=javax.jms.QueueConnectionFactory jms/EAppQueueFactory
```
