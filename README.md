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
CREATE TABLE "ORDER" (
    "id" INTEGER NOT NULL PRIMARY KEY,
    "bookId" INTEGER NOT NULL,
    "bookName" VARCHAR(100),
    "quantity" INTEGER NOT NULL,
    "status" VARCHAR(10) NOT NULL
);
```

Then go to the [glassfish page](http://localhost:4848) and then in JDBC inside Resources add a new JDBC Resource with:

**name:** WarehouseDB
**pool name:** DerbyPool

