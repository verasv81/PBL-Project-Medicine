package com.example.user.my_medicine_separatisti.mFragments;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.user.my_medicine_separatisti.R;

public class TreatmentFragment extends Fragment {
    public static TreatmentFragment newInstance() {
        TreatmentFragment fragment = new TreatmentFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_treatment, container, false);
    }
}
