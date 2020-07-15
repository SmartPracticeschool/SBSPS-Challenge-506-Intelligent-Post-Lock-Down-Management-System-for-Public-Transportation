package com.example.postcovidtransport;

import org.parceler.Parcel;

@Parcel
public class DataofUser {
    String PNRNo ="";
      String scheduleDept="";
      String Date_of_Booking="";
      String ResvUpto="";
     String trainnumber="";
     String aadharno="";
     String boardingstation="";
     boolean prioritizedEntry = false;

    public String getUserclass() {
        return userclass;
    }

    public void setUserclass(String userclass) {
        this.userclass = userclass;
    }

     String userclass = "";

    @Override
    public String toString() {
        return "DataofUser{" +
                "PNRNo='" + PNRNo + '\'' +
                ", scheduleDept='" + scheduleDept + '\'' +
                ", Date_of_Booking='" + Date_of_Booking + '\'' +
                ", ResvUpto='" + ResvUpto + '\'' +
                ", trainnumber='" + trainnumber + '\'' +
                ", aadharno='" + aadharno + '\'' +
                ", boardingstation='" + boardingstation + '\'' +
                ", prioritizedEntry=" + prioritizedEntry +
                ", userclass='" + userclass + '\'' +
                '}';
    }

    public String getBoardingstation() {
        return boardingstation;
    }

    public void setBoardingstation(String boardingstation) {
        this.boardingstation = boardingstation;
    }

    public boolean isPrioritizedEntry() {
        return prioritizedEntry;
    }

    public void setPrioritizedEntry(boolean prioritizedEntry) {
        this.prioritizedEntry = prioritizedEntry;
    }

    public String getTrainnumber() {
        return trainnumber;
    }

    public void setTrainnumber(String trainnumber) {
        this.trainnumber = trainnumber;
    }

    public String getAadharno() {
        return aadharno;
    }

    public void setAadharno(String aadharno) {
        this.aadharno = aadharno;
    }

    public DataofUser() {
    }

    public String getResvUpto() {
        return ResvUpto;
    }

    public void setResvUpto(String resvUpto) {
        ResvUpto = resvUpto;
    }

    public String getDate_of_Booking() {
        return Date_of_Booking;
    }

    public void setDate_of_Booking(String date_of_Booking) {
        Date_of_Booking = date_of_Booking;
    }





    public String getPNRNo() {
        return PNRNo;
    }

    public void setPNRNo(String PNRNo) {
        this.PNRNo = PNRNo;
    }

    public String getScheduleDept() {
        return scheduleDept;
    }

    public void setScheduleDept(String scheduleDept) {
        this.scheduleDept = scheduleDept;
    }
}
