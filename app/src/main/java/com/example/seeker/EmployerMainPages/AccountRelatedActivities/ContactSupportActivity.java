package com.example.seeker.EmployerMainPages.AccountRelatedActivities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.seeker.R;




public class ContactSupportActivity extends AppCompatActivity {

    private EditText eTo;
    private EditText eSubject;
    private EditText eMsg;
    private Button btn;
    private ImageView backBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_support);
            eTo = (EditText)findViewById(R.id.txtTo);
            eTo.setText("SeekerApp@hotmail.com");
            eTo.setEnabled(false);
            eSubject = (EditText)findViewById(R.id.txtSub);
            backBtn = findViewById(R.id.support_back);
            backBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    finish();

                }
            });
            eMsg = (EditText)findViewById(R.id.txtMsg);
            btn = (Button)findViewById(R.id.btnSend);
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent it = new Intent(Intent.ACTION_SEND);
                    String seekerEmail = "SeekerApp@hotmail.com";
                    it.putExtra(Intent.EXTRA_EMAIL, new String[]{seekerEmail});
                    it.putExtra(Intent.EXTRA_SUBJECT,eSubject.getText().toString());
                    it.putExtra(Intent.EXTRA_TEXT,eMsg.getText());
                    it.setType("message/rfc822");
                    startActivity(Intent.createChooser(it,"Choose Mail App"));
                }
            });
        }
    }

