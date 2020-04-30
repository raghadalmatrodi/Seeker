package com.example.seeker.PostProject;

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

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.seeker.Database.ApiClients;
import com.example.seeker.Model.Project;
import com.example.seeker.Model.Responses.ApiResponse;
import com.example.seeker.Model.User;
import com.example.seeker.R;
import com.example.seeker.SharedPref.Constants;
import com.example.seeker.SharedPref.MySharedPreference;
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

    private User user;



    private RecyclerView recyclerView;
    private AttachmentAdapter adapter;
    List<File> files;




    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_project_information, container, false);

        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        getUser();
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
                uploadAttachment();

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
//        postBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//
//                title = titleText.getText().toString();
//                description = descriptionText.getText().toString();
//                budget = budgeText.getText().toString();
//                deadlineDate = deadlineDateText.getText().toString();
//
//                if (user.getIsEnabled().equals("1")){
//
//                    if (validate()) {
//
//                        expiryDate = setExpiryDate();
//
//                        setTime();
//
//                        deadlineDate = deadlineDate + timeString;
//                        expiryDate = expiryDate + timeString;
//
//                        deadlineLocalDateTime = convertStringToLocalDateTime(deadlineDate);
//                        expiryLocalDateTime = convertStringToLocalDateTime(expiryDate);
//
//
//                        Log.i("PROJECT", deadlineLocalDateTime.toString());
//
//                        Log.i("FILES", files.toString());
//
//
//                        if (files.isEmpty()) {
//                            projectInformationListener.onPostProjectItemSelected(title, description, budget, deadlineLocalDateTime.toString(), expiryLocalDateTime.toString());
//                            Log.i("PROJECT", deadlineLocalDateTime.toString());
//                        } else {
//
//                            //TODO CREATE THE OBJECT
//                            //  Double budgetToSave =  Double.parseDouble(budget);
//                            // Project project = new Project(title, description, budgetToSave,projectType,paymentType,expiryLocalDateTime ,deadlineLocalDateTime,  "0");
//                            projectInformationListener.onPostProjectItemSelectedWithAttachments(title, description, budget, deadlineLocalDateTime.toString(), expiryLocalDateTime.toString(), files);
//
//                            // Project projectToSave = new Project(title , description , budgetToSave ,deadlineLocalDateTime.toString(), expiryLocalDateTime.toString());
//                            //   createProjectWithAttachments(files);
//
//                        }
//
//
//                    } else {
//
//                        wrongInfoDialog("Missing information");
//
//
//                    }
//            }else{
//
//                    wrongInfoDialog("Your Account has been deactivated  \n to further information contact the support");
//                }
//
//            }
//        });



        return view;
    }//End of onCreateView()

    private void uploadAttachment() {
        ChooserDialog chooserDialog = new ChooserDialog(view.getContext());
        chooserDialog.withStartFile("");
        chooserDialog .withFilter(false, false, "jpg", "jpeg", "png")
        .withChosenListener(new ChooserDialog.Result() {
            @Override
            public void onChoosePath(String path, File pathFile) {

               if(adapter.getItemCount() == 4){

                   wrongInfoDialog("The maximum number of attachments is 4");
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

    @Override
    public void onResume() {
        super.onResume();

    }

    private void getUser() {

        long user_id = MySharedPreference.getLong(getContext(), Constants.Keys.USER_ID, -1);

        ApiClients.getAPIs().findUserById(user_id).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()){
                    user = response.body();
                    postBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {


                            title = titleText.getText().toString();
                            description = descriptionText.getText().toString();
                            budget = budgeText.getText().toString();
                            deadlineDate = deadlineDateText.getText().toString();

                            if (user.getIsEnabled().equals("1")){

                                if (validate()) {

                                    expiryDate = setExpiryDate();

                                    setTime();

                                    deadlineDate = deadlineDate + timeString;
                                    expiryDate = expiryDate + timeString;

                                    deadlineLocalDateTime = convertStringToLocalDateTime(deadlineDate);
                                    expiryLocalDateTime = convertStringToLocalDateTime(expiryDate);


                                    if (files.isEmpty()) {
                                        projectInformationListener.onPostProjectItemSelected(title, description, budget, deadlineLocalDateTime.toString(), expiryLocalDateTime.toString());
                                        Log.i("PROJECT", deadlineLocalDateTime.toString());
                                    } else {

                                        projectInformationListener.onPostProjectItemSelectedWithAttachments(title, description, budget, deadlineLocalDateTime.toString(), expiryLocalDateTime.toString(), files);


                                    }


                                } else {

                                    wrongInfoDialog("Missing information");


                                }
                            }else{

                                wrongInfoDialog("Your Account has been deactivated  \n to further information contact the support");
                            }

                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

            }
        });
    }


    public void setData(String paymentType) {

       if(paymentType.equals("Hourly")){

           budgeText.setHint("Price per hour");
       }else {
           budgeText.setHint("Total budget");
       }
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

    private String setExpiryDate() {

        LocalDate today = LocalDate.now();

        // increment days by 7

        System.out.println("Current Date: " + today);
        today = today.plusDays(10);
        String formattedDate = today.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        return formattedDate;
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

    private void wrongInfoDialog(String msg) {
        final androidx.appcompat.app.AlertDialog.Builder alertDialog = new AlertDialog.Builder(getContext());

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



