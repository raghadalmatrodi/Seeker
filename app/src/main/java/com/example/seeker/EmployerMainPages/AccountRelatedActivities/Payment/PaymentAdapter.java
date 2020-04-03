package com.example.seeker.EmployerMainPages.AccountRelatedActivities.Payment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.seeker.Model.Milestone;
import com.example.seeker.Model.Project;
import com.example.seeker.R;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import ayalma.ir.expandablerecyclerview.ExpandableRecyclerView;

public class PaymentAdapter extends ExpandableRecyclerView.Adapter<PaymentAdapter.ChildViewHolder, ExpandableRecyclerViews.SimpleGroupViewHolder, Milestone, Project> {

    List<Project> projectList;
    List<Milestone> milestoneList;
    Context context;

    public  PaymentAdapter(List<Project> projectList, Context context){
        this.projectList = projectList;
        this.context = context;
        milestoneList = new ArrayList<>();
    }

    public void setMilestoneList(Project project){

        milestoneList = new ArrayList<>();
        for(int i =0; i<project.getMilestones().size(); i++){
            if(project.getMilestones().get(i).getStatus().equals("0")){
                milestoneList.add(project.getMilestones().get(i));
            }
        }



    }
    @Override
    public int getGroupItemCount() {
        return projectList.size()-1;
    }

    @Override
    public int getChildItemCount(int i) {

        setMilestoneList(projectList.get(i));

        return milestoneList.size();
    }

    @Override
    public Project getGroupItem(int i) {
        return projectList.get(i);
    }

    @Override
    public Milestone getChildItem(int project, int milestone) {
     return milestoneList.get(milestone);
    }

    @Override
    protected ExpandableRecyclerViews.SimpleGroupViewHolder onCreateGroupViewHolder(ViewGroup parent)
    {
        return new ExpandableRecyclerViews.SimpleGroupViewHolder(parent.getContext());
    }

    @Override
    protected ChildViewHolder onCreateChildViewHolder(ViewGroup parent, int viewType)
    {
        View rootView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_milestone_payment,parent,false);
        return new ChildViewHolder(rootView);
    }

    @Override
    public void onBindGroupViewHolder(final ExpandableRecyclerViews.SimpleGroupViewHolder holder, int position) {
        super.onBindGroupViewHolder(holder, position);

        Log.d("projectList", ""+projectList.size());
        Log.d("position", ""+position);
        if(position < projectList.size()){
            Project project = projectList.get(position);

            setMilestoneList(project);
            if(project.getTitle() != null)
                holder.setText(project.getTitle());
        }




    }

    @Override
    public void onBindChildViewHolder(ChildViewHolder holder, int group, int position)
    {
        super.onBindChildViewHolder(holder, group, position);



        if(milestoneList != null) {
            Milestone milestone = projectList.get(group).getMilestones().get(position);

            if (milestone.getDescription() != null)
                holder.description.setText(milestone.getDescription());

            if (milestone.getDeadline() != null)
                holder.deadline.setText(milestone.getDeadline().toString().substring(0, 10));

            String stringPrice = String.valueOf(milestone.getAmount());
            int index = stringPrice.indexOf(".");
            stringPrice = stringPrice.substring(0, index);
            holder.price.setText(stringPrice + " SAR");

            holder.payment.setText(milestone.getStatus());
            if (milestone.getStatus().equals("0")) {

//                holder.payment.setTextColor(Color.parseColor("#9e1c08"));
                holder.payment.setText("Pay");
            }
            else {

                holder.payment.setTextColor(Color.parseColor("#237a23"));
                holder.payment.setText("Paid");

            }

            holder.payment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Intent intent = new Intent(context, PaymentMethodActivity.class);
                    intent.putExtra("milestone", (Serializable) milestone);
                    context.startActivity(intent);

                }
            });

        }

    }

        @Override
    public int getChildItemViewType(int i, int i1) {
        return 1;
    }

    public class ChildViewHolder extends RecyclerView.ViewHolder
    {
        public TextView description;
        public TextView deadline;
        public TextView price;
        public Button payment;
        public ChildViewHolder(View view) {
            super(view);

            description = view.findViewById(R.id.row_milestone_description);
            deadline = view.findViewById(R.id.row_milestone_deadline);
            price = view.findViewById(R.id.row_milestone_price);
            payment = view.findViewById(R.id.row_milestone_payment);


        }
    }
}
