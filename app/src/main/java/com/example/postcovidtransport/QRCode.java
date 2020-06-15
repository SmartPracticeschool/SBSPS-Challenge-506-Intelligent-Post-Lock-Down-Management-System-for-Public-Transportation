package com.example.postcovidtransport;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;

import com.example.postcovidtransport.ui.QRCode.QRCodeFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import org.parceler.Parcels;

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
        Parcelable parcelable = getIntent().getParcelableExtra("dataofuser");
        DataofUser dataofUser = Parcels.unwrap(parcelable);

        QRCodeFragment qrCodeFragment = new QRCodeFragment();
//        Bundle bundle = new Bundle();
//        bundle.putString("test","sarthak");
//       // bundle.putParcelable("data",parcelable);
//        qrCodeFragment.setArguments(bundle);
        Intent i = new Intent(QRCode.this,QRCodeFragment.class);
        i.putExtra("a","sarthak");
        Log.e("check here ",dataofUser.getPNRNo());
        startActivity(i);

    }

}