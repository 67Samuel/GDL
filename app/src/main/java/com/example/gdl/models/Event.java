package com.example.gdl.models;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

public class Event {

    private String mStartDate;
    private String mEventName;
    private ArrayList<Member> mEventMembers;
    //private ArrayList<Bill> mEventBills;

    public Event(String mStartDate, String mEventName, ArrayList<Member> mEventMembers) {
        this.mStartDate = mStartDate;
        this.mEventName = mEventName;
        this.mEventMembers = mEventMembers;
    }
}
