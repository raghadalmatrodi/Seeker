package com.example.seeker.Rating;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.icu.text.DecimalFormat;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.seeker.R;

public class test extends AppCompatActivity {

    TextView idk;
    Button show;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        idk =findViewById(R.id.tv);
        show = findViewById(R.id.show_d);
        show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                ShowDialog();

                ShowCustomDialog();
            }
        });
    }

    public void ShowDialog()
    {
        final AlertDialog.Builder popDialog = new AlertDialog.Builder(this);

        LinearLayout linearLayout = new LinearLayout(this);
        final RatingBar rating = new RatingBar(this);
        final RatingBar rating1 = new RatingBar(this);
        final RatingBar rating2 = new RatingBar(this);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );

        linearLayout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.setGravity(View.TEXT_ALIGNMENT_CENTER);
        rating.setLayoutParams(lp);
        rating.setNumStars(5);
        rating.setStepSize(1);

        rating1.setLayoutParams(lp);
        rating1.setNumStars(5);
        rating1.setStepSize(1);

        rating2.setLayoutParams(lp);
        rating2.setNumStars(5);
        rating2.setStepSize(1);

        //add ratingBar to linearLayout
        linearLayout.addView(rating);
        linearLayout.addView(rating1);
        linearLayout.addView(rating2);


        popDialog.setIcon(android.R.drawable.btn_star_big_on);
        popDialog.setTitle("Add Rating: ");

        //add linearLayout to dailog
        popDialog.setView(linearLayout);



        rating.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                System.out.println("Rated val:"+v);
            }
        });

        rating1.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                System.out.println("Rated val:"+v);
            }
        });

        rating2.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                System.out.println("Rated val:"+v);
            }
        });



        // Button OK
        popDialog.setPositiveButton(android.R.string.ok,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

//                        double i2=i/60000;
//                        idk.setText(new DecimalFormat("##.##").format(i2));

                        double avg = (rating.getProgress() + rating1.getProgress() + rating2.getProgress()) / 3.0;
                        idk.setText(new DecimalFormat("##.##").format(avg) + "avg ");

//                        idk.setText("avg = "+avg);
                        dialog.dismiss();
                    }

                })

                // Button Cancel
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

        popDialog.create();
        popDialog.show();

    }

    TextView q1, q2, q3;
    public void ShowCustomDialog() {

        Dialog rankDialog = new Dialog(test.this);
        rankDialog.setContentView(R.layout.dialog_test);
        rankDialog.setCancelable(false);
        final RatingBar ratingBar = (RatingBar)rankDialog.findViewById(R.id.rbq_one);
//        ratingBar.setRating(userRankValue);

                 q1 = rankDialog.findViewById(R.id.rq_one);
                 q2 = rankDialog.findViewById(R.id.rq_two);
                 q3 = rankDialog.findViewById(R.id.rq_three);
            setUpQs();

        Button updateButton = (Button) rankDialog.findViewById(R.id.finish);
        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                double avg = ratingBar.getProgress();
                idk.setText(new DecimalFormat("##.##").format(avg) + "avg ");

                rankDialog.dismiss();
            }
        });
        //now that the dialog is set up, it's time to show it
        rankDialog.show();

    }

    private void setUpQs() {

        q1.setText(R.string.emp_professionalisim_rating_q);
        q2.setText(R.string.emp_otp_rating_q);
        q3.setText(R.string.emp_response_time_rating_q);

    }




}
