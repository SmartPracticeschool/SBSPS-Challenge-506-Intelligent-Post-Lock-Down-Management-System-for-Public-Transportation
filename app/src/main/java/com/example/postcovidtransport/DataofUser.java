package com.example.postcovidtransport;

import org.parceler.Parcel;

@Parcel
public class DataofUser {
    private String PNRNo;
     private String scheduleDept;

    public DataofUser() {
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
