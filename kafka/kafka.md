# Download Zookeer and Kafka Image

sudo docker run -d \
  --name zookeeper \
  -p 2181:2181 \
  -e ZOOKEEPER_CLIENT_PORT=2181 \
  -e ZOOKEEPER_TICK_TIME=2000 \
  confluentinc/cp-zookeeper:7.5.0


sudo docker run -d \
    --name kafka \
    -p 9092:9092 \
    -e KAFKA_BROKER_ID=1 \
    -e KAFKA_ZOOKEEPER_CONNECT=zookeeper:2181 \
    -e KAFKA_ADVERTISED_LISTENERS=PLAINTEXT://localhost:9092 \
    -e KAFKA_OFFSETS_TOPIC_REPLICATION_FACTOR=1 \
    --link zookeeper:zookeeper \
    confluentinc/cp-kafka:7.5.0


# Check Docker images and Containers
* sudo docker images          //  showing running docker images
* sudo docker images -a       //  showing all docker images
* sudo docker ps              //  Showing running only
* sudo docker ps -a           //  Showing running or stoped both
* sudo docker container ls

# Get Docker Kafka container access using CLI
sudo docker exec -it kafka bash

# Create Topic
kafka-topics --create --topic my-topic --bootstrap-server localhost:9092 --partitions 3 --replication-factor 1


# List All Kafka Topics
kafka-topics --list --bootstrap-server localhost:9092


# Describe Kafka Topics
kafka-topics --describe --topic new-topic --bootstrap-server localhost:9092


# strat Producer Message producing
kafka-console-producer --topic my-topic --bootstrap-server localhost:9092

kafka-console-producer --topic my-topic --bootstrap-server localhost:9092     //can direct send message hi, hello

kafka-console-producer --topic my-topic --bootstrap-server localhost:9092 --property "parse.key=true" --property "key.separator=:"    //need to send message with key:value pair m1:hi, m2:hello


# strat Consumer Message Consuming
kafka-console-consumer --topic my-topic --bootstrap-server localhost:9092 --from begnning

kafka-console-consumer --topic my-topic --bootstrap-server localhost:9092 --group my-group --from beginning

kafka-consumer-groups --bootstrap-server localhost:9092 --describe --group my-group








                   


# update Linux
sudo apt update
sudo apt upgrade -y



# Install Java 17
sudo apt install openjdk-17-jdk -y


# Use Multiple Java Versions
sudo update-alternatives --config java                  //  Then select option
sudo update-alternatives --config javac                 //  Then select option
java -version                                           //  Confirm