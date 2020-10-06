package com.example.prototyped1.account;

public class Customer extends UserAccount {

    public Customer(String nameFirst, String nameLast, String email, String password, String role) {
        super(email, password, nameFirst, nameLast, role);
    }
}
