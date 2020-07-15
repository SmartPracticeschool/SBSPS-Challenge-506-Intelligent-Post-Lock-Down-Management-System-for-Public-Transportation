package com.example.postcovidtransport.ui.QRCode;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.airbnb.lottie.LottieAnimationView;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.postcovidtransport.DataofUser;
import com.example.postcovidtransport.R;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcels;

import java.text.SimpleDateFormat;
import java.util.Date;

public class QRCodeFragment extends Fragment {
    ImageView imgView;
    String TAG = "Sarthak";
    TextView titletxt,time,waitingroomno;
    private String retreivedClass,timeSlot="",startTime,endTime;
    private boolean isprioritized=false;
    DataofUser dataofUser ;
    Spinner spinner;
    private RequestQueue requestQueue;
    LottieAnimationView lottieAnimationView;
    public QRCodeFragment(DataofUser dataofUser) {
    this.dataofUser = dataofUser;
    }

    Button generate;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        final View root = inflater.inflate(R.layout.fragment_qrcode, container, false);
        super.onCreate(savedInstanceState);
       // setContentView(R.layout.activity_q_rcodegen);
        imgView = root.findViewById(R.id.code);
        titletxt = root.findViewById(R.id.text1);
        generate = root.findViewById(R.id.button);
        time = root.findViewById(R.id.time);
        waitingroomno = root.findViewById(R.id.waitingroomno);
        lottieAnimationView = root.findViewById(R.id.progressbarfortime);
        lottieAnimationView.setVisibility(View.INVISIBLE);
        generate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lottieAnimationView.setVisibility(View.VISIBLE);
                Log.e(TAG, "pnr number value "+dataofUser.getPNRNo());
                waitingroomno.setText(room(dataofUser.getPNRNo()));

                Log.e(TAG, "onClick: before json call");
                //json();
                json_parsing_station();
                Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.bounce);
                QRCodeWriter qrCodeWriter = new QRCodeWriter();
                try {
                    Log.e(TAG, "in try ");
//                    QRCodeFragment qrCodeFragment = new QRCodeFragment(dataofUser);
//                    qrCodeFragment.json();
                    Log.e("sarthak", "onClick: "+dataofUser.toString());
                    BitMatrix bitMatrix = qrCodeWriter.encode(dataofUser.toString(), BarcodeFormat.QR_CODE, 200, 200);
                    Bitmap bitmap = Bitmap.createBitmap(200, 200, Bitmap.Config.RGB_565);
                    for (int x = 0; x<200; x++){
                        for (int y=0; y<200; y++){
                            bitmap.setPixel(x,y,bitMatrix.get(x,y)? Color.BLACK : Color.WHITE);
                        }
                    }
                    imgView.setImageBitmap(bitmap);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                //startActivity(new Intent(getApplicationContext(),Login.class));
                generate.startAnimation(animation);
            }
        });
        return root;
    }
    void json_parsing_station(){

        Log.d("a","in json");
        requestQueue = Volley.newRequestQueue(getContext());
        if (dataofUser.isPrioritizedEntry()){
            isprioritized = true;
        }
        //String url = "https://indianrailapi.com/api/v2/LiveStation/apikey/33a105af0fcbc56d4054e55083d1b998/StationCode/";
        String url = "https://indianrailapi.com/api/v2/TrainSchedule/apikey/33a105af0fcbc56d4054e55083d1b998/TrainNumber/";

        url+=dataofUser.getTrainnumber();

        //+"20200620";//
        //

        final JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("Route");

                            for (int i=0;i<jsonArray.length();i++){
                                JSONObject traindata = jsonArray.getJSONObject(i);

                                String timeFormat = "AM";
                                //Log.d("DATA",traindata.getString("StationName"));
                                //Log.d("DATA",traindata.getString("StationName").equals(doU.getBoardingstation())+" ");
                                //       if (traindata.getString("StationName").equals(doU.getBoardingstation()))

                                if (traindata.getString("StationName").contains(dataofUser.getBoardingstation().toUpperCase())) {
                                    String arrival1 = traindata.getString("ArrivalTime").substring(0, 2);


                                    String dept1 = traindata.getString("DepartureTime").substring(0, 2);
                                    String dept2 = traindata.getString("DepartureTime").substring(3, 5);

                                    if (arrival1.equalsIgnoreCase("SR")) {

                                        if (isprioritized == true) {
                                            startTime = dept1 + ":" + String.valueOf(Integer.parseInt(dept2) - 20);
                                            endTime = dept1 + ":" + String.valueOf(Integer.parseInt(dept2) - 15);
                                        } else if (retreivedClass == "3A") {
                                            startTime = dept1 + ":" + String.valueOf(Integer.parseInt(dept2) - 15);
                                            endTime = dept1 + ":" + String.valueOf(Integer.parseInt(dept2) - 10);
                                        } else if (retreivedClass == "2A") {
                                            startTime = dept1 + ":" + String.valueOf(Integer.parseInt(dept2) - 10);
                                            endTime = dept1 + ":" + String.valueOf(Integer.parseInt(dept2) - 5);
                                        } else if (retreivedClass == "Sl") {
                                            startTime = dept1 + ":" + String.valueOf(Integer.parseInt(dept2) - 5);
                                            endTime = dept1 + ":" + dept2;
                                        }
                                        if (Integer.parseInt(dept1) >= 12) {
                                            timeFormat = "PM";
                                        }
                                        time.setText( "From " + startTime +timeFormat+ " To " + endTime);

                                    }

                                    else {
                                        String arrival2 = traindata.getString("ArrivalTime").substring(3, 5);

                                        int diff1 = Integer.parseInt(dept1) - Integer.parseInt(arrival1);
                                        int diff2 = Integer.parseInt(dept2) - Integer.parseInt(arrival2);

                                        int totalTime = diff2;
                                        if (diff1 != 0) {
                                            totalTime = diff2 + diff1 * 60;
                                        }

                                        if (totalTime >= 4) {
                                            if (totalTime % 4 != 0)
                                                totalTime = totalTime - (totalTime % 4);

                                            Log.d("Total time:", totalTime + "");

                                            if (isprioritized == true) {
                                                startTime = (arrival1 + ":" + arrival2);
                                                endTime = (arrival1 + ":" + (Integer.parseInt(arrival2) + totalTime / 4));
                                            } else if (retreivedClass == "3A") {
                                                startTime = (arrival1 + ":" + (Integer.parseInt(arrival2) + totalTime / 4));
                                                endTime = arrival1 + ":" + (Integer.parseInt(arrival2) + (2 * totalTime / 4));
                                            } else if (retreivedClass == "2A") {
                                                startTime = arrival1 + ":" + (Integer.parseInt(arrival2) + (2 * totalTime / 4));
                                                endTime = arrival1 + ":" + (Integer.parseInt(arrival2) + (3 * totalTime / 4));
                                            } else if (retreivedClass == "Sl") {
                                                startTime = arrival1 + ":" + (Integer.parseInt(arrival2) + (3 * totalTime / 4));
                                                endTime = (dept1 + ":" + dept2);
                                            }
                                            if (Integer.parseInt(arrival1) >= 12) {
                                                timeFormat = "PM";
                                            }
                                            time.setText("From " + startTime  + " To: " + endTime );
                                        } else {
                                            startTime = (arrival1 + ":" + arrival2);
                                            if (Integer.parseInt(arrival1) >= 12) {
                                                timeFormat = "PM";
                                            }
                                            time.setText( startTime +" "+ timeFormat );
                                        }
                                        Log.d("", "Arrival: " + startTime + ", " + "Departure : " + endTime);
                                    }
                                }
                            }

                            Log.d("Total Size Value is:  ", jsonArray.length()+"");
                            lottieAnimationView.setVisibility(View.INVISIBLE);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Error Occured :  " ,error.getMessage());
            }
        });

        requestQueue.add(request);
    }
//     public void json() {
    // this api stopped working during development
//      //   lottieAnimationView.getProgress();
//       //  lottieAnimationView.setVisibility(View.INVISIBLE);
//        Log.d("a","in json");
//        requestQueue = Volley.newRequestQueue(getContext());
//
//        if (dataofUser.isPrioritizedEntry()){
//            isprioritized = true;
//        }
//
//        String url = "https://indianrailapi.com/api/v2/livetrainstatus/apikey/33a105af0fcbc56d4054e55083d1b998/trainnumber/";
//        // String url = "https://indianrailapi.com/api/v2/livetrainstatus/apikey/33a105af0fcbc56d4054e55083d1b998/trainnumber/";
//        url+=dataofUser.getTrainnumber();
//        //"12592";
//        url+="/date/"+getdate();
//        //+"20200620";//
//        //
//        final JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
//                new Response.Listener<JSONObject>() {
//
//                    @Override
//                    public void onResponse(JSONObject response) {
//                        try {
//                            JSONArray jsonArray = response.getJSONArray("TrainRoute");
//
//                            for (int i=0;i<jsonArray.length();i++){
//                                JSONObject traindata = jsonArray.getJSONObject(i);
//                                //Log.d("DATA",traindata.getString("StationName"));
//                                //Log.d("DATA",traindata.getString("StationName").equals(doU.getBoardingstation())+" ");
//                                //       if (traindata.getString("StationName").equals(doU.getBoardingstation()))
//
//                                if (traindata.getString("StationName").equalsIgnoreCase(dataofUser.getBoardingstation())) {
//                                    String arrival1 = traindata.getString("ScheduleArrival").substring(0, 2);
//                                    String arrival2 = traindata.getString("ScheduleArrival").substring(3, 5);
//
//
//                                    String dept1 = traindata.getString("ActualDeparture").substring(0, 2);
//                                    String dept2 = traindata.getString("ActualDeparture").substring(3, 5);
//
//                                    // for am/pm
//                                    String timeformat = traindata.getString("ActualDeparture").substring(5, 7);
//
//                                    if (arrival1.equalsIgnoreCase("So")) {
//
//                                        if (isprioritized) {
//                                            startTime = dept1+":"+String.valueOf(Integer.parseInt(dept2)-20);
//                                            endTime = dept1+":"+String.valueOf(Integer.parseInt(dept2)-15);
//                                        } else if (retreivedClass == "3A") {
//                                            startTime = dept1+":"+String.valueOf(Integer.parseInt(dept2)-15);
//                                            endTime = dept1+":"+String.valueOf(Integer.parseInt(dept2)-10);
//                                        } else if (retreivedClass == "2A") {
//                                            startTime = dept1+":"+String.valueOf(Integer.parseInt(dept2)-10);
//                                            endTime = dept1+":"+String.valueOf(Integer.parseInt(dept2)-5);
//                                        } else if (retreivedClass == "Sl") {
//                                            startTime = dept1+":"+String.valueOf(Integer.parseInt(dept2)-5);
//                                            endTime = dept1+":"+dept2;
//                                        }
//                                        time.setText(" From " +startTime+timeformat+" To "+endTime+timeformat);
//                                    }
//
//                                    else {
//                                        int diff1 = Integer.parseInt(dept1) - Integer.parseInt(arrival1);
//                                        int diff2 = Integer.parseInt(dept2) - Integer.parseInt(arrival2);
//
//                                        int totalTime = diff2;
//                                        if (diff1 != 0) {
//                                            totalTime = diff2 + diff1 * 60;
//                                        }
//
//                                        if (totalTime >= 4) {
//                                            if (totalTime % 4 != 0)
//                                                totalTime = totalTime - (totalTime % 4);
//
//                                            Log.d("Total time:", totalTime + "");
//
//                                            if (isprioritized == true) {
//                                                startTime = (arrival1 + ":" + arrival2);
//                                                endTime = (arrival1 + ":" + (Integer.parseInt(arrival2) + totalTime / 4));
//                                            } else if (retreivedClass == "3A") {
//                                                startTime = (arrival1 + ":" + (Integer.parseInt(arrival2) + totalTime / 4));
//                                                endTime = arrival1 + ":" + (Integer.parseInt(arrival2) + (2 * totalTime / 4));
//                                            } else if (retreivedClass == "2A") {
//                                                startTime = arrival1 + ":" + (Integer.parseInt(arrival2) + (2 * totalTime / 4));
//                                                endTime = arrival1 + ":" + (Integer.parseInt(arrival2) + (3 * totalTime / 4));
//                                            } else if (retreivedClass == "Sl") {
//                                                startTime = arrival1 + ":" + (Integer.parseInt(arrival2) + (3 * totalTime / 4));
//                                                endTime = (dept1 + ":" + dept2);
//                                            }
//                                            time.setText( "From " + startTime + timeformat + " To: " + endTime +" "+ timeformat);
//                                        } else {
//                                            startTime = (arrival1 + ":" + arrival2);
//                                            time.setText(startTime +" "+ timeformat);
//                                        }
//                                        Log.d("", "Arrival: " + startTime + timeformat + ", " + "Departure : " + timeformat + endTime);
//                                    }
//                                }
//                            }
//
//                            Log.d("Total Size Value is:  ", jsonArray.length()+"");
//                            lottieAnimationView.setVisibility(View.INVISIBLE);
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                Log.d("Error Occured :  " ,error.getMessage());
//            }
//        });
//
//        requestQueue.add(request);
//
//    }
    public static String getdate(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String currentDate = sdf.format(new Date());
        return currentDate;
    }
    public static String room(String pnr){
        int num=Integer.parseInt(pnr.substring(pnr.length()-1));
        if((num%2)==0){
            return "Room no. 2";
        }else{
            return "Room no. 1";
        }

    }
}