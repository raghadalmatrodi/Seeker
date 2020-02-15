package com.example.seeker.PostBid;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.seeker.Database.ApiClients;
import com.example.seeker.Model.Bid;
import com.example.seeker.Model.Responses.ApiResponse;
import com.example.seeker.R;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PostBidActivity extends AppCompatActivity {

    private TextView project_title;
    private EditText price, delivery_date, bid_decsription, bid_title;
    private Button post_bid;
    private ImageButton close_bid;
    private String priceStr, dateStr, bidDescriptionStr, bidTitleStr;
    private double priceDouble;
    private LocalDateTime localdt;
    final Calendar currentDate = Calendar.getInstance();
    private Calendar date;

    private static final String LOG = PostBidActivity.class.getSimpleName();


//    final Calendar myCalendar = Calendar.getInstance();




    //TODO: YOU HAVE TO PASS THE PROJECT TITLE!!

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_bid);

        init();

        //When x is pressed:
        close_bid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // do sth.
                /**
                 * CHECK!!!
                 * */
                finish();

            }
        });

        //When delivery date is selected
        delivery_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //todo: make sure to let the user select valid dates (not in the past!)
//                new DatePickerDialog(PostBidActivity.this, date, myCalendar
//                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
//                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();

                showDateTimePicker();

            }
        });


        //When post bid is pressed
        post_bid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(validInput()){
                    Bid bid = new Bid(bidTitleStr,bidDescriptionStr,priceDouble,null ,"pending");

//                    String checkStr = bidTitleStr+" -- "+bidDescriptionStr+ " -- "+ priceStr + " -- "+" -- " + dateStr;
//                    Toast.makeText(getApplicationContext(),checkStr,Toast.LENGTH_LONG).show();

                    executePostBidRequest(bid);

                }

            }
        });




    }//end onCreate()

    private void init() {
        project_title = findViewById(R.id.project_title_to_post_bid_in);
        price = findViewById(R.id.post_bid_price);
        delivery_date = findViewById(R.id.post_bid_delivery_date);
        bid_decsription = findViewById(R.id.bid_description);
        post_bid = findViewById(R.id.post_a_bid_btn);
        close_bid = findViewById(R.id.close_post_bid_btn);
        bid_title = findViewById(R.id.post_bid_title);

    }//end init()

    //call it when pressing post btn
    private boolean validInput(){

        priceStr = price.getText().toString();
        dateStr = delivery_date.getText().toString();
        bidDescriptionStr = bid_decsription.getText().toString();
        bidTitleStr = bid_title.getText().toString();

        priceDouble = Double.parseDouble(priceStr);



        if(bidTitleStr.isEmpty() || priceStr.isEmpty() || dateStr.isEmpty() || bidDescriptionStr.isEmpty()) {
            //todo: show dialog.
            Toast.makeText(this,"Some fields are empty", Toast.LENGTH_SHORT).show();
            return false;

        } else
            return true;

    }//end validInput()

    /**
     * src:
     * https://stackoverflow.com/questions/14933330/datepicker-how-to-popup-datepicker-when-click-on-edittext
     *
     * */

//    DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
//
//
//        @Override
//        public void onDateSet(DatePicker view, int year, int monthOfYear,
//                              int dayOfMonth) {
//            myCalendar.set(Calendar.YEAR, year);
//            myCalendar.set(Calendar.MONTH, monthOfYear);
//            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
//            updateLabel();
//        }
//
//    };
//
//
    //To be fixed!
    //problem with selecting date, it only add today's date

    private void updateLabel() {
        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        delivery_date.setText(sdf.format(date.getTime()));
    }//End updateLabel()
//





    public void showDateTimePicker(){
        date = Calendar.getInstance();

        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
                date.set(year, monthOfYear, dayOfMonth);

                //use this date as per your requirement
            }
        };
        DatePickerDialog datePickerDialog = new  DatePickerDialog(PostBidActivity.this, dateSetListener, currentDate.get(Calendar.YEAR), currentDate.get(Calendar.MONTH),   currentDate.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
        datePickerDialog.show();

        updateLabel();
    }

    private void executePostBidRequest(Bid bid){

        ApiClients.getAPIs().getPostBidRequest(bid).enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {


                if(response.isSuccessful()){
                    Log.i(LOG, "onResponse : " + response.body().toString());


                    Toast.makeText(PostBidActivity.this,"Your bid has been posted successfully!!",Toast.LENGTH_SHORT).show();
                }else {

                    Toast.makeText(PostBidActivity.this,"sorry error",Toast.LENGTH_SHORT).show();

                }


            }//end onResponse()

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                Log.i(LOG, "onFailure : " );

            }//end onFailure()
        });

    }//End executePostBidRequest()
















































}//End class.
