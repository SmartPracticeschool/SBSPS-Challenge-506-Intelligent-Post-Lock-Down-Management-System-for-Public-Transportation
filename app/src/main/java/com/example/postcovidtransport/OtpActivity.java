package com.example.postcovidtransport;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class OtpActivity extends AppCompatActivity {
    private EditText phonenumberetxt;
    private Button sendotpbtn,signinbtn;
    String phonenumber;
    private String verificationid;
    private FirebaseAuth firebaseAuth;
    private ProgressBar progressBar;
    private EditText otpcode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);
        firebaseAuth = FirebaseAuth.getInstance();
        phonenumberetxt = findViewById(R.id.editTextPhone);
        progressBar = findViewById(R.id.progressBar);
        sendotpbtn = findViewById(R.id.sendoptbtn);
        otpcode = findViewById(R.id.editTextCode);
        signinbtn = findViewById(R.id.signinbtn);
        sendotpbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation animation = AnimationUtils.loadAnimation(OtpActivity.this, R.anim.bounce);
              //  final Animation translateScale = AnimationUtils.loadAnimation(this, R.anim.zoomin);
                String code = "91";
                String number = phonenumberetxt.getText().toString().trim();
                if (number.isEmpty() || number.length() < 10) {
                    phonenumberetxt.setError("Please provide valid number");
                    phonenumberetxt.requestFocus();
                    return;
                }
                 phonenumber = "+" + code + number;
                Toast.makeText(getApplicationContext(),"Otp sent sucessfully",Toast.LENGTH_LONG).show();
                sendverificationcode(phonenumber);
                sendotpbtn.startAnimation(animation);

            }
        });

        signinbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation animation = AnimationUtils.loadAnimation(OtpActivity.this, R.anim.bounce);
                    String code = otpcode.getText().toString().trim();
                    if ((code.isEmpty() || code.length() < 6)){
                        otpcode.setError("Enter code...");
                        otpcode.requestFocus();
                        return;
                    }
                signinbtn.startAnimation(animation);
                    verifycode(code);
                }
        });
    }
//    protected void onStart() {
//        super.onStart();
//        if(FirebaseAuth.getInstance().getCurrentUser()!=null){
//            Intent intent = new Intent(OtpActivity.this,TicketVerification.class);
//            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//            startActivity(intent);
//        }
//    }
    private void sendverificationcode(String phonenumber) {
        Log.e("sendverificationcode",phonenumber+" ");
        PhoneAuthProvider.getInstance().verifyPhoneNumber(phonenumber,60, TimeUnit.SECONDS, TaskExecutors.MAIN_THREAD, new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                super.onCodeSent(s, forceResendingToken);
                verificationid = s;
            }

            @Override
            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                String code = phoneAuthCredential.getSmsCode();
                if(code!=null){
                    Log.e("sendverifivation code","the code is "+code);
                    progressBar.setVisibility(View.VISIBLE );
                    verifycode(code);
                }
            }

            @Override
            public void onVerificationFailed(@NonNull FirebaseException e) {
                Toast.makeText(OtpActivity.this, e.getMessage(),Toast.LENGTH_LONG).show();
            }
        });

    }
    private void verifycode(String code) {
        Log.e("verifycode",code);
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationid,code);
        signInWithCredential(credential);
    }
    private void signInWithCredential(PhoneAuthCredential credential) {
        Log.e("signInWithCredential","in  signInWithCredential");
        firebaseAuth.signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Log.e("signInWithCredential","intent should be called");
                    Intent intent = new Intent(OtpActivity.this, TicketVerification.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);

                } else {
                    Toast.makeText(OtpActivity.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });
    }


}