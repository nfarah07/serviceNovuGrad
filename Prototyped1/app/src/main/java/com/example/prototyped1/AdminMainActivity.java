package com.example.prototyped1;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import com.example.prototyped1.Account;
import com.example.prototyped1.Admin;
import com.example.prototyped1.UserAccount;
import com.google.firebase.auth.FirebaseAuth;

public class AdminMainActivity extends AppCompatActivity {
    private Admin adminUser ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_main);
        adminUser = (Admin) getIntent().getSerializableExtra("USER_INFO");
        String emailOfAdmin= adminUser.getEmail();

//        setContentView(R.layout.activity_admin_main);
        TextView message = (TextView) findViewById(R.id.messageDisplayID);

        message.setText( " Welcome " + emailOfAdmin + "! You are logged in as the Admin");

    }
    public void onClickSignOut(View view) {
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(intent);
        finish();
    }

    public void onServicesList(View view) {
        Intent intent = new Intent(getApplicationContext(), AdminListActivity.class);
        startActivity(intent);
        finish();
    }

    public void onEmployeeList(View view) {
        Intent intent = new Intent(getApplicationContext(), AdminEmployeeActivity.class);
        startActivity(intent);
        finish();
    }

    public void onCustomerList(View view) {
        Intent intent = new Intent(getApplicationContext(), AdminCustomerActivity.class);
        startActivity(intent);
        finish();
    }
}
