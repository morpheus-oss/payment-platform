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
