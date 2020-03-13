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
    private List<SkillRecyclerView> projectSkillList;
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
        if(!projectSkillList.isEmpty()) {
            for(int i =0; i<projectSkillList.size(); i++) {
                SkillRecyclerView s = projectSkillList.get(i);
                if (s.getId() == skill.getId()) {
                    s.setSelected(false);
                    projectSkillList.remove(s);
                }
            }

//            for (SkillRecyclerView s : projectSkillList) {
//                if (s.getId() == skill.getId()) {
//                    s.setSelected(false);
//                    projectSkillList.remove(s);
//                }
//            }
        }

    }


    public SkillsAdapter(List<SkillRecyclerView> skillList, List<SkillRecyclerView> projectSkillList) {

        this.skillList = skillList;
        if(projectSkillList != null){
            this.projectSkillList = projectSkillList;
        }else{
            this.projectSkillList = new ArrayList<>();
        }

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


                if (skillList.get(pos).isSelected()) {

                    skillList.get(pos).setSelected(false);
                    removeSkill(skillList.get(pos));



                } else {

                    if(projectSkillList.size() < 4)
                    {
                        skillList.get(pos).setSelected(true);

                        SkillRecyclerView skillRecyclerView = skillList.get(pos);
                        projectSkillList.add(skillRecyclerView);
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
        return projectSkillList;


    }



}
