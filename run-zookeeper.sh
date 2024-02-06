#!/bin/bash

echo "Starting zookeeper..."
cd kafka
bin/zookeeper-server-start.sh config/zookeeper.properties

echo "Starting Kafka..."
#bin/kafka-server-start.sh  config/server.properties