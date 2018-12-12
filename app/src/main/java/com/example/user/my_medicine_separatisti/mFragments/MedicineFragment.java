package com.example.user.my_medicine_separatisti.mFragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.my_medicine_separatisti.R;
import com.example.user.my_medicine_separatisti.mAdapters.DataBaseAdapter;
import com.example.user.my_medicine_separatisti.mAdapters.MedicineAdapter;
import com.example.user.my_medicine_separatisti.mData.Model.Medicine;
import com.example.user.my_medicine_separatisti.mExpandables.MedicineChild;
import com.example.user.my_medicine_separatisti.mExpandables.MedicineHeader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MedicineFragment extends Fragment {

    MedicineAdapter listAdapter;
    ExpandableListView expListView;
    List<MedicineHeader> listDataHeader = new ArrayList<MedicineHeader>();
    List<Medicine> medicines = new ArrayList<>();
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

        expListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                CharSequence colors[] = new CharSequence[]{"Edit", "Delete"};

                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Choose option");
                builder.setItems(colors, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (which == 0) {
                            showMedicineDialog(medicines.get(position), position);
                        } else {
                            //aici faci delete!!!
                        }
                    }
                });
                builder.show();
                return true;
            }
        });

        viewData();

        return view;
    }

    private void showMedicineDialog(final Medicine medicine,final int position) {
        LayoutInflater layoutInflaterAndroid = LayoutInflater.from(getContext());
        View view = layoutInflaterAndroid.inflate(R.layout.edit_dialog, null);

        AlertDialog.Builder alertDialogBuilderUserInput = new AlertDialog.Builder(getActivity());
        alertDialogBuilderUserInput.setView(view);

        final EditText mName = view.findViewById(R.id.edit_medicineName);
        final EditText mQuantity = view.findViewById(R.id.edit_medicineQuantity);
        final EditText mExpirationDate = view.findViewById(R.id.edit_medicineExpirationDate);
        final EditText mComments = view.findViewById(R.id.edit_medicineComments);

        TextView dialogTitle = view.findViewById(R.id.dialog_title);
        dialogTitle.setText(getString(R.string.lbl_edit_note_title));

        if (medicine != null) {
            mName.setText(medicine.getName());
            mQuantity.setText(medicine.getQuantity());
            mExpirationDate.setText(medicine.getExpiration_date());
            mComments.setText(medicine.getComments());
        }
        alertDialogBuilderUserInput
                .setCancelable(false)
                .setPositiveButton( "update" , new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogBox, int id) {
                        if (medicine != null) {
                            // update note by it's id
                            Medicine updMed=new Medicine(mName.getText().toString(),mExpirationDate.getText().toString(),mQuantity.getText().toString(),mComments.getText().toString());
                            updateMedicine(updMed, position);
                        }
                    }
                })
                .setNegativeButton("cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialogBox, int id) {
                                dialogBox.cancel();
                            }
                        });

        final AlertDialog alertDialog = alertDialogBuilderUserInput.create();
        alertDialog.show();

        }

    private void viewData() {
        Cursor cursor=db.getAllMedicines();

        if(cursor.getCount()==0)
            Toast.makeText(getActivity(),"No medicines",Toast.LENGTH_SHORT).show();
        else
            while (cursor.moveToNext()){
                listDataHeader.add(new MedicineHeader(cursor.getString(1)));
                List<MedicineChild> pastilaCursor = new ArrayList<MedicineChild>();
                pastilaCursor.add(new MedicineChild(cursor.getString(2),cursor.getString(3),cursor.getString(4)));
                listDataChild.put(listDataHeader.get(listDataHeader.size()- 1).toString(),pastilaCursor);
                Medicine m=new Medicine();
                m.setId(cursor.getInt(0));
                m.setName(cursor.getString(1));
                m.setQuantity(cursor.getString(2));
                m.setExpiration_date(cursor.getString(3));
                m.setComments(cursor.getString(4));
                medicines.add(m);
            }

        MedicineAdapter adapter = new MedicineAdapter(getActivity(), listDataHeader, listDataChild);

        expListView.setAdapter(adapter);

    }

    void updateMedicine(Medicine m,int position){
        Medicine n = medicines.get(position);
        // updating note text
        n.setName(m.getName());
        n.setQuantity(m.getQuantity());
        n.setExpiration_date(m.getExpiration_date());
        n.setComments(m.getComments());

        // updating note in db
        db.updateMedicines(n);

        // refreshing the list
        medicines.set(position, n);
listAdapter.notifyDataSetChanged();
    }
}
