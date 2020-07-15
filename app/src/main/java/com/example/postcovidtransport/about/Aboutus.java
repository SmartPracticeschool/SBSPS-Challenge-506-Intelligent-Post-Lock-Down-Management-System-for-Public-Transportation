package com.example.postcovidtransport.about;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import com.example.postcovidtransport.R;

import java.util.ArrayList;

public class Aboutus extends AppCompatActivity implements Adapter.onClick {
RecyclerView recyclerView;
ArrayList<aboutmodel> titles;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aboutus);
        recyclerView = findViewById(R.id.recyclerview);
        titles = new ArrayList<>();
        fillarraylist();
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        RecyclerView.Adapter adapter = new Adapter(titles,this);
     //  Log.e("a",adapter.getItemCount()+" ");
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    private void fillarraylist() {
        aboutmodel a1 = new aboutmodel();
        a1.setTitle("Found a bug , Report us !!");
        a1.setImageid(R.drawable.unhygiene);
        aboutmodel a2 = new aboutmodel();
        a2.setTitle("VERSION 1.0.1");
        a2.setImageid(R.drawable.version);
        aboutmodel a3 = new aboutmodel();
        a3.setTitle("ABOUT THE APP");
        a3.setImageid(R.drawable.about);
        aboutmodel a4 = new aboutmodel();
        a4.setTitle("Created by Rajat , Shaurya Dutta and Sarthak Sethi");
        a4.setImageid(R.drawable.created);
//        titles.add("VERSION 1.0.1");
//        titles.add("ABOUT THE APP");
//        titles.add("Created by Rajat , Shaurya Dutta and Sarthak Sethi");
        titles.add(a1);
        titles.add(a2);
        titles.add(a3);
        titles.add(a4);
    }

    @Override
    public void onClick(int position) {
        Log.e("pos",position+" ");
        if (position == 0){
            sendmail();
        }
        else if (position == 2) {
         opendialog();
        }
    }

    private void opendialog() {
        Dialog dialog = new Dialog();
        dialog.show(getSupportFragmentManager(),"example");
    }

    private void sendmail() {
        String emailaddress = "immutablecoders@gmail.com";
        String subject = "BUG REPORT";
        String message = "--Support Info--\n" +
                "Debug info: +918557024411\n" +
                "Version: 1.0.1\n" +
                "App: com.example.postcovidtransport\n" +
                "Socket Conn: UP\n" +
                "Target: release\n" ;
        Intent i = new Intent(Intent.ACTION_SEND);
        i.putExtra(Intent.EXTRA_EMAIL,new String[]{emailaddress});
        i.putExtra(Intent.EXTRA_SUBJECT,subject);
        i.putExtra(Intent.EXTRA_TEXT,message);
        i.setType("message/rfc822");
        startActivity(Intent.createChooser(i,"choose an email client"));
    }
}