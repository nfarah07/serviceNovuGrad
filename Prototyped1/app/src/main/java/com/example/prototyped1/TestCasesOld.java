package com.example.prototyped1;

import android.os.Bundle;

public class TestCasesOld {

    public static void main( String[] argv ) {

        //In the format of firstName, lastName, email, password
        String customer1[] = {"Customer", "Testing", "customer@email.com", "customer"}; //Normal inputs
        String customer2[] = {"Customer2", "AnotherTest", "secondcustomer@email.com", "a"}; //Password is to short
        String customer3[] = {"Customer3", "ThirdTest", "notAnEmail", "password"}; //Invalid email format

        String employee1[] = {"Employee", "Testing", "employee@email.com", "employee"}; //Normal inputs
        String employee2[] = {"Employee2", "AnotherTest", "secondcustomer@email.com", "a"}; //Password is to short
        String employee3[] = {"Employee3", "ThirdTest", "notAnEmail", "password"}; //Invalid email format

        String[] accountTests[] = {customer1,customer2,customer3,employee1,employee2,employee3};

        for (int i = 0; i < 6; i++) {
            //SignUpActivity signUp = new SignUpActivity();

        }

    }

    private void accountCreation(String accountDetails[]){

    }

}
