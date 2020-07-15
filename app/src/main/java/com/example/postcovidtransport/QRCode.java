package com.example.postcovidtransport;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.postcovidtransport.about.Aboutus;
import com.example.postcovidtransport.ui.QRCode.QRCodeFragment;
import com.example.postcovidtransport.ui.report.reportFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import org.parceler.Parcels;

public class QRCode extends AppCompatActivity {
String TAG = "sarthak";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_q_r_code);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        Parcelable parcelable = getIntent().getParcelableExtra("dataofuser");
        final DataofUser dataofUser = Parcels.unwrap(parcelable);

        checkInternetConnection();
        navView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment selectedFrag = null;
                switch (item.getItemId()){
                    case R.id.qrcode:
                        selectedFrag = new QRCodeFragment(dataofUser);
                        Log.d(TAG, "onNavigationItemSelected: "+dataofUser.toString());
                        break;
                    case R.id.report_unhygiene:
                        selectedFrag = new reportFragment("test");
                        break;
                    default:
                        return false;
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFrag).commit();
                return true;
            }
        });

    }
    private void checkInternetConnection() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo =  connectivityManager.getActiveNetworkInfo();
        if(networkInfo == null){
            opendialog();
        }

    }
    private void opendialog() {
        DialogforConnectivity dialog = new DialogforConnectivity();
        dialog.show(getSupportFragmentManager(),"example");
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
            FirebaseAuth.getInstance().signOut();//logout
            startActivity(new Intent(getApplicationContext(),LoginActivity.class));
            finish();
        }
        else {
            return super.onOptionsItemSelected(item);
        }
        return true;
    }

}