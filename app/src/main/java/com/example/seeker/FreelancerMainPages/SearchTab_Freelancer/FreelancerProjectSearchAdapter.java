package com.example.seeker.FreelancerMainPages.SearchTab_Freelancer;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.seeker.Model.Freelancer;
import com.example.seeker.Model.Project;
import com.example.seeker.R;

import java.util.ArrayList;
import java.util.List;

public class FreelancerProjectSearchAdapter extends RecyclerView.Adapter<FreelancerProjectSearchAdapter.MyViewHolder> implements Filterable {


    private List<Project> projecSearchtList;
    private List<Project> projectList;

    FreelancerProjectSearchAdapterListener listener;
    private List<Project> filteredProjectSearchList;

    public void setListener(FreelancerProjectSearchAdapterListener listener) {
        this.listener = listener;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {


        public ImageView arrow;
        public TextView title;
        public TextView description;

        public MyViewHolder(View view) {
            super(view);

            title = view.findViewById(R.id.row_search_freelancer_project_title);
            description = view.findViewById(R.id.row_search_freelancer_project_description);
            arrow = view.findViewById(R.id.row_arrow_search_freelancer);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener != null)
                        listener.freelancerOnProjectItemSelectedAdapter(projectList.get(getAdapterPosition()));

                }
            });



        }//End of MyViewHolder()


    }//Enf of class MyViewHolder


    public FreelancerProjectSearchAdapter(List<Project> projectList) {

        this.projectList = projectList;
        this.projecSearchtList=projectList;
    }//End of CategorySearchAdapter()

    @Override
    public FreelancerProjectSearchAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_freelancer_search_project, parent, false);

        MyViewHolder evh = new MyViewHolder(itemView);
        return evh;
    }//End of onCreateViewHolder

    @Override
    public void onBindViewHolder(final FreelancerProjectSearchAdapter.MyViewHolder holder, int position) {
        if(projectList.size()!=0){
            Project project = projectList.get(position);
            holder.title.setText(project.getTitle());
            holder.description.setText(project.getCategory().getTitle());

        }


    }//End of onBindViewHolder


    public interface FreelancerProjectSearchAdapterListener {
        void freelancerOnProjectItemSelectedAdapter(Project project);
    }
    @Override
    public int getItemCount() {
        return projectList.size();
    }



    //search view


    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String charSequenceString = constraint.toString();
                if (charSequenceString.isEmpty()) {

                    // projectList=projecSearchtList;
                    filteredProjectSearchList = projectList;
                } else {
                    // filteredProjectSearchList.clear();
                    List<Project> filteredList = new ArrayList<>();
                    for (Project project : projectList) {
                        if (project.getTitle().toLowerCase().contains(charSequenceString.toLowerCase())) {
                            filteredList.add(project);
                        }


                        projectList = filteredList;
                    }

                }
                FilterResults results = new FilterResults();
                results.values = filteredProjectSearchList;
                return results;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {


                filteredProjectSearchList = (List<Project>) results.values;
                notifyDataSetChanged();
            }
        };
    }


}
