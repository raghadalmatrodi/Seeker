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

import com.example.seeker.Model.ApiResponse;
import com.example.seeker.Model.Exception.ApiError;
import com.example.seeker.Model.Exception.ApiException;
import com.example.seeker.Model.Role;
import com.example.seeker.Model.User;
import com.example.seeker.Model.UserResponse;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Converter;
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
              //  Intent i = new Intent(SignupActivity.this, LoginActivity.class);
              //  startActivity(i);
            }

        });
    }//End of onCreate()

    private void executeSignUpApiRequest() {

       // showProgressDialog(false);
      User userToSignup = new User();
      userToSignup.setEmail(mail);
      userToSignup.setPassword(pass);
      userToSignup.setUsername(name);

      Role role = new Role(1,null);
      Set<Role> setOfRole = new HashSet<>();
      setOfRole.add(role);
      userToSignup.setRoles(setOfRole);




        //POST the data
        ApiClients.getAPIs().getSignUpRequest(userToSignup).enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
               // hideProgressDialog();

                Log.i(LOG, "onResponse : Success");

                if (response.isSuccessful()) {


                    Log.i(LOG, "onResponse : " + response.body().toString());

                    //Todo: intent
                    Dialog("ok");

                } else{
                    Converter<ResponseBody,ApiException> converter = ApiClients.getInstant().responseBodyConverter(ApiException.class,new Annotation[0]);
                    ApiException exception = null;
                    try {
                        exception = converter.convert(response.errorBody());

                       List<ApiError> errors= exception.getErrors();


                       if(!errors.isEmpty())
                        Dialog(errors.get(0).getMessage());
                       // Dialog(exception.getMessage());

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

            }//End of onResponse()

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                Log.i(LOG, t.getLocalizedMessage());
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
                dialog.dismiss();
               finish();

            }//end onClick
        });//end setPositiveButton

        alertDialog.show();

    }//End of Dialog()

}//End of SignUpActivity
