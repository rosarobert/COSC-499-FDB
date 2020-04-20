# FDB Prescriber API

An API that uses the First Databank database to find the following things:
  * Query drugs 
  * Query Allergies
  * Harmful interactions that could occur with a drug
  
## Dependencies
This API uses the following technologies
  * Gradle
  * JDBC SQL Server Driver for SQL Server 2017
  * Testng

## Install Steps

  * Install SQL Server 2017 or SQL Server 2019
  * Clone this repository into a folder: https://github.com/rosarobert/COSC-499-FDB
  * Create a file `databaseConnectionConfig.txt` in `src/main/resources` and put your JDBC connection string to the FDB database
     * You will need a SDK of FDB, or a FDB backup file to do this
     * The backup file we used can be found [here](https://1drv.ms/u/s!AlrDWS4T-uh8l8czlIh6_oZ1fAjJPw?e=B8cDbD)
     * Ours was something similar to `jdbc:sqlserver://localhost;database=FDB;user=[your username];password=[your password];` Usually, username = 'sa' and password is your system admin password for your computer.
  * Run the code
  
## Organization of Code
There are 4 main components to our project:
  * The API
    * This can be found in the folder `src/main/java/Prescriber
    * You use the API by calling one of the static factories in the Prescriber interface, but preferable just `FdbPrescriber` to get all the optimizations we did
  * Container Objects
    * Found in the folder `src/main/java/Prescriber`
    * These are objects used by the API to contain info about patients, drugs, and drug interactions
  * UI
    * Found in `src/main/java/Apps`
    * Consists of the CLI and the GUI
    * Both are not neccessary for using this API. They were only created to show our COSC 499 class
    * There is also a `ConnectionConguration` file here, and this is used to create the JDBC connection based on your config file
  * Tests
    * Found in src/test
    * As said above, do not include any real good tests
    * These would need to be improved before using in production
  
 

## Implementation Milestones 

  - [x] Query drugs based on a keyword
  - [x] Query drugs based on a keyword, page, and page size
  - [x] Find all harmful interactions between a drug and a list of prescribed drugs
  - [x] Find all harmful interactions between a drug and a list of patient allergies
  - [x] Find all harmful food interactions that could occur when prescribed a drug
  - [x] Query allergies 

## Testing 

  * The tests are based on what we think is the correct way to query the FDB database. Once we found what we think is a correct query for a function, we looked at all possible drugs and took that as the correct result
  * Our test coverage is over 80% 
  * There are no tests based on another implementation of this interface
  * We do not see any other way to test this without input from doctors or another implementation to test against
 
