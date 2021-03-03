#! /bin/bash

echo "Building JAR..."
mvn package > /dev/null 

echo "Build DONE!"

mkdir $HOME/deployableJARs > /dev/null 2>&1

now=$(date +'%d-%m-%Y-%T')
echo "Timestamp: $now"

cp ./target/plantalive-0.0.1-SNAPSHOT.jar $HOME/deployableJARs/"plantalive-build-$now.jar"

echo "Copying files DONE!"