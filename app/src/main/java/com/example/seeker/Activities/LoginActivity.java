package com.example.seeker.Activities;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

import com.example.seeker.Admin.AdminActivity;
import com.example.seeker.Database.ApiClients;
import com.example.seeker.Database.PublicKeyDatabase;
import com.example.seeker.EmployerMainPages.EmployerMainActivity;
import com.example.seeker.FreelancerMainPages.FreelancerMainActivity;
import com.example.seeker.Model.Employer;
import com.example.seeker.Model.Exception.ApiError;
import com.example.seeker.Model.Exception.ApiException;
import com.example.seeker.Model.Freelancer;
import com.example.seeker.Model.Login;
import com.example.seeker.Model.Responses.ApiResponse;
import com.example.seeker.Model.User;
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
    private String encryptedPassword;
    private String currentType;
    private Button resetPassword;
    private EditText forget_pass_email;

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        init();


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                userEmail = email.getText().toString().toLowerCase().trim();
                userPassword = password.getText().toString();

//        if (userEmail.equals("admin@seeker.com")&& userPassword.equals("seekerapp2020"))
//        {
//            Intent intent = new Intent(LoginActivity.this, AdminActivity.class);
//            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
//            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            startActivity(intent);
//             finish();
//        }

                if(validate(userEmail, userPassword))
                {
                    try {
                        encryptedPassword = PublicKeyDatabase.encryptMessage(userPassword);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    Log.d("testLog", encryptedPassword);
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

        forgotPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showForgetPasswordDialog();
            }
        });

    }//End onCreate()

    private void showForgetPasswordDialog() {
        final ForgotPassDialog customDialog = new ForgotPassDialog(LoginActivity.this);
        customDialog.show();
        customDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        resetPassword = customDialog.findViewById(R.id.btn_send);
        forget_pass_email = customDialog.findViewById(R.id.forgotPass_edtEmail);

        resetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(forget_pass_email.getText().toString().equals("") || forget_pass_email.getText().toString().trim().equals("") ){
                    customDialog.dismiss();
                    wrongInfoDialog("The email is empty");
                } else if(!isValid(forget_pass_email.getText().toString())) {
                    customDialog.dismiss();
                    wrongInfoDialog("Wrong email format");
                }else{
                    ApiClients.getAPIs().forgetPassword(forget_pass_email.getText().toString()).enqueue(new Callback<Void>() {
                        @Override
                        public void onResponse(Call<Void> call, Response<Void> response) {
                            if(response.isSuccessful()){
                                customDialog.dismiss();

                                showDialog("If your'e registered the reset password email is sent to your email");

                            }else{
                                customDialog.dismiss();

                                showDialog("If your'e registered the reset password email is sent to your email");

                            }

                        }

                        @Override
                        public void onFailure(Call<Void> call, Throwable t) {
                            customDialog.dismiss();

                            wrongInfoDialog("Error, please try again later");

                        }
                    });


                }

            }//End  onClick() //for forgotPassDialog_sendBtn btn
        });

    }

    private boolean isValid(String forget_pass_email) {
        String regex = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";

        return  forget_pass_email.matches(regex);
    }

    private void LoginApiRequest() {

        ApiClients.getAPIs().getLoginRequest(new Login(userEmail,encryptedPassword)).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

                Log.i(LOG, "onResponse : Success");
                if (response.isSuccessful()) {
                    {    Log.i(LOG, "onResponse : " + response.body().toString());
                    executeGetUserByEmailRequest(userEmail);



//    else {
//        Intent intent = new Intent(LoginActivity.this, FreelancerMainActivity.class);
//        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
//        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        startActivity(intent);
//        finish();
//    }


                }}else if(response.code()==400){
                    Converter<ResponseBody,ApiException> converter = ApiClients.getInstant().responseBodyConverter(ApiException.class,new Annotation[0]);
                    ApiException exception = null;
                    try {
                        exception = converter.convert(response.errorBody());

                                wrongInfoDialog(exception.getMessage());

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
              //   wrongInfoDialog(response.message());
                } else{
                 wrongInfoDialog("Wrong Email or Password");

                }
            }//End of big if

            @Override
            public void onFailure(Call<String> call, Throwable t) {
             wrongInfoDialog(t.getLocalizedMessage());
            }
        });

    }//End of LoginApiRequest()


    private void navigateToMainPage() {
        if(currentType.equals("EMPLOYER"))
        {
            Intent intent = new Intent(LoginActivity.this, EmployerMainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();}

        if(currentType.equals("FREELANCER")){



            Intent intent = new Intent(LoginActivity.this, FreelancerMainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        } else if (currentType.equals("ADMIN")){
                        Intent intent = new Intent(LoginActivity.this, AdminActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
             finish();
        }
    }

    private void init() {
        email = findViewById(R.id.login_emailET);
        password = findViewById(R.id.login_passwordlET);
        forgotPass = findViewById(R.id.goto_resetPassword);
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

                dialog.dismiss();
                email.setText("");
                password.setText("");
            }//end onClick
        });//end setPositiveButton

 alertDialog.show();

    }//end wrongInfoDialog()

    private void showDialog(String msg) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        // Setting Dialog Title
        alertDialog.setTitle("Successful");

        // Setting Dialog Message
        alertDialog.setMessage(msg);

        // Setting Icon to Dialog
//        alertDialog.setIcon(R.drawable.exclamation);
        //Setting Negative "ok" Button
        alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

                dialog.dismiss();
                email.setText("");
                password.setText("");
            }//end onClick
        });//end setPositiveButton

        alertDialog.show();

    }//end wrongInfoDialog()

    private void executeGetUserByEmailRequest(String usermail){
        ApiClients.getAPIs().findUSerByEmailRequest(usermail).enqueue(new Callback<User>() {

            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()){
                    Log.i(LOG, "onResponse: " + response.body().toString());
                 currentType=   response.body().getCurrent_type();

                    addCurrentUser(response.body());

                }else {
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

        MySharedPreference.putBoolean(this,Constants.Keys.IS_LOGIN, true);
        Long userId = new Long(user.getId());
        MySharedPreference.putLong(this, Constants.Keys.USER_ID, userId);
        MySharedPreference.putString(this, Constants.Keys.USER_NAME, user.getUsername());
        MySharedPreference.putString(this, Constants.Keys.USER_EMAIL,  user.getEmail());
        MySharedPreference.putString(this,Constants.Keys.USER_CURRENT_TYPE,user.getCurrent_type() );
        MySharedPreference.putString(this, Constants.Keys.USER_IMG, user.getAvatar());

        MySharedPreference.putBoolean(this, Constants.Keys.enableAcceptBidNoti, user.isEnableAcceptBidNoti());
        MySharedPreference.putBoolean(this, Constants.Keys.enableMilestoneDLNoti, user.isEnableMilestoneDLNoti());
        MySharedPreference.putBoolean(this, Constants.Keys.enableProjectExpiryNoti, user.isEnableProjectExpiryNoti());
        MySharedPreference.putBoolean(this, Constants.Keys.enableProjectSkillNoti, user.isEnableProjectSkillNoti());

        navigateToMainPage();
//        MySharedPreference.putString(this, Constants.Keys.ENABLE_NOTI, user.getEnable_noti());


        String token = MySharedPreference.getString(getApplicationContext(),Constants.Keys.TOKEN_ID,"");

        if(!(token == null || token.equals("")) ){
            ApiClients.getAPIs().updateToken(token ,userId ).enqueue(new Callback<ApiResponse>() {
                @Override
                public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                    if(response.isSuccessful()){
                        Log.d(LOG, "successful token: " + response.message());

                    }else{
                        Log.d(LOG, "Not suc: " + response.message());

                    }
                }

                @Override
                public void onFailure(Call<ApiResponse> call, Throwable t) {
                    Log.d(LOG, "Failure : " + t.getMessage());

                }
            });
        }

        getEmployerByUserId(MySharedPreference.getLong(this,Constants.Keys.USER_ID,-1));
        //todo: hind added to test get freelancer by use id -> remove later if didn't work :)
        getFreelancerByUserIDRequest(MySharedPreference.getLong(this,Constants.Keys.USER_ID,-1));

        /**
         * WHY?? DO I NEED IT? THINK!
         */

//        finish();
    }//End addCurrentUser()

    private void getEmployerByUserId(long user_id) {

        ApiClients.getAPIs().getEmployerByUserIdRequest(user_id).enqueue(new Callback<Employer>() {
            @Override
            public void onResponse(Call<Employer> call, Response<Employer> response) {

                if(response.isSuccessful()){

                    Employer employer = response.body();
                    MySharedPreference.putLong(LoginActivity.this, Constants.Keys.EMPLOYER_ID, employer.getId());
                    Log.i(LOG, "Employer ID "+   MySharedPreference.getLong(LoginActivity.this, Constants.Keys.EMPLOYER_ID,-1));

                }
            }

            @Override
            public void onFailure(Call<Employer> call, Throwable t) {

            }
        });

    }

    //todo: HIND FIND FREELANCER BY USER ID API REQUEST METHOD IMPLEMENTATION

    private void getFreelancerByUserIDRequest(long user_id ){

        ApiClients.getAPIs().getFreelancerByUserIdRequest(user_id).enqueue(new Callback<Freelancer>() {
            @Override
            public void onResponse(Call<Freelancer> call, Response<Freelancer> response) {
                if (response.isSuccessful()){
                    Freelancer freelancer = response.body();
                    MySharedPreference.putLong(LoginActivity.this, Constants.Keys.FREELANCER_ID, freelancer.getId());
                    MySharedPreference.putString(LoginActivity.this, Constants.Keys.FREELANCER_NAME, freelancer.getUser().getUsername());
                }else{

                }
            }

            @Override
            public void onFailure(Call<Freelancer> call, Throwable t) {

            }
        });






    }//End getFreelancerByUserIdRequest()


}//End of LoginActivity
