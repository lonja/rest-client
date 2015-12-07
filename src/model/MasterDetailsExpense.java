package model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class MasterDetailsExpense {

    @SerializedName("date")
    @Expose
    private Date date;

//    @SerializedName("id")
//    @Expose
//    private int masterId;
//
//    @SerializedName("name")
//    @Expose
//    private String name;
//
//    @SerializedName("surname")
//    @Expose
//    private String surname;

    @SerializedName("title")
    @Expose
    private String detailTitle;


    @SerializedName("quantity")
    @Expose
    private int quantity;

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

//    public int getMasterId() {
//        return masterId;
//    }
//
//    public void setMasterId(int masterId) {
//        this.masterId = masterId;
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    public String getSurname() {
//        return surname;
//    }
//
//    public void setSurname(String surname) {
//        this.surname = surname;
//    }

    public String getDetailTitle() {
        return detailTitle;
    }

    public void setDetailTitle(String detailTitle) {
        this.detailTitle = detailTitle;
    }
}
