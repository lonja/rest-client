package model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class OperationMaster {

    @SerializedName("masterId")
    @Expose
    private int masterId;

    @SerializedName("operationId")
    @Expose
    private int operationId;

    @SerializedName("startDate")
    @Expose
    private Date startDate;

    @SerializedName("finishDate")
    @Expose
    private Date finishDate;

    public int getMasterId() {
        return masterId;
    }

    public void setMasterId(int masterId) {
        this.masterId = masterId;
    }

    public int getOperationId() {
        return operationId;
    }

    public void setOperationId(int operationId) {
        this.operationId = operationId;
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
}
