package com.example.seeker.PostBid;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.seeker.Database.ApiClients;
import com.example.seeker.Model.Bid;
import com.example.seeker.Model.Exception.ApiError;
import com.example.seeker.Model.Exception.ApiException;
import com.example.seeker.Model.Responses.ApiResponse;
import com.example.seeker.R;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Converter;
import retrofit2.Response;

public class PostBidActivity extends AppCompatActivity {

    private static final String LOG = PostBidActivity.class.getSimpleName();


    private TextView project_title, delivery_date;
    private EditText price, bid_decsription, bid_title;

    private Button post_bid;
    private ImageButton close_bid;

    private String priceStr, dateStr, bidDescriptionStr, bidTitleStr;
    private String timeString;
    private double priceDouble;

    private LocalDateTime localDateTimet;
    private DatePickerDialog picker;







    //TODO: YOU HAVE TO PASS THE PROJECT TITLE!!

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_bid);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

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

                calendarDialog();

            }
        });



        //When post bid is pressed
        post_bid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(validInput()){

                    setTime();
                    dateStr = dateStr+timeString;
                    localDateTimet = convertStringToLocalDateTime(dateStr);



                    Bid bid = new Bid(bidTitleStr,bidDescriptionStr,priceDouble,localDateTimet.toString() ,"pending");

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




        if(bidTitleStr.isEmpty() || priceStr.isEmpty() || dateStr.isEmpty() || bidDescriptionStr.isEmpty()) {
//            Toast.makeText(this,"Some fields are empty", Toast.LENGTH_SHORT).show();
            wrongInfoDialog(getString(R.string.some_fields_are_empty));
            return false;

        } else {

            priceDouble = Double.parseDouble(priceStr);
            return true;
        }


    }//end validInput()




    //todo: finish the method.
    private void executePostBidRequest(Bid bid){

        ApiClients.getAPIs().getPostBidRequest(bid).enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {


                if(response.isSuccessful()){

                    Log.i(LOG, "onResponse: " + response.body().toString());

//                    Toast.makeText(PostBidActivity.this,"Your bid has been posted successfully!!",Toast.LENGTH_SHORT).show();

                    Dialog(getString(R.string.your_bid_has_been_posted_successfully));
                }else {

//                    wrongInfoDialogWithTitle(getString(R.string.something_went_wrong),response.message());
//                    Toast.makeText(PostBidActivity.this,response.toString(),Toast.LENGTH_SHORT).show();
                    Converter<ResponseBody, ApiException> converter = ApiClients.getInstant().responseBodyConverter(ApiException.class, new Annotation[0]);
                    ApiException exception = null;
                    try {

                        exception = converter.convert(response.errorBody());

                        List<ApiError> errors = exception.getErrors();

                        if (errors != null)
                            if (!errors.isEmpty())
                                wrongInfoDialog(errors.get(0).getMessage());
                        wrongInfoDialog(exception.getMessage());

                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }


            }//end onResponse()

            /**
             * DO STH!!!
             */

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                Log.i(LOG, t.getLocalizedMessage() );
                wrongInfoDialog("Error!");
//                Toast.makeText(PostBidActivity.this, t.getLocalizedMessage(),Toast.LENGTH_SHORT).show();

            }//end onFailure()
        });



    }//End executePostBidRequest()


    private void calendarDialog() {

        final Calendar cldr = Calendar.getInstance();
        int day = cldr.get(Calendar.DAY_OF_MONTH);
        int month = cldr.get(Calendar.MONTH);
        int year = cldr.get(Calendar.YEAR);
        // date picker dialog
        picker = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                        int month = monthOfYear + 1;
                        if(month <= 9)
                        {
                            if(dayOfMonth <= 9){

                                delivery_date.setText("0"+ dayOfMonth + "-" +"0"+ month + "-" + year);

                            }else
                            {
                                delivery_date.setText(dayOfMonth + "-" +"0"+ month + "-" + year);
                            }


                        }else{

                            if(dayOfMonth <= 9){

                                delivery_date.setText("0"+dayOfMonth + "-" + month + "-" + year);
                            }else
                            {
                                delivery_date.setText(dayOfMonth + "-" + month + "-" + year);

                            }

                        }




                    }
                }, year, month, day);
        picker.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
        picker.show();




    }//End calendarDialog()

    private LocalDateTime convertStringToLocalDateTime(String dateString) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        return LocalDateTime.parse(dateString, formatter);
    }

    private void setTime() {

        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

        String dateString = formatter.format(date);
        timeString=  dateString.substring(10);
    }

    private void Dialog(String msg) {

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);

        // Setting Dialog Message
        alertDialog.setMessage(msg);

        //Setting Negative "ok" Button
        alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                finish();

            }//end onClick
        });//end setPositiveButton

        alertDialog.show();

    }//End of Dialog()

    private void wrongInfoDialog(String msg) {
        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);

//        alertDialog.setTitle(title);

        // Setting Dialog Message
        alertDialog.setMessage(msg);

        // Setting Icon to Dialog
//        alertDialog.setIcon(R.drawable.exclamation);

        //Setting Negative "ok" Button
        alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }//end onClick
        });//end setPositiveButton

        alertDialog.show();

    }//End wrongInfoDialog()


    private void wrongInfoDialogWithTitle(String title, String msg) {
        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);

        alertDialog.setTitle(title);

        // Setting Dialog Message
        alertDialog.setMessage(msg);

        // Setting Icon to Dialog
//        alertDialog.setIcon(R.drawable.exclamation);

        //Setting Negative "ok" Button
        alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }//end onClick
        });//end setPositiveButton

        alertDialog.show();

    }//End wrongInfoDialog()




}//End class.
