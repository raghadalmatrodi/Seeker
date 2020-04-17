package com.example.seeker.Admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.seeker.Activities.LoginActivity;
import com.example.seeker.Activities.SignupActivity;
import com.example.seeker.Model.Login;
import com.example.seeker.R;

public class AdminActivity extends AppCompatActivity {
    public Button projects,bids,users;
    ImageView logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        init();
projects.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        startActivity( new Intent(AdminActivity.this, ProjectsActivity.class));

    }
});
        users.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity( new Intent(AdminActivity.this, UsersActivity.class));

            }
        });
        bids.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity( new Intent(AdminActivity.this, BidsActivity.class));

            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity( new Intent(AdminActivity.this, LoginActivity.class));
finish();
            }
        });


    }

    public void init(){
        projects = findViewById(R.id.projects_btn);
        bids = findViewById(R.id.bids_btn);
        users = findViewById(R.id.users_btn);
        logout=findViewById(R.id.admin_logout_btn);
    }
}
