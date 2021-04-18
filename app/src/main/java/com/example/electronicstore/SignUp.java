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

import com.example.electronicstore.Model.UserHelperClass;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUp extends AppCompatActivity {


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
        setContentView(R.layout.activity_sign_up);


        nameText = (EditText) findViewById(R.id.nameAD);
        usernameText = (EditText) findViewById(R.id.usernameAD);
        emailText = (EditText) findViewById(R.id.Admin);
        phoneNumberText = (EditText) findViewById(R.id.phoneNumberAD);
        passwordText = (EditText) findViewById(R.id.passwordAdmin);

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

                    Toast.makeText(SignUp.this, "Username or password is less than 6 characters", Toast.LENGTH_LONG).show();



                }  if (validatePasswrod(password) == true) {

                    Toast.makeText(SignUp.this, "Whitespace not allowed in password ", Toast.LENGTH_LONG).show();

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
                                                Toast.makeText(SignUp.this, "Verification Email Has been Sent.", Toast.LENGTH_SHORT).show();
                                            }
                                        }).addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Log.d(TAG, "onFailure: Email not sent " + e.getMessage());
                                            }
                                        });


                                        UserHelperClass user = new UserHelperClass(  name,  username,  email,  phoneNumber,  password);

                                        FirebaseDatabase.getInstance().getReference("Users")
                                                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                                .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if(task.isSuccessful()){
                                                    Toast.makeText(SignUp.this, "User Created ", Toast.LENGTH_LONG).show();
                                                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                                    startActivity(intent);

                                                }
                                                else {
                                                    Toast.makeText(SignUp.this, "Error ", Toast.LENGTH_LONG).show();

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

        Intent intent1 = new Intent(SignUp.this, Login.class);
        startActivity(intent1);
    }
}