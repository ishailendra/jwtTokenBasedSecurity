# jwtTokenBasedSecurity
This project is an attempt to implement Spring Security with JWT token in a monolithic architecture application.

## Getting Started
Download or Clone the project on your local machine and import it as maven project in your IDE. After successful import run as **Spring Boot Application**

### Prerequisites
```
MySQL 8
JDK 8 or above
Apache MAVEN
```

### Testing the application in POSTMAN

1. Register a new Student with following JSON
```
URL:- http://localhost:8080/student/register
{
	"studentName":"John",
	"email":"john@mail.com",
	"password":"john123",
	"marks":{
		"math":50,
		"eng":70,
		"chem":75,
		"ip":85,
		"phy":65
	},
	"standard":9
}
```

2. Login to generate the token
```
URL:- http://localhost:8080/student/login

{
	"workEmail":"john_190901@shail.com",
	"password":"shail123"
}
```

3. Get the Details of all Student
URL:- http://localhost:8080/student/getall

Add the JWT Token generated in previous step in the Authorization Bearer Token
