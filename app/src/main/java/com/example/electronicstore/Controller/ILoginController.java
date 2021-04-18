package com.example.electronicstore.Controller;

import com.example.electronicstore.Model.UserHelperClass;
import com.example.electronicstore.View.ILoginView;

public class ILoginController implements LoginController {
    ILoginView loginView;

    public ILoginController(ILoginView loginView) {
        this.loginView = loginView;
    }

    @Override
    public void OnLogin(String email, String password) {

        UserHelperClass user = new UserHelperClass(email,password );
        int logincode = user.isValid();

        if (logincode == 0) {

            loginView.OnLoginError("password is less than 6 characters");


        }  if (logincode == 1) {

            loginView.OnLoginError("Whitespace not allowed in password ");


        } if (logincode == 2) {
            loginView.OnLoginError("Email is Required");


        }  if (logincode == 3) {
            loginView.OnLoginError("Password is Required");


        } else {
            loginView.OnLoginSuccess("login Successful");

        }


    }
}
