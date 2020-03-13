package com.example.seeker.PostBid;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.example.seeker.Database.ApiClients;
import com.example.seeker.Model.Bid;
import com.example.seeker.Model.Category;
import com.example.seeker.Model.Responses.ApiResponse;
import com.example.seeker.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BidsAdapter extends RecyclerView.Adapter<BidsAdapter.MyViewHolder> {

    private List<Bid> bidList;
    private BidsAdapterListener listener;
    boolean isEmployer =false;
    boolean isPending=false;

    public void setListener(BidsAdapterListener listener) {
        this.listener = listener;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {


        public ImageView user_img;
        public TextView username;
        public TextView description;
        public TextView price;
        public TextView deadline;
        public Button acceptBid;


        public MyViewHolder(View view) {
            super(view);

//            user_img = view.findViewById(R.id.bid_user_img);
            username = view.findViewById(R.id.bid_user_name);
            description = view.findViewById(R.id.bid_user_description);
            price = view.findViewById(R.id.bid_proposed_price);
            deadline = view.findViewById(R.id.bid_deliverydate);
            acceptBid = view.findViewById(R.id.accept_bid);

            if(isEmployer && isPending){
                acceptBid.setVisibility(View.VISIBLE);
            }


            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    listener.onBidItemClick(bidList.get(getAdapterPosition()));

                }
            });

        }//End of MyViewHolder()


    }//Enf of class MyViewHolder
    public void showAccept(){
        isEmployer= true;
        isPending = true;

    }
    public BidsAdapter(List<Bid> bidList) {

        this.bidList = bidList;
    }//End of BidsAdapter()


    public interface BidsAdapterListener {
        void onBidItemClick(Bid bid);

        //انا مسويته ياهند اذا صاح عندك سوي له امبلمنت وبس خليه فاضي
        void onAcceptBidItemClick(Bid bid);
    }

    @Override
    public BidsAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_bid, parent, false);

        MyViewHolder evh = new MyViewHolder(itemView);
        return evh;
    }//End of onCreateViewHolder

    @Override
    public void onBindViewHolder(final BidsAdapter.MyViewHolder holder, int position) {
        Bid bid = bidList.get(position);

        //*
        //
        // *
        // add user image
//        holder.user_img

        /**
         *    HOW TO GET THE USER'S NAME?
         */
//        if (bid.getFreelancerResponse() != null) {
//
//            holder.username.setText(String.valueOf(bid.getFreelancerResponse().getId()));
//        } else {
//            holder.username.setText(bid.getProject().getTitle());
//        }
//        if (bid.getFreelancer() != null ) {
//            if (bid.getFreelancer().getUser() != null){
//                if (bid.getFreelancer().getUser().getUsername() != null){
//                    holder.username.setText(bid.getFreelancer().getUser().getUsername());
//                }else holder.username.setText("empty");
//            }else{
//                holder.username.setText("user null");
//            }
//
//
//
//
//        }
//        else {
//            holder.username.setText("NO NAME FOUND!");
//        }


        String shortDescription, longDescription;
        longDescription = bid.getDescription();
        if(longDescription.length() > 85 ){
            shortDescription = longDescription.substring(0,85).concat("...");
            holder.description.setText(shortDescription);

        } else {
            holder.description.setText(bid.getDescription());
        }
        String priceStr = Double.toString(bid.getPrice());
        holder.price.setText(priceStr);
        if (bid.getDeliver_date() == null ){
            holder.deadline.setText(bid.getDeliver_date());

        } else{
            //TODO CHECK IF THE LENGTH IS MORE THAN 9
            String noTimeDeadline = bid.getDeliver_date().substring(0,10);
            holder.deadline.setText(noTimeDeadline);
        }

        holder.acceptBid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO ACCEPT BID REQUEST
//                Log.i("bid information ", bid.toString());
//
//                ApiClients.getAPIs().acceptBid(bid.getId()).enqueue(new Callback<ApiResponse>() {
//                    @Override
//                    public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
//                        Log.i("onResponse ",response.message());
//
//                        bidList.remove(bidList.get(position));
//                        notifyDataSetChanged();
//
//                    }
//
//                    @Override
//                    public void onFailure(Call<ApiResponse> call, Throwable t) {
//                        Log.i("onFailure ",t.getLocalizedMessage());
//
//                    }
//                });

                listener.onAcceptBidItemClick(bid);


            }

        });
//        if(!bid.getDeliver_date().isEmpty()){
//            String noTimeDeadline = bid.getDeliver_date().substring(0,10);
//            holder.deadline.setText(noTimeDeadline);
//        } else {
//            holder.deadline.setText(bid.getDeliver_date());
//        }


    }//End of onBindViewHolder


    @Override
    public int getItemCount() {
        return bidList.size();
    }

}
