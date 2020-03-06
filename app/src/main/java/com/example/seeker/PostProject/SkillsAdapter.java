package com.example.seeker.PostProject;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.seeker.Model.Category;
import com.example.seeker.Model.Skill;
import com.example.seeker.Model.SkillRecyclerView;
import com.example.seeker.R;

import java.util.ArrayList;
import java.util.List;

public class SkillsAdapter extends RecyclerView.Adapter<SkillsAdapter.MyViewHolder> {


    private List<SkillRecyclerView> skillList;
    private List<SkillRecyclerView> projectSkillList = new ArrayList<>();
    private static final String LOG = SkillsAdapter.class.getSimpleName();



    public class MyViewHolder extends RecyclerView.ViewHolder {


        public CheckBox checkBox;
        public TextView title;

        public MyViewHolder(View view) {
            super(view);

            title = view.findViewById(R.id.row_title);
            checkBox = view.findViewById(R.id.row_arrow);







        }//End of MyViewHolder()


    }//Enf of class MyViewHolder

    private void removeSkill(SkillRecyclerView skill) {

        projectSkillList.remove(skill);

    }


    public SkillsAdapter(List<SkillRecyclerView> skillList) {

        this.skillList = skillList;
    }//End of CategorySearchAdapter()

    @Override
    public SkillsAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_skills, parent, false);

        SkillsAdapter.MyViewHolder evh = new SkillsAdapter.MyViewHolder(itemView);
        return evh;
    }//End of onCreateViewHolder

    @Override
    public void onBindViewHolder(final SkillsAdapter.MyViewHolder holder, int position) {

        SkillRecyclerView skill = skillList.get(position);
        holder.title.setText(skill.getName());

        holder.checkBox.setChecked(skill.isSelected());
        holder.checkBox.setTag(position);

        holder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Integer pos = (Integer) holder.checkBox.getTag();


                if (skillList.get(position).isSelected()) {

                    skillList.get(pos).setSelected(false);
                    removeSkill(skillList.get(position));

                } else {

                    if(projectSkillList.size() < 4)
                    {
                        skillList.get(pos).setSelected(true);
                        projectSkillList.add(skillList.get(pos));
                    }else{
                        skillList.get(pos).setSelected(false);
                        holder.checkBox.setChecked(false);
                    }

                }
            }
        });



    }//End of onBindViewHolder


    @Override
    public int getItemCount() {
        return skillList.size();
    }

    public List<SkillRecyclerView> getProjectSkills()
    {
//        List<SkillRecyclerView> newSkillList = projectSkillList;
//        projectSkillList = new ArrayList<>();
        return projectSkillList;


    }
}
