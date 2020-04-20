package com.example.seeker.FreelancerMainPages;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.seeker.Database.ApiClients;
import com.example.seeker.Model.Freelancer;
import com.example.seeker.R;
import com.example.seeker.SharedPref.Constants;
import com.example.seeker.SharedPref.MySharedPreference;

import org.iban4j.IbanFormatException;
import org.iban4j.IbanUtil;
import org.iban4j.InvalidCheckDigitException;
import org.iban4j.UnsupportedCountryException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FreelancerPayment extends AppCompatActivity {

    EditText ibanNumber;
    EditText fullName;
    Button save;
    ImageView backBtk;
    String iban,name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_freelancer_payment);
        init();

        backBtk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {
                    if(validate()) {
                        IbanUtil.validate(ibanNumber.getText().toString());
                        setFreelancer();
                    }else {
                        wrongInfoDialog("Missing fields");
                    }
                    // valid
                } catch (IbanFormatException |
                        InvalidCheckDigitException |
                        UnsupportedCountryException e) {
                    // invalid

                    wrongInfoDialog(e.getLocalizedMessage());



                }



            }
        });
    }

    private boolean validate() {

        iban = ibanNumber.getText().toString();
        name = fullName.getText().toString();

        if(iban.isEmpty() || name.isEmpty()){

            return false;
        }else{
            return true;
        }
    }

    private void setFreelancer() {

        Freelancer freelancer = new Freelancer();
        freelancer.setId(MySharedPreference.getLong(this, Constants.Keys.FREELANCER_ID,-1));
        freelancer.setFullName(name);
        freelancer.setIbanNumber(iban);

       ApiClients.getAPIs().setFreelancerIban(freelancer).enqueue(new Callback<Void>() {
           @Override
           public void onResponse(Call<Void> call, Response<Void> response) {
               if(response.isSuccessful()){

                   dialog("your IBAN was successfully added");
               }
           }

           @Override
           public void onFailure(Call<Void> call, Throwable t) {

           }
       });
    }

    private void init() {

        ibanNumber = findViewById(R.id.iban);
        fullName = findViewById(R.id.fullName);
        save = findViewById(R.id.save);
        backBtk = findViewById(R.id.payment_freelancer_back);

    }
    private void wrongInfoDialog(String msg) {
        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);

        alertDialog.setTitle("Warning");

        // Setting Dialog Message
        alertDialog.setMessage(msg);

        // Setting Icon to Dialog
//        alertDialog.setIcon(R.drawable.exclamation);

        //Setting Negative "ok" Button
        alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }//end onClick
        });//end setPositiveButton

        alertDialog.show();

    }//End wrongInfoDialog()

    private void dialog(String msg) {
        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);


        // Setting Dialog Message
        alertDialog.setMessage(msg);

        // Setting Icon to Dialog
//        alertDialog.setIcon(R.drawable.exclamation);

        //Setting Negative "ok" Button
        alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

                finish();
                dialog.dismiss();
            }//end onClick
        });//end setPositiveButton

        alertDialog.show();

    }//End wrongInfoDialog()

    @Override
    protected void onResume() {
        super.onResume();

        ApiClients.getAPIs().findFreelancerById(MySharedPreference.getLong(this, Constants.Keys.FREELANCER_ID,-1)).enqueue(new Callback<Freelancer>() {
            @Override
            public void onResponse(Call<Freelancer> call, Response<Freelancer> response) {
                if(response.isSuccessful()){

                    Freelancer freelancer = response.body();
                    if(freelancer.getFullName() != null)
                    {
                        if(!freelancer.getFullName().isEmpty()){
                            fullName.setText(freelancer.getFullName());
                        }

                    }
                    if(freelancer.getIbanNumber() != null){

                        if(!freelancer.getIbanNumber().isEmpty()){
                            ibanNumber.setText(freelancer.getIbanNumber());
                        }
                    }

                }
            }

            @Override
            public void onFailure(Call<Freelancer> call, Throwable t) {

            }
        });
    }
}
