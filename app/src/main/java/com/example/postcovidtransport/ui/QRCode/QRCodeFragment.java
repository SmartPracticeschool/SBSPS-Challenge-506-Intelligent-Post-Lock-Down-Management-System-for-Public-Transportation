package com.example.postcovidtransport.ui.QRCode;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.postcovidtransport.DataofUser;
import com.example.postcovidtransport.R;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import org.parceler.Parcels;

public class QRCodeFragment extends Fragment {
    ImageView imgView;
    EditText titletxt;
    String s ;
    public QRCodeFragment(String s) {
    this.s = s;
    }

    Button generate;
    private QRCodeViewModel QRCodeViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_qrcode, container, false);
        super.onCreate(savedInstanceState);
       // setContentView(R.layout.activity_q_rcodegen);
        imgView = root.findViewById(R.id.code);
        titletxt = root.findViewById(R.id.text1);
        generate = root.findViewById(R.id.button);
        titletxt.setText(s);
        //DataofUser dataofUser = new DataofUser();
       // Log.e("A",getArguments().getString("test"));

        //Log.e("A",dataofUser.getScheduleDept());
       // Bundle bundle = new Bundle();
//        Log.e("A",bundle.getString("test"));
  //      Log.e("test",bundle.getString("test"));
//        Parcelable parcelable = bundle.getParcelable("data");
  //      DataofUser dataofUser = Parcels.unwrap(parcelable);
    //    Log.e("in fragment",dataofUser.getPNRNo());
        generate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                QRCodeWriter qrCodeWriter = new QRCodeWriter();
                try {
                    BitMatrix bitMatrix = qrCodeWriter.encode(titletxt.getText().toString(), BarcodeFormat.QR_CODE, 200, 200);
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
            }
        });
        return root;

    }
}