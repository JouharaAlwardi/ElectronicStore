package com.example.electronicstore;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class AdminstratorLogin extends AppCompatActivity {
    EditText mEmail, mPassword;
    Button login;
    FirebaseAuth fAuth;
    TextView forgetPassword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adminstrator_login);

        mEmail = (EditText) findViewById(R.id.Admin);
        mPassword = (EditText) findViewById(R.id.passwordAdmin);
        forgetPassword = (TextView) findViewById(R.id.forgetPassword);
        fAuth = FirebaseAuth.getInstance();

        /////////////////////
     Button signupPage = (Button) findViewById(R.id.adminTitle
        );
        signupPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
          startActivity(new Intent(getApplicationContext(), AdminstratorSignUp.class));

            }
        });

        /////////////////////
        login = (Button) findViewById(R.id.loginAdmin);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = mEmail.getText().toString();
                String password = mPassword.getText().toString();

                if (password.length() < 6) {

                    Toast.makeText(AdminstratorLogin.this, "password is less than 6 characters", Toast.LENGTH_LONG).show();


                }  if (validatePasswrod(password) == true) {

                    Toast.makeText(AdminstratorLogin.this, "Whitespace not allowed in password ", Toast.LENGTH_LONG).show();

                } if (TextUtils.isEmpty(email)) {
                    mEmail.setError("Email is Required");
                    return;

                }  if (TextUtils.isEmpty(password)) {
                    mPassword.setError("Password is Required");
                    return;

                } else {


                }



                fAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()) {

                            Toast.makeText(AdminstratorLogin.this, "Logged in Successfully ", Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(AdminstratorLogin.this, MainActivityAdmin.class);
                            startActivity(intent);


                        }
                    }
                });


            }
        });


        /////////////////////

        forgetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final EditText resetMail = new EditText(v.getContext());
                final AlertDialog.Builder passwordResetDialog = new AlertDialog.Builder(v.getContext());
                passwordResetDialog.setTitle("Reset Password ?");
                passwordResetDialog.setMessage("Enter Your Email To Received Reset Link.");
                passwordResetDialog.setView(resetMail);

                passwordResetDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // extract the email and send reset link
                        String mail = resetMail.getText().toString();
                        fAuth.sendPasswordResetEmail(mail).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(AdminstratorLogin.this, "Reset Link Sent To Your Email.", Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(AdminstratorLogin.this, "Error ! Reset Link is Not Sent" + e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });

                    }
                });

                passwordResetDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // close the dialog
                    }
                });

                passwordResetDialog.create().show();

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
}