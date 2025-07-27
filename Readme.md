# Online Platform JDBC Project

This project is a Java-based backend for an online article publishing platform, using JDBC to interact with a MySQL database. It provides schema creation, data insertion, and frequent analytics queries for articles, users, subscriptions, and more.

## Features

- **Database Schema Creation:**  
  Automatically creates all necessary tables for users, articles, categories, subscriptions, comments, and more.  
  See [`SchemaCreator`](src/SchemaCreator.java) for details.

- **Data Insertion Utilities:**  
  Helper methods to insert users, articles, categories, tags, purchases, etc.  
  See [`ArticleInserter`](src/ArticleInserter.java).

- **Frequent Analytics Queries:**  
  Predefined queries for common analytics, such as articles per author per month, churn rate, top articles, and revenue by category.  
  See [`FrequentQueries`](src/FrequentQueries.java).

## Project Structure

```
src/
  Main.java                # Entry point, connects to DB and creates schema
  SchemaCreator.java       # Creates/drops all tables
  ArticleInserter.java     # Utility for inserting data
  FrequentQueries.java     # Analytics queries
  META-INF/MANIFEST.MF     # Manifest for JAR
lib/
  mysql-connector-j-9.4.0.jar # MySQL JDBC driver
```

## Getting Started

### Prerequisites

- Java 17 or above
- MySQL server running (default: `localhost:3306`)
- MySQL JDBC driver (already included in `lib/`)

### Setup

1. **Configure Database:**
   - Create a MySQL database named `articlepublish`.
   - Update the DB username and password in [`Main.java`](src/Main.java):

     ```java
     String username = "Your_dbUsername";
     String password = "your_dbPassword";
     ```

2. **Build and Run:**

   Compile and run the project from the root directory:

   ```sh
   javac -cp "lib/mysql-connector-j-9.4.0.jar" src/*.java
   java -cp "lib/mysql-connector-j-9.4.0.jar:src" Main
   ```

   This will connect to the database and create all tables.

3. **Insert Data:**

   Use methods from [`ArticleInserter`](src/ArticleInserter.java) to populate tables.

4. **Run Analytics Queries:**

   Call methods from [`FrequentQueries`](src/FrequentQueries.java) to get analytics.

## Notes

- The schema supports advanced features like article reviews, subscriptions, purchases, and analytics indexes.
- All SQL is written for MySQL.
- See code comments for usage examples.

