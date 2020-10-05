package com.example.servicenovigrad;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;

public class SignIn extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
    }

    public void noAccountClick() {
        Intent intent = new Intent(this, CreateAccount.class);
        startActivity(intent);
    }
}