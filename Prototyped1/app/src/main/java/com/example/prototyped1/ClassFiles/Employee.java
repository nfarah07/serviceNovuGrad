package com.example.prototyped1.ClassFiles;


public class Employee extends UserAccount {

    public String phone;
    public String address;

    public Employee(){
        super();
    }

    public Employee(String  firstName, String  lastName, String email, String  hashedPwd, String id, String phone, String address){
        super(firstName, lastName, email, hashedPwd,id);
        this.phone = phone;
        this.address = address;
    }
}
