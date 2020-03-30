package com.example.seeker.Rating;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.seeker.Database.ApiClients;
import com.example.seeker.Model.Employer;
import com.example.seeker.Model.EmployerRating;
import com.example.seeker.Model.Freelancer;
import com.example.seeker.Model.Responses.ApiResponse;
import com.example.seeker.R;

import org.w3c.dom.Text;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FreelancerRatesEmployer extends AppCompatActivity {

    //what appears to the freelancer to RATE HIS EMPLOYER
    TextView question1, question2, question3,
            totalRatingTv;

    RatingBar q1Rating, q2Rating, q3Rating, tot;

    Button nextBtn;

    private int professionalismE , onTimePaymentE , responseTimeE ;




    float empTotalRates;

    List<Integer> allEmpRatingVals;
    //Values will be returned in this order
    private int  num_of_ratings;
    private int  response_time;
    private int  total_on_time_payment;

    //TODO: GIVE REAL ID FOR EMP AND FR BASED ON THE TRIGGER
    long empId = 752, frId = 252;
    /**
     * FR.26. The freelancer shall be able to rate the employer after full payment.
     * The freelancer shall be able to rate the employer’s professionalism.
     * The freelancer shall be able to rate the employers on time payment.
     * The freelancer shall be able to rate the employer’s response time (communication skills).
     * @param savedInstanceState
     */


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_freelancer_rating);

        init();


    }

    private void init() {
        question1 = findViewById(R.id.fr_rq_one);
        q1Rating = findViewById(R.id.fr_rbq_one);

        question2 = findViewById(R.id.fr_rq_two);
        q2Rating = findViewById(R.id.fr_rbq_two);

        question3 = findViewById(R.id.fr_rq_three);
        q3Rating = findViewById(R.id.fr_rbq_three);

        nextBtn = findViewById(R.id.next_rq_btn);
        totalRatingTv = findViewById(R.id.fr_all_ratings_total_tv);
        tot = findViewById(R.id.fr_total_rbq);


        setUpQs();
//        totalRatingTv.setText("Rate from q1 = " + q1 + " , from 2 = " + q2);

        q1Rating.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                professionalismE = (int)ratingBar.getRating();
            }
        });

        q2Rating.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                onTimePaymentE = (int)ratingBar.getRating();
            }
        });


        q3Rating.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
               responseTimeE = (int)ratingBar.getRating();
            }
        });


//
//        professionalismE = q1Rating.getRating();
//        onTimePaymentE = q2Rating.getRating();
//        responseTimeE =  q3Rating.getRating();


        Employer employer = new Employer(empId);
        Freelancer freelancer = new Freelancer(frId);

        getEmployerTotalRatingVal(empId);
        getRatingValues(empId);




        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                totalRatingTv.setText("prof= "+professionalismE+" , otp= "+ onTimePaymentE+" , res time= "+responseTimeE);
                RateTheEmp(professionalismE , onTimePaymentE , responseTimeE);


//                perfomRateEmployerRequest(new EmployerRating(responseTimeE, professionalismE, onTimePaymentE, freelancer, employer));
//                Toast.makeText(FreelancerRatesEmployer.this,"emp total = "+empTotalRates,Toast.LENGTH_LONG).show();

//                Toast.makeText(FreelancerRatesEmployer.this,"number of rating = "+num_of_ratings+"\n response time = "+response_time+"\n total otp = "+total_on_time_payment,Toast.LENGTH_LONG).show();


            }
        });

//        RateTheEmp(professionalismE, onTimePaymentE, responseTimeE);


    }


    private void setUpQs() {

        question1.setText(R.string.emp_professionalisim_rating_q);
        question2.setText(R.string.emp_otp_rating_q);
        question3.setText(R.string.emp_response_time_rating_q);

//        questionsTv.setText("How well do you rate the employer’s professionalism");
//        q1 = questionsRating.getRating();
//
//        nextBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                counter++;
//                questionsRating.setRating(0);
//                questionsTv.setText("How well do you rate the employer's on time payment");
//
//                q2 = questionsRating.getRating();
//
//
//            }
//        });

    }

    /**
     * if ratingUser.equals(employer), then
     * RatingE= (professionalismE + onTimePaymentE + communicationSkillsE)/3
     * totalRatingF+= RatingE
     * numOfEmployerRating= numOfEmployerRating+1
     * avgRating= totalRatingE /numOfEmployerRating
     * userRating= avgRating
     *
     * 1- get totalEmpRating, calc ratingE then summation with total then set the total
     * 2- get num of emp rating and increase it by 1 then set it ;
     *
     *
     * EMPLOYER ON DB:
     *
     * //for the algorithm
     *     private int  num_of_ratings;  //to be incremented with each emp rating
     *     private int  response_time;      // i guess its the total.
     *     private int  num_of_posted_Projects; //to be incremented each time the emp posts a project.
     *     private int  total_on_time_payment;
     *
     *     //ADD total emp rating. --> to be able to calc total user rating.
     *     private double total_emp_ratings;
     *
     *
     * //if ratingUser.equals(freelancer), then
     * RatingF= (qualityOfWorkF+ professionalismF+ coherenceAndRespectOfDeadlinesF+ selectedBudgetF + communicationSkillsF)/5
     * totalRatingF+= RatingF
     * numOfFreelancerRating= numOfFreelancerRating+1
     * avgRating= totalRatingF /numOfFreelancerRating
     * userRating = avgRating
     * return userRating
     *
     *
     *
     */

//    private float professionalismE, onTimePaymentE, communicationSkillsE;
//    private float ratingEmp =0, totalRatingEmp =0, numOfEmpRating=0;
//    private float avg=0;


    private void getEmployerTotalRatingVal(long id){
        ApiClients.getAPIs().getEmployerTotalRating(id).enqueue(new Callback<Float>() {
            @Override
            public void onResponse(Call<Float> call, Response<Float> response) {
                if (response.isSuccessful()) {
                    empTotalRates = response.body();
//                    Toast.makeText(FreelancerRatesEmployer.this,"success",Toast.LENGTH_LONG).show();

                } else {
//                    Toast.makeText(FreelancerRatesEmployer.this,"not success: "+response.errorBody().toString(),Toast.LENGTH_LONG).show();

                }
            }

            @Override
            public void onFailure(Call<Float> call, Throwable t) {
//                Toast.makeText(FreelancerRatesEmployer.this,"failure: "+t.getLocalizedMessage(),Toast.LENGTH_LONG).show();

            }
        });
    }

    private void getRatingValues(long id){

        //Values will be returned in this order
        //  private int  num_of_ratings;
        //  private int  response_time;
        //  private int  total_on_time_payment;

        ApiClients.getAPIs().getEmployerRatingValues(id).enqueue(new Callback<List<Integer>>() {
            @Override
            public void onResponse(Call<List<Integer>> call, Response<List<Integer>> response) {
                if (response.isSuccessful()) {
//                    allEmpRatingVals = response.body();
                    num_of_ratings =  response.body().get(0);
                    response_time = response.body().get(1);
                    total_on_time_payment = response.body().get(2);

//                    Toast.makeText(FreelancerRatesEmployer.this,"success",Toast.LENGTH_LONG).show();

                } else {
//                    Toast.makeText(FreelancerRatesEmployer.this,"not success: "+response.errorBody().toString(),Toast.LENGTH_LONG).show();

                }

            }

            @Override
            public void onFailure(Call<List<Integer>> call, Throwable t) {
//                Toast.makeText(FreelancerRatesEmployer.this,"failure: "+t.getLocalizedMessage(),Toast.LENGTH_LONG).show();

            }
        });
    }


    /**
     *    float empTotalRates;

     *     //Values will be returned in this order
     *     private int  num_of_ratings;
     *     private int  response_time;
     *     private int  total_on_time_payment;
     *
     */


    public void RateTheEmp(int professionalismE , int onTimePaymentE, int responseTimeE){
         float ratingEmp, totalRatingEmp =0, numOfEmpRating=0;
         float avg;
        //Q1 -> professionalism
        //Q2 -> on time payment
        //Q3 -> response time

        ratingEmp = ((float)professionalismE + (float)onTimePaymentE + (float)responseTimeE)/3;
        empTotalRates+= ratingEmp;
        num_of_ratings+= 1;

        response_time+= responseTimeE;
        total_on_time_payment+= onTimePaymentE;

        avg = empTotalRates/ ((float)num_of_ratings);

        totalRatingTv.setText("total rating"+ avg);
        tot.setRating(avg);

//        long id, int num_of_ratings, int response_time, int total_on_time_payment, float total_emp_ratings
        setRatingValues(new Employer(empId, num_of_ratings, response_time, total_on_time_payment, empTotalRates));

        /**for statistics i need to:
         *  1- get total response time and increase it by new res time then set the total to te summation of them
         *  2- get on time payment and increase it by new otp  then set the total to te summation of them
         */

    }




    private void setRatingValues(Employer employer){
        ApiClients.getAPIs().setAllEmployerRatingValues(employer).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful())
                    Toast.makeText(FreelancerRatesEmployer.this,"success",Toast.LENGTH_LONG).show();
                    else
                        Toast.makeText(FreelancerRatesEmployer.this,"not success: "+response.errorBody().toString(),Toast.LENGTH_LONG).show();

            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(FreelancerRatesEmployer.this,"set values failure: "+t.getLocalizedMessage(),Toast.LENGTH_LONG).show();

            }
        });
    }


    float avgRating, userRating;

//    float ratingE, totalRatingE, numOfEmployerRating;
//    float ratingF, totalRatingF, numOfFreelancerRating;

//    public float RateEmp(float professionalismE, float onTimePaymentE, float communicationSkillsE ){
//
//        ratingE = (professionalismE + onTimePaymentE + communicationSkillsE)/3;
//        totalRatingE+= ratingE;
//        numOfEmployerRating = numOfEmployerRating+1;
//        avgRating = totalRatingE/numOfEmployerRating;
//        userRating = avgRating;
//
//        return userRating;
//
//    }
//
//
//
//    public float RateFr(float qualityOfWorkF, float professionalismF, float coherenceAndRespectOfDeadlinesF, float selectedBudgetF, float communicationSkillsF){
//
//        ratingF= (qualityOfWorkF+ professionalismF+ coherenceAndRespectOfDeadlinesF+ selectedBudgetF + communicationSkillsF)/5;
//        totalRatingF+= ratingF;
//        numOfFreelancerRating= numOfFreelancerRating+1;
//        avgRating= totalRatingF /numOfFreelancerRating;
//        userRating = avgRating;
//        return userRating;
//
//    }
//
//    public float UserRating(String ratingUser, float professionalismE, float onTimePaymentE, float communicationSkillsE, float qualityOfWorkF, float professionalismF, float coherenceAndRespectOfDeadlinesF, float selectedBudgetF, float communicationSkillsF){
//
//        if (ratingUser.equals("employer")){
//            ratingE = (professionalismE + onTimePaymentE + communicationSkillsE)/3;
//            totalRatingE+= ratingE;
//            numOfEmployerRating = numOfEmployerRating+1;
//            avgRating = totalRatingE/numOfEmployerRating;
//            userRating = avgRating;
//
//
//
//        }
//        if (ratingUser.equals("freelancer")){
//            ratingF= (qualityOfWorkF+ professionalismF+ coherenceAndRespectOfDeadlinesF+ selectedBudgetF + communicationSkillsF)/5;
//            totalRatingF+= ratingF;
//            numOfFreelancerRating= numOfFreelancerRating+1;
//            avgRating= totalRatingF /numOfFreelancerRating;
//            userRating = avgRating;
//        }
//
//        return userRating;
//
//    }
//
//


    private void perfomRateEmployerRequest(EmployerRating employerRating){

        ApiClients.getAPIs().getRateEmployerRequest(employerRating).enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                if (response.isSuccessful())
                    Toast.makeText(FreelancerRatesEmployer.this, "Successful", Toast.LENGTH_LONG).show();
                else
                    Toast.makeText(FreelancerRatesEmployer.this, "Not Successful: "+ response.errorBody().toString(), Toast.LENGTH_LONG).show();

            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                Toast.makeText(FreelancerRatesEmployer.this, t.getLocalizedMessage(), Toast.LENGTH_LONG).show();

            }
        });

    }




}
