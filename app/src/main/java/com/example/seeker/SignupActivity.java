package com.example.seeker;


import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

import com.example.seeker.Model.UserResponse;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SignupActivity extends Activity {

    private EditText userName, email, password, confirmPassword;
    private String name, pass, confirmpass, mail;
    private TextView haveAccount;
    private Button signUp;
    private AlertDialog dialog;
    private CheckBox checkTerms ;
private static final String LOG=SignupActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);


        init();

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                //validate method

                if (validate()) {

                    String mail = email.getText().toString().trim();
                    String pass = password.getText().toString().trim();


                    executeSignUpApiRequest();
                    //Progress Dialog

                    //here we will check if this email was used before
                    //wrongInfoDialog("This email is already exist");
                    //here we will check if this username was used before
                    //wrongInfoDialog("This username is already exist");


                    //if it was successful here we will send email verification
                    //sendEmailVerification()

                }//End if block


            }//end onClick()

        });

        haveAccount.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent i = new Intent(SignupActivity.this, LoginActivity.class);
                startActivity(i);
            }

        });
    }//End of onCreate()

    private void executeSignUpApiRequest() {

       // showProgressDialog(false);

        //POST the data
        ApiClients.getAPIs().getSignUpRequest(createPartFromString(userName.getText().toString().trim()),
                createPartFromString(mail),
                createPartFromString(pass)
                ).enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
               // hideProgressDialog();

                Log.i(LOG, "onResponse : Success");

                if (response.body() != null) {

                    Log.i(LOG, "onResponse : " + response.body().toString());
                    if (response.body().getStatus() == 1) {
                        Dialog("ok");
                    }//End of if
                    else {
                      //  showDialog(response.body().getMsg());
                    }//End of else
                }//End of big if


            }//End of onResponse()

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                Log.i(LOG, "onFailure");
                Dialog("Error");
//                hideProgressDialog();
//                showDialog(getString(R.string.errorMessage));

            }//End of onFailure()
        });//End of Callback

    }//End of executeSignUpApiRequest()



    private RequestBody createPartFromString(String field) {
        if (field != null) {
            return RequestBody.create(MediaType.parse("text/plain"), field);
        }//End of if
        else {
            return RequestBody.create(MediaType.parse("text/plain"), "");

        }//End of else
    }//End of createPartFromString()




    private void init() {

        userName = findViewById(R.id.signup_username);
        email = findViewById(R.id.signup_emailET);
        password = findViewById(R.id.signup_passwordlET);
        confirmPassword = findViewById(R.id.signup_conform_password);
        signUp = findViewById(R.id.signup_btn);
        haveAccount = findViewById(R.id.goto_login);
        checkTerms = findViewById(R.id.terms_conditions_cb);
    }//End of init()

    private boolean validate() {


        name = userName.getText().toString();
        pass = password.getText().toString();
        confirmpass = confirmPassword.getText().toString();
        mail = email.getText().toString();
        String regex = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";

        if (name.isEmpty() || pass.isEmpty() || confirmpass.isEmpty() || mail.isEmpty()) {

            wrongInfoDialog("Missing field");

        } else {
            if (!mail.matches(regex)) {

                wrongInfoDialog("Invalid email format");

            } else {
                if (pass.length() < 6) {

                    wrongInfoDialog("Password should be more than 6 characters, please try again");


                } else {
                    if (!pass.equals(confirmpass)) {

                        wrongInfoDialog("Passwords do not match,, please check then try again");


                    } else {
                        if(checkTerms.isChecked())
                        {
                            return true;
                        }else{
                            wrongInfoDialog("you have to accept terms and condition before signing up ");
                        }
                    }

                }
            }


        }
        return false;

    }//End of validate()

    private void sendEmailVerification() {

        //here we will send and use Dialog()

        //Dialog("Verification email has been sent");
    }//End of sendEmailVerification()

    private void wrongInfoDialog(String msg) {
        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);

        alertDialog.setTitle("Warning");

        // Setting Dialog Message
        alertDialog.setMessage(msg);

        // Setting Icon to Dialog
//        alertDialog.setIcon(R.drawable.exclamation);

        //Setting Negative "ok" Button
        alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }//end onClick
        });//end setPositiveButton

        alertDialog.show();

    }//End wrongInfoDialog()

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
                intent = new Intent(SignupActivity.this, LoginActivity.class);
                startActivity(intent);

            }//end onClick
        });//end setPositiveButton

        alertDialog.show();

    }//End of Dialog()

}//End of SignUpActivity
