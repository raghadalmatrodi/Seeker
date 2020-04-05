package com.example.seeker.FreelancerMainPages;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.seeker.Database.ApiClients;
import com.example.seeker.FreelancerMainPages.SearchTab_Freelancer.AddSkillActivity;
import com.example.seeker.Model.Employer;
import com.example.seeker.Model.Freelancer;
import com.example.seeker.Model.Skill;
import com.example.seeker.R;
import com.example.seeker.SharedPref.Constants;
import com.example.seeker.SharedPref.MySharedPreference;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FreelancerEditProfile extends AppCompatActivity {

    ImageButton add_skill;
    ImageView maroof, phoneNumber, nationalId;

    TextView name, nameAsFreelancer, nameAsEmployer,skillText;
    private String LOG = FreelancerEditProfile.class.getName();
    private Set<Skill> skillsList = new HashSet<>();
    private Freelancer freelancer;
    long current_freelancer_id = MySharedPreference.getLong(FreelancerEditProfile.this, Constants.Keys.FREELANCER_ID, -1);


    private RatingBar totalRates;
    private TextView numberOfRatings;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_freelancer_edit_profile);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        init();
        fillCurrentUSerData();
        initToolbar();
        ExcecuteGetFreelancerAPIRequest();

        fillSkills();



    }

    private void ExcecuteGetFreelancerAPIRequest() {
        ApiClients.getAPIs().getFreelancerByUserIdRequest(MySharedPreference.getLong(FreelancerEditProfile.this, Constants.Keys.USER_ID, -1)).enqueue(new Callback<Freelancer>() {
            @Override
            public void onResponse(Call<Freelancer> call, Response<Freelancer> response) {
                if (response.isSuccessful()){

                    numberOfRatings.setText("("+response.body().getNum_of_ratings()+")");

                     freelancer = response.body();
                    Log.i(LOG,"onResponse: suc" + freelancer.toString());
                    skillsList = freelancer.getSkills();
                    Log.i(LOG,"onResponse: suc" + freelancer.getSkills());
                    Log.i(LOG,"onResponse: suc" + skillsList.toString());

                    skillText = findViewById(R.id.list_skills);
                    String skillToDisplay ="";

                    for (Skill subset : skillsList) {
                        skillToDisplay += " - " +subset.getName() ;
                    }

                    skillText.setText(skillToDisplay );

                }else {
                    Log.i(LOG,"onResponse: notSuc" + response.toString());

                }//end else block
            }//End onResponse()

            @Override
            public void onFailure(Call<Freelancer> call, Throwable t) {
                Log.i(LOG,"onFailure :" + t.toString());


            }//end onFailure()
        });

    }

    private void fillSkills() {


    }

    private void init() {

        add_skill = findViewById(R.id.add_skill_fr);
        maroof = findViewById(R.id.maroof_icon);
        phoneNumber = findViewById(R.id.fr_phone_num);
        nationalId = findViewById(R.id.fr_nid_icon);

        name = findViewById(R.id.freelancer_edit_profile_name);
        nameAsEmployer = findViewById(R.id.edit_profile_name_as_fr);
        nameAsFreelancer = findViewById(R.id.edit_profile_name_as_emp);
//        if(MySharedPreference.getString(FreelancerEditProfile.this,Constants.Keys.USER_CURRENT_TYPE,"0").equals("EMPLOYER"))


            maroof.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               AddInfoDialog(0);
            }
        });

        phoneNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddInfoDialog(1);
            }
        });

        nationalId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddInfoDialog(2);
            }
        });

        add_skill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(FreelancerEditProfile.this, AddSkillActivity.class));
            }
        });


        totalRates = findViewById(R.id.freelancer_total_rating_in_profile);
        numberOfRatings = findViewById(R.id.numOfRatings_FreelanceProfile);

        calculateTotalRates(new Freelancer(current_freelancer_id));

    }//init()

    private void calculateTotalRates(Freelancer freelancer_id) {
        ApiClients.getAPIs().CalculateFreelancerTotalRating(freelancer_id).enqueue(new Callback<Double>() {
            @Override
            public void onResponse(Call<Double> call, Response<Double> response) {
                if (response.isSuccessful())
                    totalRates.setRating(Float.valueOf(String.valueOf(response.body())));

            }

            @Override
            public void onFailure(Call<Double> call, Throwable t) {

            }
        });
    }

    private void fillCurrentUSerData(){

        name.setText(MySharedPreference.getString(FreelancerEditProfile.this, Constants.Keys.USER_NAME, ""));

//        String current = MySharedPreference.getString(EditProfileActivity.this, Constants.Keys.USER_NAME, "");
//        String CapCurrent = current.substring(0,1).toUpperCase();
//
//        current = CapCurrent+current+" as Freelancer:";

        nameAsFreelancer.setText(MySharedPreference.getString(FreelancerEditProfile.this, Constants.Keys.USER_NAME, "")+" as Freelancer:");

        nameAsEmployer.setText(MySharedPreference.getString(FreelancerEditProfile.this, Constants.Keys.USER_NAME, "")+ " as Employer:");



    }

    private void initToolbar(){

        //init toolbar

        Toolbar toolbar = findViewById(R.id.editprofile_toolbar);
        toolbar.setTitle(MySharedPreference.getString(FreelancerEditProfile.this, Constants.Keys.USER_NAME, ""));
        toolbar.setNavigationIcon(R.drawable.back_arrow_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }//End onClick()
        });

    }//End initToolBar()

    //todo: check validity of phone number, maroof acc and national id.
    //
    private void AddInfoDialog(int type) {

        /**
         *
         * type 0 -> maroof
         * type 1 -> phone
         * type 2 -> nid
         *
         */

        final AlertDialog dialogBuilder = new AlertDialog.Builder(this).create();
        dialogBuilder.setCanceledOnTouchOutside(false);
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.add_accounts_dialog, null);

        final EditText infoET = dialogView.findViewById(R.id.accounts_editText);
        TextView title = dialogView.findViewById(R.id.txt_exit);
        TextView add =  dialogView.findViewById(R.id.btn_add);
        TextView cancel =  dialogView.findViewById(R.id.btn_cancel);

        switch (type){
            case 0:
                title.setText("Please enter your maarof account");
                infoET.setHint("Maarof account");
                break;


            case 1:
                title.setText("Please enter your phone number");
                infoET.setHint("Phone Number");
                break;

            case 2:
                title.setText("Please enter your national ID");
                infoET.setHint("National ID");
                break;
        }


        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogBuilder.dismiss();
            }
        });
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (infoET.getText().toString().equals(""))
                    wrongInfoDialog("Field is empty, please fill it with required information");
                    else {
                    switch (type){
                        case 0:
                            executeAddMaroofAccRequest(MySharedPreference.getLong(FreelancerEditProfile.this, Constants.Keys.FREELANCER_ID, -1), infoET.getText().toString());
                            dialogBuilder.dismiss();

                            break;

                        case 1:
                            executeAddPhoneNumberRequest(MySharedPreference.getLong(FreelancerEditProfile.this, Constants.Keys.USER_ID, -1), infoET.getText().toString());
                            dialogBuilder.dismiss();

                            break;
                        case 2:
                            executeAddNationalIdRequest(MySharedPreference.getLong(FreelancerEditProfile.this, Constants.Keys.USER_ID, -1), infoET.getText().toString());
                            dialogBuilder.dismiss();

                            break;
                    }//end switch
                }

                // DO SOMETHINGS

            }
        });

        dialogBuilder.setView(dialogView);
        dialogBuilder.show();
    }//End info dialog


    //ZERO
    private void executeAddMaroofAccRequest(long id, String maroofAcc) {
        ApiClients.getAPIs().getPostMaroofAccountRequest(id, maroofAcc).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful())
                    Toast.makeText(FreelancerEditProfile.this, "Success", Toast.LENGTH_LONG).show();
                else
                    Toast.makeText(FreelancerEditProfile.this, response.errorBody().toString(), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(FreelancerEditProfile.this, t.getLocalizedMessage(), Toast.LENGTH_LONG).show();

            }
        });

    }//end add maroof

    //ONE
    private void executeAddPhoneNumberRequest(long id, String phone) {
//        ApiClients.getAPIs().getPostPhoneNumberRequest(id, phone).enqueue(new Callback<Void>() {
//            @Override
//            public void onResponse(Call<Void> call, Response<Void> response) {
//                if (response.isSuccessful())
//                    Toast.makeText(FreelancerEditProfile.this, "Success", Toast.LENGTH_LONG).show();
//                else
//                    Toast.makeText(FreelancerEditProfile.this, "Not Success", Toast.LENGTH_LONG).show();
//
//            }
//
//            @Override
//            public void onFailure(Call<Void> call, Throwable t) {
//                Toast.makeText(FreelancerEditProfile.this, t.getLocalizedMessage(), Toast.LENGTH_LONG).show();
//
//            }
//        });
    }//end add phone

   //TWO
   private void executeAddNationalIdRequest(long id, String NationalId){
//        ApiClients.getAPIs().getPostNationalIdRequest(id, NationalId).enqueue(new Callback<Void>() {
//            @Override
//            public void onResponse(Call<Void> call, Response<Void> response) {
//                if (response.isSuccessful())
//                    Toast.makeText(FreelancerEditProfile.this, "Success", Toast.LENGTH_LONG).show();
//                else
//                    Toast.makeText(FreelancerEditProfile.this, "Not Success", Toast.LENGTH_LONG).show();
//            }
//
//            @Override
//            public void onFailure(Call<Void> call, Throwable t) {
//                Toast.makeText(FreelancerEditProfile.this, t.getLocalizedMessage(), Toast.LENGTH_LONG).show();
//
//            }
//        });

   }







    private void wrongInfoDialog(String msg) {
        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);

//        alertDialog.setTitle(title);

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


}//End class


