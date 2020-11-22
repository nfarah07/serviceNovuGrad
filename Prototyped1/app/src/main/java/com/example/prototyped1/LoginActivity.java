package com.example.prototyped1;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.view.View;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.prototyped1.ClassFiles.Admin;
import com.example.prototyped1.ClassFiles.Customer;
import com.example.prototyped1.ClassFiles.Employee;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.*;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth mAuth; //used to authenticate user
    private TextView signUp;    //clickable text for going to sign up screen
    private ProgressBar loading;    //loading view for when you need to load
    private Button btnLogin; // used to track when the user proceeds to loginPage
    private EditText email,password;



    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT); // prevents changing orientation
        //FirebaseDatabase.getInstance().setPersistenceEnabled(true); //caches login data for faster login in the future
        signUp= findViewById(R.id.redirectSignup);
        mAuth = FirebaseAuth.getInstance();
        loading = findViewById(R.id.progressBar);
        loading.setVisibility(View.GONE);
        btnLogin = findViewById(R.id.Login);
        email = (EditText) findViewById(R.id.Email);
        password= (EditText) findViewById(R.id.Password);


    }


    public void onLoginClicked (View view) {
        loading.setVisibility(View.VISIBLE);
        String sEmail = email.getText().toString().trim();
        String sPwd = password.getText().toString().trim();

        if (sEmail.equals("admin") && sPwd.equals("admin")) { // hard code for admin login
            Admin adminMain = new Admin("admin", "admin");
            Intent intent = new Intent(getApplicationContext(), AdminMainActivity.class);
            //Application Context and Activity
            intent.putExtra("USER_INFO",  adminMain);

            startActivity(intent);//, ProfileActivity.REQUEST_NEW_TEAM);
            finish();
        }
        else {
            if (emailIsValid(sEmail) && !(sPwd.equals(""))) { // checks if login inputs are valid
                //creates listener used to authenticate login
                mAuth.signInWithEmailAndPassword(sEmail, sPwd).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            getData(); // if valid login, get user data

                        } else {
                            Toast.makeText(LoginActivity.this, "Login has failed!", Toast.LENGTH_LONG).show();
                            loading.setVisibility(View.GONE);
                        }
                    }
                });
            }
            else {
                Toast.makeText(this, "Please enter a valid email and password", Toast.LENGTH_LONG).show();
                loading.setVisibility(View.GONE);
            }
        }
    }


    public void onSignUp(View view){
        Intent intent = new Intent(getApplicationContext(), SignUpActivity.class);   //Application Context and Activity
        startActivity(intent);//, ProfileActivity.REQUEST_NEW_TEAM);
        finish();
    }


    //This checks to see if we have a valid login

    private void getData(){
        final String userID = mAuth.getCurrentUser().getUid();

        // Determine if the user is a customer
        DatabaseReference customerRef = FirebaseDatabase.getInstance().getReference().child("Customer");
        customerRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChild(userID)) {
                    String email = dataSnapshot.child(userID).child("email").getValue(String.class);
                    String pwd = dataSnapshot.child(userID).child("password").getValue(String.class);
                    String firstName = dataSnapshot.child(userID).child("nameFirst").getValue(String.class);
                    String lastName = dataSnapshot.child(userID).child("nameLast").getValue(String.class);
                    Customer userB;
                    userB = new Customer(firstName, lastName,email, pwd ,userID);

                    Intent intent = new Intent(getApplicationContext(), CDisplayActivity.class);
                    intent.putExtra("USER_INFO", userB);
                    startActivity(intent);
                    finish();
                } else {

                    return;
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) { }
        });

        // Determine if the user is an employee
        DatabaseReference employeesRef = FirebaseDatabase.getInstance().getReference().child("Employees");
        employeesRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChild(userID)) {
                    String email = dataSnapshot.child(userID).child("email").getValue(String.class);
                    String pwd = dataSnapshot.child(userID).child("password").getValue(String.class);
                    String firstName = dataSnapshot.child(userID).child("nameFirst").getValue(String.class);
                    String lastName = dataSnapshot.child(userID).child("nameLast").getValue(String.class);
                    String phone = dataSnapshot.child(userID).child("phone").getValue(String.class);
                    String address = dataSnapshot.child(userID).child("address").getValue(String.class);
                    Employee userB = new Employee(firstName, lastName,email, pwd ,userID, phone, address);

                    Intent intent = new Intent(getApplicationContext(), CDisplayActivity.class);
                    intent.putExtra("USER_INFO", userB);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(LoginActivity.this, "If your log in was not successful, your account was deleted by the administrator", Toast.LENGTH_LONG).show();

                    return;
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) { }
        });
        loading.setVisibility(View.GONE);
    }



    private static final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    // Valid email pattern

    public static boolean emailIsValid(String email) {
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX .matcher(email);
        return matcher.find();
    }



}