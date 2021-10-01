# About this repository

This repository uses Redis core data structures, RediSearch and TimeSeries to build a simple Java/Lettuce application that demonstrates an extremely light-weight application that uses Redis + modules without relying on a higher-level abstraction framework such as Spring, Quarkus, JakartaEE or others. The only dependency is lettuce-core.

Features:

1. Store and retrieve a simple key/value pair
2. Store and retrieve a key/hash pair
3. Use the Lettuce Commands interface to extend the supported command set outside of the Redis OSS command set
4. Store and retrieve a collection of RedisTimeSeries values
5. Create an index on the hash data structure and phonetic search ("Try searching for something that sounds like 'world' but type it differently, e.g. 'wurld')
6. Use full-text search with highlighting

# Getting Started

## Prerequisites

1. JDK 16 or higher (https://openjdk.java.net/install/index.html) (Note: not using any JDK16 specific features as of today, so feel free to downgrade if needed, should be fine with 8 and up)
2. Docker Desktop (https://www.docker.com/products/docker-desktop) (Note: if you have a different favorite method of running containers on your local development machine that's also perfectly fine)

## Running locally

1. Checkout the project
2. `docker run -p 6379:6379 redislabs/redismod:latest` in a terminal window
3. `./mvnw clean package assembly:assembly` in a second terminal window
4. `java -cp target/demo-1.0-SNAPSHOT-jar-with-dependencies.jar com.redis.lars.App`
5. Observe output
6. ...
7. Profit!

## Troubleshooting

1. When using this repo as this basis for your app, take a look at the pom.xml