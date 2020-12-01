package com.example.gdl.models;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;


import java.util.ArrayList;

public class Member implements Parcelable {

    private static final String TAG = "Member";

    private String name;
    private String id;
    private Uri picId;
    private double debt;
    private double lent;
    private ArrayList<Event> eventsList;

    //For use only to calculate amt owed per event   ~Zhixuan
    private double credit = 0.0;
    private double debit = 0.0;

    public Member() {
    }

    public Member(String mName, String mId) {
        this.name = mName;
        this.id = mId;
        this.debt = 0;
        this.lent = 0;
    }

    protected Member(Parcel in) {
        name = in.readString();
        id = in.readString();
        picId = in.readParcelable(Uri.class.getClassLoader());
        debt = in.readDouble();
        lent = in.readDouble();
        eventsList = in.createTypedArrayList(Event.CREATOR);
        credit = in.readDouble();
        debit = in.readDouble();
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

    public void setPicId(Uri mPicId) {
        this.picId = mPicId;
    }

    public void setDebt(double mDebt) {
        this.debt = mDebt;
    }

    public void setLent(double mLent) {
        this.lent = mLent;
    }

    public void setEventsList(ArrayList<Event> mEvents) {
        this.eventsList = mEvents;
    }

    public void setCredit(double credit) {
        this.credit = credit;
    }

    public void setDebit(double debit) {
        this.debit = debit;
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

    public ArrayList<Event> getEventsList() {
        return eventsList;
    }

    public double getCredit() {
        return credit;
    }

    public double getDebit() {
        return debit;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(id);
        dest.writeParcelable(picId, flags);
        dest.writeDouble(debt);
        dest.writeDouble(lent);
        dest.writeTypedList(eventsList);
        dest.writeDouble(credit);
        dest.writeDouble(debit);
    }

}
