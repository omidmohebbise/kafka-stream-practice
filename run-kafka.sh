#!/bin/bash

echo "Starting kafka..."
cd kafka
bin/kafka-server-start.sh  config/server.properties