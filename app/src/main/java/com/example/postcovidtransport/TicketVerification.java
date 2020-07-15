package com.example.postcovidtransport;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.postcovidtransport.aadhar.ValidateAadhar;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.ml.vision.FirebaseVision;
import com.google.firebase.ml.vision.common.FirebaseVisionImage;
import com.google.firebase.ml.vision.text.FirebaseVisionText;
import com.google.firebase.ml.vision.text.FirebaseVisionTextRecognizer;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcels;

import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TicketVerification extends AppCompatActivity {
    private EditText ticket_number,ticket_aadhar_number;
    private ImageView ticket_image;
    private RadioGroup radioGroup1;
    private RadioButton rd_senior,rd_preg,rd_special;
    private Button submit;
    private TextView text_note,text_cities,text;
    private boolean flag=false;
    private Uri imageUri;
    public String dataonticket;
    DataofUser dataofUser = new DataofUser();
    private String ticketno,aadharno;
    private static final int gallery_pic_code =1;
    private RequestQueue requestQueue;
    private ArrayList cities_list;
    private AutoCompleteTextView cities_editText;
    Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket_verification);
        cities_list = new ArrayList();
        initialise();
        json();
        spinner = findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getApplicationContext(),R.array.userclass,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position!=0) {
                    dataofUser.setUserclass(parent.getItemAtPosition(position).toString());
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(getApplicationContext(),"Please select correct class",Toast.LENGTH_LONG).show();
            }
        });
        ticket_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent galleryIntent = new Intent();
                galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
                galleryIntent.setType("image/*");
                startActivityForResult(galleryIntent,gallery_pic_code);
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.bounce);
                if(checkFields()) {
                    dataofUser.setAadharno(aadharno);
                    dataofUser.setBoardingstation(cities_editText.getText().toString());
                    submit();
                }
                submit.startAnimation(animation);
            }
        });
    }

     public void json(){
        // fetch city from json array
         Log.e("a","in json");
        requestQueue = Volley.newRequestQueue(this);

        //String url = "http://www.json-generator.com/api/json/get/bUqVBMBTyq?indent=2";
        String url =  "https://www.json-generator.com/api/json/get/cgoxLceAlK?indent=2";

        final JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e("a","before try");
                        try {
                            Log.e("a","in try");
                            JSONArray jsonArray = response.getJSONArray("cities");

                            for (int i=0;i<jsonArray.length();i++){
                                JSONObject cities = jsonArray.getJSONObject(i);
                                String city = cities.getString("City");
                                cities_list.add(city);

                                Log.d("", city+ ",");
                            }
                            Log.d("Total Size Value is:  ", cities_list.size()+"");


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

    private void submit ()
    {
        Parcelable parcelable = Parcels.wrap(dataofUser);
        Intent intent = new Intent(getApplicationContext(), QRCode.class);
            intent.putExtra("dataofuser",parcelable);
//       // intent.putExtra("dataofuser", (Serializable) dataofUser);
        startActivity(intent);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == gallery_pic_code && resultCode == RESULT_OK && data!=null){
            imageUri = data.getData();
            // ML
            try{
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),imageUri);
                ticket_image.setImageBitmap(bitmap);
                flag = true;
                FirebaseVisionImage image;
                try {
                    image = FirebaseVisionImage.fromFilePath(getApplicationContext(), imageUri);
                    FirebaseVisionTextRecognizer textRecognizer = FirebaseVision.getInstance().getOnDeviceTextRecognizer();
                    textRecognizer.processImage(image)
                            .addOnSuccessListener(new OnSuccessListener<FirebaseVisionText>() {
                                @Override
                                public void onSuccess(FirebaseVisionText result) {
                                    dataonticket = result.getText();
                                   dataofUser =  fetchdatafromimage(dataonticket);
                                    Log.e("TEXT",dataonticket);
                                    ticket_number.setText(dataofUser.getPNRNo());
                                }
                            })
                            .addOnFailureListener(
                                    new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(getApplicationContext(),"Task Failed",Toast.LENGTH_LONG).show();
                                        }
                                    });

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            catch (Exception e){
                Toast.makeText(getApplicationContext(),"Some Error Occured",Toast.LENGTH_SHORT).show();
            }
        }
    }
 private DataofUser fetchdatafromimage (String dataonticket) {
        DataofUser dataofUser = new DataofUser();

      //Fetching pnr_no
     Pattern p = Pattern.compile("(PNR No: )");
     Matcher matcher = p .matcher(dataonticket);
     while (matcher.find()) {
         int startindexofPNR =   matcher.end();
         dataofUser.setPNRNo(dataonticket.substring(startindexofPNR, startindexofPNR+10));
     }

//     // fetching scheduled departure
//     Pattern p1 = Pattern.compile("(Scheduled Departure: )");
//     Matcher matcher1 = p .matcher(dataonticket);
//     while (matcher.find()) {
//         int startindexofPNR =   matcher1.end();
//         dataofUser.setScheduleDept(dataonticket.substring(startindexofPNR, startindexofPNR+5));
//     }
//
//     //fetching Date of Booking:
//     Pattern p2 = Pattern.compile("(Date of Booking:)");
//     Matcher matcher2 = p .matcher(dataonticket);
//     while (matcher.find()) {
//         int startindexofPNR =   matcher1.end();
//         dataofUser.setDate_of_Booking(dataonticket.substring(startindexofPNR, startindexofPNR+25));
//     }
       //  Log.e("a",dataofUser.getDate_of_Booking());
     Pattern p3 = Pattern.compile("(Train No. & Name: )");
     Matcher matcher3 = p3 .matcher(dataonticket);
     while (matcher3.find()) {
         int startindexofPNR =   matcher3.end();
         dataofUser.setTrainnumber(dataonticket.substring(startindexofPNR, startindexofPNR+5));
     }
     return dataofUser;
    }
    private void initialise()
    {
        ticket_number = (EditText) findViewById(R.id.ticket_number);
        ticket_aadhar_number = (EditText)findViewById(R.id.ticket_aadhar);

        ticket_image = (ImageView) findViewById(R.id.ticket_image);

        radioGroup1 = (RadioGroup)findViewById(R.id.radio_group1);

        rd_senior = (RadioButton)findViewById(R.id.rd_senior);
        rd_preg = (RadioButton)findViewById(R.id.rd_preg);
        rd_special = (RadioButton)findViewById(R.id.rd_special);

        text_note  = (TextView) findViewById(R.id.text_note);
        text_cities  = (TextView) findViewById(R.id.text_cities);
        text  = (TextView) findViewById(R.id.text);

        submit  = (Button) findViewById(R.id.submit_button);

        cities_editText = (AutoCompleteTextView) findViewById(R.id.cities_textview);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,cities_list);
        cities_editText.setAdapter(adapter);

    }

    private boolean checkFields() {
        ticketno = ticket_number.getText().toString().trim();
        aadharno = ticket_aadhar_number.getText().toString().trim();

        if (rd_senior.isChecked()) {
            text_note.setVisibility(View.VISIBLE);
            Toast.makeText(getApplicationContext(),"Note: Please carry an age proof along with you",Toast.LENGTH_SHORT).show();
            dataofUser.setPrioritizedEntry(true);
        }
        if(rd_preg.isChecked()){
            dataofUser.setPrioritizedEntry(true);
        }
        if(rd_special.isChecked()){
            dataofUser.setPrioritizedEntry(true);
        }
        if (ticketno.isEmpty()) {
            ticket_number.setError("Field Required");
            ticket_number.requestFocus();
            return false;
        }
        if (aadharno.isEmpty()) {
            ticket_aadhar_number.setError("Field Required");
            ticket_aadhar_number.requestFocus();
            return false;
        }
        if (aadharno.length() != 12) {
            ticket_aadhar_number.setError("Invalid");
            ticket_aadhar_number.requestFocus();
            return false;
        }
        if (!ValidateAadhar.validateAadhar(aadharno)){
                ticket_aadhar_number.setError("invalid aadhar no");
               // ticket_aadhar_number.setFocusable(true);
                ticket_aadhar_number.requestFocus();
                return false;
            }
        if(cities_editText.getText().length()<=1){
            cities_editText.setError("Field Required");
            cities_editText.requestFocus();
            return false;
        }

        if (!flag){
            Toast.makeText(getApplicationContext(),"Image required",Toast.LENGTH_SHORT).show();
            return false;
        }
    return true;
    }

}