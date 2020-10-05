package com.example.prototyped1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.prototyped1.account.Customer;
import com.google.firebase.auth.FirebaseAuth;

public class CDisplayActivity extends AppCompatActivity {
    private Customer user;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        user = (Customer) getIntent().getSerializableExtra("USER_DATA");
        String userFirstName = user.getNameFirst();
        setContentView(R.layout.activity_customer_display);
        TextView message = (TextView) findViewById(R.id.messageDisplayID);

        message.setText( " Welcome" + userFirstName + "! You are logged in as a Customer.");
    }


    public void onClickSignOut(View view) {
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(intent);
        finish();
    }
}
