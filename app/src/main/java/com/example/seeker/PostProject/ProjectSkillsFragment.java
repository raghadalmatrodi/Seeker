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
import com.example.seeker.R;

import java.util.ArrayList;
import java.util.List;

public class ProjectSkillsFragment  extends Fragment  {
    private View view;
    private RecyclerView recyclerView;
    private SkillsAdapter adapter;
    private List<Skill> skillList = new ArrayList<>();
    private SkillsListener skillsListener;
    private BackSkillListener backSkillListener;
    private ImageView backBtn;
    private Button nextBtn;
    private List<Skill> projectSkillList;






    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_project_skills, container, false);

        //init
        backBtn = view.findViewById(R.id.project_skills_back);
        nextBtn = view.findViewById(R.id.next_skill);

        skillList.add(new Skill(11,"Java"));
        skillList.add(new Skill(21,"C#"));
        skillList.add(new Skill(31,"swift"));
        skillList.add(new Skill(22,"python"));
        skillList.add(new Skill(33,"php"));
        skillList.add(new Skill(44,"html"));


        //RecyclerView Code
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new SkillsAdapter(skillList);
        skillList = new ArrayList<>();
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

        recyclerView.setNestedScrollingEnabled(true);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);


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


    //Needed Interfaces
    public interface SkillsListener{

        void onNextSelected(List<Skill> skillList);
    }//End of interface

    public interface BackSkillListener{

        void onBacSkillSelected();
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
