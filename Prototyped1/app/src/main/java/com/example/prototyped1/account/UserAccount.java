package com.example.prototyped1.account;

public abstract class UserAccount extends Account {
    private String nameFirst;
    private String nameLast;
    private char role;


    public UserAccount(String nameFirst,  String nameLast, String email, String password,char role) {
        super(email, password);
        this.role = role;
        this.nameFirst = nameFirst;
        this.nameLast = nameLast;
    }

    public String getNameFirst() {
        return nameFirst;
    }

    public char getRole(){return role;}

    public String getNameLast() {
        return nameLast;
    }
    public void setNameFirst(String nameFirst) {
        this.nameFirst = nameFirst;
    }

    public void setNameLast(String nameLast) {
        this.nameLast = nameLast;
    }
}