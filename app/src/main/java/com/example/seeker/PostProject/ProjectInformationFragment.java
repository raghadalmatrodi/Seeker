package com.example.seeker.PostProject;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
    private TextView visibleText;



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
    int i=0;





    private RecyclerView recyclerView;
    private AttachmentAdapter adapter;
    List<File> files;




    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_project_information, container, false);

        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        init();

      files = new ArrayList<>(3);
        recyclerView =  view.findViewById(R.id.attachment_recycle_view);
        adapter = new AttachmentAdapter(getContext(), files);

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL,false);
        mLayoutManager.setStackFromEnd(false);
        recyclerView.setLayoutManager(mLayoutManager);

        recyclerView.addItemDecoration(new ProjectInformationFragment.VerticalSpaceItemDecoration(30));


        recyclerView.setItemAnimator(new DefaultItemAnimator());

        recyclerView.setAdapter(adapter);


        attachFile = view.findViewById(R.id.attach_file);
        attachFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChooserDialog chooserDialog = new ChooserDialog(view.getContext());
                chooserDialog.withStartFile("");
                chooserDialog.withChosenListener(new ChooserDialog.Result() {
                    @Override
                    public void onChoosePath(String path, File pathFile) {

                       if(adapter.getItemCount() == 4){

                           showDialog("The maximum number of attachments is 4");
                       }else {

                           files.add(pathFile);

                           adapter.notifyDataSetChanged();
                       }
                        //هنا الزبده
                        // لاتشيلين ذا الكومنت ياريما


                    }
                });
                chooserDialog.withOnCancelListener(new DialogInterface.OnCancelListener() {
                    public void onCancel(DialogInterface dialog) {
                        Log.d("CANCEL", "CANCEL");
                        dialog.cancel(); // MUST have
                    }
                });
                chooserDialog.build();
                chooserDialog.show();// to handle the back key pressed or clicked outside the dialog:

            }
        });



        deadlineDateText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                visibleText.setVisibility(TextView.VISIBLE);
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

                    Log.i("FILES", files.toString());


                    if(files.isEmpty()){
                        projectInformationListener.onPostProjectItemSelected(title,description,budget,deadlineLocalDateTime.toString(),expiryLocalDateTime.toString());
                        Log.i("PROJECT", deadlineLocalDateTime.toString());
                    }else{

                        //TODO CREATE THE OBJECT
                      //  Double budgetToSave =  Double.parseDouble(budget);
                   // Project project = new Project(title, description, budgetToSave,projectType,paymentType,expiryLocalDateTime ,deadlineLocalDateTime,  "0");
                        projectInformationListener.onPostProjectItemSelectedWithAttachments(title,description,budget,deadlineLocalDateTime.toString(),expiryLocalDateTime.toString() ,files);

                       // Project projectToSave = new Project(title , description , budgetToSave ,deadlineLocalDateTime.toString(), expiryLocalDateTime.toString());
                     //   createProjectWithAttachments(files);

                    }



                }else {

                    showDialog("Missing information");


                }

            }
        });



        return view;
    }//End of onCreateView()


    private String setExpiryDate() {

        LocalDate today = LocalDate.now();

        // increment days by 7

        System.out.println("Current Date: " + today);
        today = today.plusDays(10);
        String formattedDate = today.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
       return formattedDate;
    }

    //TODO reema remove comment
//
//    private void createProjectWithAttachments(List<File> file)  {
//        Project project = new Project("ne111w","new11",333,null , null , null,
//                null ,null);
//        List<MultipartBody.Part> attachments = new ArrayList<>();
//
//            file.stream().forEach(file1 -> {
//                try {
//                    attachments.add(MultipartBody.Part.createFormData("attachments",file1.getName() , RequestBody
//                            .create(MediaType.parse(Files.probeContentType(file1.toPath()).toString()) , file1)));
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            });
//
//
//        Gson gson = new Gson();
//        RequestBody projectRequestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), gson.toJson(project));
//
//        ApiClients.getAPIs().getPostProjectWithAttachmentsRequest(projectRequestBody,attachments).enqueue(new Callback<ApiResponse>() {
//            private  final String LOG = ProjectInformationFragment.class.getSimpleName() ;
//
//            @Override
//            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
//                if (response.isSuccessful()) {
//
//                    Log.i(LOG, "onResponse : Success");
//
//                }else{
//                    Log.i(LOG, "onResponse : fail");
//
//                }
//            }
//
//            @Override
//            public void onFailure(Call<ApiResponse> call, Throwable t) {
//                Log.i(LOG, "onFailure : fail");
//
//            }
//        });
//    }

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



        postBtn = view.findViewById(R.id.project_postBtn);
        backBtn = view.findViewById(R.id.project_info_back);
        visibleText = view.findViewById(R.id.app_date);

    }//End of init()

    public void setListener (ProjectInformationListener projectInformationListener, BackInformationListener backInformationListener)
    {
        this.projectInformationListener = projectInformationListener;
        this.backInformationListener = backInformationListener;
    }

    public interface ProjectInformationListener{

         void onPostProjectItemSelected(String title, String description, String budget, String deadlineLocalDateTime, String expiryLocalDateTime);
         void onPostProjectItemSelectedWithAttachments(String title, String description, String budget, String deadlineLocalDateTime, String expiryLocalDateTime , List<File> files );

    }//End of interface

    public interface BackInformationListener{
        void onBackInfoClick();
    }


    public void showDialog(final String msg ) {
        final AlertDialog alertDialog = new AlertDialog.Builder(getContext()).create();
        // alertDialog.getWindow().setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.dialogbackground));
        alertDialog.setMessage(msg);
        alertDialog.setIcon(R.mipmap.ic_launcher);

        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Ok",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        alertDialog.dismiss();


                    }//End onClick()
                });//End BUTTON_POSITIVE


        alertDialog.show();

    }//end showDialog




    public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

        private int spanCount;
        private int spacing;
        private boolean includeEdge;

        public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
            this.spanCount = spanCount;
            this.spacing = spacing;
            this.includeEdge = includeEdge;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view); // item position
            int column = position % spanCount; // item column

            if (includeEdge) {
                outRect.left = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
                outRect.right = (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)

                if (position < spanCount) { // top edge
                    outRect.top = spacing;
                }
                outRect.bottom = spacing; // item bottom
            } else {
                outRect.left = column * spacing / spanCount; // column * ((1f / spanCount) * spacing)
                outRect.right = spacing - (column + 1) * spacing / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
                if (position >= spanCount) {
                    outRect.top = spacing; // item top
                }
            }
        }
    }
    public class VerticalSpaceItemDecoration extends RecyclerView.ItemDecoration {

        private final int verticalSpaceHeight;

        public VerticalSpaceItemDecoration(int verticalSpaceHeight) {
            this.verticalSpaceHeight = verticalSpaceHeight;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent,
                                   RecyclerView.State state) {
            outRect.bottom = verticalSpaceHeight;
        }
    }
    /**
     * Converting dp to pixel
     */
    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }


}



