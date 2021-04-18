package com.example.electronicstore;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.electronicstore.Model.Adminstrator;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AdminstratorSignUp extends AppCompatActivity {


    public static final String TAG = "TAG";
    EditText nameText, usernameText, emailText, phoneNumberText, passwordText;
    FirebaseDatabase rootNode;
    DatabaseReference referance;
    FirebaseAuth fAuth;
    Button register;
    Button loginPage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adminstrator_sign_up);


        nameText = (EditText) findViewById(R.id.nameAD);
        usernameText = (EditText) findViewById(R.id.usernameAD);
        emailText = (EditText) findViewById(R.id.AD);
        phoneNumberText = (EditText) findViewById(R.id.phoneNumberAD);
        passwordText = (EditText) findViewById(R.id.passwordAD);

        fAuth = FirebaseAuth.getInstance();

        // if (fAuth.getCurrentUser() != null) {

        //Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        //startActivity(intent);

        //}


        register = (Button) findViewById(R.id.startAD
        );
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String name = nameText.getText().toString().trim();
                final String username = usernameText.getText().toString().trim();
                final String email = emailText.getText().toString().trim();
                final String phoneNumber = phoneNumberText.getText().toString().trim();
                final String password = passwordText.getText().toString().trim();

                //Validation
                if (username.length() < 6 || password.length() < 6) {

                    Toast.makeText(AdminstratorSignUp.this, "Username or password is less than 6 characters", Toast.LENGTH_LONG).show();



                }  if (validatePasswrod(password) == true) {

                    Toast.makeText(AdminstratorSignUp.this, "Whitespace not allowed in password ", Toast.LENGTH_LONG).show();

                }  if (TextUtils.isEmpty(email)) {
                    emailText.setError("Email is Required");
                    return;

                }  if (TextUtils.isEmpty(password)) {
                    passwordText.setError("Password is Required");
                    return;

                }

                else {


                    fAuth.createUserWithEmailAndPassword(email, password)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if(task.isSuccessful()){

                                        FirebaseUser fuser = fAuth.getCurrentUser();
                                        fuser.sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                Toast.makeText(AdminstratorSignUp.this, "Verification Email Has been Sent.", Toast.LENGTH_SHORT).show();
                                            }
                                        }).addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Log.d(TAG, "onFailure: Email not sent " + e.getMessage());
                                            }
                                        });


                                        Adminstrator adminstrator = new Adminstrator(  name,  username,  email,  phoneNumber,  password);

                                        FirebaseDatabase.getInstance().getReference("Adminstrator")
                                                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                                .setValue(adminstrator).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if(task.isSuccessful()){
                                                    Toast.makeText(AdminstratorSignUp.this, "Adminstrator Created ", Toast.LENGTH_LONG).show();
                                                    Intent intent = new Intent(getApplicationContext(), MainActivityAdmin.class);
                                                    startActivity(intent);

                                                }
                                                else {
                                                    Toast.makeText(AdminstratorSignUp.this, "Error ", Toast.LENGTH_LONG).show();

                                                }
                                            }
                                        });


                                    }
                                }
                            });



                }


            }
        });

        loginPage = (Button) findViewById(R.id.loginPageAD
        );
        loginPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                loginPage(v);

            }
        });


    }


    public boolean validatePasswrod(String pas) {
        boolean valid = true;

        for (int i = 0; i < pas.length(); i++) {

            char ch = pas.charAt(i);
            if (ch == ' ') {

                valid = true;
                break;

            } else {

                valid = false;

            }

        }
        return valid;

    }

    private void loginPage(View v) {

        Intent intent1 = new Intent(AdminstratorSignUp.this, AdminstratorLogin.class);
        startActivity(intent1);
    }
}