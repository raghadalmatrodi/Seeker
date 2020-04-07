package com.example.seeker.EmployerMainPages.AccountRelatedActivities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
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
import android.widget.TextView;
import android.widget.Toast;

import com.example.seeker.Activities.LoginActivity;
import com.example.seeker.Database.ApiClients;
import com.example.seeker.EmployerMainPages.MyProjectsTab_Emp.Emp_MyProjectsFragment;
import com.example.seeker.FreelancerMainPages.MyProjectsTab_Freelancer.ProjectsStatusFragments.FRProjectAdapter;
import com.example.seeker.FreelancerMainPages.MyProjectsTab_Freelancer.ProjectsStatusFragments.Freelancer_MyProjects_Pending_Fragment;
import com.example.seeker.Model.Bid;
import com.example.seeker.Model.Employer;
import com.example.seeker.Model.Project;
import com.example.seeker.Model.Responses.ApiResponse;
import com.example.seeker.R;
import com.example.seeker.SharedPref.Constants;
import com.example.seeker.SharedPref.MySharedPreference;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SettingActivity extends AppCompatActivity {

    private ImageView backBtn;
    private TextView deactivateBtn;
    private Employer employer;
    boolean checkBids;
    List<Project> projectList = new ArrayList<>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        long employer_id = MySharedPreference.getLong(this, Constants.Keys.EMPLOYER_ID, -1);
        employer = new Employer(employer_id);

        init();

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        deactivateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {





           checkProject();





            }
        });
    }

    private void init() {

        backBtn = findViewById(R.id.setting_back);
        deactivateBtn = findViewById(R.id.deactivate_account);
    }

    private boolean checkBids() {


        ApiClients.getAPIs().getProjectsByStatusOnly("1").enqueue(new Callback<List<Project>>() {
            @Override
            public void onResponse(Call<List<Project>> call, Response<List<Project>> response) {

                if (response.isSuccessful()) {

                    long currentFreelancer = MySharedPreference.getLong(SettingActivity.this, Constants.Keys.FREELANCER_ID, -1);

                    /**
                     * LET'S RESTART, FROM THE BEGINNING.
                     * ASSUMING ALL PROJECTS HAS LIST OF BIDS. -> unfortunately not, so check on that plz :), no need we got bids size
                     * 1ST: WE FOR LOOP IN ALL PROJECTS, K
                     * 2ND: WE FOR LOOP IN ALL BIDS IN PROJECT K, H
                     * 3RD: NOW I'M ON THE FIRST BID H OF THE FIRST PROJECT K, (ASSUMING THAT ALL BIDS CONTAINS FREELANCER OBJS) -> unfortunately not, so check on that too plz :)
                     * -> I'LL CHECK WHETHER PROJECT K . BID H . FREELANCER . FRID EQUALS CURRENT FREELANCER ID
                     * IF YES, I'LL ADD THIS PROJECT K TO MY PROJECTSLIST. IF NOT, MOVE TO BID H+1.
                     *
                     */

                    int responseSize = response.body().size();
                    int bidSize = 0;

                    //Projects loop
                    for (int k = 0; k < responseSize; k++) {

                        bidSize = response.body().get(k).getBids().size();

                        //Bids on project k loop
                        for (int h = 0; h < bidSize; h++) {

                            if (response.body().get(k).getBids().get(h).getFreelancer() != null)
                                if (response.body().get(k).getBids().get(h).getStatus().equals("accepted"))
                                    if (response.body().get(k).getBids().get(h).getFreelancer().getId() == currentFreelancer)
                                        projectList.add(response.body().get(k));

                        }//Bids loop
                    }//Projects loop.

                    if (!projectList.isEmpty()) {

                        wrongInfoDialog("You have to complete all your In-Progress Bids Project ");

                    } else {

                        finalCheck();
                    }


                }else{

                    wrongInfoDialog(response.message());
                }
            }

            @Override
            public void onFailure(Call<List<Project>> call, Throwable t) {

                wrongInfoDialog("Try again later");

            }
        });

        return checkBids;

    }

    private void finalCheck() {


        finalDialog();


    }

    private void checkProject() {


        ApiClients.getAPIs().getProjectByStatus("1", employer).enqueue(new Callback<List<Project>>() {
            @Override
            public void onResponse(Call<List<Project>> call, Response<List<Project>> response) {

                if (response.isSuccessful()) {

                    List myProject = response.body();
                    if (myProject != null) {

                        Log.d("ProjectSize",""+ myProject.size() );

                        if (myProject.isEmpty()) {

                            Log.d("Empty",""+ myProject.size() );
                                checkBids();

                        }else {
                            Log.d("NotEmpty",""+ myProject.size() );

                            wrongInfoDialog("You have to complete all your In-Progress Projects");

                        }
                    }

                }else{

                    wrongInfoDialog(response.message());
                }

            }

            @Override
            public void onFailure(Call<List<Project>> call, Throwable t) {

                wrongInfoDialog("Try again later");

            }
        });


    }

    private void finalDialog() {

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        // Setting Dialog Message
        alertDialog.setTitle("Are you sure you want to deactivate you account!");


        //Setting positive "ok" Button
        alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

               deleteAllProject();

               deleteAllBid();


               deleteUser();




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

    private void deleteUser() {

        long user_id = MySharedPreference.getLong(this, Constants.Keys.USER_ID, -1);

        ApiClients.getAPIs().deleteUserById(user_id).enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                logout();

            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {

            }
        });

    }

    private void deleteAllBid() {


        ApiClients.getAPIs().getProjectsByStatusOnly("0").enqueue(new Callback<List<Project>>() {
            @Override
            public void onResponse(Call<List<Project>> call, Response<List<Project>> response) {

                if(response.isSuccessful()){
                    /**
                     *     int responseSize = response.body().size();
                     *                     for (int i = 0; i< responseSize; i++){
                     *                         if (response.body().get(i).getFreelancer() != null)
                     *                         if (response.body().get(i).getFreelancer().getId() == 252){
                     *                             bidList.add(response.body().get(i));
                     *                         }
                     *                     }
                     */


                    long currFree = MySharedPreference.getLong(SettingActivity.this, Constants.Keys.FREELANCER_ID,-1);


                    /**
                     * LET'S RESTART, FROM THE BEGINNING.
                     * ASSUMING ALL PROJECTS HAS LIST OF BIDS.
                     * 1ST: WE FOR LOOP IN ALL PROJECTS, K
                     * 2ND: WE FOR LOOP IN ALL BIDS IN PROJECT K, H
                     * 3RD: NOW I'M ON THE FIRST BID H OF THE FIRST PROJECT K, (ASSUMING THAT ALL BIDS CONTAINS FREELANCER OBJS) -> unfortunately not, so check on that plz :)
                     * -> I'LL CHECK WHETHER PROJECT K . BID H . FREELANCER . FRID EQUALS CURRENT FREELANCER ID
                     * IF YES, I'LL ADD THIS PROJECT K TO MY PROJECTSLIST. IF NOT, MOVE TO BID H+1.
                     *
                     */

                    int rSize = response.body().size();
                    int bidSize = 0;

                    //Projects loop
                    for (int k = 0; k < rSize; k++){
                        bidSize = response.body().get(k).getBids().size();

                        //Bids on project k loop
                        for (int h = 0; h < bidSize; h++ ){

                            if (response.body().get(k).getBids().get(h).getFreelancer() != null)
                                if (response.body().get(k).getBids().get(h).getFreelancer().getId() == currFree)
                                    deleteBid(response.body().get(k).getBids().get(h).getId());

                        }//Bids loop
                    }//Projects loop.


                }else{

                }
            }

            @Override
            public void onFailure(Call<List<Project>> call, Throwable t) {

            }
        });


    }

    private void deleteAllProject() {

        ApiClients.getAPIs().getProjectByStatus("0", employer).enqueue(new Callback<List<Project>>() {
            @Override
            public void onResponse(Call<List<Project>> call, Response<List<Project>> response) {

                if(response.isSuccessful()){

                    List<Project> deletedProject = response.body();

                    if(!deletedProject.isEmpty()){

                        for(int i=0; i<deletedProject.size(); i++){

                            deleteProject(deletedProject.get(i));
                        }
                    }
                }

            }

            @Override
            public void onFailure(Call<List<Project>> call, Throwable t) {

            }
        });
    }

    private void deleteProject(Project project) {
        ApiClients.getAPIs().deleteProject(project.getId()).enqueue(new Callback<ApiResponse>() {
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

    }
    private void wrongInfoDialog(String msg) {
        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);

        alertDialog.setTitle("Warning");

        // Setting Dialog Message
        alertDialog.setMessage(msg);

        // Setting Icon to Dialog

        //Setting Negative "ok" Button
        alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }//end onClick
        });//end setPositiveButton

        alertDialog.show();

    }//End wrongInfoDialog()

    private void deleteBid(long id) {


            ApiClients.getAPIs().deleteBid(id).enqueue(new Callback<ApiResponse>() {
                @Override
                public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                    if (response.isSuccessful()) {
                    }
                }

                @Override
                public void onFailure(Call<ApiResponse> call, Throwable t) {

                }
            });



    }

    //TODO: REEMA LOGOUT
    public void logout() {

        clearData();

         Intent intent = new Intent(this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);

    }//End of logout()

    public void clearData() {

        MySharedPreference.clearData(this);

    }//End of clearData()



}
