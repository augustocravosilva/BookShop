Create table Books
(isbn VARCHAR(100) not NULL,
stock INTEGER,
PRIMARY KEY (isbn));

Create table Clients
(id INTEGER GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
fullname VARCHAR(100) not NULL,
adress VARCHAR(500),
email VARCHAR(100) not NULL,
PRIMARY KEY (id));

Create table BookOrders
(id INTEGER GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
quantity INTEGER,
STATE VARCHAR(200),
isbn VARCHAR(100) not NULL,
clientId INTEGER not NULL,
PRIMARY KEY (id),
FOREIGN KEY (clientId) REFERENCES Clients(id),
FOREIGN KEY (isbn) REFERENCES Books(isbn));


Create table BookSells
(id INTEGER not NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
quantity INTEGER,
totalPrice DOUBLE,
clientId INTEGER not NULL,
isbn VARCHAR(100) not NULL,
PRIMARY KEY (id),
FOREIGN KEY (clientId) REFERENCES Clients(id),
FOREIGN KEY (isbn) REFERENCES Books(isbn));
