package com.example.prototyped1.ClassFiles;


public abstract class UserAccount extends Account {
    private String nameFirst;
    private String nameLast;
    private String id;


    public UserAccount(String nameFirst,  String nameLast, String email, String password, String id) {
        super(email, password);
        this.id = id;
        this.nameFirst = nameFirst;
        this.nameLast = nameLast;
    }

    public UserAccount() {
        super();
    }

    public String getNameFirst() {
        return nameFirst;
    }

    public String getID(){return id;}

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