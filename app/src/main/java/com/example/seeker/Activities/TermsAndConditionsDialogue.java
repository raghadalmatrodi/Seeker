package com.example.seeker.Activities;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.example.seeker.R;

public class TermsAndConditionsDialogue  extends Dialog implements android.view.View.OnClickListener {

    public Activity c;
    public Dialog d;
    public Button okay;
    TextView textView;

    public TermsAndConditionsDialogue(Activity a) {
        super(a);
        // TODO Auto-generated constructor stub
        this.c = a;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.terms_and_conditions);
        textView=(TextView)findViewById(R.id.txt_dia);
        textView.setText(R.string.terms_and_conditions_content);




        okay = (Button) findViewById(R.id.okayBtn);
        okay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });


    }

    @Override
    public void onClick(View view) {

    }
}