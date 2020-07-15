package com.example.postcovidtransport.ui.report;public class reporthelper
{
    public String seatnumber, description;

    public reporthelper(){

    }
    public reporthelper(String seatnumber, String description) {
        this.seatnumber = seatnumber;
        this.description = description;
    }

    public String getSeatnumber() {
        return seatnumber;
    }

    public void setSeatnumber(String seatnumber) {
        this.seatnumber = seatnumber;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
