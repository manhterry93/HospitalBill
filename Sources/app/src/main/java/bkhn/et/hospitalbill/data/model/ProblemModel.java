package bkhn.et.hospitalbill.data.model;

/**
 * Created by PL_itto on 5/15/2018.
 */

public class ProblemModel {
    String id;
    double cost;
    String name;
    String unit;
    String departmentId;
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
        return String.valueOf(getCost()) + " " + getUnit();
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
}
