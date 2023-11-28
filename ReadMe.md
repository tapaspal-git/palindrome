# Getting Started

### Code Generation
This Spring boot Palindrome Application is generated from
* [Spring initializer](https://start.spring.io/)

This is a maven build project which has all the dependencies in pom.xml

### API description

This is a POST API with URL - localhost:8080/validate/palindrome

which has body in below Json format<br>
{<br>
"username"  : "user",<br>
"text"      : "kayak"<br>
}

For successful validation returning Http status code as 200

### Assumption

For validate of the input
checking if the first word of the text is space or number then returning Http Status 400
