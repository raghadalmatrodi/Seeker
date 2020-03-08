package com.example.seeker.EmployerMainPages.SearchTab_Emp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.seeker.Model.Project;
import com.example.seeker.R;

import java.util.List;

public class ProjectSearchAdapter extends RecyclerView.Adapter<ProjectSearchAdapter.MyViewHolder>  {


    private List<Project> projectList;
    ProjectSearchAdapterListener listener;

    public void setListener(ProjectSearchAdapterListener listener) {
        this.listener = listener;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {


        public ImageView arrow;
        public TextView title;
        public TextView description;

        public MyViewHolder(View view) {
            super(view);

            title = view.findViewById(R.id.row_search_emp_project_title);
            description = view.findViewById(R.id.row_search_emp_project_description);
            arrow = view.findViewById(R.id.row_arrow_search_emp);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener != null)
                    listener.onProjectItemSelectedAdapter(projectList.get(getAdapterPosition()));

                }
            });



        }//End of MyViewHolder()


    }//Enf of class MyViewHolder


    public ProjectSearchAdapter(List<Project> projectList) {

        this.projectList = projectList;
    }//End of CategorySearchAdapter()

    @Override
    public ProjectSearchAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_emp_search_project, parent, false);

        MyViewHolder evh = new MyViewHolder(itemView);
        return evh;
    }//End of onCreateViewHolder

    @Override
    public void onBindViewHolder(final ProjectSearchAdapter.MyViewHolder holder, int position) {
        Project project = projectList.get(position);
        holder.title.setText(project.getTitle());
       holder.description.setText(project.getCategory().getTitle());


    }//End of onBindViewHolder


    public interface ProjectSearchAdapterListener {
        void onProjectItemSelectedAdapter(Project project);
    }
    @Override
    public int getItemCount() {
        return projectList.size();
    }
}