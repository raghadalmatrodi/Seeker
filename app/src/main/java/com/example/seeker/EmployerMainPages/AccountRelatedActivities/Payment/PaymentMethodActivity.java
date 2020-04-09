package com.example.seeker.EmployerMainPages.AccountRelatedActivities.Payment;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.seeker.Model.Milestone;
import com.example.seeker.R;

public class PaymentMethodActivity extends AppCompatActivity {

    ImageView backBtn;
    public TextView description;
    public TextView deadline;
    public TextView price;
    public Button payment;
    Milestone milestone;
    Button payButton;


        @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_method);

        init();

            Intent intent = getIntent();
             milestone = (Milestone) intent.getSerializableExtra("milestone");
            setInfo();
            payButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    
                }
            });

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void setInfo() {

        if (milestone.getDescription() != null)
           description.setText(milestone.getDescription());

        if (milestone.getDeadline() != null)
           deadline.setText(milestone.getDeadline().toString().substring(0, 10));

        String stringPrice = String.valueOf(milestone.getAmount());
        int index = stringPrice.indexOf(".");
        stringPrice = stringPrice.substring(0, index);
      price.setText(stringPrice + " SAR");

    }

    private void init() {
        backBtn = findViewById(R.id.payment_back);
        description = findViewById(R.id.row_milestone_description);
        deadline = findViewById(R.id.row_milestone_deadline);
        price = findViewById(R.id.row_milestone_price);
        payment = findViewById(R.id.row_milestone_payment);
        payButton = findViewById(R.id.payButton);

    }

}
