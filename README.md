# TwilioCommHub

## Overview

The **TwilioCommHub** is a Spring Boot application designed to facilitate SMS communication through the Twilio API. It provides functionality for sending bulk SMS, managing message templates, and logging message statuses. The application incorporates features such as JWT-based authentication, Swagger API documentation, and Prometheus metrics for monitoring.

## Features

- **Send SMS**: Send SMS to individual recipients or in bulk.
- **Message Templates**: Create and manage reusable message templates with placeholder support.
- **Webhook Integration**: Update message status via webhooks from Twilio.
- **Rate Limiting and Load Balancing**: Built-in support for rate limiting and load balancing using Spring Cloud Gateway.
- **Monitoring**: Expose application metrics for Prometheus monitoring.
- **Authentication**: Secure endpoints using JWT authentication.

## Technologies Used

- **Spring Boot**: Framework for building the application.
- **Twilio SDK**: For SMS communication.
- **Spring Data JPA**: For database interactions.
- **H2 Database**: For in-memory database during development (changeable to MySQL for production).
- **JUnit & Mockito**: For unit and integration testing.
- **Swagger**: For API documentation.
- **Prometheus**: For monitoring metrics.

## Getting Started

### Prerequisites

- Java 17 or higher
- Maven
- Twilio Account (for API credentials)

### Installation

1. Clone the repository:
   ```bash
   git clone https://github.com/chandrakanthrck/TwilioCommHub.git
   ```

2. Navigate to the project directory:
   ```bash
   cd twilio-communication
   ```

3. Update `application.properties` with your Twilio credentials:
   ```properties
   twilio.account.sid=your_twilio_account_sid
   twilio.auth.token=your_twilio_auth_token
   twilio.phone.number=your_twilio_phone_number
   ```

4. Build the project:
   ```bash
   mvn clean install
   ```

5. Run the application:
   ```bash
   mvn spring-boot:run
   ```

### API Endpoints

#### Authentication

- **POST** `/api/authenticate`
    - Request Body: `{ "username": "your_username", "password": "your_password" }`
    - Response: JWT token for authentication.

#### SMS

- **POST** `/api/v1/sms/send`
    - Request Parameters: `to`, `message`
    - Sends an SMS to the specified recipient.

- **POST** `/api/sms/send/bulk`
    - Request Parameters: `to` (list of recipients), `message`, `scheduledTime` (optional)
    - Sends bulk SMS or schedules messages.

#### Message Templates

- **POST** `/api/template/create`
    - Request Parameters: `name`, `templateContent`
    - Creates a new message template.

- **POST** `/api/template/send`
    - Request Parameters: `templateName`, `to`, `params` (placeholders)
    - Sends an SMS using a specified template.

#### Webhook

- **POST** `/api/webhook/sms-status`
    - Request Parameters: `MessageSid`, `MessageStatus`
    - Updates the status of a sent SMS.

## Testing

Run the test suite with Maven:
```bash
mvn test
```

## Monitoring

Prometheus metrics are available at `/actuator/prometheus`. Configure Prometheus to scrape this endpoint for monitoring.

## Swagger API Documentation

Swagger documentation is available at `/swagger-ui.html` once the application is running.

## Contributing

Feel free to open issues or submit pull requests for improvements or bug fixes!

## License

This project is licensed under the MIT License.