package bkhn.et.hospitalbill.data.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import bkhn.et.hospitalbill.utils.AppUtils;

/**
 * Created by PL_itto on 5/17/2018.
 */

public class RecordModel implements Serializable {
    List<ProblemModel> mProblemList;
    String doctorId;
    String doctorName;
    String note;
    String patientId;
    String patientName;
    long time;
    String recordId;

    public RecordModel() {
        mProblemList = new ArrayList<>();
    }

    public List<ProblemModel> getProblemList() {
        return mProblemList;
    }

    public ProblemModel getProblemAt(int pos) {
        return mProblemList.get(pos);
    }

    public void setProblemList(List<ProblemModel> problemList) {
        mProblemList = problemList;
    }

    public int getProblemCount() {
        return mProblemList.size();
    }

    public String getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(String doctorId) {
        this.doctorId = doctorId;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public long getTime() {
        return time;
    }

    public String getTimeLiteString() {
        return AppUtils.getTimeLite(getTime());
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getRecordId() {
        return recordId;
    }

    public void setRecordId(String recordId) {
        this.recordId = recordId;
    }
}
