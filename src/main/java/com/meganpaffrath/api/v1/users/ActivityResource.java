package com.meganpaffrath.api.v1.users;

import spark.Request;
import spark.Route;

public class ActivityResource {
    ActivityContainer activityContainer;

    public enum Param {
        USERNAME("username"),
        ACTIVITY("activity"),
        START("start"),
        STOP("stop"),
        DATE("date"),
        MINUTES("minutes"),
        YEAR("year"),
        MONTH("month");

        private String value;
        Param(final String value) {
            this.value = value;
        }

        public String value() {
            return this.value;
        }
    }

    public ActivityResource() {
        activityContainer = new ActivityContainer();
    }

    public Route addLog(final Request request) {
        try {
            return ok(activityContainer.addLog(
                    request.queryParams(Param.USERNAME.value()),
                    request.queryParams(Param.ACTIVITY.value()),
                    request.queryParams(Param.DATE.value()),
                    request.queryParams(Param.MINUTES.value())
                )
            );
        } catch (final Exception e) {
            return bad(e.getMessage());
        }
    }

    public Route getYear(final Request request) {
        try {
            return ok(activityContainer.getYear(
                    request.queryParams(Param.USERNAME.value()),
                    request.queryParams(Param.YEAR.value()),
                    request.queryParams(Param.ACTIVITY.value())
                )
            );
        } catch (final Exception e) {
            return bad(e.getMessage());
        }
    }


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
