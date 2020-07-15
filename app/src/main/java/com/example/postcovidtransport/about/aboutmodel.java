package com.example.postcovidtransport.about;

import android.widget.ImageView;

public class aboutmodel {
    private String title;
    private int imageid;

    public int getImageid() {
        return imageid;
    }

    public void setImageid(int imageid) {
        this.imageid = imageid;
    }

    public aboutmodel() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

}
