package com.example.seeker.PostBid;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
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
import com.example.seeker.Model.Responses.ApiResponse;
import com.example.seeker.Model.User;
import com.example.seeker.R;

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
//    private B caListener;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_bid);

        /**
         * Call getbids
         */

        getBids();




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

//    String tryName;

    @Override
    public void onBidItemClick(Bid bid) {

        Intent i = new Intent(this, ViewFullBid.class);

        //casting to serializable didn't work, so i let class bid implements the serializable and it worked :)
        i.putExtra("bidObj", (Serializable) bid);
        startActivity(i);
    }



//    private void executeGetUserByMailRequest(){
//        ApiClients.getAPIs().findUSerByEmailRequest("hindoo87@hotmail.com").enqueue(new Callback<Login>() {
//            @Override
//            public void onResponse(Call<Login> call, Response<Login> response) {
//                if (response.isSuccessful()){
//                    Toast.makeText(ViewBid.this,"Correct",Toast.LENGTH_SHORT).show();
//                    tryName = response.body().getPassword()+response.body().getEmail();
//                    t2.setText(tryName);
//                }else{
//                    Toast.makeText(ViewBid.this,response.errorBody().toString(),Toast.LENGTH_SHORT).show();
//
////                    wrongInfoDialogWithTitle(getString(R.string.something_went_wrong),response.message());
////                    Toast.makeText(PostBidActivity.this,response.toString(),Toast.LENGTH_SHORT).show();
//                    Converter<ResponseBody, ApiException> converter = ApiClients.getInstant().responseBodyConverter(ApiException.class, new Annotation[0]);
//                    ApiException exception = null;
//                    try {
//
//                        exception = converter.convert(response.errorBody());
//
//                        List<ApiError> errors = exception.getErrors();
//
//                        if (errors != null)
//                            if (!errors.isEmpty())
//                                wrongInfoDialog(errors.get(0).getMessage());
//                        wrongInfoDialog(exception.getMessage());
//
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//
//                }
//
//
//            }
//
//            @Override
//            public void onFailure(Call<Login> call, Throwable t) {
//                Toast.makeText(ViewBid.this,t.getLocalizedMessage().toString(),Toast.LENGTH_LONG).show();
//
//            }
//        });
//    }

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
