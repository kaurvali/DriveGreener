# DriveGreener

---

## What is DriveGreener?

DriveGreener is an application to track your electric or internal combustion vehicle's fuel consumption and get a better overview on how to save money on your travels!

This application was created by Kaur Vali, for his Bachelors thesis at University of Tartu in 2023.
It was written using Java with Spring Boot and Angular. PostgreSQL is the database.

The login in the application was based off of a tutorial by bezkoder: [https://www.bezkoder.com/angular-15-spring-boot-jwt-auth/](https://www.bezkoder.com/angular-15-spring-boot-jwt-auth/)

### How does it work?

1. Create an account
2. First, add your vehicle using the Vehicles page. You have to fill some information about the vehicle
3. After that, add a the first fillup for the vehicle
4. With second fillup, all types of different statistics get calculated for the vehicle
5. Enjoy!

### What kind of statistics and values are calculated and displayed?
For each fillup
- Fuel consumption for each fillup
- Price per 100km for each fillup

For a vehicle page
- Total fuel consumption
- Total distance travelled
- Total Cost
- Fuel/Energy used
- CO2 created in kg's

Graphs
- Consumption per fillup
- Trip/distance driven per filling
- Price per filling
- Unit price for fillup
- Consumption in the city
- Consumption for fuel type
- Consumption for driving style
- Consumption for tire type
- Consumption for vehicle load
- Consumption per month
- Cost per month

---

## How to install it?

1. Clone this repo and open it in your favourite IDE. Tested and developed using JetBrains IntelliJ.

2. Download and set up PostgreSQL - a server is needed for the application. Application has been tested with PostgreSQL 15.2.

3. In ```back/src/main/resources/application.properties``` add your connection URL to the server and login & password credentials.

4. In ```pom.xml``` make sure that all of the dependancies have been downloaded correctly.

5. Run command ```mvn clean install```.

6. Run the back-end applcation.

7. In the database (for example using PgAdmin) run following commands:
```
INSERT INTO roles(name) VALUES('ROLE_USER');
INSERT INTO roles(name) VALUES('ROLE_ADMIN');
INSERT INTO roles(name) VALUES('ROLE_MODERATOR');
```

8. Open front end folder of the cloned repo using your favourite IDE. Tested and developed using Visual Studio Code.

9. Make sure that ```npm``` is installed on your computer. If not, please install it.

10. Run command ```npm install``` in terminal. This should install all of the needed packages.

11. Make sure angular is installed. If not, use command ```npm install -g @angular/cli``` in terminal.

12. Run ```ng serve --port 8081``` in the terminal.

13. Open the application in your web browser at [http://localhost:8081/](http://localhost:8081/).

14. Enjoy the application.

---
