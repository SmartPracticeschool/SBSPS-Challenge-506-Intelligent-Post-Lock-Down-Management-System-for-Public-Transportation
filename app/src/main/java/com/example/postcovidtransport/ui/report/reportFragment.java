package com.example.postcovidtransport.ui.report;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.postcovidtransport.QRCode;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import com.example.postcovidtransport.R;

public class reportFragment extends Fragment {
    TextView heading, seat;
    EditText seatno, description1;
    Button mReportBtn;
    DatabaseReference reference;
    String s;
    public reportFragment(String s) {
        this.s = s;
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_report, container, false);
        heading   = (TextView) root.findViewById(R.id.report);
        seat      = (TextView) root.findViewById(R.id.seatnotxt);
        mReportBtn= (Button)root.findViewById(R.id.reportbtn);
        seatno   = (EditText)root.findViewById(R.id.seatnumber);
        description1      = (EditText)root.findViewById(R.id.description);
        //  reference= FirebaseDatabase.getInstance().getReference().child("reporthelper");

        mReportBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.bounce);
                String seatnumber=seatno.getText().toString();
                String description =description1.getText().toString();
                if(TextUtils.isEmpty(seatnumber)){
                    seatno.setError("SeatNumber is Required.");
                    return;
                }

                if(seatnumber.length() > 4){
                    seatno.setError("Please Add Correct Number");
                    return;
                }
                if(TextUtils.isEmpty(description)){
                    description1.setError("Details is Required.");
                    return;
                }
                reporthelper infromation = new reporthelper(seatnumber, description);
                FirebaseDatabase.getInstance().getReference("report").child(FirebaseAuth.getInstance().getCurrentUser()
                        .getUid()).setValue(infromation).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                       // Toast.makeText(QRCode.this,"Registration Complete", Toast.LENGTH_SHORT).show();
                    }
                });
        mReportBtn.startAnimation(animation);
            }
        });

        Log.e("a",s);
        return root;

    }
}