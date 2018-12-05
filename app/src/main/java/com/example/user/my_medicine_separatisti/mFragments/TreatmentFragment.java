package com.example.user.my_medicine_separatisti.mFragments;

import android.app.AlertDialog;
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
import com.example.user.my_medicine_separatisti.mAdapters.TreatmentAdapter;
import com.example.user.my_medicine_separatisti.mExpandables.TreatmentChild;
import com.example.user.my_medicine_separatisti.mExpandables.TreatmentHeader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TreatmentFragment extends Fragment {

    TreatmentAdapter listAdapter;
    ExpandableListView expListView;
    List<TreatmentHeader> listDataHeader;
    HashMap<String, List<TreatmentChild>> listDataChild;
    AlertDialog.Builder mBuilder;
    View mView;
    DataBaseAdapter db;

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
        final View view = inflater.inflate(R.layout.activity_treatment, container, false);

        db = new DataBaseAdapter(getActivity().getApplicationContext());
        // get the listview
        expListView = (ExpandableListView) view.findViewById(R.id.lvExp2);

        // preparing list data
        prepareListData();

        listAdapter = new TreatmentAdapter(getActivity(), listDataHeader, listDataChild);

        // setting list adapter
        expListView.setAdapter(listAdapter);


        expListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {

            @Override
            public boolean onGroupClick(ExpandableListView parent, View v,
                                        int groupPosition, long id) {
                // Toast.makeText(getApplicationContext(),
                // "Group Clicked " + listDataHeader.get(groupPosition),
                // Toast.LENGTH_SHORT).show();
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
        expListView = (ExpandableListView) view.findViewById(R.id.lvExp2);
        viewData();
        return view;
    }

    private void prepareListData() {
        listDataHeader = new ArrayList<TreatmentHeader>();
        listDataChild = new HashMap<String, List<TreatmentChild>>();


        listDataHeader.add(new TreatmentHeader("Raceala", "Done"));
        listDataHeader.add(new TreatmentHeader("Gripa", "Done"));
        listDataHeader.add(new TreatmentHeader("Angina", "In Progress"));

        // Adding child data
        List<TreatmentChild> pastila1 = new ArrayList<TreatmentChild>();
        pastila1.add(new TreatmentChild("5 days", "no description", "ibuprfen, nurofen"));


        List<TreatmentChild> pastila2 = new ArrayList<TreatmentChild>();
        pastila2.add(new TreatmentChild("12 days", "take the ascorbinka 3 times a day", "ascorbinka"));


        List<TreatmentChild> pastila3 = new ArrayList<TreatmentChild>();
        pastila3.add(new TreatmentChild("2 days", "no description", "mezim"));


        listDataChild.put(listDataHeader.get(0).toString(), pastila1); // Header, Child data
        listDataChild.put(listDataHeader.get(1).toString(), pastila2);
        listDataChild.put(listDataHeader.get(2).toString(), pastila3);

        //Bundle extras = getActivity().getIntent().getExtras();
        //if (extras != null) {
        //    String treatmentTitle = getActivity().getIntent().getExtras().getString("treatment title");
        //String treatmentDescription = getActivity().getIntent().getExtras().getString("treatment description");
        //String spinnerState = getActivity().getIntent().getExtras().getString("spinner state");
        //listDataHeader.add(treatmentTitle);
        //String strtext = getArguments().getString("my_key");
        //    listDataHeader.add(treatmentTitle);
        //}
    }

    private void viewData() {
        Cursor cursor2 = db.getAllTreatments();

        if(cursor2.getCount()==0)
            Toast.makeText(getActivity(),"No treatments",Toast.LENGTH_SHORT).show();
        else
            while (cursor2.moveToNext()){

                listDataHeader.add(new TreatmentHeader(cursor2.getString(1),cursor2.getString(4)));
                List<TreatmentChild> tratamentList = new ArrayList<>();
                tratamentList.add(new TreatmentChild(cursor2.getString(2),cursor2.getString(3),cursor2.getString(5)));
                listDataChild.put(listDataHeader.get(listDataHeader.size() - 1).toString(),tratamentList);
            }

        TreatmentAdapter adapter = new TreatmentAdapter(getActivity(), listDataHeader, listDataChild);

        expListView.setAdapter(adapter);

    }
}
