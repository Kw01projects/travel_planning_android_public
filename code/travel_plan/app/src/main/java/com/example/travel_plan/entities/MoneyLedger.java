package com.example.travel_plan.entities;

public class MoneyLedger {
    private Long id;
    private String title;
    private Long amount;
    private String date;
    private String type; // IN is 수입, OUT is 지출


    public static final String TBL_NAME = "tbl_money_ledger";
    public static final String ID_FIELD = "id";
    public static final String TITLE_FIELD = "title";
    public static final String AMOUNT_FIELD = "amount";
    public static final String DATE_FIELD = "date_c";
    public static final String TYPE_FIELD = "type";

    public static final String CREATE_TBL_SQL = "CREATE TABLE " + TBL_NAME + " ( " +
            ID_FIELD + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
            TITLE_FIELD + " TEXT," +
            AMOUNT_FIELD + " INTEGER," +
            DATE_FIELD + " TEXT," +
            TYPE_FIELD + " TEXT" +
            ");";

    public MoneyLedger(){}
    public MoneyLedger(String title, Long amount, String date, String type) {
        this.title = title;
        this.amount = amount;
        this.date = date;
        this.type = type;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
