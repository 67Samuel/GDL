package com.example.gdl.models;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

public class Event {

    //Samuel's attributes
    private String mStartDate;
    private String mEventName;
    private ArrayList<Member> mEventMembers;
    //private ArrayList<Bill> mEventBills;

    //Zichen's attributes
    public String eventName;
    public String eventTime;
    public String eventSpending;
    public String eventMembers;
    public int imageId;
    public boolean status;

    // Samuel's constructor function
    public Event(String mStartDate, String mEventName, ArrayList<Member> mEventMembers) {
        //Samuel's attributes
        this.mStartDate = mStartDate;
        this.mEventName = mEventName;
        this.mEventMembers = mEventMembers;
    }

    // Zichen's constructor function
    public Event(String eventName, String eventTime, String eventSpending, String eventMembers, int imageId, boolean status) {

        this.eventName = eventName;
        this.imageId = imageId;
        this.eventName = eventName;
        this.eventTime = eventTime;
        this.eventSpending = eventSpending;
        this.eventMembers = eventMembers;
        this.status = status;
    }

    public String getEventName(){
        return eventName;
    }
    public String getEventTime(){
        return eventTime;
    }
    public String getEventSpending(){
        return eventSpending;
    }
    public String getEventMembers(){
        return eventMembers;
    }
    public int getImageId(){
        return imageId;
    }
    public boolean getStatus() {
        return status;
    }


}
