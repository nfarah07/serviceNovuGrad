package com.example.prototyped1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.prototyped1.Customer;
import com.example.prototyped1.Employee;
import com.example.prototyped1.UserAccount;
import com.google.firebase.auth.FirebaseAuth;

public class CDisplayActivity extends AppCompatActivity {
    private UserAccount user;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        user = (UserAccount) getIntent().getSerializableExtra("USER_INFO");
//        getIntent().getSerializableExtra("")
        String userFirstName = user.getNameFirst();
        setContentView(R.layout.activity_customer_display);
        TextView message = (TextView) findViewById(R.id.messageDisplayID);
        if (user instanceof Customer){
            message.setText( " Welcome " + userFirstName + "! You are logged in as a Customer");

        }
        if(user instanceof Employee){
            message.setText( " Welcome " + userFirstName + "! You are logged in as a Branch");

        }


    }


    public void onClickSignOut(View view) {
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(intent);
        finish();
    }
}
