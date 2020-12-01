package com.example.gdl.models;


import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;
import android.widget.ImageView;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class Member implements Parcelable {

    private static final String TAG = "Member";

    private String id;
    private String name;
    private String email;
    private String status;
    private double debt;
    private double lent;
    private List<String> events;
    private List<String> friends;


    protected Member(Parcel in) {
        id = in.readString();
        name = in.readString();
        email = in.readString();
        status = in.readString();
        debt = in.readDouble();
        lent = in.readDouble();
        events = in.createStringArrayList();
        friends = in.createStringArrayList();
    }

    public static final Creator<Member> CREATOR = new Creator<Member>() {
        @Override
        public Member createFromParcel(Parcel in) {
            return new Member(in);
        }

        @Override
        public Member[] newArray(int size) {
            return new Member[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(name);
        dest.writeString(email);
        dest.writeString(status);
        dest.writeDouble(debt);
        dest.writeDouble(lent);
        dest.writeStringList(events);
        dest.writeStringList(friends);
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public double getDebt() {
        return debt;
    }

    public void setDebt(double debt) {
        this.debt = debt;
    }

    public double getLent() {
        return lent;
    }

    public void setLent(double lent) {
        this.lent = lent;
    }

    public List<String> getEvents() {
        return events;
    }

    public void setEvents(List<String> events) {
        this.events = events;
    }

    public List<String> getFriends() {
        return friends;
    }

    public void setFriends(List<String> friends) {
        this.friends = friends;
    }
}