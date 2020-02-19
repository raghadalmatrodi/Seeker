package com.example.seeker.PostProject;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.seeker.Database.ApiClients;
import com.example.seeker.Model.Project;
import com.example.seeker.Model.Responses.ApiResponse;
import com.example.seeker.R;
import com.google.gson.Gson;
import com.obsez.android.lib.filechooser.ChooserDialog;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProjectInformationFragment extends Fragment {

    private View view;
    private DatePickerDialog picker;
    private ProjectInformationListener projectInformationListener;
    private BackInformationListener backInformationListener;


    private EditText titleText;
    private EditText descriptionText;
    private EditText budgeText;
    private TextView deadlineDateText;


    private ImageView addSkillBtn;
    private ImageView addFileBtn;
    private TextView attach1;
    private Button postBtn;
    private ImageView backBtn;
    private LinearLayout attachFile;

    private String title;
    private String description;
    private String budget;
    private String deadlineDate;


    private LocalDateTime deadlineLocalDateTime;
    private LocalDateTime expiryLocalDateTime;
    private String timeString;
    private String expiryDate;







    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_project_information, container, false);

        init();


        attachFile = view.findViewById(R.id.attach_file);
        attachFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new ChooserDialog(view.getContext())
                        .withStartFile("")
                        .withChosenListener(new ChooserDialog.Result() {
                            @Override
                            public void onChoosePath(String path, File pathFile) {
                                Toast.makeText(view.getContext(), "FILE: " + path, Toast.LENGTH_SHORT).show();
                                attach1 = view.findViewById(R.id.display_attach1);
                            //    attach1.setText(pathFile.getName());

                                //هنا الزبده
                                // لاتشيلين ذا الكومنت ياريما
//                                createProjectWithAttachments(Arrays.asList(pathFile));
                            }
                        })
                        // to handle the back key pressed or clicked outside the dialog:
                        .withOnCancelListener(new DialogInterface.OnCancelListener() {
                            public void onCancel(DialogInterface dialog) {
                                Log.d("CANCEL", "CANCEL");
                                dialog.cancel(); // MUST have
                            }
                        })
                        .build()
                        .show();
            }
        });


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

                    expiryDate = setExpiryDate();

                    setTime();

                    deadlineDate = deadlineDate+timeString;
                    expiryDate = expiryDate+timeString;

                   deadlineLocalDateTime=  convertStringToLocalDateTime(deadlineDate);
                   expiryLocalDateTime = convertStringToLocalDateTime(expiryDate);


                    Log.i("PROJECT", deadlineLocalDateTime.toString());

                    projectInformationListener.onPostProjectItemSelected(title,description,budget,deadlineLocalDateTime.toString(),expiryLocalDateTime.toString());
                    Log.i("PROJECT", deadlineLocalDateTime.toString());


                }







            }
        });



        return view;
    }//End of onCreateView()

    private String setExpiryDate() {

        LocalDate today = LocalDate.parse(expiryDate);

        // increment days by 7

        System.out.println("Current Date: " + today);
        today = today.plusDays(10);
        String formattedDate = today.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
       return formattedDate;
    }

    private void createProjectWithAttachments(List<File> file)  {
        Project project = new Project("hello","hello",333,null , null , null,
                null ,"0");
        List<MultipartBody.Part> attachments = new ArrayList<>();

            file.stream().forEach(file1 -> {
                try {
                    attachments.add(MultipartBody.Part.createFormData("attachments",file1.getName() , RequestBody
                            .create(MediaType.parse(Files.probeContentType(file1.toPath()).toString()) , file1)));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });


        Gson gson = new Gson();
        RequestBody projectRequestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), gson.toJson(project));

        ApiClients.getAPIs().getPostProjectWithAttachmentsRequest(projectRequestBody,attachments).enqueue(new Callback<ApiResponse>() {
            private  final String LOG = ProjectInformationFragment.class.getSimpleName() ;

            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                if (response.isSuccessful()) {

                    Log.i(LOG, "onResponse : Success");

                }else{
                    Log.i(LOG, "onResponse : fail");

                }
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                Log.i(LOG, "onFailure : fail");

            }
        });
    }

    private LocalDateTime convertStringToLocalDateTime(String dateString) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
         return LocalDateTime.parse(dateString, formatter);
    }

    private void setTime() {

        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

        String dateString = formatter.format(date);
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

                                expiryDate = year+ "-" + "0" + month + "-" + "0" + dayOfMonth;

                            }else
                            {
                                deadlineDateText.setText(dayOfMonth + "-" +"0"+ month + "-" + year);
                                expiryDate = year+ "-" + "0" + month + "-" + dayOfMonth;
                            }


                        }else{

                            if(dayOfMonth <= 9){

                                deadlineDateText.setText("0"+dayOfMonth + "-" + month + "-" + year);
                                expiryDate = year + "-" + month + "-" +"0" + dayOfMonth ;
                            }else
                            {
                                deadlineDateText.setText(dayOfMonth + "-" + month + "-" + year);
                                expiryDate = year + "-" + month + "-" + dayOfMonth;

                            }

                        }




                    }
                }, year, month, day);

        picker.getDatePicker().setMinDate(System.currentTimeMillis()-1000);
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

         void onPostProjectItemSelected(String title, String description, String budget, String deadlineLocalDateTime, String expiryLocalDateTime);

    }//End of interface

    public interface BackInformationListener{
        void onBackInfoClick();
    }

//        @Override
//    public void onAttach(Context context) {
//        super.onAttach(context);
//        projectInformationListener = (ProjectInformationListener) getActivity();
//    }




}
