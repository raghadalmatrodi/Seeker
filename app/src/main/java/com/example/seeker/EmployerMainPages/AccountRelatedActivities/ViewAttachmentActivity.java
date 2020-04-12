package com.example.seeker.EmployerMainPages.AccountRelatedActivities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.seeker.CustomGlideModule;
import com.example.seeker.R;


import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import static com.ayokunlepaul.frescoloader.dsl.DSL.image;


public class ViewAttachmentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_attachment);


        Intent intent = getIntent();
        String image = intent.getStringExtra("image");
        ImageView imageView = findViewById(R.id.view_attachment);
        ImageView backButton = findViewById(R.id.back_button);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        Glide.with(getApplicationContext())
                .load(image)
                .placeholder(R.drawable.folder)
                .into(imageView);


    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }



    @Override
    public void onBackPressed() {
        if (getFragmentManager().getBackStackEntryCount() == 0) {
            this.finish();
        } else {
            super.onBackPressed(); //replaced
        }
    }

}