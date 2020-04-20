package com.example.seeker.Activities;


import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.seeker.R;


public class ForgotPassDialog extends Dialog {

  public Activity activity;
  public TextView send;
  public ImageView dismissDialog;

  public ForgotPassDialog(Activity a) {
    super(a);
    this.activity = a;
  }//End ForgotPassDialog() constructor

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    requestWindowFeature(Window.FEATURE_NO_TITLE);
    setContentView(R.layout.forget_pass_dialog);

    send = findViewById(R.id.btn_send);
    send.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        dismiss();
      }//End onClick
    });
    dismissDialog = findViewById(R.id.dismiss);
    dismissDialog.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        dismiss();
      }
    });
  }//End onCreate()

}//End class