## 1. Start Local Kafka Server

#### a. Start ZooKeeper
```sh
bin/zookeeper-server-start.sh config/zookeeper.properties
```
#### b. Start Kafka 
```sh
bin/kafka-server-start.sh config/server.properties
```

## 2. Create Topic 'hello-world'
```sh
bin/kafka-topics.sh --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic hello-world
```

## 3. Import Project
Checkout the code and import is as gradle project

## 4. Run ```com.helloworld.Main```
