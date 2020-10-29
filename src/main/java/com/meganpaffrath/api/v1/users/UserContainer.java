package com.meganpaffrath.api.v1.users;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.meganpaffrath.TimeTracker;
import com.meganpaffrath.pojos.User;
import com.mongodb.client.model.Filters;
import org.bson.Document;

// for mongo
import javax.print.Doc;

import java.util.Map;

import static com.mongodb.client.model.Filters.eq;

public class UserContainer {

    /**
     * Create a new user
     * @param newUsername
     * @return created if user successfully added to database
     * @throws Exception if user already exists
     */
    // http://localhost:1111/api/v1/users/new?username=meganpaffrath
    public String addUser(String newUsername) throws Exception {
        long count = TimeTracker.users.count((eq("user", newUsername)) );
        System.out.println("There are " + count + " users of name " + newUsername);

        if (count == 0L) { // create user
            User newUserT = new User();
            newUserT.username = newUsername;
            newUserT.activities = null;
            Map<String, Object> map = new ObjectMapper().convertValue(newUserT, Map.class);
            map.remove("_id");

            Document newUser = new Document(map);
//            Document newUser = new Document("user", newUsername);
            TimeTracker.users.insertOne(newUser);
            return "created";
        } else {
            throw new Exception("Failed to create user: " + newUsername);
        }
    }

    /**
     * Delete user
     * @param username
     * @return
     */



    // REMOVE:
//    public String getInfo(String username) {
//        String returned = TimeTracker.users.find().first().toString();
//        return "Getting info for " + username + " and " + returned;
//    }
}
