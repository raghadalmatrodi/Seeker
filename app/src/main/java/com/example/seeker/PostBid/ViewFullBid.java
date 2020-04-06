package com.example.seeker.PostBid;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.seeker.Database.ApiClients;
import com.example.seeker.EmployerMainPages.AccountRelatedActivities.EditProfileActivity;
import com.example.seeker.Model.Bid;
import com.example.seeker.Model.Freelancer;
import com.example.seeker.Model.Project;
import com.example.seeker.Model.Responses.ApiResponse;
import com.example.seeker.R;
import com.example.seeker.SharedPref.Constants;
import com.example.seeker.SharedPref.MySharedPreference;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ViewFullBid extends AppCompatActivity {

    private TextView price, deliverydate, desc, title;
    private TextView username;
    private ImageView userImg, trashIcon;


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
        trashIcon=findViewById(R.id.delete_bid);

    }
    String capitalizedName;
    private void initToolbar(Bid bid){

        //init toolbar

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(bid.getTitle());
        toolbar.setNavigationIcon(R.drawable.back_arrow_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }//End onClick()
        });

    }//End initToolBar()
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
//        username.setText(String.valueOf(bid.getFreelancer().getId()));

        if (bid.getFreelancer() != null )
//            findFreelancerById(bid.getFreelancer().getId());

            if(bid.getFreelancer().getId()==MySharedPreference.getLong(this,Constants.Keys.FREELANCER_ID,-1)){
                if(bid.getStatus().equals("pending")){
                trashIcon.setVisibility(View.VISIBLE);
                trashIcon.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        executeDeleteBid(bid);

                    }
                });}
            }

            ApiClients.getAPIs().findFreelancerById(bid.getFreelancer().getId()).enqueue(new Callback<Freelancer>() {
                @Override
                public void onResponse(Call<Freelancer> call, Response<Freelancer> response) {
                    if (response.isSuccessful()){
//                    freelancer = response.body();
//                        holder.username.setText(response.body().getUser().getName());
                        capitalizedName = response.body().getUser().getName().substring(0,1).toUpperCase() + response.body().getUser().getName().substring(1,response.body().getUser().getName().length());
                        username.setText(capitalizedName);
                    }
                }

                @Override
                public void onFailure(Call<Freelancer> call, Throwable t) {

                }
            });



//        if (bid.getFreelancer().getUser() != null)
//        username.setText(bid.getFreelancer().getUser().getUsername());
//        else
//            username.setText(String.valueOf(bid.getFreelancer().getId()));



        initToolbar(bid);


    }

    private void executeDeleteBid(Bid bid) {

        showDialog("Are you sure you want to delete this Bid?",  bid);
    }


    public void showDialog(final String msg,  Bid bid) {
        final android.app.AlertDialog alertDialog = new android.app.AlertDialog.Builder(this).create();
        alertDialog.setMessage(msg);
        alertDialog.setIcon(R.mipmap.ic_launcher);

        alertDialog.setButton(android.app.AlertDialog.BUTTON_POSITIVE, "Yes",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                            deleteBid(bid);
                            infoDialog("Successful", "Your Bid has been deleted successfully.");





                    }//End onClick()


                });//End BUTTON_POSITIVE


        alertDialog.setButton(android.app.AlertDialog.BUTTON_NEGATIVE, "No",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        alertDialog.dismiss();

                    }//End onClick()
                });//End BUTTON_POSITIVE


        alertDialog.show();

    }//end showDialog

    private void deleteBid(Bid bid) {

        ApiClients.getAPIs().deleteBid(bid.getId()).enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                if (response.isSuccessful()) {
                }
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {

            }
        });
//to refresh the list
       // getFragmentManager().beginTransaction().detach(this).attach(this).commit();



    }

    private void infoDialog(String title, String msg) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        // Setting Dialog Title
        alertDialog.setTitle(title);

        // Setting Dialog Message
        alertDialog.setMessage(msg);

        alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

                dialog.dismiss();
                finish();


            }//end onClick
        });//end setPositiveButton

        alertDialog.show();

    }//end wrongInfoDialog()


}
