package com.example.seeker.PostProject;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;

import com.example.seeker.R;

public class ProjectInformationFragment extends Fragment {

    private View view;
    private EditText titleText;
    private EditText descriptionText;
    private EditText budgeText;
    private EditText deadlineDate;

    private ImageView addSkillBtn;
    private ImageView addFileBtn;
    private Button postBtn;

    private String title;
    private String description;
    private String budget;




    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_project_information, container, false);

        postBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               if(validate()) {

               }
            }
        });

        init();

        return view;
    }//End of onCreateView()

    private boolean validate() {

        title = titleText.getText().toString();
        description = descriptionText.getText().toString();
        budget = budgeText.getText().toString();

        if(title.isEmpty() || budget.isEmpty()){
            return false;
        }else{
            return true;
        }
    }//End of validate()

    private void init() {

        titleText = view.findViewById(R.id.project_title);
        descriptionText = view.findViewById(R.id.project_description);
        budgeText = view.findViewById(R.id.project_budget);
        deadlineDate = view.findViewById(R.id.project_deadline);

        addSkillBtn = view.findViewById(R.id.project_add_skill);
        addFileBtn = view.findViewById(R.id.project_add_file);
        postBtn = view.findViewById(R.id.project_postBtn);

    }//End of init()
}
