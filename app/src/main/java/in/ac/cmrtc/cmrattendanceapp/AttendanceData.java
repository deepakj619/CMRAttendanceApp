package in.ac.cmrtc.cmrattendanceapp;

import java.util.ArrayList;

/**
 * Created by root on 30/6/17.
 */


public class AttendanceData implements  java.io.Serializable {


    public ArrayList<String> presentlist = new ArrayList<>();
    public ArrayList<String> absentlist  = new ArrayList<>();
    public ArrayList<String> periodlist= new ArrayList<>();
    public ArrayList<String> validperiods =new ArrayList<>();
    public String subject;

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public AttendanceData() {
    }

    public ArrayList<String> getValidperiods() {
        return validperiods;
    }

    public void setValidperiods(ArrayList<String> validperiods) {
        this.validperiods = validperiods;
    }

    public AttendanceData(ArrayList<String> presentlist, ArrayList<String> absentlist, ArrayList<String> periodlist, ArrayList<String> validperiods,String subject) {
        this.presentlist = presentlist;
        this.absentlist = absentlist;
        this.periodlist = periodlist;
        this.validperiods=validperiods;
        this.subject=subject;
    }

    public void setPresentlist(ArrayList<String> presentlist) {
        this.presentlist = presentlist;
    }

    public void setAbsentlist(ArrayList<String> absentlist) {
        this.absentlist = absentlist;
    }

    public void setPeriodlist(ArrayList<String> periodlist) {
        this.periodlist = periodlist;
    }


    public ArrayList<String> getPresentlist() {
        return presentlist;
    }

    public ArrayList<String> getAbsentlist() {
        return absentlist;
    }

    public ArrayList<String> getPeriodlist() {
        return periodlist;
    }





}
