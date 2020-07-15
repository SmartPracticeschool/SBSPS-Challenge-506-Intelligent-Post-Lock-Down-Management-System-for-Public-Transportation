package com.example.postcovidtransport.about;

import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

public class Dialog extends AppCompatDialogFragment {
    @NonNull
    @Override
    public AlertDialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        String message ="The proposal is to make a mobile application to manage post lock down public transportation. " +
                "During this pandemic the major risk for the spread can be through the use of public transports ," +
                "the application will provide an effective solution for the passengers to board a train at railway .\n\n"+
                "Technology Stack:\n" +
                "Software Used : Android Studio (3.6.3),Adobe Illustrator, Corel DRAW\n" +
                "Languages: Java(J2ME),XML(UI/UX Development)\n" +
                "Database : Google Firebase, Cloud Server,sql-lite(for Devise Storage)\n" +
                "Frameworks and API : Machine Learning KIT, Adhar API ,QR-ENCODER\n" +
                "API Level 19 : Runs on 98.1% percent of devices (Android 4.4 (Kitkat)) [min android version]\n";
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("ABOUT THE APP").setMessage(message).setPositiveButton("ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        return builder.create();
    }
}
