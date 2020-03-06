package com.example.seeker.PostProject;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.seeker.Model.Category;
import com.example.seeker.Model.Skill;
import com.example.seeker.Model.SkillRecyclerView;
import com.example.seeker.R;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ProjectSkillsFragment  extends Fragment  {
    private View view;
    private RecyclerView recyclerView;
    private SkillsAdapter adapter;


    private SkillsListener skillsListener;
    private BackSkillListener backSkillListener;
    private ImageView backBtn;
    private Button nextBtn;

    private List<SkillRecyclerView> projectSkillList = new ArrayList<>();
    private List<SkillRecyclerView> skillArrayList = new ArrayList<>();


    private Set<Skill> skillList = new HashSet<>();







    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_project_skills, container, false);

        //init
        backBtn = view.findViewById(R.id.project_skills_back);
        nextBtn = view.findViewById(R.id.next_skill);





        //Back Button ToolBar listener
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                backSkillListener.onBacSkillSelected();
            }
        });

        //Next Button Listener
        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


               projectSkillList=  adapter.getProjectSkills();
               if(projectSkillList.isEmpty()){
                   wrongInfoDialog("You have to choose at least one skill");
               }else {


                   skillsListener.onNextSelected(projectSkillList);
               }


            }
        });




        return view;
    }//End of onCreateView()

    public void setData(Category category){

         skillArrayList= new ArrayList<>();
        setRecyclerView(category, null);

    }
    public void setBackData(Category category, List<SkillRecyclerView> skillRecyclerViews){

        skillArrayList= new ArrayList<>();
        setRecyclerView(category, skillRecyclerViews);
    }

    //Needed Interfaces
    public interface SkillsListener{

        void onNextSelected(List<SkillRecyclerView> skillList);
    }//End of interface

    public interface BackSkillListener{

        void onBacSkillSelected();
    }

    public void setRecyclerView(Category category, List<SkillRecyclerView> skillRecyclerViews){

        skillList =  category.getSkills();


        for(Skill s : skillList){
            SkillRecyclerView skill = new SkillRecyclerView(s.getId(),s.getName(), false);
            skillArrayList.add(skill);
        }


        if( skillRecyclerViews != null){

            if(!skillRecyclerViews.isEmpty()) {

                for (SkillRecyclerView s : skillArrayList) {

                    for (SkillRecyclerView r : skillRecyclerViews) {

                        if (s.getId() == r.getId()) {
                            s.setSelected(true);

                            break;
                        }
                    }
                }


            }
        }



        //RecyclerView Code
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new SkillsAdapter(skillArrayList,skillRecyclerViews);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
        recyclerView.setNestedScrollingEnabled(true);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);

    }

    public void setListener (SkillsListener skillsListener, BackSkillListener backSkillListener)
    {
        this.skillsListener = skillsListener;
        this.backSkillListener = backSkillListener;
    }

    private void wrongInfoDialog(String msg) {
        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(getContext());

        alertDialog.setTitle("Warning");

        // Setting Dialog Message
        alertDialog.setMessage(msg);

        // Setting Icon to Dialog
//        alertDialog.setIcon(R.drawable.exclamation);

        //Setting Negative "ok" Button
        alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }//end onClick
        });//end setPositiveButton

        alertDialog.show();

    }//End wrongInfoDialog()




}
