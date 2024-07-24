Spring Boot Web Application "TicketToRide".

[ Idea of Application ]

Build a Web Application with UI for registering new users and being able to provide an API
to find the most optimal route between two cities based on the price.

[ Stack ] :

Java, Spring, Spring Boot, Spring Security, Spring Data, PostgreSQL, Mailgun API, 
Junit Jupiter, Maven/Gradle, Mockito, Thymeleaf, Apache Commons, Hibernate.

[ Functionality ] :

1. Possibility to choose Sign-In or Sign-Up to the system.
2. Possibility to register new users using credential requirements and then persist the object to the database.
3. Possibility to login to the system with existing user credentials (after registration).
4. Possibility to choose cities(Departure city, Arrival city) from 2 drop-down charts.
5. Possibility to see full ticket information at the end, with the price and quantity of the segments on the route.
   

[ SQL Schema ] :

<img width="216" alt="Screenshot 2024-07-23 at 9 13 45 PM" src="https://github.com/user-attachments/assets/e4fbb60b-d0e2-4915-92dc-e30d4039d080">



[ API ] :

  to start : "localhost:8080/"
  
  to register new user : "localhost:8080/register
  
  to login with existing user : "localhost:8080/login
  
  to get to the main menu : "localhost:8080/main_menu (for proper working of program, recommended to start from the welcome page).
  
  to get to the optimal ticket page : "localhost:8080/optimal_ticket (for proper working of program, recommended to start from the welcome page).


  [ !IMPORTANT NOTICE! ] :
  
  For proper work of the program I would highly recommend excluding mail service from runtime process, since it's still under developing.
