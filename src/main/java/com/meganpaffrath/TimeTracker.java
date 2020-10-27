package com.meganpaffrath;
import com.meganpaffrath.api.v1.users.UserContainer;
import com.meganpaffrath.api.v1.users.UserResource;
import spark.Request;
import spark.Response;
import spark.Route;

import static spark.Spark.*;

/**
 * Hello world!
 *
 */
public class TimeTracker {
    private UserResource userResource;

    TimeTracker() {
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
            path("/users", () -> {
                get("/:" + UserResource.Param.USERNAME.value(), map((req, res) -> userResource.getUser(req)));
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
