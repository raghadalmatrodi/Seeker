package com.example.seeker;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class Profile extends AppCompatActivity  {
private Text text_notification ,text_setting , text_contractsupport , text_terms ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        TextView text_notification = findViewById(R.id.profile_notifications);
        TextView text_setting=findViewById(R.id.profile_settings);
        TextView text_contractsupport=findViewById(R.id.profile_contractsupport);
        TextView text_terms =findViewById(R.id.profile_terms);

//        text_notification.setOnClickListener(this);
//        text_setting.setOnClickListener(this);
//        text_terms.setOnClickListener(this);


//        text_notification.setOnClickListener(new ;



//
//    @Override
//    public void onClick(View view) {
//        //switch(v.getId()){
//           // case R.id.profile_notifications:
//                //Toast.makeText(context)
//       // openDialog()
//
//        //}
//    }

    //private void openDialog() {
       // new AlertDialog.Builder(context)
              //  .setTitle("Delete entry")
             //   .setMessage("Are you sure you want to delete this entry?")
//    }

}
}