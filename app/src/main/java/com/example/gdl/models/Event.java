package com.example.gdl.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

public class Event implements Parcelable {

    //Samuel's attributes
    private String mStartDate;
    private String mEventName;
    private ArrayList<Member> mEventMembers;
    private ArrayList<Bill> mEventBills;

    //Zichen's attributes
    public String eventName;
    public String eventTime;
    public String eventSpending;
    public String eventMembers;
    public int imageId;
    public boolean status;

    // Zhixuan's Parcelable implementation
    public static final Creator<Event> CREATOR = new Creator<Event>() {
        @Override
        public Event createFromParcel(Parcel in) {
            return new Event(in);
        }

        @Override
        public Event[] newArray(int size) {
            return new Event[size];
        }
    };

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

    protected Event(Parcel in) {
        mStartDate = in.readString();
        mEventName = in.readString();
        mEventMembers = in.createTypedArrayList(Member.CREATOR);
        mEventBills = in.createTypedArrayList(Bill.CREATOR);
        eventName = in.readString();
        eventTime = in.readString();
        eventSpending = in.readString();
        eventMembers = in.readString();
        imageId = in.readInt();
        status = in.readByte() != 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mStartDate);
        dest.writeString(mEventName);
        dest.writeTypedList(mEventMembers);
        dest.writeTypedList(mEventBills);
        dest.writeString(eventName);
        dest.writeString(eventTime);
        dest.writeString(eventSpending);
        dest.writeString(eventMembers);
        dest.writeInt(imageId);
        dest.writeByte((byte) (status ? 1 : 0));
    }

    @Override
    public int describeContents() {
        return 0;
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

    // Zhixuan's add-on methods
    public ArrayList<Bill> getmEventBills() {
        return mEventBills;
    }


}
