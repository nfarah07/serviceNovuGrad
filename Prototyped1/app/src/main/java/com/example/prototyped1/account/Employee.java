package com.example.prototyped1.account;

public class Employee extends UserAccount {


    public Employee(String  firstName, String  lastName, String email, String  hashedPwd, char role){
        super(firstName, lastName, email, hashedPwd,role);

    }
}
