package com.meganpaffrath;
import com.meganpaffrath.api.v1.users.ActivityResource;
import com.meganpaffrath.pojos.Activity;
import com.mongodb.ConnectionString;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.codecs.configuration.CodecRegistry;
import spark.Request;
import spark.Response;
import spark.Route;

import org.bson.codecs.pojo.PojoCodecProvider;
import com.mongodb.MongoClientSettings;
import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;
import static spark.Spark.*;

public class TimeTracker {
    // API
    private ActivityResource activityResource;

    // Database
    public static MongoClient mongoClient;
    public static MongoDatabase database;
    public static MongoCollection<Activity> activities;

    TimeTracker() {
        // Mongo & Database
        ConnectionString connectionString = new ConnectionString("mongodb://localhost:27017");
        CodecRegistry pojoCodecRegistry = fromProviders(PojoCodecProvider.builder().automatic(true).build());
        CodecRegistry codecRegistry = fromRegistries(MongoClientSettings.getDefaultCodecRegistry(), pojoCodecRegistry);
        MongoClientSettings clientSettings = MongoClientSettings.builder()
                .applyConnectionString(connectionString)
                .codecRegistry(codecRegistry)
                .build();

        MongoClient mongoClient = MongoClients.create(clientSettings);

//        MongoClient mongoClient = new MongoClient("localhost", 27017);
        database = mongoClient.getDatabase("timeTrackerDB");
        activities = database.getCollection("activities", Activity.class);
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