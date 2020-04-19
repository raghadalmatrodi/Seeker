package com.example.seeker.Admin;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.seeker.Database.ApiClients;
import com.example.seeker.EmployerMainPages.MyProjectsTab_Emp.ProjectsStatusFragments.ProjectAdapter;
import com.example.seeker.Model.Bid;
import com.example.seeker.Model.User;
import com.example.seeker.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BidsAdapter extends RecyclerView.Adapter<BidsAdapter.MyViewHolder> implements Filterable {

    private List<Bid> bidSearchList;
    private List<Bid> bidList;
    private Context context;
    private BidsAdapterListener listener;

    private List<Bid> filteredbidSearchList;

    public void setListener(BidsAdapterListener listener) {
        this.listener = listener;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public ImageView avatar;
        public TextView deactivate;
        public TextView userName;
        public TextView bidTitle;
        LinearLayout bidLinear;

        public MyViewHolder(View view) {
            super(view);

            userName = view.findViewById(R.id.row_username_bid);
            bidTitle = view.findViewById(R.id.row_bid_title);
            deactivate = view.findViewById(R.id.row_bid_deactivate);
            bidLinear= view.findViewById(R.id.bid_linear);
deactivate.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        listener.onDeleteBidItemClick(bidList.get(getAdapterPosition()));

    }
});

            bidLinear.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onBidItemClick(bidList.get(getAdapterPosition()));

                }
            });


        }//End of MyViewHolder()
    }//Enf of class MyViewHolder



    public BidsAdapter(List<Bid> bidList, Context context) {

        this.bidList = bidList;
        this.bidSearchList=bidList;
        this.context =context;

      //  setHasStableIds(true);
    }//End of CategorySearchAdapter()

    @Override
    public BidsAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_bid_admin, parent, false);

        MyViewHolder evh = new MyViewHolder(itemView);
        return evh;
    }//End of onCreateViewHolder

    @Override
    public void onBindViewHolder(final BidsAdapter.MyViewHolder holder, int position) {


        Bid bid = bidList.get(position);
        holder.userName.setText(bid.getFreelancer().getUser().getUsername());
        holder.bidTitle.setText(bid.getTitle());

    }//End of onBindViewHolder



    @Override
    public int getItemCount() {
        return bidList.size();
    }



    //search view


    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String charSequenceString = constraint.toString();
                if (charSequenceString.isEmpty()) {
                    bidList=bidSearchList;
                    filteredbidSearchList = bidList;
                } else {
                    // filteredProjectSearchList.clear();
                    List<Bid> filteredList = new ArrayList<>();
                    for (Bid bid : bidList) {
                        if (bid.getFreelancer().getUser().getUsername().toLowerCase().contains(charSequenceString.toLowerCase())) {
                            filteredList.add(bid);
                        }


                        bidList = filteredList;
                    }

                }
                FilterResults results = new FilterResults();
                results.values = filteredbidSearchList;
                return results;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {


                filteredbidSearchList = (List<Bid>) results.values;
                notifyDataSetChanged();
            }
        };
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }



    public interface BidsAdapterListener {
        void onBidItemClick(Bid bid);

        void onDeleteBidItemClick(Bid bid);
    }

}
