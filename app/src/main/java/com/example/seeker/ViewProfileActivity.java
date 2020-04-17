package com.example.seeker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.seeker.Database.ApiClients;
import com.example.seeker.Model.Employer;
import com.example.seeker.Model.Freelancer;
import com.example.seeker.Model.Skill;
import com.example.seeker.Model.User;

import java.util.HashSet;
import java.util.Set;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ViewProfileActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private static final String LOG = ViewProfileActivity.class.getSimpleName();


    private TextView userLinkedin, userTwitter, userFacebook;
    private ImageView userImg, phoneNumber, nationalId, maroofImg;

    private String
            baseLinkedin = "www.linkedin.com/in/",
            baseTwiter = "www.twitter.com/",
    //TODO: CHECK FACEBOOK URL
            baseFacebook = "www.facebook.com/";

    private TextView username, nameAsFreelancer, nameAsEmployer;
    private TextView educationTV;

    private TextView totalTrustPoints_TV;
    double empRatings, frRatings;

    private RatingBar userTotalRating;
    private TextView userNumberOfRatings;

    private TextView FrWorkedOnProjects, FrAvgResponseTime, FrAvgQualityOfWork,
            EmpNumOfProjects, EmpAvgResponseTime, EmpAvgOTP;

    private ProgressBar trustPointsPB;
    private ProgressBar empAvgResponseTimePB, empOnTimePaymentPB, frAvgResponseTimePB, frQualityPB;

    private TextView skillText;
    private Set<Skill> skillsList = new HashSet<>();

    User user;
    Freelancer freelancer;
    Employer employer;


    //TODO: CAPITALIZE NAME!

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_profile);

        init();

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            user = (User) bundle.getSerializable("myuser");

        }
//        if (user != null){
//
//        }
//        executeFindFreelancerByUserIdRequest(Long.valueOf(user.getId()));

        if (user != null) {
            initToolbar(user.getName());
            c();
            getEmpByUserId();

            setNames();

            if (user.getNational_id() != null)
                nationalId.setVisibility(View.VISIBLE);

            if (user.getPhone_number() != null)
                phoneNumber.setVisibility(View.VISIBLE);

            CalculateFrTP(Long.valueOf(user.getId()));

            if (user.getLinkedIn() != null)
            userLinkedin.setText(baseLinkedin+user.getLinkedIn());
                //Didn't work :)
//            userLinkedin.setMovementMethod(LinkMovementMethod.getInstance());
            else
                userLinkedin.setText("NA");

            if (user.getTwitter() != null)
                userTwitter.setText(baseTwiter+user.getTwitter());
            else
                userTwitter.setText("NA");

            //TODO: CHECK THE LINK
            if (user.getFacebook() != null)
                userFacebook.setText(baseFacebook+user.getFacebook());
            else
                userFacebook.setText("NA");

            if (user.getEducation() != null)
                educationTV.setText(user.getEducation());
            else
                educationTV.setText("No education added yet.");

            compareRatings(Long.valueOf(user.getId()));


        }//end if

    }//End onCreate()

    private void c() {
        //مايشوف الفريلانسر الا جوا :)
        ApiClients.getAPIs().getFreelancerByUserIdRequest(Long.valueOf(user.getId())).enqueue(new Callback<Freelancer>() {
            @Override
            public void onResponse(Call<Freelancer> call, Response<Freelancer> response) {
                if (response.isSuccessful()){
                    freelancer = response.body();
                    if (freelancer.getMaarof_account() != null)
                        maroofImg.setVisibility(View.VISIBLE);

//                    calculateFreelancerTotalRates(new Freelancer(freelancer.getId()));
                    //didn't work -> try doing it in backend.
//                        userTotalRating.setRating(Float.valueOf(String.valueOf(frRatings)));

                    //WORKED but we have to get total of freelancer + employer, so i'll keep it commented
//                        userNumberOfRatings.setText("("+freelancer.getNum_of_ratings()+")");

                    FrWorkedOnProjects.setText(response.body().getNum_of_hired_projects()+"");
                    double response_time = response.body().getTotal_response_time();
                    double num_of_ratings = response.body().getNum_of_ratings();
                    double avgRT =( ((response_time/num_of_ratings) /5) *100);

                    double quality = response.body().getTotal_quality_of_work();
                    double avgQ =( (quality/num_of_ratings)/5 )*100;

                    FrAvgResponseTime.setText((int)avgRT+"%");
                    frAvgResponseTimePB.setProgress((int)avgRT);
                    FrAvgQualityOfWork.setText((int)avgQ+"%");
                    frQualityPB.setProgress((int)avgQ);

                    Log.i(LOG,"onResponse: suc" + freelancer.toString());
                    skillsList = freelancer.getSkills();
                    Log.i(LOG,"onResponse: suc" + freelancer.getSkills());
                    Log.i(LOG,"onResponse: suc" + skillsList.toString());

                    skillText = findViewById(R.id.list_skills);
                    String skillToDisplay ="";

                    for (Skill subset : skillsList) {
                        skillToDisplay += subset.getName() + "\n";
                    }

                    skillText.setText(skillToDisplay );



                }else {
                    Log.i(LOG,"onResponse: notSuc" + response.toString());

                }//end else block
            }//End onResponse()

            @Override
            public void onFailure(Call<Freelancer> call, Throwable t) {
                Log.i(LOG,"onFailure :" + t.toString());


            }//end onFailure()
        });

    }

    private void getEmpByUserId(){
        ApiClients.getAPIs().getEmployerByUserIdRequest(Long.valueOf(user.getId())).enqueue(new Callback<Employer>() {
            @Override
            public void onResponse(Call<Employer> call, Response<Employer> response) {
                if (response.isSuccessful()){

                    //didn't work
                    employer = response.body();

                    //Filling As employer statistics
                    EmpNumOfProjects.setText(response.body().getNum_of_posted_Projects()+"");

                    double response_time = response.body().getResponse_time();
                    double num_of_ratings = response.body().getNum_of_ratings();
                    double avgRT =( ((response_time/num_of_ratings) /5) *100);
                    double total_otp = response.body().getTotal_on_time_payment();
                    double avgOTP = ((total_otp/num_of_ratings)/5) *100;
                    EmpAvgResponseTime.setText((int)avgRT+"%");
                    empAvgResponseTimePB.setProgress((int)avgRT);
                    EmpAvgOTP.setText((int)avgOTP+"%");
                    empOnTimePaymentPB.setProgress((int)avgOTP);


                }
            }

            @Override
            public void onFailure(Call<Employer> call, Throwable t) {

            }
        });
    }

    private void init() {

        //INIT IS AFTER DECLARING THE USER SO DON'T DO ANYTHING ELSE IN HERE! ONLY INITIATING VARS PLEASE!!!
        toolbar = findViewById(R.id.view_profile_toolbar);

        username = findViewById(R.id.view_profile_name);
        nameAsFreelancer = findViewById(R.id.view_profile_name_as_fr);
        nameAsEmployer = findViewById(R.id.view_profile_name_as_emp);
        userImg = findViewById(R.id.view_profile_pic);
        nationalId = findViewById(R.id.view_nid_icon);
        phoneNumber = findViewById(R.id.view_phone_num);
        maroofImg = findViewById(R.id.view_maroof_icon);

        nationalId.setVisibility(View.INVISIBLE);
        phoneNumber.setVisibility(View.INVISIBLE);
        maroofImg.setVisibility(View.INVISIBLE);

        totalTrustPoints_TV = findViewById(R.id.view_total_trust_points);
        trustPointsPB = findViewById(R.id.view_tp_progressBar);

        userTotalRating = findViewById(R.id.view_profile_total_ratings);
        userNumberOfRatings = findViewById(R.id.view_profile_numOfRatings);

        FrWorkedOnProjects = findViewById(R.id.view_AsFreelancer_numOfWorkedOnProjects);
        FrAvgResponseTime = findViewById(R.id.view_AsFreelancer_AvgResponseTime);
        frAvgResponseTimePB = findViewById(R.id.view_fr_avg_RT_pb);
        FrAvgQualityOfWork = findViewById(R.id.view_AsFreelancer_avgQuality);
        frQualityPB = findViewById(R.id.view_fr_avg_quality_pb);

        EmpNumOfProjects = findViewById(R.id.view_AsEmployer_NumOfProjects);
        EmpAvgResponseTime = findViewById(R.id.view_AsEmployer_AvgResponseTime);
        empAvgResponseTimePB = findViewById(R.id.view_emp_avg_rt_pb);
        EmpAvgOTP = findViewById(R.id.view_AsEmployer_AvgOtp);
        empOnTimePaymentPB = findViewById(R.id.view_emp_otp_pb);

        userLinkedin = findViewById(R.id.view_linkedin_link);
        userTwitter = findViewById(R.id.view_twitter_link);
        userFacebook = findViewById(R.id.view_fb_link);
        educationTV = findViewById(R.id.view_education);

        skillText = findViewById(R.id.list_skills);




    }//End init()

    private void setNames() {

        username.setText(user.getName());
        nameAsFreelancer.setText(user.getName()+" "+getString(R.string.as_freelancer));
        nameAsEmployer.setText(user.getName()+" "+getString(R.string.as_employer));

    }

    private void initToolbar(String username) {

        //init toolbar
        toolbar.setTitle(username);
        toolbar.setNavigationIcon(R.drawable.back_arrow_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }//End onClick()
        });

    }// End initToolbar()


    private void executeFindFreelancerByUserIdRequest(Long id){

        ApiClients.getAPIs().getFreelancerByUserIdRequest(id).enqueue(new Callback<Freelancer>() {
            @Override
            public void onResponse(Call<Freelancer> call, Response<Freelancer> response) {
                if (response.isSuccessful()){
                    freelancer = response.body();

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

    private void CalculateFrTP(long user_id){
        ApiClients.getAPIs().CalculateFreelancerTrustPoints(user_id).enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                if (response.isSuccessful()){
                    totalTrustPoints_TV.setText(response.body()+"%");
                    trustPointsPB.setProgress(response.body());
                }
            }

            @Override
            public void onFailure(Call<Integer> call, Throwable t) {

            }
        });
    }


    private void executeCalculateEmployerTotalRating(Employer employer_id){
        ApiClients.getAPIs().CalculateEmployerTotalRating(employer_id).enqueue(new Callback<Double>() {
            @Override
            public void onResponse(Call<Double> call, Response<Double> response) {
                if (response.isSuccessful()){
                    empRatings = response.body();
//                    empTotalRates.setRating(Float.valueOf(String.valueOf(response.body())));
//                    empTotalRates.setRating(response.body().floatValue());
                }

            }

            @Override
            public void onFailure(Call<Double> call, Throwable t) {

            }
        });
    }

    private void calculateFreelancerTotalRates(Freelancer freelancer_id) {
        ApiClients.getAPIs().CalculateFreelancerTotalRating(freelancer_id).enqueue(new Callback<Double>() {
            @Override
            public void onResponse(Call<Double> call, Response<Double> response) {
                if (response.isSuccessful()){

                    if (response.body() != null)
                        frRatings = response.body();
                    Log.d("val of frRat:", ""+frRatings);

                }

//                    empTotalRates.setRating(Float.valueOf(String.valueOf(response.body())));

            }

            @Override
            public void onFailure(Call<Double> call, Throwable t) {

            }
        });
    }


    private void compareRatings(long user_id){
        ApiClients.getAPIs().compareUserRatings(user_id).enqueue(new Callback<Double>() {
            @Override
            public void onResponse(Call<Double> call, Response<Double> response) {
                if (response.isSuccessful())
                    userTotalRating.setRating(Float.valueOf(String.valueOf(response.body())));
            }

            @Override
            public void onFailure(Call<Double> call, Throwable t) {

            }
        });
    }


}//End class()
