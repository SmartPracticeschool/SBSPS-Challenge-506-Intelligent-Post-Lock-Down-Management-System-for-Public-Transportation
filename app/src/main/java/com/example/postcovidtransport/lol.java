package com.example.postcovidtransport;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.ml.vision.FirebaseVision;
import com.google.firebase.ml.vision.common.FirebaseVisionImage;
import com.google.firebase.ml.vision.text.FirebaseVisionText;
import com.google.firebase.ml.vision.text.FirebaseVisionTextRecognizer;

import java.io.IOException;
import java.util.ArrayList;

public class lol extends AppCompatActivity {
    private EditText ticket_aadhar_number;
    private ImageView ticket_image;
    private RadioGroup radioGroup1;
    private RadioButton rd_senior,rd_preg,rd_special;
    private Button submit;
    private TextView text_note,text,text_cities,text_info;
    private AutoCompleteTextView cities_editText;

    private boolean flag=false;
    private RequestQueue requestQueue;

    private String ticketno,aadharno;
    public String dataonticket;
    private ArrayList cities_list;
    private static final int gallery_pic_code =1;
    private Uri imageUri;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lol);

        cities_list = new ArrayList();
        initialise();
        json();
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
                checkFields();
                if (!cities_editText.getText().toString().isEmpty()){
                    openDialog();
                }
            }
        });

    }



    private void openDialog()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(getApplicationContext());
        builder.setTitle("Information");
        builder.setMessage("Your Verification will be processed at your selected boarding station");
        builder.setCancelable(false);
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finish();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        builder.show();

        //dialog.show();
    }

    private void submit()
    {

        Intent intent = new Intent();
        intent.putExtra("text",ticketno + ticket_aadhar_number);
        startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == gallery_pic_code && resultCode == RESULT_OK && data!=null){
            imageUri = data.getData();
            try{
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),imageUri);
                ticket_image.setImageBitmap(bitmap);
                FirebaseVisionImage image;
                try {
                    image = FirebaseVisionImage.fromFilePath(getApplicationContext(), imageUri);
                    FirebaseVisionTextRecognizer textRecognizer = FirebaseVision.getInstance().getOnDeviceTextRecognizer();
                    textRecognizer.processImage(image)
                            .addOnSuccessListener(new OnSuccessListener<FirebaseVisionText>() {
                                @Override
                                public void onSuccess(FirebaseVisionText result) {
                                    dataonticket = result.getText();
                                    Log.e("TEXT",dataonticket);
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

    private void initialise()
    {
        text_info = (TextView) findViewById(R.id.text_info);
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

    private void checkFields() {
        aadharno = ticket_aadhar_number.getText().toString().trim();

        if (rd_senior.isChecked()) {
            text_note.setVisibility(View.VISIBLE);
        }

        if (cities_editText.getText().toString().isEmpty()){
            cities_editText.setError("Field Required");
            cities_editText.requestFocus();
            return;
        }

        if (aadharno.isEmpty()) {
            ticket_aadhar_number.setError("Field Required");
            ticket_aadhar_number.requestFocus();
            return;
        }
        if (aadharno.length() != 16) {
            ticket_aadhar_number.setError("Invalid");
            ticket_aadhar_number.requestFocus();
            return;
        }
        if (!flag){
            Toast.makeText(getApplicationContext(),"Image required",Toast.LENGTH_SHORT).show();
            return;
        }

    }

    void json(){

        requestQueue = Volley.newRequestQueue(this);

        String url = "http://www.json-generator.com/api/json/get/cgoxLceAlK?indent=2";

        final JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
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

}