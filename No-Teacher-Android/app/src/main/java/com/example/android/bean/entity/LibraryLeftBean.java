package com.example.android.bean.entity;

public class LibraryLeftBean {
    private String type;
    private int id;
    private boolean check;

    public LibraryLeftBean(String type, int id, boolean check) {
        this.type = type;
        this.id = id;
        this.check = check;
    }

    public LibraryLeftBean() {
    }


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isCheck() {
        return check;
    }

    public void setCheck(boolean check) {
        this.check = check;
    }
}
