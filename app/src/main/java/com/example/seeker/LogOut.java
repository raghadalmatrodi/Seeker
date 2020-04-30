package com.example.seeker;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

import com.example.seeker.Activities.LoginActivity;
import com.example.seeker.Database.ApiClients;
import com.example.seeker.SharedPref.Constants;
import com.example.seeker.SharedPref.MySharedPreference;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LogOut extends LoginActivity {

        TextView Logout;

    long userId;

        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.fragment_emp_account);
//            Logout = findViewById(R.id.profile_logout_btn);
     userId = MySharedPreference.getLong(this, Constants.Keys.USER_ID , -1);
            Intent in = getIntent();
            String string = in.getStringExtra("message");
            Logout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(LogOut.this);
                    builder.setTitle("Confirmation PopUp!").
                            setMessage("You sure, that you want to logout?");
                    builder.setPositiveButton("Yes",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    ApiClients.getAPIs().logout(userId).enqueue(new Callback<Void>() {
                                        @Override
                                        public void onResponse(Call<Void> call, Response<Void> response) {
                                            if(response.isSuccessful()) {
                                                Log.d("response.isSuccessful(", response.message());

                                                MySharedPreference.putBoolean(getApplicationContext(), Constants.Keys.IS_LOGIN, false);
                                                Intent i = new Intent(getApplicationContext(),
                                                        LoginActivity.class);
                                                startActivity(i);
                                            }else{
                                                Log.d("response.isnotSuccessful(", response.message());

                                            }
                                        }

                                        @Override
                                        public void onFailure(Call<Void> call, Throwable t) {
                                            Log.d("failure", t.getLocalizedMessage());

                                        }
                                    });
                                  //  MySharedPreference.clearData(getApplicationContext());

                                }
                            });
                    builder.setNegativeButton("No",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            });
                    AlertDialog alert11 = builder.create();
                    alert11.show();
                }
            });
        }

    }



