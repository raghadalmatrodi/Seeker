package com.example.seeker.PostProject;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.seeker.R;
import com.google.android.material.textfield.TextInputLayout;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Calendar;

public class ProjectInformationFragment extends Fragment {

    private View view;
    DatePickerDialog picker;
    private ProjectInformationListener projectInformationListener;

    private EditText titleText;
    private EditText descriptionText;
    private EditText budgeText;
    private EditText deadlineDate;

    private TextInputLayout deadlineDateText;

    private ImageView addSkillBtn;
    private ImageView addFileBtn;
    private Button postBtn;

    private String title;
    private String description;
    private String budget;
    private String DateString;




    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_project_information, container, false);

        init();

        deadlineDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                calendarDialog();
            }
        });
        postBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                title = titleText.getText().toString();
                description = descriptionText.getText().toString();
                budget = budgeText.getText().toString();

                   projectInformationListener.onPostProjectItemSelected(title,description,budget,deadlineDate.getText().toString());


            }
        });



        return view;
    }//End of onCreateView()

    private void calendarDialog() {

        final Calendar cldr = Calendar.getInstance();
        int day = cldr.get(Calendar.DAY_OF_MONTH);
        int month = cldr.get(Calendar.MONTH);
        int year = cldr.get(Calendar.YEAR);
        // date picker dialog
        picker = new DatePickerDialog(getContext(),
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        deadlineDate.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);



                    }
                }, year, month, day);
        picker.show();




    }

    private void validate() {

        title = titleText.getText().toString();
        description = descriptionText.getText().toString();
        budget = budgeText.getText().toString();

        if(title.isEmpty() || budget.isEmpty()){

            return;
        }else{

            return;
        }
    }//End of validate()

    private void init() {

        titleText = view.findViewById(R.id.project_title);
        descriptionText = view.findViewById(R.id.project_description);
        budgeText = view.findViewById(R.id.project_budget);
        deadlineDate =  view.findViewById(R.id.project_deadline);

        addSkillBtn = view.findViewById(R.id.project_add_skill);
        addFileBtn = view.findViewById(R.id.project_add_file);
        postBtn = view.findViewById(R.id.project_postBtn);

    }//End of init()

    public void setListener (ProjectInformationListener projectInformationListener)
    {
        this.projectInformationListener = projectInformationListener;
    }

    public interface ProjectInformationListener{

         void onPostProjectItemSelected(String title, String description, String budget, String deadline);

    }//End of interface

        @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        projectInformationListener = (ProjectInformationListener) getActivity();
    }


}
