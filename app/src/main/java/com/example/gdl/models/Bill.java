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
    private String receiptPicture = null;
    private Member payer;
    private List<String> membersList;
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

        memberSize = mMembersList.size()+1;
    }


    protected Bill(Parcel in) {
        id = in.readString();
        name = in.readString();
        timeInitialized = in.readLong();
        receiptPicture = in.readString();
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

    public Uri getUriFromString() {
        Uri uri = Uri.parse(receiptPicture);
        return uri;
    }

    public void setReceiptPicture(String mReceiptPicture) {
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

    public String getReceiptPicture() {
        return receiptPicture;
    }

    public Member getPayer() {
        return payer;
    }

    public void setPayer(Member m){ payer = m; }

    public List<String> getMembersList() {
        return membersList;
    }

    public void setMembersList(List<String> membersList) {
        this.membersList = membersList;
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

    public void setTotalCost(double totalCost){this.totalCost = totalCost;}

    public void calculateSplit() {
        double amtEachPerson = this.totalCost / (this.membersList.size() + 1);
        this.expensesMap = new HashMap<>();
        for(String m : this.membersList){
            // assuming payer not inside here
            this.expensesMap.put(m, amtEachPerson);
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
        dest.writeString(receiptPicture);
        dest.writeParcelable(payer, flags);
        dest.writeStringList(membersList);
        dest.writeInt(memberSize);
        dest.writeDouble(totalCost);
    }
}
