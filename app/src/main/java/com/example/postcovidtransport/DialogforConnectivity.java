package com.example.postcovidtransport;


import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

public class DialogforConnectivity extends AppCompatDialogFragment {
    @NonNull
    @Override
    public AlertDialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        String message ="Please make sure you have an active internet connection and try again !!!!";
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("INTERNET ERROR").setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        return builder.create();
    }
}

