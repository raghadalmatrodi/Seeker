package com.example.seeker.PostBid;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.seeker.Database.ApiClients;
import com.example.seeker.Model.Bid;
import com.example.seeker.Model.User;
import com.example.seeker.R;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ViewBid extends AppCompatActivity implements Serializable,BidsAdapter.BidsAdapterListener  {

    private TextView t1,t2,t3;
    private Button getb;
    //
    private RecyclerView recyclerView;
    private BidsAdapter adapter;
    private List<Bid> bidList = new ArrayList<>();
//    private B caListener;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_bid);
        User user;

        //Add takes bid
//        bidList.add();
//        bidList.add(new Bid("test","test ddd",200,"2/2/2020","pending"));
        //  LONG DESCRIPTION PROBLEM !!
        // TRY SUBSTRING THEN CONCAT WITH ...
//        bidList.add(new Bid("Hindddooo","Hello, I am an expert in a designing using photoshop I have experience of 5 yearHello, I am an expert in a designing using photoshop I have experience of 5 years.s.",50,"13/3/2020","pending"));
//        categoryList.add(new Category("Reema", "My Test"));
//        categoryList.add(new Category("HHHHH", "Test Me Me"));

        /**
         * Call getbids
         */

        //WORKS ONLY WITH THE BUTTON! IDK WHY :) + I HAVE TO CLICK IT TWICE :)!!!!!!!!
        getb = findViewById(R.id.getbtn);
//
        getb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getBids();


                recyclerView = (RecyclerView) findViewById(R.id.recycler_view_b);
                recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                adapter = new BidsAdapter(bidList);
                bidList = new ArrayList<>();
                recyclerView.setItemAnimator(new DefaultItemAnimator());
                recyclerView.setAdapter(adapter);
                //IDK WHAT TO SET THE LISTENER :)
                adapter.setListener(ViewBid.this);

                recyclerView.setNestedScrollingEnabled(true);
                DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL);
                recyclerView.addItemDecoration(dividerItemDecoration);
            }
        });

//        recyclerView = (RecyclerView) findViewById(R.id.recycler_view_b);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//        adapter = new BidsAdapter(bidList);
//        bidList = new ArrayList<>();
//        recyclerView.setItemAnimator(new DefaultItemAnimator());
//        recyclerView.setAdapter(adapter);
//        adapter.setListener(this);
//
//        recyclerView.setNestedScrollingEnabled(true);
//        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL);
//        recyclerView.addItemDecoration(dividerItemDecoration);



//        t1 = findViewById(R.id.tv1);
//        t2 = findViewById(R.id.tv2);
//        t3 = findViewById(R.id.tv3);
//
//        getb = findViewById(R.id.getbtn);
////
//        getb.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//            getBids();
//            }
//        });





    }//end onCreate


//    private List<Bid> bids;
    private void getBids() {

       ApiClients.getAPIs().getAllBids().enqueue(new Callback<List<Bid>>() {
           @Override
           public void onResponse(Call<List<Bid>> call, Response<List<Bid>> response) {
               if (response.isSuccessful()){
//                   while(!response.body().isEmpty()){ //غلط الشرط
//                       for(int i = 0; i<response.body().lastIndexOf(response.body()); i++)
//                       bids.add(response.body().get(i));
//                   }
//                   t1.setText(response.body().get(0).getTitle());
//                   t2.setText(response.body().get(0).getDescription());
//                   String delivery = response.body().get(4).getDeliver_date().substring(0,10);
//                   t3.setText(delivery);
//                   bidList.add(new Bid("test","test ddd",200,"2/2/2020","pending"));

//                   bidList.add(new Bid(response.body().get(0).getTitle(),response.body().get(0).getDescription(),response.body().get(0).getPrice(),response.body().get(0).getDeliver_date(),response.body().get(0).getStatus() ));

                   int responseSize = response.body().size();
                   for (int i = 0; i<responseSize; i++){
                       //todo: YOU HAVE TO GET THE FREELANCER'S INFO --> NAME, PROFILE PIC ...
//                       Bid b = response.body().get(i);
//                       bidList.add(b);
                       bidList.add(response.body().get(i));
                   }
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


    @Override
    public void onBidItemClick(Bid bid) {

        Intent i = new Intent(this, ViewFullBid.class);

        //casting to serializable didn't work, so i let class bid implements the serializable and it worked :)
        i.putExtra("bidObj", (Serializable) bid);
        startActivity(i);
    }
}
