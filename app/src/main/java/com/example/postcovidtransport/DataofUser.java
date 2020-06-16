package com.example.postcovidtransport;

import org.parceler.Parcel;

@Parcel
public class DataofUser {
    private String PNRNo;
     private String scheduleDept;
    private  String Date_of_Booking;
    private  String ResvUpto;
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
