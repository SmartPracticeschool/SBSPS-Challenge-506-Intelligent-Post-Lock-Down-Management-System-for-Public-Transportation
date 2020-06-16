package com.example.postcovidtransport;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.postcovidtransport.about.Aboutus;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

public class QRCode extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_q_r_code);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
//        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
//                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
//                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
//        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);
      //  Parcelable parcelable = getIntent().getParcelableExtra("dataofuser");
        //DataofUser dataofUser = Parcels.unwrap(parcelable);

        //QRCodeFragment qrCodeFragment = new QRCodeFragment();
//        Bundle bundle = new Bundle();
//        bundle.putString("test","sarthak");
//       // bundle.putParcelable("data",parcelable);
//        qrCodeFragment.setArguments(bundle);
     //   Intent i = new Intent(QRCode.this,QRCode.class);
      //  i.putExtra("a","sarthak");
        //Log.e("check here ",dataofUser.getPNRNo());
       // startActivity(i);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.top_menu,menu);
        return true;
       // return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.aboutus){
            Intent i = new Intent(getApplicationContext(), Aboutus.class);
            startActivity(i);
        }
        else if(item.getItemId() == R.id.logout){
            // RAjat
        }
        else {
            return super.onOptionsItemSelected(item);
        }
        return true;
    }
}