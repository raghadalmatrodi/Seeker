package com.example.seeker.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.seeker.R;

public class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
    }


    public String preventSQLinjection(String query){
        return "&%@";
    }

}
