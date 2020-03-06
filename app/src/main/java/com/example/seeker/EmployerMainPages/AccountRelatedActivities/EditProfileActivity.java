package com.example.seeker.EmployerMainPages.AccountRelatedActivities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.seeker.Database.ApiClients;
import com.example.seeker.Model.Exception.ApiError;
import com.example.seeker.Model.Exception.ApiException;
import com.example.seeker.Model.Responses.ApiResponse;
import com.example.seeker.Model.UserSocialMedia;
import com.example.seeker.PostBid.PostBidActivity;
import com.example.seeker.R;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Converter;
import retrofit2.Response;


public class EditProfileActivity extends AppCompatActivity {
    private static final String LOG = EditProfileActivity.class.getSimpleName();

    ImageView userImg;
    ImageView linkedinImg, twitterImg, facebookImg;
    ImageView editEdu, editLinks;
    EditText linkedinET, twitterET, facebookET;
    TextView saveSocialEditing;
    TextView name, nameAsFreelancer, nameAsEmployer;
    TextView education;
    TextView saveEducationEditing;
    EditText EducationET;


    boolean LinkedinFlag = false;
    boolean TwitteFlag = false;
    boolean FacebookFlag = false;


//    todo: ADD THEM
//    TextView circlers;
//    previous samples

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        init();
        executeGetSocialMedia();
        executeGetEducation();



        editEdu.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                EducationET.setEnabled(true);


                saveEducationEditing.setVisibility(View.VISIBLE);


                saveEducationEditing.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
//                        UserSocialMedia userSocialMedia = new UserSocialMedia(EducationET.getText().toString());
//                        executePostSocialMediaRequest(userSocialMedia);


                        EducationET.setEnabled(false);

                        //Hiding Save
                        saveEducationEditing.setVisibility(View.INVISIBLE);

                    }

                });

            }//end onClick


            });



        editLinks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               //Enable links
                linkedinET.setEnabled(true);
                twitterET.setEnabled(true);
                facebookET.setEnabled(true);



                if(linkedinET.getText().toString() == "N/A"){
                    //todo create linkedin api
                    LinkedinFlag = true;
                }
                if (twitterET.getText().toString() == "N/A"){
                    TwitteFlag = true;

                }  if(facebookET.getText().toString() == "N/A"){
                    FacebookFlag = true;
                }

                //Show Save
                saveSocialEditing.setVisibility(View.VISIBLE);

                saveSocialEditing.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

//                        if(LinkedinFlag && TwitteFlag && FacebookFlag) {
//
////                            executePostSocialMediaRequest();
//                        }

                        UserSocialMedia userSocialMedia = new UserSocialMedia(twitterET.getText().toString(),facebookET.getText().toString(),linkedinET.getText().toString());
                        executePostSocialMediaRequest(userSocialMedia);
                        //Disable links
                        linkedinET.setEnabled(false);
                        twitterET.setEnabled(false);
                        facebookET.setEnabled(false);


                        //Hiding Save
                        saveSocialEditing.setVisibility(View.INVISIBLE);




                    }//end save
                });//end save

            }//end edit links
        });//End edit links


    }//End onCreate

    private void executeGetEducation() {

    }

    private void init() {
        editLinks = findViewById(R.id.edit_social_media);
        linkedinImg = findViewById(R.id.linkedin);
        twitterImg = findViewById(R.id.twitter);
        facebookImg = findViewById(R.id.facebook);

        linkedinET = findViewById(R.id.linkedin_link);
        twitterET = findViewById(R.id.twitter_link);
        facebookET = findViewById(R.id.facebook_link);
        //Disable links
        linkedinET.setEnabled(false);
        twitterET.setEnabled(false);
        facebookET.setEnabled(false);

        saveSocialEditing = findViewById(R.id.save_editing_social_media);

        //Hiding Save
        saveSocialEditing.setVisibility(View.INVISIBLE);

    }//End init()


    private void executePostSocialMediaRequest(UserSocialMedia userSocialMedia){
        ApiClients.getAPIs().getPostSocialMediaRequest(userSocialMedia).enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {


                if(response.isSuccessful()){

                    Log.i(LOG, "onResponse: " + response.body().toString());
                    facebookET.setText(facebookET.getText().toString());
                    twitterET.setText(twitterET.getText().toString());
                    linkedinET.setText(linkedinET.getText().toString());
                    //todo: change msg
                    Dialog("Information updated!");
                }else {

                    Converter<ResponseBody, ApiException> converter = ApiClients.getInstant().responseBodyConverter(ApiException.class, new Annotation[0]);
                    ApiException exception = null;
                    try {

                        exception = converter.convert(response.errorBody());

                        List<ApiError> errors = exception.getErrors();

                        if (errors != null)
                            if (!errors.isEmpty())
                                wrongInfoDialog(errors.get(0).getMessage());
                        wrongInfoDialog(exception.getMessage());

                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }


            }//End onResponse

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {

            }
        });
    }


    private void executeGetSocialMedia(){
        ApiClients.getAPIs().getAllSocialMedia().enqueue(new Callback<List<UserSocialMedia>>() {
            @Override
            public void onResponse(Call<List<UserSocialMedia>> call, Response<List<UserSocialMedia>> response) {
                if(response.isSuccessful()){
                    int responseSize = response.body().size() - 1;
                    UserSocialMedia user = response.body().get(responseSize);

                    linkedinET.setText(user.getLinkedIn());
                    twitterET.setText(user.getTwitter());
                    facebookET.setText(user.getFacebook());
                }
            }

            @Override
            public void onFailure(Call<List<UserSocialMedia>> call, Throwable t) {

            }
        });
    }


    private void Dialog(String msg) {

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);

        // Setting Dialog Message
        alertDialog.setMessage(msg);

        //Setting Negative "ok" Button
        alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }//end onClick
        });//end setPositiveButton

        alertDialog.show();
    }//End of Dialog()



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









}
