#!/bin/bash

LIB_PATH="lib/*"

function build() {
    # Check and create the out directory if it doesn't exist
    if [ ! -d "out" ]; then
        mkdir out
    fi

    echo "Building Server..."
    javac -d out -cp "src:$LIB_PATH" src/com/myredis/server/*.java
    javac -d out -cp "src:$LIB_PATH" src/com/myredis/client/*.java

    echo "Creating server JAR..."
    echo "Main-Class: com.myredis.server.RedisServer" > manifest-server.txt
    jar cvfm my-redis-server.jar manifest-server.txt -C out/ .

    echo "Creating client JAR..."
    echo "Main-Class: com.myredis.client.RedisClient" > manifest-client.txt
    jar cvfm my-redis-client.jar manifest-client.txt -C out/ .

    echo "Server build complete."
}

function run_server() {
   java -cp "my-redis-server.jar:$LIB_PATH" com.myredis.server.RedisServer "$@"
}

function run_client() {
    java -jar my-redis-client.jar
}

if [ "$1" == "build" ]; then
    # Build command
    build
elif [ "$1" == "run" ] && [ "$2" == "server" ]; then
    shift 2
    run_server "$@"
elif [ "$1" == "run" ] && [ "$2" == "client" ]; then
    run_client
else
    echo "Usage: ./build.sh [build | run]"
fi
