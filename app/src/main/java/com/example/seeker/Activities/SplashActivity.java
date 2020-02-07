package com.example.seeker.Activities;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.seeker.R;

public class SplashActivity extends Activity {

    ConstraintLayout splashL;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);


        //replace it with our code
//        firebaseAuth = FirebaseAuth.getInstance();
//        //checks with the database if the user is already logged into our app or not
//        final FirebaseUser user = firebaseAuth.getCurrentUser();


        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
//                if (user != null) {
//                    finish();
//                    Intent i = new Intent(SplashActivity.this, .class);
//                    startActivity(i);
//                } else {
                    Intent i = new Intent(SplashActivity.this, LoginActivity.class);
                    startActivity(i);
                    finish();
              //  }
            }//End of run()
        }, 3000);

    }//End onCreate()


}//End class


