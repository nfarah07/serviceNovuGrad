package com.example.prototyped1.ClassFiles;


import java.util.Map;

public class Employee extends UserAccount {

    public String phone;
    public String address;
    public Map<String,String> mapOfHours;
    public Map<String,Object> Offered;

    public Employee(){
        super();
    }

    public Employee(String  firstName, String  lastName, String email, String  hashedPwd, String id, String phone, String address){
        super(firstName, lastName, email, hashedPwd,id);
        this.phone = phone;
        this.address = address;
    }

    public Employee(String nameFirst, String nameLast, String email, String password, String id, String phone, String address, Map<String, String> hours) {
        super(nameFirst, nameLast, email, password,id);
        this.phone = phone;
        this.address = address;
        this.mapOfHours = hours;
    }

    public String toString(){
        return this.getNameFirst() + "\n" + this.getAddress() + "\n" + this.phone ;
    }

    public Map<String, String> getOpenHours() {
        return mapOfHours;
    }
    public String getAddress() {return address;}
}
