package bkhn.et.hospitalbill.data.model;

import android.content.Context;

import bkhn.et.hospitalbill.R;

/**
 * Created by PL_itto on 5/15/2018.
 */

public class DepartmentModel {
    String id;
    String name;

    // Create a department with id =-1 (ALL)
    public DepartmentModel(Context context) {
        id = "-1";
        name = context.getString(R.string.all);
    }

    public DepartmentModel() {

    }

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
}
