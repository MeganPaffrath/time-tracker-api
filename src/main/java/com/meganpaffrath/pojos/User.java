package com.meganpaffrath.pojos;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import org.bson.types.ObjectId;

import java.util.List;

public class User {
    @JsonProperty("_id")
    public ObjectId id;
    public String username;
    public List<Activity> activities;
}
