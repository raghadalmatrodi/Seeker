package com.example.seeker.PostBid;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.seeker.R;

public class PostBidActivity extends AppCompatActivity {

    private TextView project_title;
    private EditText price, delivery_date, bid_decsription;
    private Button post_bid;
    private ImageButton close_bid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_bid);

        init();

    }//end onCreate()

    private void init() {
        project_title = findViewById(R.id.project_title_to_post_bid_in);
        price = findViewById(R.id.post_bid_price);
        delivery_date = findViewById(R.id.post_bid_delivery_date);
        bid_decsription = findViewById(R.id.bid_description);
        post_bid = findViewById(R.id.post_a_bid_btn);
        close_bid = findViewById(R.id.close_post_bid_btn);

    }//end init()

    private void validateInput(){

    }//end validateInput()
}
