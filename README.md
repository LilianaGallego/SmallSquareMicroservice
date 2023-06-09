<br />
<div align="center">
<h3 align="center">PRAGMA POWER-UP</h3>
  <p align="center">
    <p align="justify">
    This repository contains the small square microservice that the restaurant creates, the dishes according to the category and the orders. In the database, the category is entered into the category table manually.
  </p >
   <p align="justify">
      The implementation of this microservice begins with the creation of the restaurant that a user with the role of 
owner must have and the creation of a dish that belongs to a restaurant and that has an assigned category, update of the dish, security of the routes is added, the employee is created, the dish is enabled or disabled, 
all the restaurants are listed with pagination and the dishes are listed by category id and restaurant id by role consumer, @Setter
    private static Long idUser, make an order, list order by state, assign order to employee, update status order, notify order state ready, update state order to delivered and cancel order

   </p>

</div>

### Built With

* ![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=java&logoColor=white)
* ![Spring](https://img.shields.io/badge/Spring-6DB33F?style=for-the-badge&logo=spring&logoColor=white)
* ![Gradle](https://img.shields.io/badge/Gradle-02303A.svg?style=for-the-badge&logo=Gradle&logoColor=white)
* ![MySQL](https://img.shields.io/badge/MySQL-00000F?style=for-the-badge&logo=mysql&logoColor=white)


### Steps to keep in mind
It contains the connection with the relational database in MySql, for which the powerup name database must be created and



### Built With

* ![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=java&logoColor=white)
* ![Spring](https://img.shields.io/badge/Spring-6DB33F?style=for-the-badge&logo=spring&logoColor=white)
* ![Gradle](https://img.shields.io/badge/Gradle-02303A.svg?style=for-the-badge&logo=Gradle&logoColor=white)
* ![MySQL](https://img.shields.io/badge/MySQL-00000F?style=for-the-badge&logo=mysql&logoColor=white)

### Prerequisites

* JDK 17 [https://jdk.java.net/java-se-ri/17](https://jdk.java.net/java-se-ri/17)
* Gradle [https://gradle.org/install/](https://gradle.org/install/)
* MySQL [https://dev.mysql.com/downloads/installer/](https://dev.mysql.com/downloads/installer/)

### Recommended Tools
* IntelliJ Community [https://www.jetbrains.com/idea/download/](https://www.jetbrains.com/idea/download/)
* Postman [https://www.postman.com/downloads/](https://www.postman.com/downloads/)

<!-- GETTING STARTED -->
## Getting Started

To get a local copy up and running follow these steps:

1. Clone the repository
2. Create a database in MySql with the smallsquare
3. Update the credentials must be modified in the application-dev.yml file.
   ![img.png](img/img.png)

4. Run the microservice by right clicking on the UserMicroserviceApplication file and then on run
5. The port on which the microservice runs is 8080 which can be modified by entering the application-dev.yml file.
6. The documentation of the code is in the following link

- Create restaurant
   [http://localhost:9080/swagger-ui/index.html#](http://localhost:9080/swagger-ui/index.html) in your web browser
   ![img.png](img/img3.png)
- Create plate
   ![img.png](img/img7.png)
- Update plate
   ![img.png](img/img8.png)
- Update state
   ![img.png](img/img9.png)
- List all restaurantes 
   ![img.png](img/img10.png)
- List plates by restaurantId and categoryId
   ![img_1.png](img/img11.png)
- Make an order by role consumer
  ![img_1.png](img/img1.png)
    ![img.png](img/img14.png)
- List orders by state
  ![img.png](img/img5.png)
- Assing order and update state to preparation
  ![img.png](img/img12.png)
- Notify order ready
    ![img.png](img/img13.png)
- Update state order to delivered with code client
    ![img.png](img16.png)
- Cancel order if state is earning
    ![img_1.png](img15.png)


<!-- ROADMAP -->
## Tests

Right-click the test folder and choose Run tests with coverage:
- JUnit5 - Library used for testing
  ![Junit5.jpg](img/Junit5.jpg)

- Mockito - Framework used for code testing
  ![Mockito.jpg](img/Mockito.jpg)


- Jacoco - Test coverage tool
  ![jacoco.jpg](img/jacoco.jpg)

### Microservice structure

- Layer adpters
  ![img.png](img/img2.png)

- Layer configuration
  ![img_1.png](img/img_6.png)

- Layer domain
  ![img.png](img/img4.png)



### Author
[@LilianaGallego](https://github.com/LilianaGallego) - Liliana Gallego

