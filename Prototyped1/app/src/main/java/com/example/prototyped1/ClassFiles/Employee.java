package com.example.prototyped1.ClassFiles;


import java.util.Map;

public class Employee extends UserAccount {

    public String phone;
    public String address;
    public Map<String,Integer> openHours;

    public Employee(){
        super();
    }

    public Employee(String  firstName, String  lastName, String email, String  hashedPwd, String id, String phone, String address){
        super(firstName, lastName, email, hashedPwd,id);
        this.phone = phone;
        this.address = address;
    }

    public Employee(String nameFirst, String nameLast, String email, String password, String id, String phone, String address, Map<String, Integer> hours) {
        super(nameFirst, nameLast, email, password,id);
        this.phone = phone;
        this.address = address;
        this.openHours = hours;
    }

    public Map<String, Integer> getOpenHours() {
        return openHours;
    }
}
