package com.example.seeker.Admin;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
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
                infoDialog("Warning"," Are you sure you want to logout?");

            }
        });


    }

    public void init(){
        projects = findViewById(R.id.projects_btn);
        bids = findViewById(R.id.bids_btn);
        users = findViewById(R.id.users_btn);
        logout=findViewById(R.id.admin_logout_btn);
    }


    private void infoDialog(String title, String msg) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        // Setting Dialog Title
        alertDialog.setTitle(title);

        // Setting Dialog Message
        alertDialog.setMessage(msg);

        alertDialog.setPositiveButton("yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

                dialog.dismiss();
                startActivity( new Intent(AdminActivity.this, LoginActivity.class));
                finish();

            }//end onClick
        });//end setPositiveButton
        alertDialog.setNegativeButton("No",new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

                dialog.dismiss();


            }//end onClick
        });//end setNegativeButton

        alertDialog.show();

    }//end wrongInfoDialog()
}
