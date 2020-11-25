package com.example.gdl.models;


import java.util.ArrayList;
import java.util.HashMap;

public class Item {

    int mId;
    String mName;
    double mCost;
    ArrayList<Member> mMembersList;
    HashMap<Member, Double> mExpensesMap;

    public HashMap<Member, Double> splitExpense() {

        return mExpensesMap;

    }
}
