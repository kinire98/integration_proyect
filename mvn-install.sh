#!/usr/bin/bash
cd ./CommonValues
mvn clean install
cd ..
cd ./components
mvn clean install
cd ..
cd ./DB
mvn clean install
cd ..
cd ./NetworkCommunication
mvn clean install
cd ..
cd ./clientServerConnection
mvn clean install
cd ..
