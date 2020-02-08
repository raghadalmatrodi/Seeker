package com.example.seeker.PostProject;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.seeker.Model.Project;
import com.example.seeker.R;
import com.google.android.material.tabs.TabLayout;

public class PostProjectActivity extends AppCompatActivity implements ProjectTypeFragment.ProjectTypeListener,ProjectCategoryFragment.CategoryListener, PaymentTypeFragment.PaymentListener,ProjectInformationFragment.ProjectInformationListener{

    private FragmentAdapter adapter;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ProjectTypeFragment projectTypeFragment;
    private ProjectCategoryFragment projectCategoryFragment;
    private PaymentTypeFragment paymentTypeFragment;
    private ProjectInformationFragment projectInformationFragment;



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

            Dialog(project.toString());

        }

    }

    private void Dialog(String msg) {

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);

        // Setting Dialog Message
        alertDialog.setMessage(msg);

        //Setting Negative "ok" Button
        alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();


            }//end onClick
        });//end setPositiveButton

        alertDialog.show();

    }//End of Dialog()
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


}

