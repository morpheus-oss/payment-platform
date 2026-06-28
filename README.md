# Wallet Payment Platform

Production-grade payment platform demonstrating modern backend architecture using Spring Boot microservices.

---

## Project Goals

- Production-ready implementation
- Domain-driven design
- Hexagonal architecture
- Event-driven communication
- Cloud-native deployment
- High testability
- Educational reference implementation

## Technology Stack

- Java 21
- Spring Boot 3.5.14
- Maven
- PostgreSQL
- RabbitMQ
- Redis
- MongoDB
- OpenTelemetry
- Kubernetes
- Terraform

---

## Features

- Payment Processing
- Fraud Detection
- Event-Driven Architecture
- Outbox Pattern
- CloudEvents
- RabbitMQ
- PostgreSQL
- Redis
- MongoDB
- OpenTelemetry
- Prometheus
- Grafana
- Kubernetes


---

## Build

```bash
mvn clean verify
```

## Status

🚧 Active Development

---

## Repository Structure

````
payment-platform/
│
├── pom.xml
├── README.md
├── CONTRIBUTING.md
├── LICENSE
├── .gitignore
├── .gitattributes
├── .editorconfig
│
├── .github/
│   └── workflows/
│       ├── build.yml
│       ├── dependency-check.yml
│       └── release.yml
│
├── docs/
│   ├── architecture/
│   ├── decisions/
│   ├── diagrams/
│   └── api/
│
├── build/
│   ├── checkstyle/
│   ├── spotless/
│   ├── archunit/
│   ├── sonar/
│   └── scripts/
│
├── shared/
│
├── services/
│
├── observability/
│
├── deploy/
│
└── infra/
````

---

## **Payment Service Architecture**

Initial Draft

````
Client
   |
REST API
   |
Payment Service
   |
+---------------------+
| PostgreSQL          |
|---------------------|
| wallets             |
| transfers           |
| outbox_events       |
+---------------------+
    |
Outbox Publisher
    |
RabbitMQ
````

### Flow

````
Create Wallet
     ↓
Transfer Money
     ↓
Store Transaction
     ↓
Store Outbox Event
     ↓
Store Idempotency Record
     ↓
Outbox Publisher
     ↓
Publish CloudEvent
     ↓
RabbitMQ
````

## **Fraud Service**

Initial Draft

### Flow

````
                   MoneyTransferred
Payment Service ----------------------------+
                                            |
                                            v
                                      RabbitMQ
                                            |
                                            v
                                   Fraud Service
                                            |
               +----------------------------+---------------------------+
               |                            |                           |
               v                            v                           v
         Velocity Rule             Large Transfer Rule          Country Rule
               |                            |                           |
               +------------ Risk Score Aggregator ---------------------+
                                            |
                                            v
                                      MongoDB Store
                                            |
                                            v
                                  FraudDetected Event
                                            |
                                            v
                                      RabbitMQ
````