package com.example.servicenovigrad;

public class Employee extends User {

    public Branch worksAt;
    public static Service[] allServices;

    public Employee(String u, String e, String p, String r) {
        super(u, e, p, r);
    }
}
