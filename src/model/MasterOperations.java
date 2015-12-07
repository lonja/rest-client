package model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class MasterOperations {

//    @SerializedName("id")
//    @Expose
//    private int masterId;

    @SerializedName("title")
    @Expose
    private String title;

    @SerializedName("price")
    @Expose
    private double price;

    @SerializedName("startDate")
    @Expose
    private Date startDate;

    @SerializedName("finishDate")
    @Expose
    private Date finishDate;

//    public int getMasterId() {
//        return masterId;
//    }
//
//    public void setMasterId(int masterId) {
//        this.masterId = masterId;
//    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getFinishDate() {
        return finishDate;
    }

    public void setFinishDate(Date finishDate) {
        this.finishDate = finishDate;
    }

    @Override
    public String toString() {
        return "Перечень операций, выполнявшихся мастером";
    }
}
