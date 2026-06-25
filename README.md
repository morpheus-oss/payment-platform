# Wallet Payment Platform

An End-To-End Application 


## **Payment Service Architecture**

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
