# Flxpoint Test

This repository contains a Spring Boot microservice developed as a test for FlxPoint. The microservice exposes a REST API for customer management and synchronizes customer data with an external CRM system, using a simulated CRM service.

# Prerequisites

- <b>Docker</b>: Ensure Docker is installed on your machine.

# Instructions

To set up and run the application, follow these steps:

1) Clone this repository to your local machine:
```
git clone https://github.com/rafaelcx1/FlxPointTest.git
```

2) Navigate to the project directory:
```
cd <project-directory>
```

3) Run the application using Docker Compose:
```
docker compose up --build
```

The application should now be up and running.

# API Documentation

With the application running, you can access the API documentation via Swagger UI at the following URL:
```
http://localhost:8080/api/swagger-ui/index.html
```

If you prefer, you can use the provided Postman collection (located in the root folder: postman_collection.json).


# Solution Overview
Technology Stack:

- <b>Spring Boot</b>: Main application framework.
- <b>PostgreSQL</b>: Database used for persisting customer data.
- <b>JSON Server</b>: Mock CRM service simulating the external CRM.

Features:

- <b>Customer Management (CRUD)</b>: The API allows creating, reading, updating, and deleting customer records.
- <b>CRM Integration</b>: Each customer operation triggers an update in the external CRM.
- <b>Data Mapping</b>: The internal Customer model is mapped to the CRM's data structure, adjusting field formats and combining address data.
- <b>Retry Mechanism</b>: When the external CRM is unavailable, the system automatically retries the request asynchronously using the Spring @Retryable annotation with backoff functionality.

# Handling CRM Downtime

This application uses Spring's @Retryable annotation to handle situations where the external CRM is temporarily unavailable. The retry mechanism works as follows:

1) The initial retry occurs 5 seconds after a failed request.
2) If the request fails again, the next retry occurs after 10 seconds.
3) The delay doubles until it reaches a maximum of 20 seconds for subsequent retries.

# Error Handling

The service also includes error handling to manage validation errors in customer data, and other types of errors such as non-existent customer.