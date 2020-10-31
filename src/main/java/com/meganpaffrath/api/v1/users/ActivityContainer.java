package com.meganpaffrath.api.v1.users;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.meganpaffrath.TimeTracker;
import com.meganpaffrath.pojos.Activity;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.client.FindIterable;
import org.bson.*;

// for mongo

import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

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
        newActivity.start = start.getTime();
        newActivity.end = end.getTime();

        Map<String, Object> map = new ObjectMapper().convertValue(newActivity, Map.class);
        Document newAct = new Document(map);
        TimeTracker.activities.insertOne(newAct);


        return (
                "Start time: " + start.getTime()
                        + "\nEnd: " + end.getTime()
                        + "\nTotal: " + minutesT + " minutes"
        );
    }

    public String getYear(String username, String year, String activity) throws Exception {
        int yr = Integer.parseInt(year);
        String endStr = String.format("12/31/%d 23:59:59", yr);
        String startStr = String.format("12/31/%d 23:59:59", (yr-1) );
        DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        Calendar start = new Calendar.Builder().setInstant(df.parse(startStr)).build();
        Calendar stop = new Calendar.Builder().setInstant(df.parse(endStr)).build();


//        Calendar stop = new Calendar.Builder().setInstant(df.parse(endStr)).build();
        System.out.println("Start " + start.getTime());
        System.out.println("Stop " + stop.getTime());

        FindIterable cursor = TimeTracker.activities.find(and(eq("username", username),
                gte("start", start.getTimeInMillis()), lte("start", stop.getTimeInMillis()),
                (eq("activity", activity)))  );

        int count = 0;

        for (Object document : cursor) {
            System.out.println(document.toString());
            count++;
        }

        return (count + " " + activity + " activity log(s) found for " + year);
    }

}