package com.example.seeker;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;

import com.example.seeker.Model.Login;
import com.example.seeker.Model.UserResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends Activity {

    private static final String LOG = LoginActivity.class.getSimpleName();
    private EditText email, password;
    private Button login;
    private TextView forgotPass, createAccount;
    private String userEmail, userPassword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        init();


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                userEmail = email.getText().toString();
                userPassword = password.getText().toString();
//                validate(userEmail, userPassword);
                LoginApiRequest();
            }//End onClick()
        });

        createAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, SignupActivity.class));
            }//End onClick()
        });

//        forgotPass.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                //show forget password dialog or activity
//            }//End of onClick()
//        });

    }//End onCreate()

    //POST the data
//        ApiClients.getAPIs().getSignUpRequest(userToSignup).enqueue(new Callback<UserResponse>() {
//        @Override
//        public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
//            // hideProgressDialog();
//
//            Log.i(LOG, "onResponse : Success");
//
//            if (response.body() != null) {
//
//                Log.i(LOG, "onResponse : " + response.body().toString());
//                if (response.body().getStatus() == 1) {
//                    Dialog("ok");
//                }//End of if
//                else {
//                    //  showDialog(response.body().getMsg());
//                }//End of else
//            }//End of big if
//
//
//        }//End of onResponse()
//
//        @Override
//        public void onFailure(Call<UserResponse> call, Throwable t) {
//            Log.i(LOG, t.getLocalizedMessage());
//            Dialog("Error");
//
////                hideProgressDialog();
////                showDialog(getString(R.string.errorMessage));
//
//        }//End of onFailure()
//    });//End of Callback
//
//}//End of executeSignUpApiRequest()

    private void LoginApiRequest() {
        ApiClients.getAPIs().getLoginRequest(new Login(userEmail,userPassword)).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

                Log.i(LOG, "onResponse : " + response.body().toString());
                if (response.isSuccessful()) {
                    Dialog("ok");
                }//End of if
                else {
                 Dialog(response.message());
                }//End of else
            }//End of big if
//


            @Override
            public void onFailure(Call<String> call, Throwable t) {
             Dialog(t.getLocalizedMessage());
            }
        });

    }

    private void init() {

        email = findViewById(R.id.login_emailET);
        password = findViewById(R.id.login_passwordlET);
//        forgotPass = findViewById(R.id.forgetPass);
        createAccount = findViewById(R.id.goto_signup);
        login = findViewById(R.id.login_btn);

    }//End init()

    private void validate(String userMail, String userPass) {

        if ((!userMail.isEmpty()) && (!userPass.isEmpty())) {

            //Progress Dialog()
            //first we will check if the email in the database
            wrongInfoDialog("Invalid email or password, please check and try again");

            //if successful we will check if the email was verified
            checkEmailVerification();

        }else {
            wrongInfoDialog("Missing fields, try Again please..");
        }
    }//end validate()

    private void checkEmailVerification() {

        //we will change false
        boolean isVerified = false;

        if (isVerified) {
            finish();

//            Intent i = new Intent(LoginActivity.this, MainActivity.class);
//            MySharedPrefrance.putString(this, "password", password.getText().toString());
//            startActivity(i);

        } else {

            wrongInfoDialog("Please verify your email address");

        }
    }//End checkEmailVerification()


    private void wrongInfoDialog(String msg) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        // Setting Dialog Title
        alertDialog.setTitle("Warning");

        // Setting Dialog Message
        alertDialog.setMessage(msg);

        // Setting Icon to Dialog
//        alertDialog.setIcon(R.drawable.exclamation);
        //Setting Negative "ok" Button
        alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                email.setText("");
                password.setText("");

            }//end onClick
        });//end setPositiveButton

        alertDialog.show();

    }//end wrongInfoDialog()

    private void Dialog(String msg) {

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);

        // Setting Dialog Message
        alertDialog.setMessage(msg);

        //Setting Negative "ok" Button
        alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
//                firebaseAuth.signOut();
                finish();
                Intent intent;
             //   intent = new Intent(SignupActivity.this, LoginActivity.class);
                //startActivity(intent);

            }//end onClick
        });//end setPositiveButton

        alertDialog.show();

    }//End of Dialog()
}
