package com.example.seeker.Contract;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.seeker.Model.Milestone;
import com.example.seeker.R;

import java.util.List;

public class MilestoneSecondAdapter extends RecyclerView.Adapter<MilestoneSecondAdapter.MyViewHolder>{

    private List<Milestone> milestoneList;

    public class MyViewHolder extends RecyclerView.ViewHolder {



        public TextView description;
        public TextView deadline;
        public TextView price;

        public MyViewHolder(View view) {
            super(view);

            description = view.findViewById(R.id.row_milestone2_description);
            deadline = view.findViewById(R.id.row_milestone2_deadline);
            price = view.findViewById(R.id.row_milestone2_price);





        }//End of MyViewHolder()


    }//Enf of class MyViewHolder

    public MilestoneSecondAdapter(List<Milestone> milestoneList) {

        this.milestoneList = milestoneList;
    }//End of CategorySearchAdapter()

    @Override
    public MilestoneSecondAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_second_milestone, parent, false);

        MyViewHolder evh = new MyViewHolder(itemView);
        return evh;
    }//End of onCreateViewHolder

    @Override
    public void onBindViewHolder(final MilestoneSecondAdapter.MyViewHolder holder, int position) {
        Milestone milestone = milestoneList.get(position);
        holder.description.setText(milestone.getDescription());
        holder.deadline.setText(milestone.getDeadline().toString().substring(0,10));
        holder.price.setText(""+milestone.getAmount()+" SAR");







    }//End of onBindViewHolder


    @Override
    public int getItemCount() {
        return milestoneList.size();
    }
}

