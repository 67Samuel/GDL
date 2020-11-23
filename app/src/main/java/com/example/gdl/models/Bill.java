package com.example.gdl.models;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

public class Bill {

    int mId;
    String mName;
    String mDate;
    Member mPayer;
    ArrayList<Member> mMembersList; //members who are splitting this bill
    HashMap<Member, Double> mExpensesMap; //members and how much they owe
    HashMap<Item, ArrayList<Member>> mItemsList; //items to pay for in bill

    int memberSize = 0;
    double spending = 0.0;



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

    public Bill() {
        mMembersList = new ArrayList<Member>();
        mExpensesMap= new HashMap<Member, Double>();
        mItemsList = new HashMap<Item, ArrayList<Member>>();
    }

    public void addMember(Member member) {
        mMembersList.add(member);
        memberSize++;
    }

    public void addItem(Item item) {
        //default: all members are splitting this item
        mItemsList.put(item, mMembersList);
        spending = spending + item.mCost;
    }

    public void addItem(Item item, ArrayList<Member> membersList) {
        //specify which members are splitting this item
        mItemsList.put(item, membersList);
    }

    public int memberSize(){
        return memberSize;
    }

    public double getSpending(){
        return spending;
    }


    private void calculateSplit() {
        //call all items in this bill, calculate itemExpensesMap for each of them and aggregate into mExpensesMap
        HashMap<Member, Double> itemExpensesMap;
        for (Item item : mItemsList.keySet()) {
            itemExpensesMap = item.splitExpense(); //get expenseMap from each item
            for (Member member : itemExpensesMap.keySet()) {
                Double itemValue = itemExpensesMap.get(member);
                if (mExpensesMap.containsKey(member)) {
                    mExpensesMap.put(member, mExpensesMap.get(member)+itemValue); //update each member's expense for each item
                }
            }
        }

    }

}
