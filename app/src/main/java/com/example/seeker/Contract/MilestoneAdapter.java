package com.example.seeker.Contract;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.seeker.Model.Milestone;
import com.example.seeker.R;

import java.util.List;

public class MilestoneAdapter  extends RecyclerView.Adapter<MilestoneAdapter.MyViewHolder>{

    private List<Milestone> milestoneList;

    //------------------
    public class MyViewHolder extends RecyclerView.ViewHolder {



        public TextView description;
        public TextView deadline;
        public TextView price;
        public TextView payment;

        public MyViewHolder(View view) {
            super(view);

           description = view.findViewById(R.id.row_milestone_description);
           deadline = view.findViewById(R.id.row_milestone_deadline);
           price = view.findViewById(R.id.row_milestone_price);
           payment = view.findViewById(R.id.row_milestone_payment);




        }//End of MyViewHolder()


    }//Enf of class MyViewHolder



    public MilestoneAdapter(List<Milestone> milestoneList) {

        this.milestoneList = milestoneList;
    }//End of CategorySearchAdapter()

    @Override
    public MilestoneAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_milestone, parent, false);

        MyViewHolder evh = new MyViewHolder(itemView);
        return evh;
    }//End of onCreateViewHolder

    @Override
    public void onBindViewHolder(final MilestoneAdapter.MyViewHolder holder, int position) {
        Milestone milestone = milestoneList.get(position);

        if(milestone.getDescription() != null)
        holder.description.setText(milestone.getDescription());

        if(milestone.getDeadline() != null)
        holder.deadline.setText(milestone.getDeadline().toString().substring(0,10));

        String stringPrice = String.valueOf(milestone.getAmount());
        int index = stringPrice.indexOf(".");
        stringPrice = stringPrice.substring(0,index);
        holder.price.setText(stringPrice+" SAR");

        holder.payment.setText(milestone.getStatus());
        if(milestone.getStatus().equals("0")){

            holder.payment.setTextColor(Color.parseColor("#9e1c08"));
            holder.payment.setText("Unpaid");
        }else{

            holder.payment.setTextColor(Color.parseColor("#237a23"));
            holder.payment.setText("Paid");

        }



    }//End of onBindViewHolder


    @Override
    public int getItemCount() {
        return milestoneList.size();
    }
}
