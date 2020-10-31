package com.meganpaffrath;
import com.meganpaffrath.api.v1.users.ActivityResource;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import spark.Request;
import spark.Response;
import spark.Route;

import static spark.Spark.*;

public class TimeTracker {
    // API
    private ActivityResource activityResource;

    // Database
    public static MongoClient mongoClient;
    public static MongoDatabase database;
    public static MongoCollection activities;

    TimeTracker() {
        // Mongo & Database
        MongoClient mongoClient = new MongoClient("localhost", 27017);
        database = mongoClient.getDatabase("timeTrackerDB");
        activities = database.getCollection("activities");
        // password
//        boolean auth = database.authenticate("username", "pwd".toCharArray());

        // API Routes & Interaction
        initRoutes();
        activityResource = new ActivityResource();
    }

    public static void main( String[] args ) {
        System.out.println( "Hello World!" );
        TimeTracker tt = new TimeTracker();
    }

    /**
     * Defines app routes
     */
    public void initRoutes() {
        port(1111);

        path("/api/v1", () -> {
            path("/add", () -> {
                post("/log" , map((req, res) -> activityResource.addLog(req)));
            });
             path("/get", () -> {
                 get("/:" + ActivityResource.Param.YEAR.value(), map((req, res) -> activityResource.getYear(req)));
             });
        });
    }

    private Route map(Converter c) {
        return (req, res) -> c.convert(req, res).handle(req, res);
    }

    private interface Converter {
        Route convert(Request req, Response res);
    }
}