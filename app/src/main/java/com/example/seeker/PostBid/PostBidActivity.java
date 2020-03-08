package com.example.seeker.PostBid;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
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
import com.example.seeker.Model.Freelancer;
import com.example.seeker.Model.Project;
import com.example.seeker.Model.Responses.ApiResponse;
import com.example.seeker.R;
import com.example.seeker.SharedPref.Constants;
import com.example.seeker.SharedPref.MySharedPreference;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

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

    //todo: hind commented -> remove later
//    Freelancer freelancer;







    //TODO: YOU HAVE TO PASS THE PROJECT TITLE!!

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_bid);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        init();


//        getCurrentProject();

        Intent i = getIntent();
        Project project = (Project) i.getSerializableExtra("currentProjObj");
        project_title.setText(project.getTitle());


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

//                    Freelancer freelancer = new Freelancer(MySharedPreference.getLong(PostBidActivity.this, Constants.Keys.USER_ID, -1));

                    //CHECK! NOT SURE
//                    if (MySharedPreference.getLong(PostBidActivity.this, Constants.Keys.USER_ID, -1) != -1)
//                    freelancer.setId(MySharedPreference.getLong(PostBidActivity.this, Constants.Keys.USER_ID, -1));
                    //todo: hind commented -> remove later

//                        executeFindFreelancerByUserIdRequest(MySharedPreference.getLong(PostBidActivity.this, Constants.Keys.USER_ID, -1));
                    //todo: JUST COMMENTED SAT 7 MARCH 9PM
//
//                    long freelancerID = MySharedPreference.getLong(PostBidActivity.this, Constants.Keys.FREELANCER_ID,-1);
//
//                    Freelancer freelancer = new Freelancer(freelancerID);
//                    Employer employer = new Employer(empID);



//                    long projectID = project.getId();
//                    project.setId(projectID);
//
//
//
//                    Bid bid = new Bid(bidTitleStr,bidDescriptionStr,priceDouble,localDateTimet.toString() ,"pending", freelancer, project);
//
////                    String checkStr = bidTitleStr+" -- "+bidDescriptionStr+ " -- "+ priceStr + " -- "+" -- " + dateStr;
////                    Toast.makeText(getApplicationContext(),checkStr,Toast.LENGTH_LONG).show();
//
//                    executePostBidRequest(bid);
                    /**
                     * hind added and commented above lines on mar.7
                     *
                     */
                    executeFindFreelancerByUserIdRequest(MySharedPreference.getLong(PostBidActivity.this, Constants.Keys.USER_ID, -1));

                }

            }
        });




    }//end onCreate()

    private void getCurrentProject() {
//        Intent i = getIntent();
//        Project project = (Project) i.getSerializableExtra("currentProjObj");
//        project_title.setText(project.getTitle());
    }

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

                }//end else block


            }//end onResponse()


            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                Log.i(LOG, t.getLocalizedMessage() );
                wrongInfoDialog("Error!");
//                Toast.makeText(PostBidActivity.this, t.getLocalizedMessage(),Toast.LENGTH_SHORT).show();

            }//end onFailure()
        });



    }//End executePostBidRequest()

//    todo hind commented -> remove later

    private void executeFindFreelancerByUserIdRequest(Long id){


//        if (MySharedPreference.getLong(PostBidActivity.this, Constants.Keys.USER_ID, -1) != -1)
//          long curren_user_id = MySharedPreference.getLong(PostBidActivity.this, Constants.Keys.USER_ID, -1);

        ApiClients.getAPIs().getFreelancerByUserIdRequest(id).enqueue(new Callback<Freelancer>() {
            @Override
            public void onResponse(Call<Freelancer> call, Response<Freelancer> response) {
                if (response.isSuccessful()){

                    Log.i(LOG, "onResponse: " + response.body().toString());
                    Freelancer freelancer = response.body();
//                    Toast.makeText(PostBidActivity.this, "fr name = "+freelancer.getUser().getUsername(), Toast.LENGTH_LONG ).show();

                    /**
                     * hind just added mar. 7, moved from post bid btn to here
                     */
                    Intent i = getIntent();
                    Project project = (Project) i.getSerializableExtra("currentProjObj");

                    long projectID = project.getId();
                    project.setId(projectID);


                    Bid bid = new Bid(bidTitleStr,bidDescriptionStr,priceDouble,localDateTimet.toString() ,"pending", freelancer, project, freelancer.getUser());

//                    String checkStr = bidTitleStr+" -- "+bidDescriptionStr+ " -- "+ priceStr + " -- "+" -- " + dateStr;
//                    Toast.makeText(getApplicationContext(),checkStr,Toast.LENGTH_LONG).show();

                    executePostBidRequest(bid);
//                    freelancer.getUser().getUsername()
//                    freelancer = Freelancer(response.body().getId());
//                    freelancer.setId(response.body().getId());


                }else {
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
                }//end else block
            }//End onResponse()

            @Override
            public void onFailure(Call<Freelancer> call, Throwable t) {
                Log.i(LOG, t.getLocalizedMessage() );
                wrongInfoDialog("Error!");
            }//end onFailure()
        });



    }//End executeFindFreelancerByUserIdRequest()

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
//                Intent x;
//                x = new Intent(PostBidActivity.this, ViewBid.class);
//                startActivity(x);
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
