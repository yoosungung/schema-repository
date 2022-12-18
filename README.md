# Schema Registry
## background
### Kafka
Kafka is the most popular steam broker.
Unlike message queues, it is based on a pub/sub model and has low connection dependence. There is a lot of potential in this area. In the case of MSA (Micro Service Architecture), RestAPI is highly dependent on connection, so several alternatives are needed for disconnection situations. In the case of Kafka, it is easily connected to the source.
### Web Flux
It is a new spring web framework based on async/reactive. Asynchronous / reactive is a low-resource solution to the 10K problem triggered by javascripte, and it has sparked a new trend in all open source fields.
### H2
It is an embedded database written in java.
### Schema Registry
In all fields such as Kafka and Hive, schema management is a necessary foundation element. After conflurent changed schema-registry to community license, commercial use is difficult. Many companies, such as AWS and Cloudera, create and use their own schema management elements. The spring-schema-registry is almost the only open source alternative, but it is not compatible with the Kafka Schema Registry.
##Config
Set as spring properties, yaml, or environment variable.
```
server:
   address: 0.0.0.0
   port: 8081

schema:
   topic: "_schemas"
   groupId: group.schema

spring:
   datasource:
     url: jdbc:h2:mem:schemadb
     driverClassName: org.h2.Driver
     username: sa
     password: password
   jpa:
     database-platform: org.hibernate.dialect.H2Dialect
```
For spring.kafka related properties, refer to the spring-kafka documentation.