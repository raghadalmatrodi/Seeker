package com.example.seeker.Rating;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.app.Dialog;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.seeker.Database.ApiClients;
import com.example.seeker.Model.Employer;
import com.example.seeker.Model.EmployerRating;
import com.example.seeker.Model.Freelancer;
import com.example.seeker.Model.FreelancerRating;
import com.example.seeker.Model.Responses.ApiResponse;
import com.example.seeker.R;
import com.rd.PageIndicatorView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class EmployerRatesFreelancer extends AppCompatActivity {

    //what appears to the employer to RATE HIS FREELANCER
    //what appears to the freelancer to RATE HIS EMPLOYER
    TextView question1, question2, question3, question4, question5,
            totalRatingTv;

    RatingBar q1rb, q2rb, q3rb, q4rb, q5rb, tot;

    Button finishBtn;

    private int professionalismF , respectOfDeadlinesF, responseTimeF, budgetF, qualityF ;




    float frTotalRates;

    List<Integer> allEmpRatingVals;

    //Values will be returned in this order -> try as object.
    private int  num_of_ratings;
    private int  total_response_time;
    private int  total_quality_of_work;





    //TODO: GIVE REAL ID FOR EMP AND FR BASED ON THE TRIGGER
    long empId = 752, frId = 252;


    /**
     * SKIP THESE AS LONG AS U R USING ACTIVITY
     * these for the pager.
     */
    private SwipeDirection direction;
    private float initialXValue;

    private ViewPager questionViewPager;
    private ViewPagerAdapter mPagerAdapter;
    Button show;
    Button next;
    RatingBar rate;
    private ArrayList<Fragment> fragmentArrayList = new ArrayList<>();
    private List<String> rating_questions = new ArrayList<>();





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employer_rating);

      init();

      onRatingBarChanged();





//
//        professionalismE = q1Rating.getRating();
//        onTimePaymentE = q2Rating.getRating();
//        responseTimeE =  q3Rating.getRating();



        Freelancer freelancer = new Freelancer(frId);
        Employer employer = new Employer(empId);



        //freelancer
        getFreelancerRatingsValues(frId);
//        getRatingValues(empId);




        finishBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                totalRatingTv.setText("prof= "+professionalismF+" , respect= "+ respectOfDeadlinesF +" , budget= "+budgetF+" , res= "+responseTimeF+" , q= "+qualityF);
                RateTheFreelancer(qualityF, professionalismF, respectOfDeadlinesF, budgetF, responseTimeF);
//                RateTheEmp(professionalismE , onTimePaymentE , responseTimeE);


                NewFreelancerRating(new FreelancerRating(responseTimeF, professionalismF, respectOfDeadlinesF, qualityF, budgetF, freelancer, employer));
//                perfomRateEmployerRequest(new EmployerRating(responseTimeE, professionalismE, onTimePaymentE, freelancer, employer));
//                Toast.makeText(FreelancerRatesEmployer.this,"emp total = "+empTotalRates,Toast.LENGTH_LONG).show();

//                Toast.makeText(FreelancerRatesEmployer.this,"number of rating = "+num_of_ratings+"\n response time = "+response_time+"\n total otp = "+total_on_time_payment,Toast.LENGTH_LONG).show();


            }
        });


//        show = findViewById(R.id.show_d_btn);
//        show.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//
//                showRatingDialog(v);
//
//            }
//        });

    }//End onCreate()

    private void getFreelancerRatingsValues(long id) {
        ApiClients.getAPIs().findFreelancerById(id).enqueue(new Callback<Freelancer>() {
            @Override
            public void onResponse(Call<Freelancer> call, Response<Freelancer> response) {
                if (response.isSuccessful()){
                    num_of_ratings = response.body().getNum_of_ratings();
                    total_response_time = response.body().getTotal_response_time();
                    total_quality_of_work = response.body().getTotal_quality_of_work();

                }
            }

            @Override
            public void onFailure(Call<Freelancer> call, Throwable t) {

            }
        });



    }

    private void onRatingBarChanged() {

        q1rb.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                professionalismF = (int)ratingBar.getRating();
            }
        });

        q2rb.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                respectOfDeadlinesF = (int)ratingBar.getRating();
            }
        });


        q3rb.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                budgetF = (int)ratingBar.getRating();
            }
        });

        q4rb.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                responseTimeF = (int)ratingBar.getRating();
            }
        });

        q5rb.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                qualityF = (int)ratingBar.getRating();
            }
        });

    }//onRatingBarChanged()

    private void init() {
        question1 = findViewById(R.id.emp_rq_one);
        question2 = findViewById(R.id.emp_rq_two);
        question3 = findViewById(R.id.emp_rq_three);
        question4 = findViewById(R.id.emp_rq_four);
        question5 = findViewById(R.id.emp_rq_five);

        q1rb = findViewById(R.id.emp_rbq_one);
        q2rb = findViewById(R.id.emp_rbq_two);
        q3rb = findViewById(R.id.emp_rbq_three);
        q4rb = findViewById(R.id.emp_rbq_four);
        q5rb = findViewById(R.id.emp_rbq_five);

        finishBtn = findViewById(R.id.on_freelancer_finish_rating_btn);
        totalRatingTv = findViewById(R.id.emp_all_ratings_total_tv);
        tot = findViewById(R.id.emp_total_rbq);



        setUpQs();
    }//End init()

    private void setUpQs() {
        question1.setText("How well do you rate the freelancer's professionalism?");
        question2.setText("How well do you rate the freelancer's respect of deadlines?");
        question3.setText("How well do you rate the freelancer's selected budget?");
        question4.setText("How well do you rate the freelancer's response time?");
        question5.setText("How well do you rate the freelancer's quality of work?");

    }//End setUpQs()
    /**
     *
     *
     * if ratingUser.equals(freelancer), then
     * RatingF= (qualityOfWorkF+ professionalismF+ coherenceAndRespectOfDeadlinesF+ selectedBudgetF + communicationSkillsF)/5
     * totalRatingF+= RatingF
     * numOfFreelancerRating= numOfFreelancerRating+1
     * avgRating= totalRatingF /numOfFreelancerRating
     * userRating = avgRating
     * return userRating
     *
     *
     */

    public void RateTheFreelancer(int qualityOfWork, int professionalismF, int respectOfDeadlines , int selectedBudget, int responseTimeF){
        float rateFR, totalRatingEmp =0, numOfEmpRating=0;
        float avg;
        //Q1 -> professionalism
        //Q2 -> respect of deadlines
        //Q3 -> budget
        //Q4 -> response time
        //Q5 -> quality of work

        rateFR = ((float)qualityOfWork + (float)professionalismF + (float)respectOfDeadlines + (float)selectedBudget + (float)responseTimeF) /5;
//        empTotalRates+= ratingEmp;
        num_of_ratings+= 1;
        total_response_time+= responseTimeF;
        total_quality_of_work+= qualityOfWork;

//        avg = empTotalRates/ ((float)num_of_ratings);

        totalRatingTv.setText("total rating"+ rateFR);
        tot.setRating(rateFR);

//        long id, int num_of_ratings, int response_time, int total_on_time_payment, float total_emp_ratings
        setRatingValues(new Freelancer(frId, num_of_ratings, total_quality_of_work, total_response_time));

        /**for statistics i need to:
         *  1- get total response time and increase it by new res time then set the total to te summation of them
         *  2- get on time payment and increase it by new otp  then set the total to te summation of them
         */

    }

    private void setRatingValues(Freelancer freelancer){
        ApiClients.getAPIs().setAllFreelancerRatingValues(freelancer).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful())
                    Toast.makeText(EmployerRatesFreelancer.this,"success",Toast.LENGTH_LONG).show();
                else
                    Toast.makeText(EmployerRatesFreelancer.this,"not success: "+response.errorBody().toString(),Toast.LENGTH_LONG).show();

            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(EmployerRatesFreelancer.this,"set values failure: "+t.getLocalizedMessage(),Toast.LENGTH_LONG).show();

            }
        });
    }


    private void NewFreelancerRating(FreelancerRating freelancerRating){

        ApiClients.getAPIs().getRateFreelancerRequest(freelancerRating).enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                if (response.isSuccessful())
                    Toast.makeText(EmployerRatesFreelancer.this, "Successful", Toast.LENGTH_LONG).show();
                else
                    Toast.makeText(EmployerRatesFreelancer.this, "Not Successful: "+ response.errorBody().toString(), Toast.LENGTH_LONG).show();

            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                Toast.makeText(EmployerRatesFreelancer.this, t.getLocalizedMessage(), Toast.LENGTH_LONG).show();

            }
        });


    }









    /*FOR DIALOG*/

    float x;
    public void showRatingDialog(View v){
        Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.pager_layout);
        dialog.setCanceledOnTouchOutside(false);

        List<PagerModel> pagerArr = new ArrayList<>();
        pagerArr.add(new PagerModel("1","RATE PROFESSIONALISM"));
        pagerArr.add(new PagerModel("2", "RATE RESPECT OF DEADLINES"));
        pagerArr.add(new PagerModel("3", "SELECTED BUDGET"));
        pagerArr.add(new PagerModel("4", "RESPONSE TIME"));
        pagerArr.add(new PagerModel("5", "QUALITY OF WORK"));

        rate = dialog.findViewById(R.id.rb);
        MyPagerAdapter adapter = new MyPagerAdapter(this, pagerArr);


        ViewPager pager = dialog.findViewById(R.id.pager);

        pager.setAdapter(adapter);




        PageIndicatorView pageIndicatorView = dialog.findViewById(R.id.page_indicator2);
        pageIndicatorView.setCount(pagerArr.size()); // specify total count of indicators
//        pageIndicatorView.setSelection(v.);
        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {/*empty*/}

            @Override
            public void onPageSelected(int position) {
//                if(currentPosition < position)
//               if (pager.getCurrentItem() < position)

                pageIndicatorView.setSelection(position);
                Toast.makeText(EmployerRatesFreelancer.this,"CHANGED "+ position,Toast.LENGTH_LONG).show();

//                switch (position){
//
//                    case 0:
//                        float ratingAtZero;
//                        if (pagerArr.get(position).getRatingBar().getRating() > 0)
//
//                            ratingAtZero = pagerArr.get(position).getRatingBar().getRating();
//                        else ratingAtZero = 0;
//                        Toast.makeText(EmployerRatesFreelancer.this,"rater "+ ratingAtZero,Toast.LENGTH_LONG).show();
//                        break;
//
//                    case 1:
//                        float ratingAtOne;
//                        if (pagerArr.get(position).getRatingBar().getRating() > 0)
//                            ratingAtOne = pagerArr.get(position).getRatingBar().getRating();
//                        else ratingAtOne = 0;
//                        Toast.makeText(EmployerRatesFreelancer.this,"rater "+ ratingAtOne,Toast.LENGTH_LONG).show();
//                        break;
//                    case 3:
//                        Toast.makeText(EmployerRatesFreelancer.this,"rater "+ adapter.getR(pagerArr.get(position).getRatingBar()),Toast.LENGTH_LONG).show();
//
//                        default:
//                            break;
//
//
//
//                }

                //DIDN'T WORK -> CAUSED EXCEPTION
//                if (position > 0 ){
//                    pager.removeViewAt(position-1);
//                }

//                if (position > 0){
//                    pagerArr.remove(position-1);
//                    adapter.notifyDataSetChanged();
//                }

//                for (int i = 0; i<pagerArr.size(); i++ ){
//                    if (i == position)
//                        x = pagerArr.get(i).getRatingBar().getRating();
//                    Toast.makeText(EmployerRatesFreelancer.this,"rate "+ x,Toast.LENGTH_LONG).show();
//
//                }


            }

            @Override
            public void onPageScrollStateChanged(int state) {
//                Toast.makeText(EmployerRatesFreelancer.this,"CHANGED "+ state,Toast.LENGTH_LONG).show();
                /*empty*/}
        });


//        CirclePageIndicator pageIndicator = (CirclePageIndicator) dialog.findViewById(R.id.page_indicator);
////        CirclePageIndicator pageIndicator = (CirclePageIndicator) dialog.findViewById(R.id.page_indicator);
//        pageIndicator.setViewPager((ViewPager) pager);
//        pageIndicator.setCurrentItem(0);

        dialog.show();

//        questionViewPager = dialog.findViewById(R.id.fr_vp);
//        mPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), fragmentArrayList);
//        questionViewPager.setAdapter(mPagerAdapter);
//
//        next = dialog.findViewById(R.id.next_rq_btn);
//        next.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                moveNext();
//            }
//        });
//
//
//        dialog.show();
    }

    private void moveNext() {
//        if (questionViewPager.getCurrentItem() == (fragmentArrayList.size() - 1)) {
//            //remove test if saved
//            removeLastTest();
//
//            finish();
//        }//end if
//        //check if select answer
//        else
            if (((RatingQuestionsFragment) mPagerAdapter.getItem(questionViewPager.getCurrentItem())).isQuestionRated(true)) {
            questionViewPager.setCurrentItem(questionViewPager.getCurrentItem() + 1);
//            txtPage.setText(String.valueOf(questionViewPager.getCurrentItem() + 1));

//            //check if last question
//            if (questionViewPager.getCurrentItem() == (fragmentArrayList.size() - 2)) {
//                btnNext.setText(R.string.finish);
//            }//end if
//
//            //check if Final frag
//            else if (questionViewPager.getCurrentItem() == (fragmentArrayList.size() - 1)) {
//
//                llPage.setVisibility(View.GONE);
//                btnNext.setText(R.string.done);
//                txtContinue.setVisibility(View.GONE);
//            }//end inner else if

        }//End outer else if
    }//End MoveNext

    private boolean IsSwipeAllowed(MotionEvent event) {
        if(this.direction == SwipeDirection.all) return true;

        if(direction == SwipeDirection.none )//disable any swipe
            return false;

        if(event.getAction()==MotionEvent.ACTION_DOWN) {
            initialXValue = event.getX();
            return true;
        }

        if(event.getAction()==MotionEvent.ACTION_MOVE) {
            try {
                float diffX = event.getX() - initialXValue;
                if (diffX > 0 && direction == SwipeDirection.right ) {
                    // swipe from left to right detected
                    return false;
                }else if (diffX < 0 && direction == SwipeDirection.left ) {
                    // swipe from right to left detected
                    return false;
                }
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }

        return true;
    }

    private void initQs() {
        rating_questions.add("RATE PROFESSIONALISM");
        rating_questions.add("RATE RESPECT OF DEADLINES");
        rating_questions.add("SELECTED BUDGET");
        rating_questions.add("RESPONSE TIME");
        rating_questions.add("QUALITY OF WORK");


        for (int i = 0; i < rating_questions.size(); i++) {
            //fill the Question frags
            Bundle bundle = new Bundle();
            bundle.putString("question", rating_questions.get(i));
            RatingQuestionsFragment fragment = new RatingQuestionsFragment();
            fragment.setArguments(bundle);
            fragmentArrayList.add(fragment);
        }//end for loop

    }//end initQs








}//End class
enum SwipeDirection {
    all, left, right, none;
}
