package com.example.seeker.PostBid;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.seeker.Database.ApiClients;
import com.example.seeker.Model.Bid;
import com.example.seeker.Model.Exception.ApiError;
import com.example.seeker.Model.Exception.ApiException;
import com.example.seeker.Model.Login;
import com.example.seeker.Model.Project;
import com.example.seeker.Model.Responses.ApiResponse;
import com.example.seeker.Model.User;
import com.example.seeker.R;
import com.example.seeker.SharedPref.Constants;
import com.example.seeker.SharedPref.MySharedPreference;

import java.io.IOException;
import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Converter;
import retrofit2.Response;

public class ViewBid extends AppCompatActivity implements Serializable,BidsAdapter.BidsAdapterListener  {

    private Button getb, secondbtn;
    //
    private RecyclerView recyclerView;
    private BidsAdapter adapter;
    private List<Bid> bidList = new ArrayList<>();
    private Project mainProject;
//    private B caListener;

    //todo 5 hind
    private Button placebid;
    private TextView projtitle;

    long currFree = MySharedPreference.getLong(ViewBid.this, Constants.Keys.FREELANCER_ID, -1);
    boolean bade = false;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_bid);

        /**
         * Call getbids
         */

//        getBids();

        //todo 5 hind
//        fillData();








    }//end onCreate

    //todo 5 hind
    private void init() {
        placebid = findViewById(R.id.placebidtest);
        projtitle = findViewById(R.id.vppttest);

    }
    private void initToolbar(Project project){

        //init toolbar

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(project.getTitle());
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
        mainProject = (Project) i.getSerializableExtra("projectObj");

        projtitle.setText(mainProject.getTitle());
        getBidsByStatus("pending", mainProject);


        placebid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //check whether current freelancer already has a bid on this project
                if (bade)
                    wrongInfoDialog("Sorry, you already have placed a bid on this project..\nDelete the current bid to bid again. ");
                else {
                    /**
                     * INTENT TO POST BID WITH CURRENT PROJECT
                     */
                    Intent i = new Intent(ViewBid.this, PostBidActivity.class);

                    //casting to serializable didn't work, so i let class bid implements the serializable and it worked :)
                    i.putExtra("currentProjObj", mainProject);
                    startActivity(i);
                }


            }
        });

        initToolbar(mainProject);

    }//End fillData()



    //    private List<Bid> bids;
    private void getBids() {

       ApiClients.getAPIs().getAllBids().enqueue(new Callback<List<Bid>>() {
           @Override
           public void onResponse(Call<List<Bid>> call, Response<List<Bid>> response) {
               if (response.isSuccessful()){

                   int responseSize = response.body().size();
                   for (int i = 0; i<responseSize; i++){
                       //todo: YOU HAVE TO GET THE FREELANCER'S INFO --> NAME, PROFILE PIC ...
                       bidList.add(response.body().get(i));
                   }//end for loop

                   recyclerView = (RecyclerView) findViewById(R.id.recycler_view_b);
                   recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                   adapter = new BidsAdapter(bidList, mainProject);
                   bidList = new ArrayList<>();
                   recyclerView.setItemAnimator(new DefaultItemAnimator());
                   recyclerView.setAdapter(adapter);

                   /**
                    *  IDK WHAT TO SET THE LISTENER :)
                    */
                   adapter.setListener(ViewBid.this);

                   recyclerView.setNestedScrollingEnabled(true);
                   DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL);
                   recyclerView.addItemDecoration(dividerItemDecoration);




               }//end if success
               else {
                   //CHAAAANGE IT!
                   Toast.makeText(getApplicationContext(),response.toString(),Toast.LENGTH_SHORT).show();
               }
           }

           @Override
           public void onFailure(Call<List<Bid>> call, Throwable t) {
               //CHANGE IT!
               Toast.makeText(getApplicationContext(),t.getLocalizedMessage(),Toast.LENGTH_SHORT).show();

           }
       });
    }

    private void getBidsByStatus(String status, Project project){
        ApiClients.getAPIs().getBidByStatus(status).enqueue(new Callback<List<Bid>>() {
            @Override
            public void onResponse(Call<List<Bid>> call, Response<List<Bid>> response) {
                if (response.isSuccessful()){



                    int responseSize = response.body().size();

                    for (int i = 0; i<responseSize; i++){
                        if (response.body().get(i).getProject() != null ){
                            if (response.body().get(i).getProject().getId() == project.getId())
                                bidList.add(response.body().get(i));

                        }


                    }//end for loop

                    //Loop to check whether current freelancer has bade on this project or not
                    for (int i = 0; i< bidList.size(); i++){
                        if (bidList.get(i).getFreelancer() != null )
                            if (bidList.get(i).getFreelancer().getId() == currFree) {
                                Toast.makeText(ViewBid.this,"BID FOUND!",Toast.LENGTH_SHORT).show();
                                bade = true;
                            }

                    }

                    recyclerView = (RecyclerView) findViewById(R.id.recycler_view_b);
                    recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                    adapter = new BidsAdapter(bidList, mainProject);
                    bidList = new ArrayList<>();
                    recyclerView.setItemAnimator(new DefaultItemAnimator());
                    recyclerView.setAdapter(adapter);

                    /**
                     *  IDK WHAT TO SET THE LISTENER :)
                     */
                    adapter.setListener(ViewBid.this);

                    recyclerView.setNestedScrollingEnabled(true);
                    DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL);
                    recyclerView.addItemDecoration(dividerItemDecoration);
//                    adapter.notifyDataSetChanged();




                }//end if success
                else {
                    //CHAAAANGE IT!
                    Toast.makeText(getApplicationContext(),response.toString(),Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Bid>> call, Throwable t) {

            }
        });
    }

//    String tryName;

    @Override
    public void onBidItemClick(Bid bid) {

        Intent i = new Intent(this, ViewFullBid.class);

        //casting to serializable didn't work, so i let class bid implements the serializable and it worked :)
        i.putExtra("bidObj", bid);
        startActivity(i);
    }

    @Override
    public void onAcceptBidItemClick(Bid bid) {

    }

    @Override
    protected void onResume() {
        super.onResume();
        fillData();



//        adapter.notifyDataSetChanged();
    }


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


}//End Class
