package model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MasterOperationsSum {

    @SerializedName("id")
    @Expose
    private int masterId;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("surname")
    @Expose
    private String surname;

    @SerializedName("sum")
    @Expose
    private double sum;

    public int getMasterId() {
        return masterId;
    }

    public void setMasterId(int masterId) {
        this.masterId = masterId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public double getSum() {
        return sum;
    }

    public void setSum(double sum) {
        this.sum = sum;
    }
}