package com.meganpaffrath;
import com.meganpaffrath.api.v1.users.UserContainer;
import com.meganpaffrath.api.v1.users.UserResource;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import spark.Request;
import spark.Response;
import spark.Route;

import static spark.Spark.*;

/**
 * Hello world!
 *
 */
public class TimeTracker {
    // API
    private UserResource userResource;

    // Database
    public static MongoClient mongoClient;
    public static MongoDatabase database;
    public static MongoCollection users;

    TimeTracker() {
        // Mongo & Database
        MongoClient mongoClient = new MongoClient("localhost", 27017);
        database = mongoClient.getDatabase("timeTrackerDB");
        users = database.getCollection("users");
        // password
//        boolean auth = database.authenticate("username", "pwd".toCharArray());

        // API Routes & Interaction
        initRoutes();
        userResource = new UserResource();
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
//            path("/users", () -> {
//                get("/:" + UserResource.Param.USERNAME.value(), map((req, res) -> userResource.getUser(req)));
//            });
            path("/users", () -> {
                post("/new" , map((req, res) -> userResource.addUser(req)));
                post("/remove" , map((req, res) -> userResource.removeUser(req)));
                path("/activities", () -> {
                    post("/add" , map((req, res) -> userResource.addActivity(req)));
                });
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


/*
Route request examples:
http://localhost:1111/api/v1/users/new?username=meganpaffrath

http://localhost:1111/api/v1/users/new?username=deleteThisUser
http://localhost:1111/api/v1/users/remove?username=deleteThisUser

// add activity
http://localhost:1111/api/v1/users/activities/add?username=meganpaffrath&activity=guitar

// remove activity

 */

/*
Funcitonalities

- Add user
- Remove user
+ Add activity to user
+ Remove activity from user
+ Add time to user's activity
+ Remove time from user's activity
+ Retrieve month data for an activity of a user
+ Return year data for an activity of a user
 */