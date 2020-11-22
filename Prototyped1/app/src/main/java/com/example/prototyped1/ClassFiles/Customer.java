package com.example.prototyped1.ClassFiles;

public class Customer extends UserAccount {

    public Customer(){
        super();
    }

    public Customer(String nameFirst, String nameLast, String email, String password, String id) {
        super(nameFirst, nameLast,email, password,  id);
    }

}
