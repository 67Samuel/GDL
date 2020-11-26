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

    static ArrayList<Member> mAllMembers = new ArrayList<>();
    static ArrayList<Integer> mIdList = new ArrayList<>();
    String mName;
    Integer mId=null;
    //Integer mPicId;
    boolean mStatus;
    double mDebt;
    double mLent;
    //TODO: get photo from database and set it to mProfilePhoto
    ImageView mProfilePhoto; //hard to get this
    HashMap<Integer, Event> mEvents;

    boolean IN_DEBT = false;
    boolean NOT_IN_DEBT = true;

    protected Member(Parcel in) {
        mName = in.readString();
        mId = in.readInt();
        //mPicId = in.readInt();
        mStatus = in.readByte() != 0;
        mDebt = in.readDouble();
        mLent = in.readDouble();
        IN_DEBT = in.readByte() != 0;
        NOT_IN_DEBT = in.readByte() != 0;
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


    public Member(String name, int id) {
        // create a Member obj with name and first available Id in mIdList
        //TODO: get all data from database
        mId = id;
        mAllMembers.add(this);
        Log.d(TAG, "Member: new member added: " + this);
        mStatus = NOT_IN_DEBT;
        mDebt = 0;
        mLent = 0;
        mName = name;
        //mPicId = 0;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mName);
        dest.writeInt(mId);
        dest.writeByte((byte) (mStatus ? 1 : 0));
        dest.writeDouble(mDebt);
        dest.writeDouble(mLent);
        dest.writeByte((byte) (IN_DEBT ? 1 : 0));
        dest.writeByte((byte) (NOT_IN_DEBT ? 1 : 0));
    }

    @Override
    public String toString() {
        return "Member{" +
                "Id=" + mId +
                "Name=" + mName +
                '}';
    }

    public String getName() {
        return mName;
    }

    public Integer getId() {
        return mId;
    }

    public boolean isStatus() {
        return mStatus;
    }

    public double getDebt() {
        return mDebt;
    }

    public double getLent() {
        return mLent;
    }

    //public Integer getPicId() {return mPicId; }
}
