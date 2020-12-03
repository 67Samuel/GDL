package com.example.gdl.models;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;


import java.util.ArrayList;
import java.util.List;

public class Member implements Parcelable {

    private static final String TAG = "Member";

    private String name;
    private String id;
    private String email;
    private String picId;
    private double debt;
    private double lent;
    private List<String> eventsList = null;
    private List<String> friendsList = null;


    public Member() {
    }

    public Member(String mName, String mId) {
        this.name = mName;
        this.id = mId;
        this.debt = 0;
        this.lent = 0;
        this.picId = null;
        this.email = null;

    }

    protected Member(Parcel in) {
        name = in.readString();
        id = in.readString();
        email = in.readString();
        picId = in.readString();
        debt = in.readDouble();
        lent = in.readDouble();
        eventsList = in.createStringArrayList();
        friendsList = in.createStringArrayList();
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
    public String toString() {
        return "Member{" +
                "Id=" + id +
                "Name=" + name +
                '}';
    }

    public Uri getUriFromString() {
        Uri uri = Uri.parse(picId);
        return uri;
    }

    public void setFriendsList(List<String> friendsList) {
        this.friendsList = friendsList;
    }

    public List<String> getFriendsList() {
        return friendsList;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPicId(String mPicId) {
        this.picId = mPicId;
    }

    public void setDebt(double mDebt) {
        this.debt = mDebt;
    }

    public void setLent(double mLent) {
        this.lent = mLent;
    }

    public void setEventsList(List<String> mEvents) {
        this.eventsList = mEvents;
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public String getPicId() {
        return picId;
    }

    public double getDebt() {
        return debt;
    }

    public double getLent() {
        return lent;
    }

    public List<String> getEventsList() {
        return eventsList;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(id);
        dest.writeString(email);
        dest.writeString(picId);
        dest.writeDouble(debt);
        dest.writeDouble(lent);
        dest.writeStringList(eventsList);
        dest.writeStringList(friendsList);
    }
}
