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
import android.widget.Toast;

import com.example.seeker.R;
import com.rd.PageIndicatorView;

import java.util.ArrayList;
import java.util.List;


public class EmployerRatesFreelancer extends AppCompatActivity {

    //what appears to the employer to RATE HIS FREELANCER

    private SwipeDirection direction;
    private float initialXValue;

    private ViewPager questionViewPager;
    private ViewPagerAdapter mPagerAdapter;
    Button show;
    Button next;
    RatingBar rate;

//    ViewPager pager;
    private ArrayList<Fragment> fragmentArrayList = new ArrayList<>();
    private List<String> rating_questions = new ArrayList<>();
//    private FinalFragment finalFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employer_rating);


        initQs();

        show = findViewById(R.id.show_d_btn);
        show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                showRatingDialog(v);

            }
        });

    }

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
