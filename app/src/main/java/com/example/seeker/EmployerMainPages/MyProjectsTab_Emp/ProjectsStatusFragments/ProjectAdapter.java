package com.example.seeker.EmployerMainPages.MyProjectsTab_Emp.ProjectsStatusFragments;

import android.content.Context;

import android.view.LayoutInflater;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.seeker.Model.Project;
import com.example.seeker.R;

import java.util.List;

import static android.view.View.VISIBLE;

public class ProjectAdapter extends RecyclerView.Adapter<ProjectAdapter.MyViewHolder>  {
    int position_tab;
    private Context mContext;
    private List<Project> projectList;
    private ProjectAdapterListener listener;



    public void setListener(ProjectAdapterListener listener) {
        this.listener = listener;
    }



    public class MyViewHolder extends RecyclerView.ViewHolder {


        public ImageView arrow;
        public TextView title;
        public TextView description;
        public ImageView trashIcon, extendIcon;

        public MyViewHolder(View view) {
            super(view);

            title = view.findViewById(R.id.row_project_title);
            description = view.findViewById(R.id.row_project_description);
            arrow = view.findViewById(R.id.row_arrow);
            trashIcon = view.findViewById(R.id.image_trash);
            extendIcon = view.findViewById(R.id.image_extend);


            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                   listener.onProjectItemSelectedAdapter(projectList.get(getAdapterPosition()));

                }
            });

            trashIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onProjectDeleteSelectedAdapter(projectList.get(getAdapterPosition()));

                }
            });


            extendIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onProjectExtendsSelectedAdapter(projectList.get(getAdapterPosition()));
                }
            });



        }//End of MyViewHolder()


    }//Enf of class MyViewHolder

    public interface ProjectAdapterListener {
        void onProjectItemSelectedAdapter(Project project);
        void onProjectExtendsSelectedAdapter(Project project);
        void onProjectDeleteSelectedAdapter(Project project);


    }

    public ProjectAdapter(Context mContext, List<Project> projectList, int position) {
        this.mContext = mContext;
        this.projectList = projectList;
        position_tab = position;
    }//End of CategorySearchAdapter()

    @Override
    public ProjectAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_project, parent, false);

        MyViewHolder evh = new MyViewHolder(itemView);
        return evh;
    }//End of onCreateViewHolder

    @Override
    public void onBindViewHolder(final ProjectAdapter.MyViewHolder holder, int position) {
        Project project = projectList.get(position);
        if (project.getCategory() != null) {
            holder.title.setText(project.getTitle());
            holder.description.setText(project.getCategory().getTitle());


//trash and extend icons
            if (position_tab == 0) {
                holder.trashIcon.setVisibility(VISIBLE);
             //   if(expiryDate-createdat==0)
                //it will be visible when the project lifetime is 10
                holder.extendIcon.setVisibility(VISIBLE);

                //todo raghad
//else if(expirydate== today) we will do delete without asking

            }

        }

    }//End of onBindViewHolder


    @Override
    public int getItemCount() {
        return projectList.size();
    }
}
