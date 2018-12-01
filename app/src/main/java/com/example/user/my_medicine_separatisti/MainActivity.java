package com.example.user.my_medicine_separatisti;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.user.my_medicine_separatisti.mFragments.AddMedicineFragment;
import com.example.user.my_medicine_separatisti.mFragments.AddTreatmentFragment;
import com.example.user.my_medicine_separatisti.mFragments.MedicineFragment;
import com.example.user.my_medicine_separatisti.mFragments.SearchFragment;
import com.example.user.my_medicine_separatisti.mFragments.TreatmentFragment;

public class MainActivity extends AppCompatActivity {

    private TextView mTextMessage;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment selectedFragment=null;
            switch (item.getItemId()) {
                case R.id.navigation_medicines:
                    selectedFragment = new MedicineFragment().newInstance();
                    break;
                case R.id.navigation_treatments:
                    selectedFragment = new TreatmentFragment().newInstance();
                    break;
                case R.id.navigation_add:
                    selectedFragment = new AddMedicineFragment().newInstance();
                    break;
                case R.id.navigation_search:
                    selectedFragment = new SearchFragment().newInstance();
                    break;
                case R.id.navigation_notifications:
                    selectedFragment = new AddTreatmentFragment().newInstance();
                    break;
            }
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.frame_layout, selectedFragment);
            transaction.commit();
            return true;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final ImageView imageView = super.findViewById(R.id.image);
        imageView.setVisibility(View.VISIBLE);
        imageView.setAlpha(1f);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                imageView.setVisibility(View.GONE);
            }
        }, 5000);

        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

}
