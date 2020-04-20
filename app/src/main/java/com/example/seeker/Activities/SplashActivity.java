package com.example.seeker.Activities;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.util.Log;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.seeker.ApplictionNAme;
import com.example.seeker.Database.ApiClients;
import com.example.seeker.EmployerMainPages.EmployerMainActivity;
import com.example.seeker.FreelancerMainPages.FreelancerMainActivity;
import com.example.seeker.Model.Freelancer;
import com.example.seeker.Model.Responses.ApiResponse;
import com.example.seeker.R;
import com.example.seeker.SharedPref.Constants;
import com.example.seeker.SharedPref.MySharedPreference;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SplashActivity extends Activity {

    ConstraintLayout splashL;
//    ApplictionNAme.ANDROID_DEVICE_ID = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);




        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
              //  startActivity(new Intent(getApplicationContext(), LoginActivity.class));

                   checkIsLogin();

            }//End of run()
        }, 3000);

    }//End onCreate()

    private void checkIsLogin() {
        if (MySharedPreference.getBoolean(this, Constants.Keys.IS_LOGIN, false)) {


            executeUpdateToken();


            if(MySharedPreference.getString(SplashActivity.this,Constants.Keys.USER_CURRENT_TYPE,"0").equals("EMPLOYER")){
            startActivity(new Intent(getApplicationContext(), EmployerMainActivity.class));
            finish();}
            else
            if(MySharedPreference.getString(SplashActivity.this,Constants.Keys.USER_CURRENT_TYPE,"0").equals("FREELANCER")){
                startActivity(new Intent(getApplicationContext(), FreelancerMainActivity.class));
                finish();

            }
        }else {
            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
            finish();
        }//End of else
    }//End of checkIsLogin

    private void executeUpdateToken() {

        String token = MySharedPreference.getString(getApplicationContext(),Constants.Keys.TOKEN_ID,"");
        Log.d(SplashActivity.class.getSimpleName(), " token: " + token);

        Long userId = MySharedPreference.getLong(getApplicationContext(),Constants.Keys.USER_ID,-1);

        if((!(token == null || token.equals("")) ) && userId !=-1 ){
            ApiClients.getAPIs().updateToken(token ,userId ).enqueue(new Callback<ApiResponse>() {
                @Override
                public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                    if(response.isSuccessful()){
                      Log.d(SplashActivity.class.getSimpleName(), "successful token: " + response.message());

                    }else{
                      Log.d(SplashActivity.class.getSimpleName(), "Not suc: " + response.message());

                    }
                }

                @Override
                public void onFailure(Call<ApiResponse> call, Throwable t) {
                    Log.d(SplashActivity.class.getSimpleName(), "Failure : " + t.getMessage());

                }
            });
        }
    }


}//End class


