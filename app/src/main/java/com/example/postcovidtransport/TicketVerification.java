package com.example.postcovidtransport;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.PatternMatcher;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.ml.vision.FirebaseVision;
import com.google.firebase.ml.vision.common.FirebaseVisionImage;
import com.google.firebase.ml.vision.text.FirebaseVisionText;
import com.google.firebase.ml.vision.text.FirebaseVisionTextRecognizer;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.IOException;

public class TicketVerification extends AppCompatActivity {
    private EditText ticket_number,ticket_aadhar_number;
    private ImageView ticket_image;
    private RadioGroup radioGroup1;
    private RadioButton rd_senior,rd_preg,rd_special;
    private Button submit;
    private TextView text_note,text_cities,text;
    private Spinner all_cities;
    private boolean flag=false;
    private Uri imageUri;
    public String dataonticket;

    private String ticketno,aadharno;
    private static final int gallery_pic_code =1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket_verification);

        initialise();

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
            }
        });

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
            // ML
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

        all_cities  = (Spinner) findViewById(R.id.all_cities);
        submit  = (Button) findViewById(R.id.submit_button);

    }

    private void checkFields() {
        ticketno = ticket_number.getText().toString().trim();
        aadharno = ticket_aadhar_number.getText().toString().trim();

        if (rd_senior.isChecked()) {
            text_note.setVisibility(View.VISIBLE);
        }
        if (ticketno.isEmpty()) {
            ticket_number.setError("Field Required");
            ticket_number.requestFocus();
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

}