#! /bin/bash

echo "Building JAR..."
mvn install > /dev/null

echo "Build DONE!"

mkdir $HOME/deployableJARs > /dev/null 2>&1

now=$(date +'%d-%m-%Y-%T')
echo "Timestamp: $now"
tar -zcf $HOME/deployableJARs/plantalive-build-$now.tar.gz ./target

echo "Copying files DONE!"