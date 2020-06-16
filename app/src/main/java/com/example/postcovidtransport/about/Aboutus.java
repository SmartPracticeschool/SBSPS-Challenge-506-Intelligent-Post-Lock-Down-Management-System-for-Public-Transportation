package com.example.postcovidtransport.about;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import com.example.postcovidtransport.R;

import java.util.ArrayList;

public class Aboutus extends AppCompatActivity {
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
        RecyclerView.Adapter adapter = new Adapter(titles);
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
}