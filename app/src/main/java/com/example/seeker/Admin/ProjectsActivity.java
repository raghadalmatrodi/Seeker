package com.example.seeker.Admin;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.example.seeker.Database.ApiClients;
import com.example.seeker.EmployerMainPages.MyProjectsTab_Emp.Emp_viewProjectFragment;
import com.example.seeker.Model.Bid;
import com.example.seeker.Model.Project;
import com.example.seeker.Model.Responses.ApiResponse;
import com.example.seeker.PostBid.ViewFullBid;
import com.example.seeker.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProjectsActivity extends AppCompatActivity implements ProjectsAdapter.ProjectsAdapterListener{

    RecyclerView recyclerViewProjects;
    SearchView searchViewProjects;
    private List<Project> projectList;
    ImageView backBtnProjects;
    ProjectsAdapter projectsAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_projects);

        searchViewProjects=findViewById(R.id.search_projects);
        backBtnProjects = findViewById(R.id.projects_back);


        backBtnProjects.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });




        searchViewProjects.setOnQueryTextListener(new SearchView.OnQueryTextListener() {


            @Override
            public boolean onQueryTextSubmit(String queryString) {


                projectsAdapter.getFilter().filter(queryString);

                return false;
            }

            @Override
            public boolean onQueryTextChange(String queryString) {

                projectsAdapter.getFilter().filter(queryString);


                return false;
            }
        });

    }


    @Override
    protected void onResume() {
        super.onResume();

        getAllProjects();
    }

    private void getAllProjects(){

        ApiClients.getAPIs().getAllProjects().enqueue(new Callback<List<Project>>() {
            @Override
            public void onResponse(Call<List<Project>> call, Response<List<Project>> response) {
                if(response.isSuccessful()){

                    projectList = (List) response.body();
                    setProjectsRecyclerView();

                }
                else{

                    Log.i("ProjectsActivity", "onResponse not suc: " + response.toString());
                }
            }

            @Override
            public void onFailure(Call<List<Project>> call, Throwable t) {

                Log.i("ProjectsActivity", "Fail");
            }
        });

    }


    private void setProjectsRecyclerView() {

        recyclerViewProjects = (RecyclerView) findViewById(R.id.recycler_view_projects);
        recyclerViewProjects.setLayoutManager(new LinearLayoutManager(this));
        projectsAdapter = new ProjectsAdapter(projectList,this);
        recyclerViewProjects.setItemAnimator(new DefaultItemAnimator());
        recyclerViewProjects.setAdapter(projectsAdapter);
        if (!projectList.isEmpty())
            projectsAdapter.setListener(this);
        recyclerViewProjects.setNestedScrollingEnabled(true);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerViewProjects.getContext(), DividerItemDecoration.VERTICAL);
        recyclerViewProjects.addItemDecoration(dividerItemDecoration);
    }

//--------------------------------------





    //delete
    private void executeDeleteProject(Project project) {

        showDialog("Are you sure you want to delete this Project?",  project);
    }


    public void showDialog(final String msg,  Project project) {
        final android.app.AlertDialog alertDialog = new android.app.AlertDialog.Builder(this).create();
        alertDialog.setMessage(msg);
        alertDialog.setIcon(R.mipmap.ic_launcher);

        alertDialog.setButton(android.app.AlertDialog.BUTTON_POSITIVE, "Yes",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        deleteProject(project);
                        infoDialog("Successful", "The Project has been deleted successfully.");



                    }//End onClick()


                });//End BUTTON_POSITIVE


        alertDialog.setButton(android.app.AlertDialog.BUTTON_NEGATIVE, "No",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        alertDialog.dismiss();

                    }//End onClick()
                });//End BUTTON_POSITIVE


        alertDialog.show();

    }//end showDialog

    private void deleteProject(Project project) {

        ApiClients.getAPIs().deleteProjectAdmin(project.getId()).enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                if (response.isSuccessful()) {
                }
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {

            }
        });
//to refresh the list
        // getFragmentManager().beginTransaction().detach(this).attach(this).commit();



    }
    private void infoDialog(String title, String msg) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        // Setting Dialog Title
        alertDialog.setTitle(title);

        // Setting Dialog Message
        alertDialog.setMessage(msg);

        alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

                dialog.dismiss();
                Intent intent = new Intent(getApplicationContext(), ProjectsActivity.class);
                startActivity(intent);
                finish();

            }//end onClick
        });//end setPositiveButton

        alertDialog.show();

    }//end wrongInfoDialog()


    @Override
    public void onProjectItemClick(Project project) {
    //todo

        Fragment fragment = new Emp_viewProjectFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("project", project);
        fragment.setArguments(bundle);

        FragmentManager fragmentManager = this.getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.main_content, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    @Override
    public void onDeleteProjectItemClick(Project project) {
        executeDeleteProject(project);

    }
}
