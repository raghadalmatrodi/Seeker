package com.example.seeker.Rating;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.seeker.R;

public class RatingQuestionsFragment extends Fragment implements RatingBar.OnRatingBarChangeListener {

    private View v;

    private String questionName;
    private TextView txtQuestion;

    private RatingBar ratingBar;
    int rateAnswer;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_rating_questions, container, false);

        getExtras();
        init();

        return v;
    }

    private void init() {
        txtQuestion = v.findViewById(R.id.rating_question_txt);
        ratingBar = v.findViewById(R.id.question_rate);
        ratingBar.setOnRatingBarChangeListener(RatingQuestionsFragment.this);

        if (questionName != null)
            txtQuestion.setText(questionName);
    }

    private void getExtras() {
        Bundle bundle = getArguments();
        if (bundle != null)
            questionName = bundle.getString("question");
    }



    @Override
    public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
        if (ratingBar.getRating() > 0)
            isQuestionRated(true);

    }


    public boolean isQuestionRated(boolean rated) {

        if (rated)
            return true;
        else
            return false;


    }//end isSelectSurvey()

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
