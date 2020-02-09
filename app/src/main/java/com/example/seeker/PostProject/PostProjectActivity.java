package com.example.seeker.PostProject;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.seeker.Database.ApiClients;
import com.example.seeker.Model.Exception.ApiError;
import com.example.seeker.Model.Exception.ApiException;
import com.example.seeker.Model.Project;
import com.example.seeker.Model.Responses.ApiResponse;
import com.example.seeker.R;
import com.google.android.material.tabs.TabLayout;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Converter;
import retrofit2.Response;


public class PostProjectActivity extends AppCompatActivity implements ProjectTypeFragment.ProjectTypeListener,ProjectCategoryFragment.CategoryListener, PaymentTypeFragment.PaymentListener,ProjectInformationFragment.ProjectInformationListener{

    private FragmentAdapter adapter;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ProjectTypeFragment projectTypeFragment;
    private ProjectCategoryFragment projectCategoryFragment;
    private PaymentTypeFragment paymentTypeFragment;
    private ProjectInformationFragment projectInformationFragment;
    private static final String LOG = PostProjectActivity.class.getSimpleName();



    private String projectType,projectCategory, paymentType, title,description, budget, deadline;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_project);

        projectTypeFragment = new ProjectTypeFragment();
        projectCategoryFragment= new ProjectCategoryFragment();
        paymentTypeFragment = new PaymentTypeFragment();
        projectInformationFragment = new ProjectInformationFragment();


        projectTypeFragment.setListener(this);
        projectCategoryFragment.setListener(this);
        paymentTypeFragment.setListener(this);
        projectInformationFragment.setListener(this);


        adapter = new FragmentAdapter(getSupportFragmentManager());
        adapter.addFragment(projectTypeFragment);
        adapter.addFragment(projectCategoryFragment);
        adapter.addFragment(paymentTypeFragment);
        adapter.addFragment(projectInformationFragment);

        viewPager = (ViewPager) findViewById(R.id.viewPager);
        viewPager.beginFakeDrag();


        viewPager.setAdapter(adapter);

    }

    @Override
    public void onProjectTypeItemSelected(String projectType) {
        this.projectType = projectType;
        viewPager.setCurrentItem(1);
    }
    @Override
    public void onCategoryTypeItemSelected(String projectCategory) {
        this.projectCategory = projectCategory;
        viewPager.setCurrentItem(2);
    }
    @Override
    public void onPaymentTypeItemSelected(String paymentType) {
        this.paymentType = paymentType;
        viewPager.setCurrentItem(3);

    }

    @Override
    public void onPostProjectItemSelected(String title, String description, String budget, String deadline) {

        this.title = title;
        this.description = description;
        this.budget = budget;
        this.deadline = deadline;


        postProjectValidation();


    }

    private void postProjectValidation() {

        if(projectType.isEmpty() || projectCategory.isEmpty() || paymentType.isEmpty() || title.isEmpty() || budget.isEmpty() || deadline.isEmpty()){

            wrongInfoDialog("Missing Information");
        }
        else{

            double budgetValue = Double.parseDouble(budget);

            Project project = new Project(title, description, budgetValue,projectType,paymentType,null,null);

            Dialog(project.toString(), project);

        }

    }

    private void Dialog(final String msg, final Project project) {

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
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
                        // Dialog(exception.getMessage());

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
        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);

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

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
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


}

