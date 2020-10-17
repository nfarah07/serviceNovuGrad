package com.example.prototyped1;

import android.content.Intent;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import com.example.prototyped1.Account;
import com.example.prototyped1.Admin;
import com.example.prototyped1.Customer;
import com.example.prototyped1.Employee;

public class AdminListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_list);
        //adminUser = (Admin) getIntent().getSerializableExtra("USER_INFO");

        //val listView = findViewById<ListView>(R.id.listViewServices);
    }

    public void onToWelcome(View view){
        Admin adminMain = new Admin("admin", "admin");
        Intent intent = new Intent(getApplicationContext(), AdminMainActivity.class);   //Application Context and Activity
        intent.putExtra("USER_INFO",  adminMain);
        startActivity(intent);
        finish();
    }
}
