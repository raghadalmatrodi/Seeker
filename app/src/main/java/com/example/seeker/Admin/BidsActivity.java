package com.example.seeker.Admin;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.example.seeker.Database.ApiClients;
import com.example.seeker.FreelancerMainPages.MyProjectsTab_Freelancer.Freelancer_viewProjectFragment;
import com.example.seeker.Model.Bid;
import com.example.seeker.Model.Responses.ApiResponse;
import com.example.seeker.Model.User;
import com.example.seeker.PostBid.ViewFullBid;
import com.example.seeker.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BidsActivity extends AppCompatActivity implements BidsAdapter.BidsAdapterListener {

    RecyclerView recyclerViewBid;
    SearchView searchViewBid;
    private List<Bid> bidList;
    ImageView backBtnBid;
    BidsAdapter bidsAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bids);

        searchViewBid=findViewById(R.id.search_bids);
        backBtnBid = findViewById(R.id.bids_back);


        backBtnBid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });




        searchViewBid.setOnQueryTextListener(new SearchView.OnQueryTextListener() {


            @Override
            public boolean onQueryTextSubmit(String queryString) {


                bidsAdapter.getFilter().filter(queryString);

                return false;
            }

            @Override
            public boolean onQueryTextChange(String queryString) {

                bidsAdapter.getFilter().filter(queryString);


                return false;
            }
        });

    }


    @Override
    protected void onResume() {
        super.onResume();

        getAllBids();
    }

    private void getAllBids(){

        ApiClients.getAPIs().getAllBids().enqueue(new Callback<List<Bid>>() {
            @Override
            public void onResponse(Call<List<Bid>> call, Response<List<Bid>> response) {
                if(response.isSuccessful()){

                    bidList = (List) response.body();
                    setBidsRecyclerView();

                }
                else{

                    Log.i("BidsActivity", "onResponse not suc: " + response.toString());
                }
            }

            @Override
            public void onFailure(Call<List<Bid>> call, Throwable t) {

                Log.i("BidsActivity", "Fail");
            }
        });

    }


    private void setBidsRecyclerView() {

        recyclerViewBid = (RecyclerView) findViewById(R.id.recycler_view_bids);
        recyclerViewBid.setLayoutManager(new LinearLayoutManager(this));
        bidsAdapter = new BidsAdapter(bidList,this);
        recyclerViewBid.setItemAnimator(new DefaultItemAnimator());
        recyclerViewBid.setAdapter(bidsAdapter);
        if (!bidList.isEmpty())
            bidsAdapter.setListener(this);
        recyclerViewBid.setNestedScrollingEnabled(true);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerViewBid.getContext(), DividerItemDecoration.VERTICAL);
        recyclerViewBid.addItemDecoration(dividerItemDecoration);
    }

//--------------------------------------





//delete
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
                        infoDialog("Successful", "The Bid has been deleted successfully.");





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

        ApiClients.getAPIs().deleteBidAdmin(bid.getId()).enqueue(new Callback<ApiResponse>() {
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
                Intent intent = new Intent(getApplicationContext(), BidsActivity.class);
               startActivity(intent);
               finish();

            }//end onClick
        });//end setPositiveButton

        alertDialog.show();

    }//end wrongInfoDialog()


    @Override
    public void onBidItemClick(Bid bid) {
        Intent intent = new Intent(getBaseContext(), ViewFullBid.class);
        intent.putExtra("bidObj", bid);
        startActivity(intent);

    }

    @Override
    public void onDeleteBidItemClick(Bid bid) {
        executeDeleteBid(bid);

    }
}
