package com.example.postcovidtransport;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

//import com.ibm.mobilefirstplatform.clientsdk.android.push.api.MFPPush;
//import com.ibm.mobilefirstplatform.clientsdk.android.core.api.BMSClient;
//import com.ibm.mobilefirstplatform.clientsdk.android.push.api.MFPPushNotificationButton;
//import com.ibm.mobilefirstplatform.clientsdk.android.push.api.MFPPushNotificationCategory;
//import com.ibm.mobilefirstplatform.clientsdk.android.push.api.MFPPushNotificationListener;
//import com.ibm.mobilefirstplatform.clientsdk.android.push.api.MFPPushNotificationOptions;
//import com.ibm.mobilefirstplatform.clientsdk.android.push.api.MFPSimplePushNotification;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    Button mLoginBtn, mSignupBtn;
    ImageView logoimage;
    TextView title;
//    private MFPPush push = null;
//    private MFPPushNotificationListener notificationListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        checkInternetConnection();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        title= findViewById(R.id.title);
        mLoginBtn = findViewById(R.id.login);
        mSignupBtn = findViewById(R.id.signup);
        //logoimage = findViewById(R.id.logoimg);
//        BMSClient.getInstance().initialize(this, BMSClient.REGION_SYDNEY);
//
//        MFPPushNotificationOptions options = new MFPPushNotificationOptions();
//        MFPPushNotificationButton firstButton = new MFPPushNotificationButton.Builder("Accept Button")
//                .setIcon("check_circle_icon")
//                .setLabel("Accept")
//                .build();
//
//        MFPPushNotificationButton secondButton = new MFPPushNotificationButton.Builder("Decline Button")
//                .setIcon("extension_circle_icon")
//                .setLabel("Decline")
//                .build();
//
//        MFPPushNotificationButton secondButton1 = new MFPPushNotificationButton.Builder("Decline Button2")
//                .setIcon("extension_circle_icon")
//                .setLabel("Decline2")
//                .build();
//
//        List<MFPPushNotificationButton> getButtons =  new ArrayList<MFPPushNotificationButton>();
//        getButtons.add(firstButton);
//        getButtons.add(secondButton);
//        getButtons.add(secondButton1);
//
//        List<MFPPushNotificationButton> getButtons1 =  new ArrayList<MFPPushNotificationButton>();
//        getButtons1.add(firstButton);
//        getButtons1.add(secondButton);
//
//        List<MFPPushNotificationButton> getButtons2 =  new ArrayList<MFPPushNotificationButton>();
//        getButtons2.add(firstButton);
//
//        MFPPushNotificationCategory category = new MFPPushNotificationCategory.Builder("First_Button_Group1").setButtons(getButtons).build();
//        MFPPushNotificationCategory category1 = new MFPPushNotificationCategory.Builder("First_Button_Group2").setButtons(getButtons1).build();
//        MFPPushNotificationCategory category2 = new MFPPushNotificationCategory.Builder("First_Button_Group3").setButtons(getButtons2).build();
//
//        List<MFPPushNotificationCategory> categoryList =  new ArrayList<MFPPushNotificationCategory>();
//        categoryList.add(category);
//        categoryList.add(category1);
//        categoryList.add(category2);
//
//        options.setInteractiveNotificationCategories(categoryList);
//  //      push = MFPPush.getInstance();
//    //    push.initialize(this,options);
//
////        MFPPush.getInstance().listen(new MFPPushNotificationListener() {
////            @Override
////            public void onReceive(MFPSimplePushNotification mfpSimplePushNotification) {
////                // Handle push notification here
////                Toast.makeText(getApplicationContext(),"comes here",Toast.LENGTH_LONG).show();
////            }
////        });
//
//        notificationListener = new MFPPushNotificationListener() {
//            @Override
//            public void onReceive(final MFPSimplePushNotification message) {
//                Log.i("A", "Received a Push Notification: " + message.toString());
//                runOnUiThread(new Runnable() {
//                    public void run() {
//                        new android.app.AlertDialog.Builder(MainActivity.this)
//                                .setTitle("Received a Push Notification")
//                                .setMessage(message.getAlert())
//                                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
//                                    public void onClick(DialogInterface dialog, int whichButton) {
//                                    }
//                                })
//                                .show();
//                    }
//                });
//            }
//        };

        mSignupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.bounce);
                Log.e("A","sign up is caleed");
                Intent intent = new Intent(MainActivity.this, SignupActivity.class);
                startActivity(intent);
                //startActivity(new Intent(getApplicationContext(),Signup.class));
                mSignupBtn.startAnimation(animation);
            }
        });

        mLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.bounce);
                startActivity(new Intent(getApplicationContext(),LoginActivity.class));
                mLoginBtn.startAnimation(animation);
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
}