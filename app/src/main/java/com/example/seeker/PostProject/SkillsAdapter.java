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
import com.example.seeker.R;

import java.util.ArrayList;
import java.util.List;

public class SkillsAdapter extends RecyclerView.Adapter<SkillsAdapter.MyViewHolder> {


    private List<Skill> skillList;
    private List<Skill> projectSkillList = new ArrayList<>();
    int skillsCounter = 0;
    private static final String LOG = SkillsAdapter.class.getSimpleName();



    public class MyViewHolder extends RecyclerView.ViewHolder {


        public CheckBox checkBox;
        public TextView title;

        public MyViewHolder(View view) {
            super(view);

            title = view.findViewById(R.id.row_title);
            checkBox = view.findViewById(R.id.row_arrow);



            checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (checkBox.isChecked()) {

                        if (skillsCounter < 4){

                            skillsCounter++;

                           projectSkillList.add(skillList.get(getAdapterPosition()));

                        }else{
                            Log.i(LOG, "counter : "+skillsCounter);
                            buttonView.setChecked(false);





                        }

                    }
                 else {


                     skillsCounter--;
                     removeSkill(skillList.get(getAdapterPosition()));
                }
            }
        });



        }//End of MyViewHolder()


    }//Enf of class MyViewHolder

    private void removeSkill(Skill skill) {

        projectSkillList.remove(skill);

    }


    public SkillsAdapter(List<Skill> skillList) {

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
        Skill skill = skillList.get(position);
        holder.title.setText(skill.getName());


    }//End of onBindViewHolder


    @Override
    public int getItemCount() {
        return skillList.size();
    }

    public List<Skill> getProjectSkills()
    {
        return projectSkillList;
    }
}
