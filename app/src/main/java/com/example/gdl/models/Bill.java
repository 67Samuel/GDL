package com.example.gdl.models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Bill implements Parcelable {

    int mId;
    String mName;
    String mDate;
    //photo
    Member mPayer;
    List<Member> mMembersList = new ArrayList<>(); //members who are splitting this bill, including payer
    Map<Member, Double> mExpensesMap = new HashMap<>(); //members and how much they owe, doesn't include payer
    int memberSize = 0;
    double spending = 0.0;
    public static final Creator<Bill> CREATOR = new Creator<Bill>() {
        @Override
        public Bill createFromParcel(Parcel in) {
            return new Bill(in);
        }

        @Override
        public Bill[] newArray(int size) {
            return new Bill[size];
        }
    };

    public Bill() {
    }

    public Bill(String name, String date, double cost, Member payer){
        this.mName = name;
        this.mDate = date;
        this.spending = cost;
        this.mPayer = payer;

    }

    protected Bill(Parcel in) {
        mId = in.readInt();
        mName = in.readString();
        mDate = in.readString();
        mPayer = in.readParcelable(Member.class.getClassLoader());
        mMembersList = in.createTypedArrayList(Member.CREATOR);
        memberSize = in.readInt();
        spending = in.readDouble();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(mId);
        dest.writeString(mName);
        dest.writeString(mDate);
        dest.writeParcelable(mPayer, flags);
        dest.writeTypedList(mMembersList);
        dest.writeInt(memberSize);
        dest.writeDouble(spending);
    }

    @Override
    public int describeContents() {
        return 0;
    }



    public int getmId() {
        return mId;
    }

    public void setmId(int mId) {
        this.mId = mId;
    }

    public String getName() {
        return mName;
    }

    public void setName(String mName) {
        this.mName = mName;
    }

    public String getDate() {
        return mDate;
    }

    public void setDate(String mDate) {
        this.mDate = mDate;
    }

    public Member getPayer() {
        return mPayer;
    }

    public void setPayer(Member mPayer) {
        this.mPayer = mPayer;
    }

    public void addMember(Member member) {
        mMembersList.add(member);
    }

    public void removeMember(Member member) {
        mMembersList.remove(member);
    }

    public int getMemberSize(){
        return mMembersList.size();
    }

    public double getSpending(){
        return spending;
    }

    public List<Member> getmMembersList() {
        return mMembersList;
    }

    public void setmMembersList(List<Member> mMembersList) {
        this.mMembersList = mMembersList;
    }

    public Map<Member, Double> getmExpensesMap() {
        return mExpensesMap;
    }

    public void calculateSplit() {
        double amtEachPerson = this.spending / this.getMemberSize();
        this.mExpensesMap = new HashMap<>();
        for(Member m : this.mMembersList){
            if(!m.equals(this.mPayer)) {
                this.mExpensesMap.put(m, amtEachPerson);
            }
        }
    }

    @NonNull
    @Override
    public String toString() {
        return "Bill(" + mName + ", " + spending + ")";
    }
}
