# FDB Prescriber API

An API that uses the First Databank database to find the following things:
  * Query drugs 
  * Query Allergies
  * Find harmful interactions that could occue with a drug
  
## Dependencies
This API uses the following technologies
  * Gradle
  * JDBC SQL Server Driver for SQL Server 2017
  * Testng

## Install Steps

  * Install SQL Server 2017 or SQL Server 2019
  * Find a way to gain access to FDB
     * The backup we used can be found [here](https://1drv.ms/u/s!AlrDWS4T-uh8l8czlIh6_oZ1fAjJPw?e=B8cDbD)
  * Clone this repository into some folder
  * Create a file `databaseConnectionConfig.txt` in `src/main/resources` and put your JDBC connection string to the FDB database
     * Ours was something similar to jdbc:sqlserver://localhost;database=FDB;user=[your username];password=[your password];
  * Run the code
  
## Organization of Code
There are 3 many components to our project

## Implementation Milestones 

  - [x] Query drugs based on a keyword
  - [x] Query drugs based on a keyword, page, and page size
  - [x] Find all harmful interactions between a drug and a list of prescribed drugs
  - [x] Find all harmful interactions between a drug and a list of patient allergies
  - [x] Find all harmful food interactions that could occur when prescribed a drug
  - [x] Query allergies 

## Testing 

  * The tests are based on what we think is the correct way to query the FDB database. Once we found what we think is a correct query for a function, we looked at all possible drugs and took that as the correct result
  * Ethan coulde not run the code, so could not test code coverage
  * There are no tests based on another implementation of this interface
  * We do not see any other way to test this without input from doctors or another implementation to test against
 
