package com.example.gdl.models;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Bill implements Parcelable {

    private String id;
    private String name;
    private long timeInitialized;
    private Uri receiptPicture = null;
    private Member payer;
    private List<String> membersList; //members who are splitting this bill, including payer
    private Map<String, Double> expensesMap; //members and how much they owe, doesn't include payer
    private int memberSize = 0;
    private double totalCost = 0.0;

    public Bill() {
    }

    public Bill(String mId, String mName, Member mPayer, List<String> mMembersList, double totalCost) {
        this.id = mId;
        this.name = mName;
        Date date = new Date();
        this.timeInitialized = date.getTime();
        this.payer = mPayer;
        this.membersList = mMembersList;
        this.totalCost = totalCost;

        memberSize = mMembersList.size();
    }

    protected Bill(Parcel in) {
        id = in.readString();
        name = in.readString();
        timeInitialized = in.readLong();
        receiptPicture = in.readParcelable(Uri.class.getClassLoader());
        payer = in.readParcelable(Member.class.getClassLoader());
        membersList = in.createStringArrayList();
        memberSize = in.readInt();
        totalCost = in.readDouble();
    }

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

    public void setReceiptPicture(Uri mReceiptPicture) {
        this.receiptPicture = mReceiptPicture;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public long getTimeInitialized() {
        return timeInitialized;
    }

    public Uri getReceiptPicture() {
        return receiptPicture;
    }

    public Member getPayer() {
        return payer;
    }

    public List<String> getMembersList() {
        return membersList;
    }

    public Map<String, Double> getExpensesMap() {
        return expensesMap;
    }

    public int getMemberSize() {
        return memberSize;
    }

    public double getTotalCost() {
        return totalCost;
    }

    public void calculateSplit() {
        double amtEachPerson = this.totalCost / this.getMemberSize();
        this.expensesMap = new HashMap<>();
        for(String m : this.membersList){
            if(!m.equals(this.payer)) {
                this.expensesMap.put(m, amtEachPerson);
            }
        }
    }

    @Override
    public String toString() {
        return "Bill{" +
                "Id='" + id + '\'' +
                ", Name='" + name + '\'' +
                ", TimeInitialized=" + timeInitialized +
                ", ReceiptPicture=" + receiptPicture +
                ", Payer=" + payer +
                ", MembersList=" + membersList +
                ", ExpensesMap=" + expensesMap +
                ", memberSize=" + memberSize +
                ", totalCost=" + totalCost +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(name);
        dest.writeLong(timeInitialized);
        dest.writeParcelable(receiptPicture, flags);
        dest.writeParcelable(payer, flags);
        dest.writeStringList(membersList);
        dest.writeInt(memberSize);
        dest.writeDouble(totalCost);
    }
}
