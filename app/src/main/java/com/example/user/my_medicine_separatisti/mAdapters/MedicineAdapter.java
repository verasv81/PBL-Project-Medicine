package com.example.user.my_medicine_separatisti.mAdapters;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.example.user.my_medicine_separatisti.R;
import com.example.user.my_medicine_separatisti.mData.Model.Medicine;
import com.example.user.my_medicine_separatisti.mExpandables.MedicineChild;
import com.example.user.my_medicine_separatisti.mExpandables.MedicineHeader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class MedicineAdapter extends BaseExpandableListAdapter {

    private Context _context;
    private List<MedicineHeader> _listDataHeader; // header titles
    private List<MedicineHeader> new_listDataHeader; // header titles
    // child data in format of header title, child title
    private HashMap<String, List<MedicineChild>> _listDataChild;

    public MedicineAdapter(Context context, List<MedicineHeader> listDataHeader, HashMap<String, List<MedicineChild>> listChildData) {
        this._context = context;
        this._listDataHeader = listDataHeader;
        this._listDataChild = listChildData;
    }

    @Override
    public Object getChild(int groupPosition, int childPosititon) {
        return this._listDataChild.get(this._listDataHeader.get(groupPosition).toString()).get(childPosititon);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

        MedicineChild childText = (MedicineChild) getChild(groupPosition, childPosition);
        MedicineChild childComment = (MedicineChild) getChild(groupPosition, childPosition);
        MedicineChild childDate=(MedicineChild) getChild(groupPosition,childPosition);

        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.medicine_child_items, null);
        }

        TextView txtListChild = (TextView) convertView.findViewById(R.id.quantityId);

        TextView commentChild = (TextView) convertView.findViewById(R.id.commentId);
        TextView dataHeader = (TextView) convertView.findViewById(R.id.data);

        dataHeader.setText(childDate.getExpirationDate());
        txtListChild.setText(childText.getQuantity());
        commentChild.setText(childComment.getComment());
        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return this._listDataChild.get(this._listDataHeader.get(groupPosition).toString()).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this._listDataHeader.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return this._listDataHeader.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(final int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {

        MedicineHeader headerTitle = (MedicineHeader) getGroup(groupPosition);
        MedicineHeader headerDate = (MedicineHeader) getGroup(groupPosition);

        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.medicine_group_items, null);
        }

        TextView lblListHeader = (TextView) convertView.findViewById(R.id.lblListHeader);


        lblListHeader.setTypeface(null, Typeface.BOLD);
        lblListHeader.setText(headerTitle.getHeaderTitle());

        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}