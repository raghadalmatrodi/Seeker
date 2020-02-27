package com.example.seeker.EmployerMainPages;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.seeker.Database.ApiClients;
import com.example.seeker.Model.Category;
import com.example.seeker.Model.Exception.ApiError;
import com.example.seeker.Model.Exception.ApiException;
import com.example.seeker.Model.Project;
import com.example.seeker.Model.Responses.ApiResponse;
import com.example.seeker.PostProject.FragmentAdapter;
import com.example.seeker.PostProject.PaymentTypeFragment;
import com.example.seeker.PostProject.ProjectCategoryFragment;
import com.example.seeker.PostProject.ProjectInformationFragment;
import com.example.seeker.PostProject.ProjectTypeFragment;
import com.example.seeker.R;
import com.google.gson.Gson;

import java.io.File;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Converter;
import retrofit2.Response;


public class Emp_PostFragment extends Fragment implements ProjectTypeFragment.ProjectTypeListener
        , ProjectCategoryFragment.CategoryListener, PaymentTypeFragment.PaymentListener,
        ProjectInformationFragment.ProjectInformationListener, ProjectCategoryFragment.BackCategoryListener,
        PaymentTypeFragment.BackPaymentListener, ProjectInformationFragment.BackInformationListener{


    private FragmentAdapter adapter;

    private View view;
    private ViewPager viewPager;
    private ProjectTypeFragment projectTypeFragment;
    private ProjectCategoryFragment projectCategoryFragment;
    private PaymentTypeFragment paymentTypeFragment;
    private ProjectInformationFragment projectInformationFragment;
    private static final String LOG = Emp_PostFragment.class.getSimpleName();


    private String projectType, paymentType, title,description, budget;
    private Category category;
    private String deadlineLocalDateTime, expiryLocalDateTime;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
         view = inflater.inflate(R.layout.fragment_post_project, container, false);

        projectTypeFragment = new ProjectTypeFragment();
        projectCategoryFragment= new ProjectCategoryFragment();
        paymentTypeFragment = new PaymentTypeFragment();
        projectInformationFragment = new ProjectInformationFragment();


        projectTypeFragment.setListener(this);
        projectCategoryFragment.setListener(this,this);
        paymentTypeFragment.setListener(this,this);
        projectInformationFragment.setListener(this,this);



        adapter = new FragmentAdapter(getFragmentManager());
        adapter.addFragment(projectTypeFragment);
        adapter.addFragment(projectCategoryFragment);
        adapter.addFragment(paymentTypeFragment);
        adapter.addFragment(projectInformationFragment);

        viewPager = (ViewPager) view.findViewById(R.id.viewPager);
        viewPager.beginFakeDrag();


        viewPager.setAdapter(adapter);

         return view;

    }

    @Override
    public void onProjectTypeItemSelected(String projectType) {
        this.projectType = projectType;
        viewPager.setCurrentItem(1);
    }
    @Override
    public void onCategoryTypeItemSelected(Category category) {
        this.category = category;
        viewPager.setCurrentItem(2);
    }
    @Override
    public void onPaymentTypeItemSelected(String paymentType) {
        this.paymentType = paymentType;
        viewPager.setCurrentItem(3);

    }

    @Override
    public void onPostProjectItemSelected(String title, String description, String budget, String deadlineLocalDateTime, String expiryLocalDateTime) {

        this.title = title;
        this.description = description;
        this.budget = budget;
        this.deadlineLocalDateTime = deadlineLocalDateTime;
        this.expiryLocalDateTime = expiryLocalDateTime;


        postProjectValidation();


    }

    @Override
    public void onPostProjectItemSelectedWithAttachments(String title, String description, String budget, String deadlineLocalDateTime, String expiryLocalDateTime, List<File> files) {
        if(projectType.isEmpty() || (category == null) || paymentType.isEmpty() || title.isEmpty() || budget.isEmpty()){

            wrongInfoDialog("Missing Information");
        }
        else{

            double budgetValue = Double.parseDouble(budget);
            Project project = new Project(title, description, budgetValue,projectType,paymentType,expiryLocalDateTime ,deadlineLocalDateTime,  "0");


            AlertDialog.Builder alertDialog = new AlertDialog.Builder(getContext());
            // Setting Dialog Message
            alertDialog.setTitle("Project details Review");
            alertDialog.setMessage(project.toString());

            //Setting positive "ok" Button
            alertDialog.setPositiveButton("POST", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {


                    createProjectWithAttachments(files,project);

                    dialog.dismiss();


                }//end onClick
            });//end setPositiveButton


            alertDialog.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();

                }
            });
            alertDialog.show();

        }
    }


    private void createProjectWithAttachments(List<File> file ,Project project)  {

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

    private void postProjectValidation() {

        if(projectType.isEmpty() || (category == null) || paymentType.isEmpty() || title.isEmpty() || budget.isEmpty()){

            wrongInfoDialog("Missing Information");
        }
        else{

            double budgetValue = Double.parseDouble(budget);



            Project project = new Project(title, description, budgetValue,projectType,paymentType,expiryLocalDateTime ,deadlineLocalDateTime,  "0");

            Dialog(project.toString(), project);

        }

    }

    private void Dialog(final String msg, final Project project) {

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getContext());
        // Setting Dialog Message
        alertDialog.setTitle("Project details Review");
        alertDialog.setMessage(msg);

        //Setting positive "ok" Button
        alertDialog.setPositiveButton("POST", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {


                excutePostProjectRequest(project);

                dialog.dismiss();


            }//end onClick
        });//end setPositiveButton


        alertDialog.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();

            }
        });
        alertDialog.show();

    }//End of Dialog()

    private void excutePostProjectRequest(Project project) {

        //POST the data
        ApiClients.getAPIs().getPostProjectRequest(project).enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                // hideProgressDialog();

                Log.i(LOG, "onResponse : Success");

                if (response.isSuccessful()) {


                    Log.i(LOG, "onResponse : " + response.body().toString());

                    //Todo: intent


                    SuccesDialog("Your project has been posted successfully.");

                } else {
                    Converter<ResponseBody, ApiException> converter = ApiClients.getInstant().responseBodyConverter(ApiException.class, new Annotation[0]);
                    ApiException exception = null;
                    try {

                        exception = converter.convert(response.errorBody());

                        List<ApiError> errors = exception.getErrors();

                        if (errors != null)
                            if (!errors.isEmpty())
                                wrongInfoDialog(errors.get(0).getMessage());
                        wrongInfoDialog(exception.getMessage());

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

            }//End of onResponse()

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                Log.i(LOG, t.getLocalizedMessage());
                wrongInfoDialog("Error");

//                hideProgressDialog();
//                showDialog(getString(R.string.errorMessage));

            }//End of onFailure()
        });//End of Callback


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


    private void SuccesDialog(String msg) {

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getContext());
        alertDialog.setTitle("Successful");
        // Setting Dialog Message
        alertDialog.setMessage(msg);

        //Setting Negative "ok" Button
        alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                //finish();

            }//end onClick
        });//end setPositiveButton

        alertDialog.show();

    }//End of Dialog()


    @Override
    public void onBackPaymentClick() {

        viewPager.setCurrentItem(1);

    }

    @Override
    public void onBacCategorySelected() {

        viewPager.setCurrentItem(0);

    }

    @Override
    public void onBackInfoClick() {

        viewPager.setCurrentItem(2);


    }


}
