package com.example.prototyped1;


public class Employee extends UserAccount {

    public Employee(){
        super();
    }

    public Employee(String  firstName, String  lastName, String email, String  hashedPwd, String id){
        super(firstName, lastName, email, hashedPwd,id);

    }
}