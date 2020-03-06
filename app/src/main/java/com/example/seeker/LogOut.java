package com.example.seeker;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

import com.example.seeker.Activities.LoginActivity;

public class LogOut extends LoginActivity {

        TextView Logout;



        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.fragment_emp_account);
            Logout = findViewById(R.id.profile_logout_btn);

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
                                    Intent i = new Intent(getApplicationContext(),
                                            LoginActivity.class);
                                    startActivity(i);
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



