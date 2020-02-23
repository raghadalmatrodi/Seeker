package com.example.seeker.PostBid;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.seeker.Model.Bid;
import com.example.seeker.R;

import java.util.List;

public class BidsAdapter extends RecyclerView.Adapter<BidsAdapter.MyViewHolder> {

    private List<Bid> bidList;
    private BidsAdapterListener listener;

    public void setListener(BidsAdapterListener listener) {
        this.listener = listener;
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {


        public ImageView user_img;
        public TextView username;
        public TextView description;
        public TextView price;
        public TextView deadline;

        public MyViewHolder(View view) {
            super(view);

//            user_img = view.findViewById(R.id.bid_user_img);
            username = view.findViewById(R.id.bid_user_name);
            description = view.findViewById(R.id.bid_user_description);
            price = view.findViewById(R.id.bid_proposed_price);
            deadline = view.findViewById(R.id.bid_deliverydate);


            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    listener.onBidItemClick(bidList.get(getAdapterPosition()));

                }
            });

        }//End of MyViewHolder()


    }//Enf of class MyViewHolder

    public BidsAdapter(List<Bid> bidList) {

        this.bidList = bidList;
    }//End of BidsAdapter()


    public interface BidsAdapterListener {
        void onBidItemClick(Bid bid);
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

        //add user image
//        holder.user_img

        //HOW TO GET THE USER'S NAME?
//        holder.username.setText();
        holder.description.setText(bid.getDescription());
        String priceStr = Double.toString(bid.getPrice());
        holder.price.setText(priceStr);
        holder.deadline.setText(bid.getDeliver_date());


    }//End of onBindViewHolder


    @Override
    public int getItemCount() {
        return bidList.size();
    }

}
