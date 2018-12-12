package com.example.user.my_medicine_separatisti.mExpandables;

import java.util.ArrayList;

public class MedicineHeader {
    private String headerTitle;
    private ArrayList<MedicineChild> childList;


    public MedicineHeader(String headerTitle) {
        this.headerTitle = headerTitle;
    }


    public String getHeaderTitle() {
        return headerTitle;
    }

    public void setHeaderTitle(String headerTitle) {
        this.headerTitle = headerTitle;
    }

    public String getName() {
        return headerTitle;
    }


    public ArrayList<MedicineChild> getChildList() {
        return childList;
    }

    public void setChildList(ArrayList<MedicineChild> childList) {
        this.childList = childList;
    }

}
