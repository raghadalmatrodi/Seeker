package com.example.seeker.PostProject;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.example.seeker.R;
import com.google.android.material.textfield.TextInputLayout;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

public class ProjectInformationFragment extends Fragment {

    private View view;
    private DatePickerDialog picker;
    private ProjectInformationListener projectInformationListener;
    private BackInformationListener backInformationListener;


    private EditText titleText;
    private EditText descriptionText;
    private EditText budgeText;
    private EditText deadlineDateText;


    private ImageView addSkillBtn;
    private ImageView addFileBtn;
    private Button postBtn;
    private ImageView backBtn;

    private String title;
    private String description;
    private String budget;
    private String deadlineDate;


    private LocalDateTime deadlineLocalDateTime;
    private String dateString;
    private String timeString;







    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_project_information, container, false);

        init();

        deadlineDateText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                calendarDialog();
            }
        });

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                backInformationListener.onBackInfoClick();
            }
        });
        postBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                title = titleText.getText().toString();
                description = descriptionText.getText().toString();
                budget = budgeText.getText().toString();
                deadlineDate = deadlineDateText.getText().toString();


                if(validate()){

                    setTime();

                    deadlineDate = deadlineDate+timeString;
                    convertStringToLocalDateTime();



                    projectInformationListener.onPostProjectItemSelected(title,description,budget,deadlineLocalDateTime);
                }







            }
        });



        return view;
    }//End of onCreateView()

    private void convertStringToLocalDateTime() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        deadlineLocalDateTime = LocalDateTime.parse(deadlineDate, formatter);
    }

    private void setTime() {

        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

        dateString = formatter.format(date);
        timeString=  dateString.substring(10);
    }

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

                        int month = monthOfYear + 1;
                        if(month <= 9)
                        {
                            if(dayOfMonth <= 9){

                                deadlineDateText.setText("0"+ dayOfMonth + "-" +"0"+ month + "-" + year);

                            }else
                            {
                                deadlineDateText.setText(dayOfMonth + "-" +"0"+ month + "-" + year);
                            }


                        }else{

                            if(dayOfMonth <= 9){

                                deadlineDateText.setText("0"+dayOfMonth + "-" + month + "-" + year);
                            }else
                            {
                                deadlineDateText.setText(dayOfMonth + "-" + month + "-" + year);

                            }

                        }




                    }
                }, year, month, day);
        picker.show();




    }

    private boolean validate() {



        if(title.isEmpty() || budget.isEmpty() || deadlineDate.isEmpty()){


            return false ;


        }else{

            return true;
        }


    }//End of validate()

    private void init() {

        titleText = view.findViewById(R.id.project_title);
        descriptionText = view.findViewById(R.id.project_description);
        budgeText = view.findViewById(R.id.project_budget);
        deadlineDateText =  view.findViewById(R.id.project_deadline);

        addSkillBtn = view.findViewById(R.id.project_add_skill);
        addFileBtn = view.findViewById(R.id.project_add_file);
        postBtn = view.findViewById(R.id.project_postBtn);
        backBtn = view.findViewById(R.id.project_info_back);

    }//End of init()

    public void setListener (ProjectInformationListener projectInformationListener, BackInformationListener backInformationListener)
    {
        this.projectInformationListener = projectInformationListener;
        this.backInformationListener = backInformationListener;
    }

    public interface ProjectInformationListener{

         void onPostProjectItemSelected(String title, String description, String budget, LocalDateTime deadlineLocalDateTime);

    }//End of interface

    public interface BackInformationListener{
        void onBackInfoClick();
    }

        @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        projectInformationListener = (ProjectInformationListener) getActivity();
    }




}
