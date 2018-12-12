package com.example.user.my_medicine_separatisti.mExpandables;

public class MedicineChild {
    private String Quantity;
    private String ExpirationDate;
    private String Comment;

    public MedicineChild(String quantity,String expirationDate, String comment) {
        Quantity = quantity;
        Comment = comment;
        ExpirationDate=expirationDate;
    }

    public String getQuantity() {
        return Quantity;
    }

    public void setQuantity(String quantity) {
        Quantity = quantity;
    }

    public String getComment() {
        return Comment;
    }

    public void setComment(String comment) {
        Comment = comment;
    }

    public String getExpirationDate() {
        return ExpirationDate;
    }

    public void setExpirationDate(String expirationDate) {
        ExpirationDate = expirationDate;
    }
}
