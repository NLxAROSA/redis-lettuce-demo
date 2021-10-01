package com.redis.lars;

import java.util.HashMap;
import java.util.Map;

import io.lettuce.core.RedisClient;
import io.lettuce.core.RedisCommandExecutionException;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.dynamic.RedisCommandFactory;

/**
 * Hello world!
 *
 */
public class App {
    public static void main(String[] args) throws InterruptedException {

        // Create a connection to Redis
        RedisClient redisClient = RedisClient.create("redis://@localhost:6379");
        StatefulRedisConnection<String, String> connection = redisClient.connect();

        // Set a key/string
        connection.sync().set("hello", "World");

        System.out.println("Hello string: " + connection.sync().get("hello"));

        // Set a key/hash
        Map<String, String> myMap = new HashMap<>();
        myMap.put("Name", "World");
        myMap.put("Description", "Hello world");
        myMap.put("Purpose", "42");
        myMap.put("Thank", "You");
        if (connection.sync().exists("lars:helloWorld") == 1L) {
            connection.sync().del("lars:helloWorld");
        }
        connection.sync().hset("lars:helloWorld", myMap);
        System.out.println("Hello hash: " + connection.sync().hgetall("lars:helloWorld"));

        RedisCommandFactory rcf = new RedisCommandFactory(connection);
        TimeSeriesCommands tsc = rcf.getCommands(TimeSeriesCommands.class);

        if (connection.sync().exists("myts") == 1L) {
            connection.sync().del("myts");
        }

        tsc.create("myts", 0);
        tsc.add("myts", 1.0);
        Thread.sleep(100);
        tsc.add("myts", 1.3);
        Thread.sleep(100);
        tsc.add("myts", 1.5);
        Thread.sleep(100);
        tsc.add("myts", 1.2);
        Thread.sleep(100);
        tsc.add("myts", 1.4);

        System.out.println("Hello TimeSeries: "
                + tsc.range("myts", System.currentTimeMillis() - 1000 * 60 * 60 * 24 * 7, System.currentTimeMillis()));

        RediSearchCommands rsc = rcf.getCommands(RediSearchCommands.class);

        try {
            rsc.drop("hello_idx");
        } catch (RedisCommandExecutionException e) {
            System.out.println("Error deleting index: " + e.getMessage());
        }
        
        rsc.create("hello_idx", "lars:", "Description");
        System.out.println("Hello Search: " + rsc.searchWithHighlight("hello_idx", "wor*"));

    }
}
