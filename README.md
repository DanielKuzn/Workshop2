# Database Access Pattern in Java

This project serves as a Java design pattern illustrating the method for accessing a user database. Each class corresponds to a separate table in the database. These classes have attributes that mirror the columns in their respective tables, and they implement methods for both setting and retrieving these attributes (getters and setters). An instance of such a class represents one row in the table.

Once classes representing individual database tables are implemented, the next step is to create DAO (Data Access Object) classes, which implement methods for communicating with the database. For each class representing a table in the database, a separate DAO class is created. For example, for the User class, there should be a UserDao class.

## Basic Operations

### CRUD Operations
- **Create**: Saves an object to the table as a new row.
- **Read**: Reads one row from the table and returns an object representing that row.
- **Update**: Saves an object to the table, modifying an existing row.
- **Delete**: Deletes an object from the table, removing the row with the same ID as the object.

These are fundamental operations that can be utilized by programs operating on a database. They are commonly referred to as CRUD (Create, Read, Update, Delete) operations. Additionally, DAO classes can implement other useful methods for database operations.

### Additional Methods
- **findAll**: Reads all rows from the table, creates a new object based on each row, and returns a list containing all created objects.

## User Class
The User class representing a table should store the following data:
- User ID (usually auto-incremented by the database).
- User name.
- Password (hashed).
- User email (unique within the system).

## UserDao Class
The UserDao class should provide the following functionalities:
- Saving a new user to the database.
- Reading a user by their ID.
- Editing user data.
- Deleting a user.
- Reading all users.

## Method Details
- The `create` method should return an object with the ID filled in.
- The `read` method takes an ID and retrieves the corresponding row from the database.
- The `update` method updates data in the database based on the object's data.
- The `findAll` method retrieves all rows from the users table.
- The `delete` method removes a row from the database based on the provided ID.

## Dependencies
- This project utilizes BCrypt for password hashing.

## Usage
To test the functionality of all classes and methods, you can use the MainDao class provided in the project.

## Database Configuration
Ensure your database connection details are correctly configured in the DbUtil class.

---
For detailed code implementation, refer to the source code files provided.
