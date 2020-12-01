package com.example.gdl.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class Event implements Parcelable {

    private String id;
    private String name;
    private String date;
    private String status;
    private List<String> membersIDs;
    private List<String> billsIDs;
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

    protected Event(Parcel in) {
        id = in.readString();
        name = in.readString();
        date = in.readString();
        status = in.readString();
        membersIDs = in.createStringArrayList();
        billsIDs = in.createStringArrayList();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(name);
        dest.writeString(date);
        dest.writeString(status);
        dest.writeStringList(membersIDs);
        dest.writeStringList(billsIDs);
    }

    @Override
    public int describeContents() {
        return 0;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<String> getMembersIDs() {
        return membersIDs;
    }

    public void setMembersIDs(List<String> membersIDs) {
        this.membersIDs = membersIDs;
    }

    public List<String> getBillsIDs() {
        return billsIDs;
    }

    public void setBillsIDs(List<String> billsIDs) {
        this.billsIDs = billsIDs;
    }





}
