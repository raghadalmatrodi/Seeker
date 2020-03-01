package com.example.seeker.PostBid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.example.seeker.Model.Bid;
import com.example.seeker.R;
import com.example.seeker.SharedPref.Constants;
import com.example.seeker.SharedPref.MySharedPreference;

import java.io.Serializable;

public class ViewFullBid extends AppCompatActivity {

    private TextView price, dd, desc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_full_bid);

        price = findViewById(R.id.vbid_proposed_price);
        dd = findViewById(R.id.vbid_deliverydate);
        desc = findViewById(R.id.vbid_user_description);

        Intent i = getIntent();
        Bid dene = (Bid) i.getSerializableExtra("bidObj");

        String priceStr = Double.toString(dene.getPrice());
        price.setText(priceStr);
        dd.setText(dene.getDeliver_date());
        desc.setText(dene.getDescription());

        //Test sharedPref
//        if (!MySharedPreference.getString(ViewFullBid.this, Constants.Keys.USER_NAME, "").equals(""))
//        price.setText("NAME:  " + MySharedPreference.getString(ViewFullBid.this, Constants.Keys.USER_NAME, ""));
//        dd.setText("EMAIL:  " +MySharedPreference.getString(ViewFullBid.this, Constants.Keys.USER_EMAIL, ""));
//        desc.setText("ID:  "+MySharedPreference.getLong(ViewFullBid.this, Constants.Keys.USER_ID, -1));
    }

//    private void fillData(){
//        if (!MySharedPreference.getString(v.getContext(), Constants.Keys.USER_NAME, "").equals(""))
//            txtName.setText(MySharedPreference.getString(v.getContext(), Constants.Keys.USER_NAME, ""));
//
//        if (!MySharedPreference.getString(v.getContext(), Constants.Keys.USER_EMAIL, "").equals(""))
//            txtEmail.setText(MySharedPreference.getString(v.getContext(), Constants.Keys.USER_EMAIL, ""));
//
//        if (!MySharedPreference.getString(v.getContext(), Constants.Keys.USER_PASSWORD, "").equals(""))
//            txtPassword.setText(MySharedPreference.getString(v.getContext(), Constants.Keys.USER_PASSWORD, ""));
//
//        String imageString = MySharedPreference.getString(getContext(),Constants.Keys.USER_IMG,"");
//        Glide.with(this).load(imageString).into(profile_pic);
//
//        txtName.setEnabled(false);
//        txtPassword.setEnabled(false);
//        txtEmail.setEnabled(false);
//
//
//    }//End fillData()
}
