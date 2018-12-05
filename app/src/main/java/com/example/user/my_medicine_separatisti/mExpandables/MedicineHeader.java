package com.example.user.my_medicine_separatisti.mExpandables;

import java.util.ArrayList;

public class MedicineHeader {
    private String headerTitle;
    private String headerDate;
    private ArrayList<MedicineChild> childList;


    public MedicineHeader(String headerTitle, String headerDate) {
        this.headerTitle = headerTitle;
        this.headerDate = headerDate;
    }


    public String getHeaderTitle() {
        return headerTitle;
    }

    public void setHeaderTitle(String headerTitle) {
        this.headerTitle = headerTitle;
    }

    public String getHeaderDate() {
        return headerDate;
    }

    public void setHeaderDate(String headerDate) {
        this.headerDate = headerDate;
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
