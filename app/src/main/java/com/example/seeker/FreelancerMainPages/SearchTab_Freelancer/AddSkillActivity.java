package com.example.seeker.FreelancerMainPages.SearchTab_Freelancer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.seeker.Database.ApiClients;
import com.example.seeker.EmployerMainPages.AccountRelatedActivities.EditProfileActivity;
import com.example.seeker.FreelancerMainPages.AddSkillsAdapter;
import com.example.seeker.FreelancerMainPages.FreelancerEditProfile;
import com.example.seeker.Model.Bid;
import com.example.seeker.Model.Exception.ApiError;
import com.example.seeker.Model.Exception.ApiException;
import com.example.seeker.Model.Freelancer;
import com.example.seeker.Model.Responses.ApiResponse;
import com.example.seeker.Model.Skill;
import com.example.seeker.Model.SkillRecyclerView;
import com.example.seeker.PostBid.PostBidActivity;
import com.example.seeker.R;
import com.example.seeker.SharedPref.Constants;
import com.example.seeker.SharedPref.MySharedPreference;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Converter;
import retrofit2.Response;

public class AddSkillActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private AddSkillsAdapter adapter;


    private ImageView backBtn;
    private Button saveBtn;

    private List<SkillRecyclerView> projectSkillList = new ArrayList<>();
    private List<SkillRecyclerView> skillArrayList = new ArrayList<>();
    private String LOG = AddSkillActivity.class.getName();
    private Set<Skill> skillsList = new HashSet<>();


    private Set<Skill> skillList = new HashSet<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_skill);


        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.getSupportActionBar().setHomeButtonEnabled(true);
        this.getSupportActionBar().setTitle("Add skills");


        //init
//        backBtn = findViewById(R.id.add_skills_back);
        saveBtn = findViewById(R.id.save_skill);

//
//        //Back Button ToolBar listener
//        backBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//
//                getFragmentManager().popBackStackImmediate();
//            }
//        });

        getSkills();

        //Next Button Listener
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


//                projectSkillList=  adapter.getProjectSkills();
//                if(projectSkillList.isEmpty()){
//                    wrongInfoDialog("You have to choose at least one skill");
//                }else {
//
//
//                    skillsListener.onNextSelected(projectSkillList);
//                }
//            executeFindFreelancerByUserIdRequest(MySharedPreference.getLong(AddSkillActivity.this, Constants.Keys.USER_ID, -1));
                executeAddSkillsRequest();


            }
        });



//        setRecyclerView(null);


    }



    public void setRecyclerView(List<SkillRecyclerView> skillRecyclerViews){

//        skillList =  getSkills();
//
//
//        for(Skill s : skillList){
//            SkillRecyclerView skill = new SkillRecyclerView(s.getId(),s.getName(), false);
//            skillArrayList.add(skill);
//        }
//
//        for(int i = 0; i< skillList.size(); i++){
//            skillList.
//            SkillRecyclerView skill = new SkillRecyclerView( ,s.getName(), false);
//            skillArrayList.add(skill);
//        }

//        if( skillRecyclerViews != null){
//
//            if(!skillRecyclerViews.isEmpty()) {
//
//                for (SkillRecyclerView s : skillArrayList) {
//
//                    for (SkillRecyclerView r : skillRecyclerViews) {
//
//                        if (s.getId() == r.getId()) {
//                            s.setSelected(true);
//
//                            break;
//                        }
//                    }
//                }
//
//
//            }
//        }



        //RecyclerView Code
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        adapter = new AddSkillsAdapter(skillArrayList,skillRecyclerViews);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
        recyclerView.setNestedScrollingEnabled(true);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);

    }

    Set<Skill> freelancerSkills = null;

    private void getSkills(){

        final Set<Skill> allSkills = null;

        ApiClients.getAPIs().getAllSkills().enqueue(new Callback<Set<Skill>>() {
            @Override
            public void onResponse(Call<Set<Skill>> call, Response<Set<Skill>> response) {
                if (response.isSuccessful()){
//                    Toast.makeText(getApplicationContext(), "success", Toast.LENGTH_LONG).show();
//                    allSkills.addAll(response.body());



                    skillList =  response.body();
//                    freelancerSkills = skillList;

                    for(Skill s : skillList){
                        SkillRecyclerView skill = new SkillRecyclerView(s.getId(),s.getName(), false);
                        skillArrayList.add(skill);

                    }
//
//                    for(int i = 0; i< skillList.size(); i++){
//                        skillList.
//                                SkillRecyclerView skill = new SkillRecyclerView( ,s.getName(), false);
//                        skillArrayList.add(skill);
//                    }



                    recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
                    recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                    adapter = new AddSkillsAdapter(skillArrayList,null);
                    recyclerView.setItemAnimator(new DefaultItemAnimator());
                    recyclerView.setAdapter(adapter);
                    recyclerView.setNestedScrollingEnabled(true);
                    DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL);
                    recyclerView.addItemDecoration(dividerItemDecoration);

//                    allSkills = response.body();
                } else {
//                    Toast.makeText(getApplicationContext(), "not success", Toast.LENGTH_LONG).show();

                }
            }

            @Override
            public void onFailure(Call<Set<Skill>> call, Throwable t) {
//                Toast.makeText(getApplicationContext(), "failure", Toast.LENGTH_LONG).show();

            }
        });

//        return allSkills;
    }


    private void executeFindFreelancerByUserIdRequest(Long id){


        ApiClients.getAPIs().getFreelancerByUserIdRequest(id).enqueue(new Callback<Freelancer>() {
            @Override
            public void onResponse(Call<Freelancer> call, Response<Freelancer> response) {
                if (response.isSuccessful()){


                    Freelancer freelancer = response.body();

                    /**
                     * hind just added mar. 7, moved from post bid btn to here
                     */

                }else {
                    Log.i(LOG,"onResponse: notSuc" + response.toString());

                }//end else block
            }//End onResponse()

            @Override
            public void onFailure(Call<Freelancer> call, Throwable t) {
                Log.i(LOG,"onFailure :" + t.toString());


            }//end onFailure()
        });



    }//End executeFindFreelancerByUserIdRequest()

public void executeAddSkillsRequest(){

        List<Skill> skillsToSave = new ArrayList<>();
    projectSkillList = adapter.getProjectSkills();
    for(SkillRecyclerView s: projectSkillList){
        Skill skill = new Skill(s.getId(), s.getName());
        skillsList.add(skill);
    }

    Long freelancerId =  MySharedPreference.getLong(getApplicationContext(),Constants.Keys.FREELANCER_ID,-1);

    ApiClients.getAPIs().updateFreelancerSkill(freelancerId, skillsList).enqueue(new Callback<ApiResponse>() {
        @Override
        public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
            if(response.isSuccessful()) {

//                Log.i(LOG, "onResponse: suc" + response.toString());
                overridePendingTransition(0, 0);
                startActivity(new Intent(getApplicationContext(), EditProfileActivity.class));
                overridePendingTransition(0, 0);
                finish();

//                Intent i = new Intent(getApplicationContext(), FreelancerEditProfile.class);
//                startActivity(i);
            }else{
                Log.i(LOG,"onResponse: notSuc" + response.toString());

            }


        }

        @Override
        public void onFailure(Call<ApiResponse> call, Throwable t) {
            Log.i(LOG,"onFailure: notSuc" +t.getLocalizedMessage()+ t.getMessage() +t.toString());

        }
    });

}


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }



    @Override
    public void onBackPressed() {
        if (getFragmentManager().getBackStackEntryCount() == 0) {
            this.finish();
        } else {
            super.onBackPressed(); //replaced
        }
    }

}
