package com.example.prototyped1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.prototyped1.account.Customer;
import com.example.prototyped1.account.Employee;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SignUpActivity extends AppCompatActivity {

    private EditText fieldFirstName, fieldLastName, fieldEmail, fieldPwd;
    private RadioGroup fieldUserTypeSelection;
    private Button btnSignUp;
    private TextView signIn;
    private String userType;
    private ProgressBar loading;
    //firebase stuff
    private DatabaseReference ref;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign);
        fieldFirstName = findViewById(R.id.FirstName);
        fieldLastName = findViewById(R.id.LastName);
        fieldEmail = findViewById(R.id.Email);
        fieldPwd = findViewById(R.id.Password);
        fieldUserTypeSelection = findViewById(R.id.userType);
        signIn= findViewById(R.id.AlreadyUser);
        btnSignUp = findViewById(R.id.SignUp);
//        loading.setVisibility(View.GONE);


        //  CurrentInstance  of the databases from FireBase
        mAuth = FirebaseAuth.getInstance();
        loading = findViewById(R.id.signUpProgressBar);
        loading.setVisibility(View.INVISIBLE);

        // We need to handle the sign up Button

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Calls the method that handles this occurence
                onClickSignUpBtn();


            }
        });

        // not for this deliverable but this is how the click from the already user Tab will be handled
        //  signIn.setOnClickListener(new View.OnClickListener() {
        //            @Override
        //            public void onClick(View v) {
        //                Intent I = new Intent(SignUpActivity.this, LoginActivity.class);
        //                startActivity(I);
        //                finish();
        //            }
        //        });


    }

    public void onClickHaveAccount(View v) {
        Intent intent;
        intent = new Intent(getApplicationContext(),LoginActivity.class);
        startActivity(intent);
        finish();
    }


    public void onClickSignUpBtn () {

        final String firstName = fieldFirstName.getText().toString().trim();
        final String lastName = fieldLastName.getText().toString().trim();
        final String email = fieldEmail.getText().toString().trim();
        final String pwd = fieldPwd.getText().toString().trim();
        final String hashedPwd = getHashedPassword(pwd);


        // Loading wheel
        loading.setVisibility(View.VISIBLE);

        if (fieldUserTypeSelection.getCheckedRadioButtonId() == -1) {
            Toast.makeText(SignUpActivity.this, "Please select your status", Toast.LENGTH_LONG).show();
        }
        if (FieldUtil.fieldsAreValid(firstName, lastName, pwd,SignUpActivity.this) && !(fieldUserTypeSelection.getCheckedRadioButtonId() == -1)) {
            // Create user w/ Firebase
            mAuth.createUserWithEmailAndPassword(email, pwd)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Store in database
                                ref = FirebaseDatabase.getInstance().getReference();

                                if (userType == "C") {
                                    Customer newUser = new Customer(email, hashedPwd, firstName, lastName,userType);
                                    //send info to firebase

                                    ref.child("Customer").setValue(newUser);


                                    // This will now Display You have Succesfully signed up.
                                    // getApplicationContext() == This.Screen to -> CustomerDisplayActivity
                                    Intent intent;
                                    intent = new Intent(getApplicationContext(),CDisplayActivity.class);
//                                    intent.putExtra("USER_FIRSTNAME", firstName);
//                                    intent.putExtra("USER_LASTNAME", lastName);
                                    intent.putExtra("USER_INFO",  newUser);
                                    startActivity(intent);
                                    finish();

                                }
                                // userType == 'E'
                                else {
                                    Employee newUser2 = new Employee(firstName, lastName, email, pwd,userType);
                                    // Send to firebase

                                    // Basics : When we write to the database we use the setValue() method and pass in the value that corresponds to the appropriate child key:

                                   //  Basics :  ref.child("<childNodeName>").setValue("<someValue>");

                                    ref.child("Employee").setValue(newUser2);


                                    // This will now Display You have Succesfully signed up.
                                    // getApplicationContext() == This.Screen to -> CustomerDisplayActivity
//                                    Intent intent;
//                                    intent = new Intent(getApplicationContext(),CDisplayActivity.class);
//                                    intent.putExtra("USER_FIRSTNAME", firstName);
//                                    intent.putExtra("USER_LastNAME", firstName);
//                                    intent.putExtra("USER_INFO",  newUser2);
//                                    startActivity(intent);
//                                    finish();

                                }

                            }
                            else {
                                // Print out error message
                                loading.setVisibility(View.GONE);
                                Toast.makeText(SignUpActivity.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                            }
                        }
                    });
        }
        else {
            loading.setVisibility(View.GONE);
        }
    }

    public void setUserType(View v) {
        int selectedID = fieldUserTypeSelection.getCheckedRadioButtonId();

        if (selectedID == R.id.btnScustomer) {
            userType = "C";
        }
        else if (selectedID == R.id.btnSEmployee) {
            userType = "E";
        }
    }

    // method for secure password
    public static String getHashedPassword(String pwd) {
        try {
            // Returns a MessageDigest object that implements the specified digest algorithm.
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            //Updates the digest using the specified byte.
            md.update(pwd.getBytes());
            // RETURNS THE ARRAY OF BYTES

            byte[] digestedBytes = md.digest();
            // Create a container that stores the hexCodes

            StringBuilder hexDigest = new StringBuilder();
            for (byte digestedByte : digestedBytes) {
                hexDigest.append(Integer.toString((digestedByte & 0xff) + 0x100, 16).substring(1));
            }
            return hexDigest.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }
}


