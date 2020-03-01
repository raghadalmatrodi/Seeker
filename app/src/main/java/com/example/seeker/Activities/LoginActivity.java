package com.example.seeker.Activities;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.example.seeker.Database.ApiClients;
import com.example.seeker.EmployerMainPages.EmployerMainActivity;
import com.example.seeker.Model.Exception.ApiError;
import com.example.seeker.Model.Exception.ApiException;
import com.example.seeker.Model.Login;
import com.example.seeker.Model.User;
import com.example.seeker.PostBid.PostBidActivity;
import com.example.seeker.PostBid.ViewBid;
import com.example.seeker.R;
import com.example.seeker.SharedPref.Constants;
import com.example.seeker.SharedPref.MySharedPreference;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Converter;
import retrofit2.Response;

public class LoginActivity extends Activity {

    private static final String LOG = LoginActivity.class.getSimpleName();
    private EditText email, password;
    private Button login;
    private TextView forgotPass, createAccount;
    private String userEmail, userPassword;


//comment to test
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        init();


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                userEmail = email.getText().toString().toLowerCase();
                userPassword = password.getText().toString().toLowerCase();

                if(validate(userEmail, userPassword))
                {
                    LoginApiRequest();

                }

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



    private void LoginApiRequest() {

        ApiClients.getAPIs().getLoginRequest(new Login(userEmail,userPassword)).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

                Log.i(LOG, "onResponse : Success");
                if (response.isSuccessful()) {
                    Log.i(LOG, "onResponse : " + response.body().toString());
                    executeGetUserByEmailRequest(userEmail);
                    Dialog("ok");

                }//End of if
                else {
                 wrongInfoDialog("wrong email/password try again!");
                }//End of else
            }//End of big if
//


            @Override
            public void onFailure(Call<String> call, Throwable t) {
             wrongInfoDialog(t.getLocalizedMessage());
            }
        });

    }//End of LoginApiRequest()

    private void init() {
        email = findViewById(R.id.login_emailET);
        password = findViewById(R.id.login_passwordlET);
//        forgotPass = findViewById(R.id.forgetPass);
        createAccount = findViewById(R.id.goto_signup);
        login = findViewById(R.id.login_btn);

    }//End init()

    private boolean validate(String userMail, String userPass) {

        if ((!userMail.isEmpty()) && (!userPass.isEmpty())) {
                return true;



        }else {
            wrongInfoDialog("Missing fields, try Again please..");
        }
        return false;
    }//end validate()

    private void checkEmailVerification() {

        //we will change false
        boolean isVerified = false;

        if (isVerified) {
            finish();

//            Intent i = new Intent(LoginActivity.this, .class);
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
//                dialog.dismiss();
                Intent intent;
                intent = new Intent(LoginActivity.this, EmployerMainActivity.class);
                startActivity(intent);

            }//end onClick
        });//end setPositiveButton

        alertDialog.show();

    }//End of Dialog()



    private void executeGetUserByEmailRequest(String usermail){
        ApiClients.getAPIs().findUSerByEmailRequest(usermail).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()){
//                    Toast.makeText(LoginActivity.this,"RESPONSE ON SUCCESS", Toast.LENGTH_LONG).show();
                    Log.i(LOG, "onResponse: " + response.body().toString());
                    addCurrentUser(response.body());

                }else {
//                    Toast.makeText(LoginActivity.this,"RESPONSE Fail", Toast.LENGTH_LONG).show();
                    Converter<ResponseBody, ApiException> converter = ApiClients.getInstant().responseBodyConverter(ApiException.class, new Annotation[0]);
                    ApiException exception = null;
                    try {

                        exception = converter.convert(response.errorBody());

                        List<ApiError> errors = exception.getErrors();

                        if (errors != null)
                            if (!errors.isEmpty())
                                wrongInfoDialog(errors.get(0).getMessage());
                        wrongInfoDialog(exception.getMessage());

                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
//                Toast.makeText(LoginActivity.this,"RESPONSE ON FAILURE ", Toast.LENGTH_LONG).show();
                Log.i(LOG, t.getLocalizedMessage());
                wrongInfoDialog("Error");

            }
        });
    }

    private void addCurrentUser(User user) {
//        MySharedPreference.putBoolean(this,Constants.Keys.IS_LOGIN, true);
        MySharedPreference.putLong(this, Constants.Keys.USER_ID, user.getId());
        MySharedPreference.putString(this, Constants.Keys.USER_NAME, user.getUsername());
        MySharedPreference.putString(this, Constants.Keys.USER_EMAIL,  user.getEmail());
//        MySharedPreference.putString(this, Constants.Keys.USER_IMG, user.getImage());
//        MySharedPreference.putString(this, Constants.Keys.ENABLE_NOTI, user.getEnable_noti());


        /**
         * WHY?? DO I NEED IT? THINK!
         */
//        finish();
    }//End addCurrentUser()





}//End of LoginActivity
