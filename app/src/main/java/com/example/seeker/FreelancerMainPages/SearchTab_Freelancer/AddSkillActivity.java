package com.example.seeker.FreelancerMainPages.SearchTab_Freelancer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.seeker.Database.ApiClients;
import com.example.seeker.FreelancerMainPages.AddSkillsAdapter;
import com.example.seeker.Model.Bid;
import com.example.seeker.Model.Exception.ApiError;
import com.example.seeker.Model.Exception.ApiException;
import com.example.seeker.Model.Freelancer;
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

//
//    private SkillsListener skillsListener;
//    private BackSkillListener backSkillListener;

    private ImageView backBtn;
    private Button saveBtn;

    private List<SkillRecyclerView> projectSkillList = new ArrayList<>();
    private List<SkillRecyclerView> skillArrayList = new ArrayList<>();

    private Set<Skill> skillsList = new HashSet<>();


    private Set<Skill> skillList = new HashSet<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_skill);

        //init
        backBtn = findViewById(R.id.add_skills_back);
        saveBtn = findViewById(R.id.save_skill);


        //Back Button ToolBar listener
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


//                backSkillListener.onBacSkillSelected();
            }
        });

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
            executeFindFreelancerByUserIdRequest(MySharedPreference.getLong(AddSkillActivity.this, Constants.Keys.USER_ID, -1));


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
                    Toast.makeText(getApplicationContext(), "success", Toast.LENGTH_LONG).show();
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
                    Toast.makeText(getApplicationContext(), "not success", Toast.LENGTH_LONG).show();

                }
            }

            @Override
            public void onFailure(Call<Set<Skill>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "failure", Toast.LENGTH_LONG).show();

            }
        });

//        return allSkills;
    }



    private void executeFindFreelancerByUserIdRequest(Long id){


//        if (MySharedPreference.getLong(PostBidActivity.this, Constants.Keys.USER_ID, -1) != -1)
//          long curren_user_id = MySharedPreference.getLong(PostBidActivity.this, Constants.Keys.USER_ID, -1);

        ApiClients.getAPIs().getFreelancerByUserIdRequest(id).enqueue(new Callback<Freelancer>() {
            @Override
            public void onResponse(Call<Freelancer> call, Response<Freelancer> response) {
                if (response.isSuccessful()){


                    Freelancer freelancer = response.body();
//                    freelancer.setMaarof_account("maroofAcc");
//                    Toast.makeText(PostBidActivity.this, "fr name = "+freelancer.getUser().getUsername(), Toast.LENGTH_LONG ).show();

                    /**
                     * hind just added mar. 7, moved from post bid btn to here
                     */


                    projectSkillList = adapter.getProjectSkills();
                    for(SkillRecyclerView s: projectSkillList){
                        Skill skill = new Skill(s.getId(), s.getName());
                        skillsList.add(skill);
                        freelancer.getSkills().add(skill);
                    }

//                    Set<Skill> foo = new HashSet<Skill>(adapter.getProjectSkills());
//                    Freelancer fr = new Freelancer(freelancer, skillsList);
//                    freelancer.getSkills().addAll(skillList);
//                    freelancer.setSkills(skillsList);
//                    freelancer.ski

//                    Bid bid = new Bid(bidTitleStr,bidDescriptionStr,priceDouble,localDateTimet.toString() ,"pending", freelancer, project);

//                    String checkStr = bidTitleStr+" -- "+bidDescriptionStr+ " -- "+ priceStr + " -- "+" -- " + dateStr;
//                    Toast.makeText(getApplicationContext(),checkStr,Toast.LENGTH_LONG).show();


//                    freelancer.getUser().getUsername()
//                    freelancer = Freelancer(response.body().getId());
//                    freelancer.setId(response.body().getId());


                }else {
//                    Converter<ResponseBody, ApiException> converter = ApiClients.getInstant().responseBodyConverter(ApiException.class, new Annotation[0]);
//                    ApiException exception = null;
//                    try {
//
//                        exception = converter.convert(response.errorBody());
//
//                        List<ApiError> errors = exception.getErrors();
//
//                        if (errors != null)
//                            if (!errors.isEmpty())
////                                wrongInfoDialog(errors.get(0).getMessage());
////                        wrongInfoDialog(exception.getMessage());
//
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
                }//end else block
            }//End onResponse()

            @Override
            public void onFailure(Call<Freelancer> call, Throwable t) {
//                Log.i();
//                wrongInfoDialog("Error!");
            }//end onFailure()
        });



    }//End executeFindFreelancerByUserIdRequest()






}
