package bkhn.et.hospitalbill.data.model;

import java.io.Serializable;

import bkhn.et.hospitalbill.utils.AppUtils;

/**
 * Created by PL_itto on 5/27/2018.
 */

public class BillModel implements Serializable {
    String id;
    String recordId;
    String staffId;
    String staffName;
    long time;
    boolean paid;
    String note;
    String insuranceId;
    boolean useInsurance = false;
    double totalCost = 0;
    double insuranceDiscount = 0;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRecordId() {
        return recordId;
    }

    public void setRecordId(String recordId) {
        this.recordId = recordId;
    }

    public String getStaffId() {
        return staffId;
    }

    public void setStaffId(String staffId) {
        this.staffId = staffId;
    }

    public String getStaffName() {
        return staffName;
    }

    public void setStaffName(String staffName) {
        this.staffName = staffName;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public boolean isPaid() {
        return paid;
    }

    public void setPaid(boolean paid) {
        this.paid = paid;
    }

    public String getTimeLiteString() {
        return AppUtils.getTimeLite(getTime());
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getInsuranceId() {
        return insuranceId;
    }

    public void setInsuranceId(String insuranceId) {
        this.insuranceId = insuranceId;
    }


    public boolean isUseInsurance() {
        return useInsurance;
    }

    public void setUseInsurance(boolean useInsurance) {
        this.useInsurance = useInsurance;
    }

    public double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(double totalCost) {
        this.totalCost = totalCost;
    }

    public double getInsuranceDiscount() {
        return insuranceDiscount;
    }

    public void setInsuranceDiscount(double insuranceDiscount) {
        this.insuranceDiscount = insuranceDiscount;
    }
}
