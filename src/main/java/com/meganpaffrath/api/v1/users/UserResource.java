package com.meganpaffrath.api.v1.users;

import spark.Request;
import spark.Route;

public class UserResource {
    UserContainer userContainer;

    public enum Param {
        USERNAME("username");

        private String value;
        Param(final String value) {
            this.value = value;
        }

        public String value() {
            return this.value;
        }
    }

    public UserResource() {
        userContainer = new UserContainer();
    }

    public Route addUser(final Request request) {
        try {
            return ok(userContainer.addUser(request.queryParams(Param.USERNAME.value())));
        } catch (final Exception e) {
            return bad(e.getMessage());
        }
    }

    public Route removeUser(final Request request) {
        try {
            return ok(userContainer.removeUser(request.queryParams(Param.USERNAME.value())));
        } catch (final Exception e) {
            return bad(e.getMessage());
        }
    }

//    public Route deleteUser()

//    public Route getUser(final Request request) {
//        try {
//            return ok(userContainer.getInfo(request.params(Param.USERNAME.value())));
//        } catch (final Exception e) {
//            return bad(e.getMessage());
//        }
//    }

    private static Route ok(final String body) {
        return (req, res) -> {
            res.status(200);
            res.type("application/json");
            return body;
        };
    }

    private static Route bad(final String body) {
        return (req, res) -> {
            res.status(400);
            res.type("application/json");
            return body;
        };
    }
}
