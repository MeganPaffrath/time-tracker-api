package com.meganpaffrath.pojos;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.bson.types.ObjectId;

import java.util.List;

public class Activity {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("_id")
    public ObjectId id;

    public String name;
    public List<Interval> intervals;
}
