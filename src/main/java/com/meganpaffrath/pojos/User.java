package com.meganpaffrath.pojos;

import org.bson.types.ObjectId;

import java.util.List;

public class User {
    public ObjectId id;
    public String username;
    public List<Activity> activities;
}
