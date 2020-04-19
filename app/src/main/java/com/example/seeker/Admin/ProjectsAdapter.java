package com.example.seeker.Admin;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.seeker.Model.Bid;
import com.example.seeker.Model.Project;
import com.example.seeker.R;

import java.util.ArrayList;
import java.util.List;

public class ProjectsAdapter extends RecyclerView.Adapter<ProjectsAdapter.MyViewHolder> implements Filterable {

    private List<Project> projectSearchList;
    private List<Project> projectList;
    private Context context;
    private ProjectsAdapterListener listener;

    private List<Project> filteredProjectSearchList;

    public void setListener(ProjectsAdapterListener listener) {
        this.listener = listener;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView deactivate;
        public TextView projectTitle;
        public TextView projectDescription;
        LinearLayout projectLinear;

        public MyViewHolder(View view) {
            super(view);

            projectTitle = view.findViewById(R.id.row_project_title_admin);
            projectDescription = view.findViewById(R.id.row_project_description_admin);
            deactivate = view.findViewById(R.id.row_project_deactivate);
            projectLinear= view.findViewById(R.id.project_linear);
deactivate.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        listener.onDeleteProjectItemClick(projectList.get(getAdapterPosition()));

    }
});

            projectLinear.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onProjectItemClick(projectList.get(getAdapterPosition()));

                }
            });


        }//End of MyViewHolder()
    }//Enf of class MyViewHolder



    public ProjectsAdapter(List<Project> projectList, Context context) {

        this.projectList = projectList;
        this.projectSearchList=projectList;
        this.context =context;

      //  setHasStableIds(true);
    }//End of CategorySearchAdapter()

    @Override
    public ProjectsAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_project_admin, parent, false);

        MyViewHolder evh = new MyViewHolder(itemView);
        return evh;
    }//End of onCreateViewHolder

    @Override
    public void onBindViewHolder(final ProjectsAdapter.MyViewHolder holder, int position) {


        Project project = projectList.get(position);
        holder.projectTitle.setText(project.getTitle());
        holder.projectDescription.setText(project.getDescription());

    }//End of onBindViewHolder



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
                    projectList=projectSearchList;
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

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }



    public interface ProjectsAdapterListener {
        void onProjectItemClick(Project project);

        void onDeleteProjectItemClick(Project project);
    }

}
