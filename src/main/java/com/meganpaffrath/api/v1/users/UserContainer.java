package com.meganpaffrath.api.v1.users;

import com.meganpaffrath.TimeTracker;

public class UserContainer {

    public String getInfo(String username) {
        String returned = TimeTracker.users.find().first().toString();
        return "Getting info for " + username + " and " + returned;
    }
}
