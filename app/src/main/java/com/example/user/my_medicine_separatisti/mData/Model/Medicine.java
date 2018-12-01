package com.example.user.my_medicine_separatisti.mData.Model;

public class Medicine {
    public final static String TABLE_NAME = "medicines";
    public final static String _ID = "id";
    public final static String COLUMN_MEDICINE_NAME ="name";
    public final static String COLUMN_MEDICINE_QUANTITY = "quantity";
    public final static String COLUMN_MEDICINE_EXPIRATION_DATE = "expiration_date";
    public final static String COLUMN_MEDICINE_COMMENTS = "comment";

    private int id;
    private String name;
    private String expiration_date;
    private String quantity;
    private String comments;

    public final static String CREATE_TABLE="CREATE TABLE " + Medicine.TABLE_NAME + " ("
            + Medicine._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + Medicine.COLUMN_MEDICINE_NAME + " TEXT NOT NULL, "
            + Medicine.COLUMN_MEDICINE_QUANTITY + " INTEGER NOT NULL, "
            + Medicine.COLUMN_MEDICINE_EXPIRATION_DATE + " DATE, "
            + Medicine.COLUMN_MEDICINE_COMMENTS + " TEXT);";
    public Medicine(){};
    public Medicine(String name, String expiration_date, String quantity, String comments) {
        this.name = name;
        this.expiration_date = expiration_date;
        this.quantity = quantity;
        this.comments = comments;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getExpiration_date() {
        return expiration_date;
    }

    public void setExpiration_date(String expiration_date) {
        this.expiration_date = expiration_date;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }
}
