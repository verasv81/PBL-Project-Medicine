package com.example.user.my_medicine_separatisti.mFragments;

import android.app.Activity;
import android.database.Cursor;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.Toast;

import com.example.user.my_medicine_separatisti.R;
import com.example.user.my_medicine_separatisti.mAdapters.DataBaseAdapter;
import com.example.user.my_medicine_separatisti.mAdapters.MedicineAdapter;
import com.example.user.my_medicine_separatisti.mExpandables.MedicineChild;
import com.example.user.my_medicine_separatisti.mExpandables.MedicineHeader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MedicineFragment extends Fragment {

    MedicineAdapter listAdapter;
    ExpandableListView expListView;
    List<MedicineHeader> listDataHeader = new ArrayList<MedicineHeader>();
    HashMap<String, List<MedicineChild>> listDataChild = new HashMap<String, List<MedicineChild>>();

    DataBaseAdapter db;


    public static MedicineFragment newInstance() {
        MedicineFragment fragment = new MedicineFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_medicine, container, false);

        db = new DataBaseAdapter(getActivity().getApplicationContext());
        // get the listview
        expListView = (ExpandableListView) view.findViewById(R.id.lvExp);
        // preparing list data

        listAdapter = new MedicineAdapter(getActivity(), listDataHeader, listDataChild);

        // setting list adapter
        expListView.setAdapter(listAdapter);


        expListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {

            @Override
            public boolean onGroupClick(ExpandableListView parent, View v,
                                        int groupPosition, long id) {
                //Toast.makeText(getApplicationContext(), "Group Clicked " + listDataHeader.get(groupPosition),
                //Toast.LENGTH_SHORT).show();
                return false;
            }
        });

        // Listview Group expanded listener
        expListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {

            @Override
            public void onGroupExpand(int groupPosition) {
                //Toast.makeText(getActivity(),
                //        listDataHeader.get(groupPosition) + " Expanded",
                //        Toast.LENGTH_SHORT).show();
            }
        });

        // Listview Group collasped listener
        expListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {

            @Override
            public void onGroupCollapse(int groupPosition) {
                //Toast.makeText(getActivity(),
                //        listDataHeader.get(groupPosition) + " Collapsed",
                //        Toast.LENGTH_SHORT).show();

            }
        });

        // Listview on child click listener
        expListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {

            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {
                // TODO Auto-generated method stub
                //Toast.makeText(
                //        getActivity(),
                //        listDataHeader.get(groupPosition)
                //                + " : "
                //                + listDataChild.get(
                //                listDataHeader.get(groupPosition)).get(
                //                childPosition), Toast.LENGTH_SHORT)
                //        .show();
                return false;
            }
        });

        viewData();

        return view;
    }
//    private void prepareListData() {
//
//        listDataHeader.add(new MedicineHeader("Ibuprofen", "01.01.2019"));
//        listDataHeader.add(new MedicineHeader("Valeriana", "02.05.2020"));
//        listDataHeader.add(new MedicineHeader("Nurofen", "14.07.2019"));
//
//        List<MedicineChild> pastila1 = new ArrayList<MedicineChild>();
//        pastila1.add(new MedicineChild("23 pills", "Do no exagerate"));
//
//        List<MedicineChild> pastila2 = new ArrayList<MedicineChild>();
//        pastila2.add(new MedicineChild("120 pills", "be free"));
//
//        List<MedicineChild> pastila3 = new ArrayList<MedicineChild>();
//        pastila3.add(new MedicineChild("20 pills", "no comment"));
//
//        listDataChild.put(listDataHeader.get(0).toString(), pastila1); // Header, Child data
//        listDataChild.put(listDataHeader.get(1).toString(), pastila2);
//        listDataChild.put(listDataHeader.get(2).toString(), pastila3);
//
//    }
    private void viewData() {
        Cursor cursor=db.getAllMedicines();

        if(cursor.getCount()==0)
            Toast.makeText(getActivity(),"No medicines",Toast.LENGTH_SHORT).show();
        else
            while (cursor.moveToNext()){
                listDataHeader.add(new MedicineHeader(cursor.getString(1),cursor.getString(3)));
                List<MedicineChild> pastilaCursor = new ArrayList<MedicineChild>();
                pastilaCursor.add(new MedicineChild(cursor.getString(2),cursor.getString(4)));
                listDataChild.put(listDataHeader.get(listDataHeader.size()- 1).toString(),pastilaCursor);

            }

        MedicineAdapter adapter = new MedicineAdapter(getActivity(), listDataHeader, listDataChild);

        expListView.setAdapter(adapter);

    }

}
