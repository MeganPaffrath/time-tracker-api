package com.meganpaffrath.api.v1.users;

import com.fasterxml.jackson.annotation.JacksonAnnotation;
import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.meganpaffrath.TimeTracker;
import com.meganpaffrath.pojos.Activity;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCursor;
import com.mongodb.client.MongoCursor;
import com.mongodb.util.JSON;
import com.mongodb.DBObject;
import org.bson.types.BasicBSONList;
import com.mongodb.client.FindIterable;
import org.bson.*;

// for mongo

import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static com.mongodb.client.model.Filters.*;

public class ActivityContainer {

    public String addLog(String username, String activity, String date, String minutes) throws Exception {
        int minutesT = Integer.parseInt(minutes);

        DateFormat df = new SimpleDateFormat("MM/dd/yyyy");

        Calendar start = new Calendar.Builder().setInstant(df.parse(date)).build();
        Calendar end = new Calendar.Builder().setInstant(df.parse(date)).build();

        end.add(Calendar.MINUTE, minutesT);

        System.out.println(start.getTime());
        System.out.println(end.getTime());

        Activity newActivity = new Activity();
        newActivity.activity = activity;
        newActivity.username = username;
        newActivity.start = start.getTimeInMillis();
        newActivity.end = end.getTimeInMillis();

        TimeTracker.activities.insertOne(newActivity);


        return (
                "Added: " + "\n\tStart time: " + start.getTime()
                        + "\n\tEnd: " + end.getTime()
                        + "\n\tTotal: " + minutesT + " minutes"
        );
    }

    public String getYear(String username, String year, String activity) throws Exception {
        int yr = Integer.parseInt(year);
        String endStr = String.format("12/31/%d 23:59:59", yr);
        String startStr = String.format("12/31/%d 23:59:59", (yr-1) );
        DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        Calendar start = new Calendar.Builder().setInstant(df.parse(startStr)).build();
        Calendar stop = new Calendar.Builder().setInstant(df.parse(endStr)).build();

        MongoCursor<Activity> cursor = TimeTracker.activities.find(
                and(eq("username", username),
                        gte("start", start.getTimeInMillis()),
                        lte("start", stop.getTimeInMillis()),
                        (eq("activity", activity))
                )
        ).iterator();

        int count = 0;
        ArrayList<Activity> activities = new ArrayList<Activity>();

        try {
            while (cursor.hasNext()) {
                Activity act = cursor.next();
                activities.add(act);
                count++;
            }
        } finally {
            cursor.close();
        }

        System.out.println(count + " " + activity + " activity log(s) found for " + year);

        ObjectMapper om = new ObjectMapper();
        String json = om.writeValueAsString(activities);

        return json;
    }

}