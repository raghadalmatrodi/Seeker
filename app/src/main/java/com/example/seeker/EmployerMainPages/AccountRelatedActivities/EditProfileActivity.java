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
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.seeker.Database.ApiClients;
import com.example.seeker.Model.Certificate;
import com.example.seeker.Model.Exception.ApiError;
import com.example.seeker.Model.Exception.ApiException;
import com.example.seeker.Model.Responses.ApiResponse;
import com.example.seeker.Model.UserSocialMedia;
import com.example.seeker.R;
import com.example.seeker.SharedPref.Constants;
import com.example.seeker.SharedPref.MySharedPreference;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Converter;
import retrofit2.Response;


public class EditProfileActivity extends AppCompatActivity {



    private static final String LOG = EditProfileActivity.class.getSimpleName();
    private static final int INTENT_GALLERY = 301;
    private static final int INTENT_CAMERA = 401;

    ImageView userImg, cameraChooser;
    ImageView linkedinImg, twitterImg, facebookImg;
    ImageView editEduIcon, editLinks;
    EditText linkedinET, twitterET, facebookET;
    TextView saveSocialEditing;
    TextView name, nameAsFreelancer, nameAsEmployer;
    TextView education;
    TextView saveEducationEditing;
    EditText EducationET;
    private File imageFile;


    boolean LinkedinFlag = false;
    boolean TwitteFlag = false;
    boolean FacebookFlag = false;

    private Intent cameraIntent, photoPickerIntent;


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



        init();
        executeGetSocialMedia();
        executeGetEducation();
        fillCurrentUSerData();
    executeGetAllCertiicates();

        initToolbar();


        editEduIcon.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                EducationET.setEnabled(true);


                saveEducationEditing.setVisibility(View.VISIBLE);


                saveEducationEditing.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
//                        UserSocialMedia userSocialMedia = new UserSocialMedia(EducationET.getText().toString());
                        Certificate certificate = new Certificate(EducationET.getText().toString());
                        executePostCertificatesRequest(certificate);


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

        cameraChooser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCameraChooser();
            }
        });


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


    private void executeGetEducation() {

    }

    private void init() {
        name = findViewById(R.id.edit_profile_name);
        nameAsEmployer = findViewById(R.id.edit_profile_name_as_emp);
        nameAsFreelancer = findViewById(R.id.edit_profile_name_as_fr);
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


        editEduIcon = findViewById(R.id.edit_edu_img);
        EducationET = findViewById(R.id.education);
        saveEducationEditing = findViewById(R.id.save_editing_education);


        //disable edicationET
        EducationET.setEnabled(false);
        saveEducationEditing.setVisibility(View.INVISIBLE);

        cameraChooser = findViewById(R.id.editProfileImageChooser);
        userImg = findViewById(R.id.edit_profile_pic);




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



    private void executeGetAllCertiicates(){

        ApiClients.getAPIs().getAllCertificates().enqueue(new Callback<List<Certificate>>() {
            @Override
            public void onResponse(Call<List<Certificate>> call, Response<List<Certificate>> response) {
                if (response.isSuccessful()){
                    int responseSize = response.body().size() -1 ;
                    Certificate certificate = response.body().get(responseSize);
                    EducationET.setText(certificate.getCertificates());
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
        }//End try block
        catch (Exception e){
            Log.e(getClass().getSimpleName(), "Error writing bitmap", e);
        }//End catch

    }//End fromBitmapToFile()


}
