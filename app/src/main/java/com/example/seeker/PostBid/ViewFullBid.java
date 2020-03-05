package com.example.seeker.PostBid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.seeker.Model.Bid;
import com.example.seeker.R;

public class ViewFullBid extends AppCompatActivity {

    private TextView price, deliverydate, desc, title;
    private TextView username;
    private ImageView userImg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_full_bid);

        fillData();



    }//End onCreate()

    private void init() {

        price = findViewById(R.id.vbid_proposed_price);
        deliverydate = findViewById(R.id.vbid_deliverydate);
        desc = findViewById(R.id.vbid_user_description);
        title = findViewById(R.id.viewfullbid_title);
        username = findViewById(R.id.viewfullbidfreelancername);
    }

    private void fillData() {

        init();

        Intent i = getIntent();
        Bid bid = (Bid) i.getSerializableExtra("bidObj");

        String priceStr = Double.toString(bid.getPrice());
        price.setText(priceStr);

        if (bid.getDeliver_date() == null ){
            deliverydate.setText(bid.getDeliver_date());

        } else{
            String noTimeDeadline = bid.getDeliver_date().substring(0,10);
            deliverydate.setText(noTimeDeadline);
        }


        desc.setText(bid.getDescription());
        title.setText(bid.getTitle());
        username.setText(String.valueOf(bid.getFreelancer().getId()));




    }



}
