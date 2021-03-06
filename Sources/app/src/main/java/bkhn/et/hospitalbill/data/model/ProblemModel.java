package bkhn.et.hospitalbill.data.model;

import java.io.Serializable;

/**
 * Created by PL_itto on 5/15/2018.
 */

public class ProblemModel implements Serializable {
    String id;
    double cost = 0;
    String name;
    String unit;
    String departmentId;
    int amount = 0;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public String getName() {
        return name;
    }

    public String getCostString() {
//        return String.valueOf(getCost()) + " " + getUnit();
        return String.format("%.2f", getCost()) + " " + getUnit();
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public ProblemModel clone() {
        ProblemModel model = new ProblemModel();
        model.setId(getId());
        model.setCost(getCost());
        model.setName(getName());
        model.setUnit(getUnit());
        model.setDepartmentId(getDepartmentId());
        model.setAmount(1);
        return model;
    }
}
