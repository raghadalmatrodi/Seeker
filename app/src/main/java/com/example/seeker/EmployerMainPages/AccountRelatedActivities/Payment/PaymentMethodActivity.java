package com.example.seeker.EmployerMainPages.AccountRelatedActivities.Payment;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.seeker.Database.ApiClients;
import com.example.seeker.Model.Milestone;
import com.example.seeker.Model.PurchaseOrder;
import com.example.seeker.R;
import com.example.seeker.SharedPref.Constants;
import com.example.seeker.SharedPref.MySharedPreference;
import com.paytabs.paytabs_sdk.payment.ui.activities.PayTabActivity;
import com.paytabs.paytabs_sdk.utils.PaymentParams;

import java.util.UUID;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PaymentMethodActivity extends AppCompatActivity {

    ImageView backBtn;
    public TextView description;
    public TextView freelancer;
    public TextView employer;
    public TextView deadline;
    public TextView price;
    public TextView payment;
    public TextView title;
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
                    if(milestone.getProject().getType().equals("1")){

                        PurchaseOrder purchaseOrder = new PurchaseOrder("123456","Cash",milestone.getAmount(),milestone);
                        ApiClients.getAPIs().purchaseOrder(purchaseOrder).enqueue(new Callback<PurchaseOrder>() {
                            @Override
                            public void onResponse(Call<PurchaseOrder> call, Response<PurchaseOrder> response) {
                                //successful payment
                                payButton.setVisibility(View.GONE);
                                payment.setText("paid");
                                payment.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                            }

                            @Override
                            public void onFailure(Call<PurchaseOrder> call, Throwable t) {

                            }
                        });

                    }else{


                    Intent in = new Intent(getApplicationContext(), PayTabActivity.class);
                    in.putExtra(PaymentParams.MERCHANT_EMAIL, "NouraAlsabr@hotmail.com"); //this a demo account for testing the sdk
                    in.putExtra(PaymentParams.SECRET_KEY,"2p32OqqSGoJE1fPx0tCSvpYUTsbQrvAuVDxJ4XCKmlpnAfVAIyteXr9QTlHlRhPMVnvZk8JGt844n0F6y9lvAvmuR3PIlEug10fm");//Add your Secret Key Here
                    in.putExtra(PaymentParams.LANGUAGE,PaymentParams.ENGLISH);
                    in.putExtra(PaymentParams.TRANSACTION_TITLE, milestone.getProject().getTitle());
                    in.putExtra(PaymentParams.AMOUNT, milestone.getAmount());

                    in.putExtra(PaymentParams.CURRENCY_CODE, "SAR");
                    in.putExtra(PaymentParams.CUSTOMER_PHONE_NUMBER, "009733");

                    String email = MySharedPreference.getString(getApplicationContext(), Constants.Keys.USER_EMAIL,"");
                    in.putExtra(PaymentParams.CUSTOMER_EMAIL, email);

                    in.putExtra(PaymentParams.ORDER_ID,"123456");
                    in.putExtra(PaymentParams.PRODUCT_NAME, milestone.getDescription()); //milestone name

//Billing Address
                    in.putExtra(PaymentParams.ADDRESS_BILLING, "Flat 1,Building 123, Road 2345");
                    in.putExtra(PaymentParams.CITY_BILLING, "Manama");
                    in.putExtra(PaymentParams.STATE_BILLING, "Manama");
                    in.putExtra(PaymentParams.COUNTRY_BILLING, "BHR");
                    in.putExtra(PaymentParams.POSTAL_CODE_BILLING, "00973"); //Put Country Phone code if Postal code not available '00973'
//
////Shipping Address
                    in.putExtra(PaymentParams.ADDRESS_SHIPPING, "Flat 1,Building 123, Road 2345");
                    in.putExtra(PaymentParams.CITY_SHIPPING, "Manama");
                    in.putExtra(PaymentParams.STATE_SHIPPING, "Manama");
                    in.putExtra(PaymentParams.COUNTRY_SHIPPING, "BHR");
                    in.putExtra(PaymentParams.POSTAL_CODE_SHIPPING, "00973"); //Put Country Phone code if Postal code not available '00973'

//Payment Page Style
                    in.putExtra(PaymentParams.PAY_BUTTON_COLOR, "#008577");

//Tokenization
                    in.putExtra(PaymentParams.IS_TOKENIZATION, true);
                    startActivityForResult(in, PaymentParams.PAYMENT_REQUEST_CODE);
                }}
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
        if(milestone.getStatus().equals("0")) {
            payment.setText("Not paid");
        }else{
            payment.setText("paid");
            payment.setBackgroundColor(getResources().getColor(R.color.colorAccent));

        }
        if(milestone.getProject().getTitle() != null){
            title.setText(milestone.getProject().getTitle());
        }


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
        employer = findViewById(R.id.contract_employer);
        freelancer = findViewById(R.id.contract_freelancer);
        payButton = findViewById(R.id.payButton);
        title = findViewById(R.id.payment_project_name);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (resultCode == RESULT_OK && requestCode == PaymentParams.PAYMENT_REQUEST_CODE) {
            Log.e("Tag", data.getStringExtra(PaymentParams.RESPONSE_CODE));
            Log.e("Tag", data.getStringExtra(PaymentParams.TRANSACTION_ID));

            if (data.hasExtra(PaymentParams.TOKEN) && !data.getStringExtra(PaymentParams.TOKEN).isEmpty()) {
                Log.e("Tag", data.getStringExtra(PaymentParams.TOKEN));
                Log.e("Tag", data.getStringExtra(PaymentParams.CUSTOMER_EMAIL));
                Log.e("Tag", data.getStringExtra(PaymentParams.CUSTOMER_PASSWORD));

            }
            if(data.getStringExtra(PaymentParams.RESPONSE_CODE ).equals("100")){

                PurchaseOrder purchaseOrder = new PurchaseOrder("123456",data.getStringExtra(PaymentParams.TRANSACTION_ID) ,milestone.getAmount(),milestone);
                ApiClients.getAPIs().purchaseOrder(purchaseOrder).enqueue(new Callback<PurchaseOrder>() {
                    @Override
                    public void onResponse(Call<PurchaseOrder> call, Response<PurchaseOrder> response) {
                        //successful payment
                        payButton.setVisibility(View.GONE);
                        payment.setText("paid");
                        payment.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                    }

                    @Override
                    public void onFailure(Call<PurchaseOrder> call, Throwable t) {

                    }
                });

            }else{
                // show error not paid
            }

        }
    }
}
