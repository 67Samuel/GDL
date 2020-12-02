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
    private Uri picId;
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
        picId = in.readParcelable(Uri.class.getClassLoader());
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPicId(Uri mPicId) {
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

    public Uri getPicId() {
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
        dest.writeParcelable(picId, flags);
        dest.writeDouble(debt);
        dest.writeDouble(lent);
        dest.writeStringList(eventsList);
        dest.writeStringList(friendsList);
    }
}
