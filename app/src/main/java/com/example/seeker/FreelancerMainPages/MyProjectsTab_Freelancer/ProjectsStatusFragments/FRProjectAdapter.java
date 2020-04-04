package com.example.seeker.FreelancerMainPages.MyProjectsTab_Freelancer.ProjectsStatusFragments;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.seeker.Model.Project;
import com.example.seeker.R;

import java.util.List;

public class FRProjectAdapter extends RecyclerView.Adapter<FRProjectAdapter.MyViewHolder> {

    private List<Project> projectList;
    private ProjectAdapterListener listener;

    public void setListener(ProjectAdapterListener listener) {
        this.listener = listener;
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {


        public ImageView arrow ;
        public TextView title;
        public TextView description;

        public MyViewHolder(View view) {
            super(view);

            title = view.findViewById(R.id.row_project_title);
            description = view.findViewById(R.id.row_project_description);
            arrow = view.findViewById(R.id.row_arrow);



            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    listener.onProjectItemClick(projectList.get(getAdapterPosition()));

                }
            });




        }//End of MyViewHolder()


    }//Enf of class MyViewHolder

    public FRProjectAdapter(List<Project> projectList) {

        this.projectList = projectList;
    }//End of BidsAdapter()


    public interface ProjectAdapterListener {
        void onProjectItemClick(Project project);
    }

    @Override
    public FRProjectAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_project, parent, false);

        MyViewHolder evh = new MyViewHolder(itemView);
        return evh;
    }//End of onCreateViewHolder

    @Override
    public void onBindViewHolder(final FRProjectAdapter.MyViewHolder holder, int position) {
        Project project = projectList.get(position);
        holder.title.setText(project.getTitle());
        holder.description.setText(project.getTitle());


    }//End of onBindViewHolder


    @Override
    public int getItemCount() {
        return projectList.size();
    }

}
