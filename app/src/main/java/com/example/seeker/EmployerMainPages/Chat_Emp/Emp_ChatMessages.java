package com.example.seeker.EmployerMainPages.Chat_Emp;

import androidx.appcompat.app.AppCompatActivity;

import com.example.seeker.Model.Chat;
import com.example.seeker.R;
import com.google.gson.Gson;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

public class Emp_ChatMessages extends AppCompatActivity {
    private static final String LOG = Emp_ChatMessages.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emp__chat_messages);

        Chat chat = (Chat) getIntent().getSerializableExtra("chat");
        Log.i(LOG,"the object is :" + chat.toString());



    }
}
