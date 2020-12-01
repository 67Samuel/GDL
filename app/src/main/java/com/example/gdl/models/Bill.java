package com.example.gdl.models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Bill implements Parcelable {

    private String id;
    private String name;
    private double spending;
    //photo
    private String payer;
    private List<String> payees;


    //getters and setters

    protected Bill(Parcel in) {
        id = in.readString();
        name = in.readString();
        spending = in.readDouble();
        payer = in.readString();
        payees = in.createStringArrayList();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(name);
        dest.writeDouble(spending);
        dest.writeString(payer);
        dest.writeStringList(payees);
    }

    @Override
    public int describeContents() {
        return 0;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getSpending() {
        return spending;
    }

    public void setSpending(double spending) {
        this.spending = spending;
    }

    public String getPayer() {
        return payer;
    }

    public void setPayer(String payer) {
        this.payer = payer;
    }

    public List<String> getPayees() {
        return payees;
    }

    public void setPayees(List<String> payees) {
        this.payees = payees;
    }
}
