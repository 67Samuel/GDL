package com.example.gdl.models;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;

public class Event implements Parcelable {

    private String id;
    private String name;
    private Uri eventPicture = null;
    private long timeInitialized;
    private boolean status; //completed=true, ongoing=false
    private double totalSpent;
    private ArrayList<Member> membersList;
    private ArrayList<Bill> billsList;
    private String date;

    public Event() {
    }

    public Event(String id, String name, ArrayList<Member> membersList, String date) {
        this.id = id;
        this.name = name;
        this.membersList = membersList;
        Date date1 = new Date();
        this.timeInitialized = date1.getTime();
        this.status = false;
        this.date = date;
        this.totalSpent = 0;
    }


    protected Event(Parcel in) {
        id = in.readString();
        name = in.readString();
        eventPicture = in.readParcelable(Uri.class.getClassLoader());
        timeInitialized = in.readLong();
        status = in.readByte() != 0;
        totalSpent = in.readDouble();
        membersList = in.createTypedArrayList(Member.CREATOR);
        billsList = in.createTypedArrayList(Bill.CREATOR);
        date = in.readString();
    }

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

    public void setStatus(boolean status) {
        this.status = status;
    }

    public void setBillsList(ArrayList<Bill> billsList) {
        this.billsList = billsList;
    }

    public void setEventPicture(Uri eventPicture) {
        this.eventPicture = eventPicture;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDate() {
        return date;
    }

    public double getTotalSpent() {
        return totalSpent;
    }

    public long getTimeInitialized() {
        return timeInitialized;
    }

    public boolean getStatus() {
        return status;
    }

    public ArrayList<Member> getMembersList() {
        return membersList;
    }

    public ArrayList<Bill> getBillsList() {
        return billsList;
    }

    public Uri getEventPicture() {
        return eventPicture;
    }

    @Override
    public String toString() {
        return "Event{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", timeInitialized=" + timeInitialized +
                ", status=" + status +
                ", membersList=" + membersList +
                ", billsList=" + billsList +
                '}';
    }

    public void calculateTotalSpent() {
        for (Bill bill : billsList) {
            totalSpent+=bill.getTotalCost();
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(name);
        dest.writeParcelable(eventPicture, flags);
        dest.writeLong(timeInitialized);
        dest.writeByte((byte) (status ? 1 : 0));
        dest.writeDouble(totalSpent);
        dest.writeTypedList(membersList);
        dest.writeTypedList(billsList);
        dest.writeString(date);
    }
}
