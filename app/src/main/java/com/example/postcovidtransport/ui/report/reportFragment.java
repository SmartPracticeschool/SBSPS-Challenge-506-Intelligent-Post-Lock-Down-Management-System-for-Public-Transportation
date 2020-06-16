package com.example.postcovidtransport.ui.report;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.postcovidtransport.R;

public class reportFragment extends Fragment {
    String s;
    public reportFragment(String s) {
        this.s = s;
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
//        dashboardViewModel =
//                ViewModelProviders.of(this).get(reportUnhygieneViewModel.class);
//        View root = inflater.inflate(R.layout.fragment_reportUnhygiene, container, false);
//        final TextView textView = root.findViewById(R.id.text_dashboard);
//        dashboardViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(s);
//            }
//        });
//        return root;
        View root = inflater.inflate(R.layout.fragment_report, container, false);
        Log.e("a",s);
        return root;

    }
}