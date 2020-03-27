package com.example.seeker.EmployerMainPages.AccountRelatedActivities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.seeker.Database.ApiClients;
import com.example.seeker.Model.Certificate;
import com.example.seeker.Model.Exception.ApiError;
import com.example.seeker.Model.Exception.ApiException;
import com.example.seeker.Model.Responses.ApiResponse;
import com.example.seeker.Model.UserSocialMedia;
import com.example.seeker.R;
import com.example.seeker.SharedPref.Constants;
import com.example.seeker.SharedPref.MySharedPreference;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Converter;
import retrofit2.Response;


public class EditProfileActivity extends AppCompatActivity {



    private static final String LOG = EditProfileActivity.class.getSimpleName();
    private static final int INTENT_GALLERY = 301;
    private static final int INTENT_CAMERA = 401;

    ImageView exclamation_icon;
    ImageView userImg, cameraChooser;
    ImageView phoneNumber, nationalId;
    ImageView linkedinImg, twitterImg, facebookImg;
    Button saveLinkedin, saveTwitter, saveFb, saveEdu;
    Button cancelLinkedin, cancelTwitter, cancelFb, cancelEdu;
    ImageView editEducationIcon;
    EditText linkedinET, twitterET, facebookET;
    TextView name, nameAsFreelancer, nameAsEmployer;
    EditText educationET;

    private File imageFile;
    long current_user_id = MySharedPreference.getLong(EditProfileActivity.this, Constants.Keys.USER_ID, -1);



    private Intent cameraIntent, photoPickerIntent;

    TextView totalTrustPoints_TV;
    static int totalTrustPoints = 0;


//    todo: ADD THEM
//    TextView circlers;
//    previous samples


    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        init();
        fillCurrentUSerData();
        initToolbar();


//        editLinks.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//               //Enable links
//                linkedinET.setEnabled(true);
//                twitterET.setEnabled(true);
//                facebookET.setEnabled(true);
//
//
//
//                if(linkedinET.getText().toString() == "N/A"){
//                    //todo create linkedin api
//                    LinkedinFlag = true;
//                }
//                if (twitterET.getText().toString() == "N/A"){
//                    TwitteFlag = true;
//
//                }  if(facebookET.getText().toString() == "N/A"){
//                    FacebookFlag = true;
//                }
//
//                //Show Save
//                saveSocialEditing.setVisibility(View.VISIBLE);
//
//                saveSocialEditing.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//
////                        if(LinkedinFlag && TwitteFlag && FacebookFlag) {
////
//////                            executePostSocialMediaRequest();
////                        }
//                        executeAddLinkedInRequest(current_user_id, linkedinET.getText().toString());
//
//
////                        UserSocialMedia userSocialMedia = new UserSocialMedia(twitterET.getText().toString(),facebookET.getText().toString(),linkedinET.getText().toString());
////                        executePostSocialMediaRequest(userSocialMedia);
//                        //Disable links
//                        linkedinET.setEnabled(false);
//                        twitterET.setEnabled(false);
//                        facebookET.setEnabled(false);
//
//
//                        //Hiding Save
//                        saveSocialEditing.setVisibility(View.INVISIBLE);
//
//
//
//
//                    }//end save
//                });//end save
//
//            }//end edit links
//        });//End edit links

        cameraChooser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCameraChooser();
            }
        });

        getSocialMedia();
        getEducation();
        getNationalId();
        getPhoneNumber();


        totalTrustPoints_TV = findViewById(R.id.emp_total_trust_points);
//        totalTrustPoints_TV.setText(CalculateEmployerTrustPoints()+"%");
//        totalTrustPoints_TV.setText(totalTrustPoints+"%");

        getAllValsAndCalcTPForEmp();





    }//End onCreate


    private void initToolbar(){

            //init toolbar

            Toolbar toolbar = findViewById(R.id.editprofile_toolbar);
            toolbar.setTitle(MySharedPreference.getString(EditProfileActivity.this, Constants.Keys.USER_NAME, ""));
            toolbar.setNavigationIcon(R.drawable.back_arrow_24dp);
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onBackPressed();
                }//End onClick()
            });

        }//End initToolBar()

    //todo: remove comment :)
//    @Override
//    protected void onResume() {
//        super.onResume();
//        getSocialMedia();
//        getEducation();
//        getNationalId();
//        getPhoneNumber();
//
//
////        totalTrustPoints_TV.setText(CalculateEmployerTrustPoints()+"%");
//    }

    private void getSocialMedia() {

        //GET LINKED_IN
        getLinkedIn();

        //GET TWITTER
        getTwitter();

        //GET FACEBOOK
        getFacebook();

    }

    private void getFacebook() {
        ApiClients.getAPIs().getFacebook(current_user_id).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    facebookET.setText(response.body());
//                    if (!response.body().equals(null) )
//                        totalTrustPoints+= 5;
//                    facebook_TP = response.body();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });
    }

    private void getTwitter() {
        ApiClients.getAPIs().getTwitter(current_user_id).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    twitterET.setText(response.body());
//                    if (!response.body().equals(null))
//                        totalTrustPoints+= 5;
//                        twitter_TP = response.body();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });
    }

    private void getLinkedIn() {
        ApiClients.getAPIs().getLinkedin(current_user_id).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    linkedinET.setText(response.body());
//                    totalTrustPoints_TV.setText(response.body().equals(null));
//                    if (!response.body().equals(null))
//                        totalTrustPoints+= 5;
//                        totalTrustPoints_TV.setText(5+"%");
//                    totalTrustPoints+= 5;
////                        linkedin_TP = response.body();
                }

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });
    }
    String res;
    private void getEducation(){
        ApiClients.getAPIs().getEducation(current_user_id).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()){
                    res = response.body().toString();
                    educationET.setText(res);
                }

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });
    }

    private void getNationalId(){
        ApiClients.getAPIs().getNationalId(current_user_id).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    if (!response.body().equals(null)) {
//                        N = true;
//                        calcN(N);
//                        totalTrustPoints+= 40;
//                        totalTrustPoints_TV.setText(totalTrustPoints+"");
//                        nid_TP = response.body();
                    }

                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });
    }

    private void getPhoneNumber(){
        ApiClients.getAPIs().getPhoneNumber(current_user_id).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
//                    if (response.body() != null)
//                        phone_TP = response.body();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });
    }

    private void init() {
        name = findViewById(R.id.employer_edit_profile_name);
        nameAsEmployer = findViewById(R.id.edit_profile_name_as_emp);
        nameAsFreelancer = findViewById(R.id.edit_profile_name_as_fr);


//        editLinks = findViewById(R.id.edit_social_media);


        linkedinImg = findViewById(R.id.linkedin);
        saveLinkedin = findViewById(R.id.save_linkedin);
        cancelLinkedin = findViewById(R.id.cancel_linkedin);

        twitterImg = findViewById(R.id.twitter);
        saveTwitter = findViewById(R.id.save_twitter);
        cancelTwitter = findViewById(R.id.cancel_twitter);

        facebookImg = findViewById(R.id.facebook);
        saveFb = findViewById(R.id.save_fb);
        cancelFb = findViewById(R.id.cancel_fb);

        editEducationIcon = findViewById(R.id.emp_edit_edu_img);
        saveEdu = findViewById(R.id.save_education);
        cancelEdu = findViewById(R.id.emp_cancel_education);



        linkedinET = findViewById(R.id.linkedin_link);
        twitterET = findViewById(R.id.twitter_link);
        facebookET = findViewById(R.id.facebook_link);
        educationET = findViewById(R.id.education);


        //Disable links
        linkedinET.setEnabled(false);
        twitterET.setEnabled(false);
        facebookET.setEnabled(false);
        educationET.setEnabled(false);

        //Hide Save and Cancel buttons
        saveLinkedin.setVisibility(View.INVISIBLE);
        saveTwitter.setVisibility(View.INVISIBLE);
        saveFb.setVisibility(View.INVISIBLE);
        saveEdu.setVisibility(View.INVISIBLE);

        cancelLinkedin.setVisibility(View.INVISIBLE);
        cancelTwitter.setVisibility(View.INVISIBLE);
        cancelFb.setVisibility(View.INVISIBLE);
        cancelEdu.setVisibility(View.INVISIBLE);


        cameraChooser = findViewById(R.id.editProfileImageChooser);
        userImg = findViewById(R.id.edit_profile_pic);

        exclamation_icon = findViewById(R.id.exclamation);
        exclamation_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                exclamationDialog("Note: Click on one of these icons to add/edit your social-media accounts.");
            }
        });

        LinkedIn();
        Twitter();
        Facebook();
        Education();

        phoneNumber = findViewById(R.id.emp_phone_num);
        nationalId = findViewById(R.id.emp_nid_icon);

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




    }//End init()

    private void LinkedIn() {

        linkedinImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                linkedinET.setEnabled(true);
                saveLinkedin.setVisibility(View.VISIBLE);
                cancelLinkedin.setVisibility(View.VISIBLE);

                saveLinkedin.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        executeAddLinkedInRequest(current_user_id, linkedinET.getText().toString());
                        linkedinET.setEnabled(false);
                        saveLinkedin.setVisibility(View.INVISIBLE);
                        cancelLinkedin.setVisibility(View.INVISIBLE);
                        //todo: add editing completed dialog ?

                    }
                });

                cancelLinkedin.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        linkedinET.setEnabled(false);
                        saveLinkedin.setVisibility(View.INVISIBLE);
                        cancelLinkedin.setVisibility(View.INVISIBLE);
                        getLinkedIn();
                    }
                });


            }
        });

    }

    private void Twitter() {

        twitterImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                twitterET.setEnabled(true);
                saveTwitter.setVisibility(View.VISIBLE);
                cancelTwitter.setVisibility(View.VISIBLE);

                saveTwitter.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        executeAddTwitterRequest(current_user_id, twitterET.getText().toString());
                        twitterET.setEnabled(false);
                        saveTwitter.setVisibility(View.INVISIBLE);
                        cancelTwitter.setVisibility(View.INVISIBLE);
                        //todo: add editing completed dialog ?

                    }
                });

                cancelTwitter.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        twitterET.setEnabled(false);
                        saveTwitter.setVisibility(View.INVISIBLE);
                        cancelTwitter.setVisibility(View.INVISIBLE);
                        getTwitter();
                    }
                });


            }
        });



    }

    private void Facebook(){
        facebookImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                facebookET.setEnabled(true);
                saveFb.setVisibility(View.VISIBLE);
                cancelFb.setVisibility(View.VISIBLE);

                saveFb.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        executeAddFacebookRequest(current_user_id, facebookET.getText().toString());
                        facebookET.setEnabled(false);
                        saveFb.setVisibility(View.INVISIBLE);
                        cancelFb.setVisibility(View.INVISIBLE);
                        //todo: add editing completed dialog ?

                    }
                });

                cancelFb.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        facebookET.setEnabled(false);
                        saveFb.setVisibility(View.INVISIBLE);
                        cancelFb.setVisibility(View.INVISIBLE);
                        getFacebook();
                    }
                });


            }
        });

    }

    private void Education() {
        editEducationIcon.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                educationET.setEnabled(true);
                saveEdu.setVisibility(View.VISIBLE);
                cancelEdu.setVisibility(View.VISIBLE);


                saveEdu.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
//                        UserSocialMedia userSocialMedia = new UserSocialMedia(educationET.getText().toString());
                        executeAddEducationRequest(current_user_id, educationET.getText().toString());


                        educationET.setEnabled(false);
                        saveEdu.setVisibility(View.INVISIBLE);
                        cancelEdu.setVisibility(View.INVISIBLE);


                    }

                });

                cancelEdu.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        getEducation();
                        educationET.setEnabled(false);
                        saveEdu.setVisibility(View.INVISIBLE);
                        cancelEdu.setVisibility(View.INVISIBLE);

                    }
                });

            }//end onClick


        });
    }

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
                    int responseSize = response.body().size()-1;
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

    private void fillCurrentUSerData(){

        name.setText(MySharedPreference.getString(EditProfileActivity.this, Constants.Keys.USER_NAME, ""));

//        String current = MySharedPreference.getString(EditProfileActivity.this, Constants.Keys.USER_NAME, "");
//        String CapCurrent = current.substring(0,1).toUpperCase();
//
//        current = CapCurrent+current+" as Freelancer:";

        nameAsFreelancer.setText(MySharedPreference.getString(EditProfileActivity.this, Constants.Keys.USER_NAME, "")+" as Freelancer:");

        nameAsEmployer.setText(MySharedPreference.getString(EditProfileActivity.this, Constants.Keys.USER_NAME, "")+ " as Employer:");



    }

    private void executePostCertificatesRequest(Certificate certificate){

        ApiClients.getAPIs().getPostCertificatesRequest(certificate).enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                if (response.isSuccessful()){
                    Toast.makeText(EditProfileActivity.this,"success",Toast.LENGTH_LONG).show();
                    getAllValsAndCalcTPForEmp();
                } else {
                    Toast.makeText(EditProfileActivity.this,"not success",Toast.LENGTH_LONG).show();

                }
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                Toast.makeText(EditProfileActivity.this,"failure",Toast.LENGTH_LONG).show();


            }
        });

    }

    private void executeAddLinkedInRequest(long id, String linkedin) {

        ApiClients.getAPIs().getPostLinkedInRequest(id, linkedin).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()){
                    Toast.makeText(EditProfileActivity.this, "Success", Toast.LENGTH_LONG).show();
              }

                else
                    Toast.makeText(EditProfileActivity.this, response.errorBody().toString(), Toast.LENGTH_LONG).show();
            }


            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(EditProfileActivity.this, t.getLocalizedMessage(), Toast.LENGTH_LONG).show();

            }
        });


    }//end add linkedin

    private void executeAddTwitterRequest(long id, String twitter){

        ApiClients.getAPIs().getPostTwitterRequest(id, twitter).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()){
                    Toast.makeText(EditProfileActivity.this, "Success", Toast.LENGTH_LONG).show();
                    getAllValsAndCalcTPForEmp();
                   }
                else
                    Toast.makeText(EditProfileActivity.this, response.errorBody().toString(), Toast.LENGTH_LONG).show();
            }


            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(EditProfileActivity.this, t.getLocalizedMessage(), Toast.LENGTH_LONG).show();

            }
        });

    }//end add twitter

    private void executeAddFacebookRequest(long id, String facebook){
        ApiClients.getAPIs().getPostFacebookRequest(id, facebook).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {

                    Toast.makeText(EditProfileActivity.this, "Success", Toast.LENGTH_LONG).show();
                    getAllValsAndCalcTPForEmp();
                }
                else
                    Toast.makeText(EditProfileActivity.this, response.errorBody().toString(), Toast.LENGTH_LONG).show();
            }


            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(EditProfileActivity.this, t.getLocalizedMessage(), Toast.LENGTH_LONG).show();

            }
        });
    }//end add facebook

    private void executeAddEducationRequest(long id, String education) {
        ApiClients.getAPIs().getPostEducation(id, education).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()){
                    Toast.makeText(EditProfileActivity.this, "Success", Toast.LENGTH_LONG).show();
                }

                else
                    Toast.makeText(EditProfileActivity.this, response.errorBody().toString(), Toast.LENGTH_LONG).show();
            }


            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(EditProfileActivity.this, t.getLocalizedMessage(), Toast.LENGTH_LONG).show();

            }
        });

    }

    private void executeGetAllCertiicates(){

        ApiClients.getAPIs().getAllCertificates().enqueue(new Callback<List<Certificate>>() {
            @Override
            public void onResponse(Call<List<Certificate>> call, Response<List<Certificate>> response) {
                if (response.isSuccessful()){
                    int responseSize = response.body().size() -1;
                    Certificate certificate = response.body().get(responseSize);
                    educationET.setText(certificate.getCertificates());
//                    name.setText(certificate.getCertificates());

                }
            }

            @Override
            public void onFailure(Call<List<Certificate>> call, Throwable t) {

            }
        });
    }


    /**
     * FOR CHOOSING IMAGE
     */

//    private RequestBody createPartFromString(String field){
//
//        if(field != null){
//            return RequestBody.create(MediaType.parse("text/plain"), field);
//        }//End if block
//        else {
//            return RequestBody.create(MediaType.parse("text/plain"),"");
//        }//End else block
//
//    }//End createPartFromString()
//
//    private RequestBody createPartFromFileImg(File imgFile){
//        if(imgFile != null)
//            return  RequestBody.create(MediaType.parse("image/png"), imgFile);
//        else
//            return null;
//    }//End createPartFromFileImg()

    private void showPhotoOptionsDialog(){
        final CharSequence[] items = {"Camera","Gallery"};
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {

                if(items[item].equals("Camera")){
                    cameraIntent();
                }else if(items[item].equals("Gallery")){
                    galleryIntent();
                }//End else if block
            }//End onClick()

        });
        builder.show();
    }//End showPhotoOptionsDialog()

    private void cameraIntent() {

        cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cameraIntent,INTENT_CAMERA);

    }//End cameraIntent()

    private void galleryIntent() {

        photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        startActivityForResult(photoPickerIntent,INTENT_GALLERY);

    }//End galleryIntent()

    private void openCameraChooser(){
        if(!checkPermission()){
            requestPermission();
            return;
        }//End if block
        showPhotoOptionsDialog();
    }//End openCameraChooser()

    private boolean checkPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            // Permission is not granted
            return false;
        }//End if block
        return true;
    }//end checkPermission()

    private void requestPermission() {
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.CAMERA},
                INTENT_CAMERA);
    }//End requestPermission()


//            MySharedPreference.putString(this, Constants.Keys.USER_IMG, user.getImage());

    @Override
    protected void onActivityResult(int reqCode, int resultCode, Intent data) {
        super.onActivityResult(reqCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK)
            //End of if
            if (reqCode == INTENT_CAMERA || reqCode == INTENT_GALLERY)
                try {
                    final Bitmap bitmap;
                    if (data.getData() == null) {
                        bitmap = (Bitmap) data.getExtras().get("data");
                    }//End of if
                    else {
                        bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), data.getData());
                    }//End of else

                    userImg.setImageBitmap(bitmap);
                    fromBitmapToFile(bitmap);
                    byte[] img = ImageToByte(imageFile);

                    postImg(current_user_id, img);

//                    MySharedPreference.putString(this, Constants.Keys.USER_IMG, bitmap.toString());
//
//                    String imageString = MySharedPreference.getString(EditProfileActivity.this,Constants.Keys.USER_IMG,"");
//                    Glide.with(this).load(imageString).into(userImg);



                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }//End of catch
                catch (IOException e) {
                    e.printStackTrace();
                }//end catch block


    }//End onActivityResult()

//    private void post(File file){
//        ApiClients.getAPIs().postAvat(file).enqueue(new Callback<Void>() {
//            @Override
//            public void onResponse(Call<Void> call, Response<Void> response) {
//
//            }
//
//            @Override
//            public void onFailure(Call<Void> call, Throwable t) {
//
//            }
//        });
//    }

    //TODO: REMOVE IT!!!!!!!!!!!!!!!!!!!!!!!!!!!! NOT WORKING IDK WHY :)???!!
    private void postImg(long id, byte[] img){
        ApiClients.getAPIs().getPostImgRequest(id, img).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if(response.isSuccessful()){
                    Toast.makeText(EditProfileActivity.this,"FINALLY SUCCESSFUL",Toast.LENGTH_LONG).show();

                } else{
                    Toast.makeText(EditProfileActivity.this,response.errorBody().toString(),Toast.LENGTH_LONG).show();

                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(EditProfileActivity.this,t.getLocalizedMessage(),Toast.LENGTH_LONG).show();

            }
        });
    }

    private void fromBitmapToFile(Bitmap bitmap) {
        File filesDir = getFilesDir();

        String name1 = "";
        if (name.getText() != null )
            name1 = name.getText().toString();

        imageFile = new File(filesDir, name1 + ".png");

        OutputStream os;
        try {
            os = new FileOutputStream(imageFile);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, os);

            os.flush();
            os.close();
//            return os;
        }//End try block
        catch (Exception e){
            Log.e(getClass().getSimpleName(), "Error writing bitmap", e);
        }//End catch

    }//End fromBitmapToFile()

    private void exclamationDialog(String msg) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setMessage(msg);
        alertDialog.setIcon(R.drawable.exclamation);
        alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
               dialog.dismiss();
            }//end onClick
        });//end setPositiveButton

        alertDialog.show();
    }//end wrongInfoDialog()


    public static byte[] ImageToByte(File file) throws FileNotFoundException{

        FileInputStream fis = new FileInputStream(file);

        ByteArrayOutputStream bos = new ByteArrayOutputStream();

        byte[] buf = new byte[1024];

        try {

            for (int readNum; (readNum = fis.read(buf)) != -1;) {

                bos.write(buf, 0, readNum);

                System.out.println("read " + readNum + " bytes,");

            }

        } catch (IOException ex) {

        }

        byte[] bytes = bos.toByteArray();



        return bytes;

    }



    /**Input: email, socialMediaAccounts, phoneNumber, nationalID, maarofAccount. Output: Number of Trust points “trustPoints”.
     if email != null then trustPoints+=5 for each item in socialMediaAccounts,do
     if item != null, then trustPoints+=5
     if phoneNumber!= null then trustPoints+=20 if nationalID!= null then trustPoints+=40 if maarofAccount!= null then trustPoints+=20 return trustPoints
     */

    /**
     *
     *

     *
     *
     *
     * Email * 5%
     * Phone Number    * 20%
     * Social media Accounts  * 15% Each account gets 5%
     * National ID *  40%
     * Maarof Account   * 20%
     * Total * 100%
     */



//    int totalTrustPoints = 0;
//    public void CalculateEmployerTrustPoints(){
//        //email is always there since all users are registered by their emails.
////        totalTrustPoints+= 5;
//
////        if(N)
////            totalTrustPoints+= 40;
//
////        if (!linkedin_TP.equals(""))
////            totalTrustPoints+= 5;
////        else{
////            if (!twitter_TP.equals(""))
////                totalTrustPoints+= 5;
////            else {
////                if (!facebook_TP.equals(""))
////                    totalTrustPoints+= 5;
////                else{
////                    if (!phone_TP.equals(""))
////                        totalTrustPoints+= 20;
////                    else{
////                        if (!nid_TP.equals(""))
////                            totalTrustPoints+= 40;
////                    }
////
////
////                }
////
////
////            }
////
////
////        }
//
//
//
////        return totalTrustPoints;
//    }


    /**
     *  list.add(user.getNational_id());
     *         list.add(user.getPhone_number());
     *         list.add(user.getLinkedIn());
     *         list.add(user.getTwitter());
     *         list.add(user.getFacebook());
     */
    private void getAllValsAndCalcTPForEmp(){
        //email is always there since all users are registered by their emails.
        totalTrustPoints = 0;
        totalTrustPoints+= 5;


        ApiClients.getAPIs().getAllTPVals(current_user_id).enqueue(new Callback<List<String>>() {
            @Override
            public void onResponse(Call<List<String>> call, Response<List<String>> response) {
                if (response.isSuccessful()){

                    if (response.body().get(0) != null)
                    if (!response.body().get(0).equals(null))
                        totalTrustPoints+=40;

                    if (response.body().get(1) != null)
                    if (!response.body().get(1).equals(null))
                        totalTrustPoints+=20;

                    if (response.body().get(2) != null)
                    if (!response.body().get(2).equals(null))
                        totalTrustPoints+=5;

                    if (response.body().get(3) != null)
                    if (!response.body().get(3).equals(null))
                        totalTrustPoints+=5;

                    if (response.body().get(4) != null)
                    if (!response.body().get(4).equals(null))
                        totalTrustPoints+=5;

                    totalTrustPoints_TV.setText(totalTrustPoints+"%");

                }
            }

            @Override
            public void onFailure(Call<List<String>> call, Throwable t) {

            }
        });
//        totalTrustPoints_TV.setText(totalTrustPoints+"%");
    }


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
                        case 1:
                            executeAddPhoneNumberRequest(MySharedPreference.getLong(EditProfileActivity.this, Constants.Keys.USER_ID, -1), infoET.getText().toString());
                            dialogBuilder.dismiss();

                            break;
                        case 2:
                            executeAddNationalIdRequest(MySharedPreference.getLong(EditProfileActivity.this, Constants.Keys.USER_ID, -1), infoET.getText().toString());
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



    //ONE
    private void executeAddPhoneNumberRequest(long id, String phone) {
        ApiClients.getAPIs().getPostPhoneNumberRequest(id, phone).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(EditProfileActivity.this, "Success", Toast.LENGTH_LONG).show();
                    getAllValsAndCalcTPForEmp();
                }
                else
                    Toast.makeText(EditProfileActivity.this, "Not Success", Toast.LENGTH_LONG).show();

            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(EditProfileActivity.this, t.getLocalizedMessage(), Toast.LENGTH_LONG).show();

            }
        });
    }//end add phone

    //TWO
    private void executeAddNationalIdRequest(long id, String NationalId){
        ApiClients.getAPIs().getPostNationalIdRequest(id, NationalId).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(EditProfileActivity.this, "Success", Toast.LENGTH_LONG).show();
                    getAllValsAndCalcTPForEmp();
                }
                else
                    Toast.makeText(EditProfileActivity.this, "Not Success", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(EditProfileActivity.this, t.getLocalizedMessage(), Toast.LENGTH_LONG).show();

            }
        });

    }









































}
