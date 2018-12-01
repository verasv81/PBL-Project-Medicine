package com.example.user.my_medicine_separatisti.mFragments;

import android.app.AlertDialog;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.Toast;

import com.example.user.my_medicine_separatisti.R;
import com.example.user.my_medicine_separatisti.mAdapters.DataBaseAdapter;
import com.example.user.my_medicine_separatisti.mAdapters.MedicineAdapter;
import com.example.user.my_medicine_separatisti.mData.Model.Frequency;
import com.example.user.my_medicine_separatisti.mData.Model.Medicine;
import com.example.user.my_medicine_separatisti.mExpandables.MedicineChild;
import com.example.user.my_medicine_separatisti.mExpandables.MedicineHeader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AddMedicineFragment extends Fragment {
    List<MedicineHeader> listDataHeader = new ArrayList<MedicineHeader>();
    HashMap<String, List<MedicineChild>> listDataChild = new HashMap<String, List<MedicineChild>>();
    EditText mName;
    AlertDialog dialog;
    EditText mLifetime;
    EditText mQuantity;
    EditText mComments;
    DataBaseAdapter db;
    View mView;
    public static AddMedicineFragment newInstance() {
        AddMedicineFragment fragment = new AddMedicineFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_add_medicine, container, false);
        db = new DataBaseAdapter(getActivity().getApplicationContext());

        mName = (EditText) view.findViewById(R.id.edit_medicineName);
        mLifetime = (EditText) view.findViewById(R.id.edit_medicineExpirationDate);
        mQuantity = (EditText) view.findViewById(R.id.edit_medicineQuantity);
        mComments = (EditText) view.findViewById(R.id.edit_medicineComments);
        Button mSave = (Button) view.findViewById(R.id.buttonSave);
        final Button mCancel = (Button) view.findViewById(R.id.buttonCancel);

        mCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment=new MedicineFragment().newInstance();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frame_layout, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
        mSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!mName.getText().toString().isEmpty() &&
                        !mLifetime.getText().toString().isEmpty() &&
                        !mQuantity.getText().toString().isEmpty() &&
                        !mComments.getText().toString().isEmpty()) {
                    Toast.makeText(getActivity(),
                            getString(R.string.success_adding_medicine),
                            Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getActivity(),
                            getString(R.string.error_adding_medicine),
                            Toast.LENGTH_SHORT).show();
                }

                listDataHeader.add(new MedicineHeader(mName.getText().toString(), mLifetime.getText().toString()));
                List<MedicineChild> pastilaFinal = new ArrayList<MedicineChild>();
                pastilaFinal.add(new MedicineChild(mQuantity.getText().toString(), mComments.getText().toString()));
                listDataChild.put(listDataHeader.get(listDataHeader.size() - 1).toString(), pastilaFinal);

                Medicine s = new Medicine();
                try {
                    s.setName(mName.getText().toString());
                    s.setQuantity(mQuantity.getText().toString());
                    s.setComments(mComments.getText().toString());
                    s.setExpiration_date(mLifetime.getText().toString());
                } catch (NullPointerException e) {
                }

                db.insertMedicine(s);

                mName.setText("");
                mLifetime.setText("");
                mQuantity.setText("");
                mComments.setText("");

                Fragment fragment=new MedicineFragment().newInstance();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frame_layout, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
        return view;
    }
}