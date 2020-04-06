package com.example.seeker.EmployerMainPages.AccountRelatedActivities;

import androidx.appcompat.app.AlertDialog;
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
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.seeker.Activities.ParentEditProfileActivity;
import com.example.seeker.Database.ApiClients;
import com.example.seeker.FreelancerMainPages.FreelancerEditProfile;
import com.example.seeker.FreelancerMainPages.SearchTab_Freelancer.AddSkillActivity;
import com.example.seeker.Model.Employer;
import com.example.seeker.Model.Freelancer;
import com.example.seeker.Model.Skill;
import com.example.seeker.Model.User;
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
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class EditProfileActivity extends ParentEditProfileActivity {



    private static final String LOG = EditProfileActivity.class.getSimpleName();
    private static final int INTENT_GALLERY = 301;
    private static final int INTENT_CAMERA = 401;

    private long current_user_id = MySharedPreference.getLong(EditProfileActivity.this, Constants.Keys.USER_ID, -1);
    private long current_emp_id = MySharedPreference.getLong(EditProfileActivity.this, Constants.Keys.EMPLOYER_ID, -1);
    private long current_freelancer_id = MySharedPreference.getLong(EditProfileActivity.this, Constants.Keys.FREELANCER_ID, -1);


    private ImageView exclamation_icon;
    private ImageView userImg, cameraChooser;
    private ImageView phoneNumber, nationalId;
    private ImageView linkedinImg, twitterImg, facebookImg;
    private Button saveLinkedin, saveTwitter, saveFb, saveEdu;
    private Button cancelLinkedin, cancelTwitter, cancelFb, cancelEdu;
    private ImageView editEducationIcon;
    private EditText linkedinET, twitterET, facebookET;
    private TextView name, nameAsFreelancer, nameAsEmployer;
    private EditText educationET;

    private File imageFile;


    private Intent cameraIntent, photoPickerIntent;

    private TextView totalTrustPoints_TV;

    private RatingBar empTotalRates;
    private TextView numberOfRatings;

//    todo: MAKE THEM PROGRESS BAR
      private TextView FrWorkedOnProjects, FrAvgResponseTime, FrAvgQualityOfWork,
               EmpNumOfProjects, EmpAvgResponseTime, EmpAvgOTP;

      private ProgressBar trustPointsPB;


//    previous samples


    //#1
    User user;

    /**
     *
     * Freelancer required declarations
     *
     *
     */

    private ImageView maroofImg;
//    + skill related stuff :/
    private LinearLayout skillsLL, divider;
    ImageButton add_skill;
    TextView skillText;
    private Set<Skill> skillsList = new HashSet<>();
//    private Freelancer freelancer;

    Freelancer fr;


    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
    @Override
    protected void onResume() {

        super.onResume();
//        this.onCreate(null);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);


        getUserById(current_user_id);

        init();
        fillCurrentUSerData();

        cameraChooser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCameraChooser();
            }
        });


        if(MySharedPreference.getString(EditProfileActivity.this,Constants.Keys.USER_CURRENT_TYPE,"0").equals("EMPLOYER")){
            CalculateEmpTP(current_user_id);
        } else if(MySharedPreference.getString(EditProfileActivity.this,Constants.Keys.USER_CURRENT_TYPE,"0").equals("FREELANCER")){
            CalculateFrTP(current_user_id);
        }



    }//End onCreate


    private void initToolbar(String username){
            //init toolbar
            Toolbar toolbar = findViewById(R.id.editprofile_toolbar);
            toolbar.setTitle(username);
            toolbar.setNavigationIcon(R.drawable.back_arrow_24dp);
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onBackPressed();
                }//End onClick()
            });

        }//End initToolBar()


    private void init() {
        add_skill = findViewById(R.id.add_skill_fr);
        add_skill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(EditProfileActivity.this, AddSkillActivity.class));
            }
        });
        totalTrustPoints_TV = findViewById(R.id.emp_total_trust_points);
        trustPointsPB = findViewById(R.id.TPprogressBar);

        name = findViewById(R.id.employer_edit_profile_name);
        nameAsEmployer = findViewById(R.id.edit_profile_name_as_emp);
        nameAsFreelancer = findViewById(R.id.edit_profile_name_as_fr);

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

        disableLinks();

        hidingButtons();


        cameraChooser = findViewById(R.id.editProfileImageChooser);
        userImg = findViewById(R.id.edit_profile_pic);

        exclamation_icon = findViewById(R.id.exclamation);
        exclamation_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                exclamationDialog("Note: Click on one of these icons to add/edit your social-media accounts.");
            }
        });

        onEditAnythingClicked();


        phoneNumber = findViewById(R.id.emp_phone_num);
        nationalId = findViewById(R.id.emp_nid_icon);
        maroofImg = findViewById(R.id.edit_maroof_icon);
        skillsLL = findViewById(R.id.skills_ll);
        divider = findViewById(R.id.skills_divider);

        if(MySharedPreference.getString(EditProfileActivity.this,Constants.Keys.USER_CURRENT_TYPE,"0").equals("EMPLOYER")){
            maroofImg.setVisibility(View.GONE);
            skillsLL.setVisibility(View.GONE);
            divider.setVisibility(View.GONE);
        }

        onIconsClicked();


        empTotalRates = findViewById(R.id.employer_total_rating_in_profile);
        Employer emp = new Employer(current_emp_id);

        if(MySharedPreference.getString(EditProfileActivity.this,Constants.Keys.USER_CURRENT_TYPE,"0").equals("EMPLOYER"))
        executeCalculateEmployerTotalRating(emp);
        else if(MySharedPreference.getString(EditProfileActivity.this,Constants.Keys.USER_CURRENT_TYPE,"0").equals("FREELANCER"))
        calculateFreelancerTotalRates(new Freelancer(current_freelancer_id));

//        getEmployer(current_emp_id);
        getEmployerByUserId(current_user_id);
        getFreelancerByUserIDRequest(current_user_id);
        numberOfRatings = findViewById(R.id.numOfRatings_EmployerProfile);



        initStats();


    }//End init()

    private void onEditAnythingClicked() {
        LinkedIn();
        Twitter();
        Facebook();
        Education();
    }

    private void hidingButtons() {

        //Hide Save and Cancel buttons
        saveLinkedin.setVisibility(View.INVISIBLE);
        saveTwitter.setVisibility(View.INVISIBLE);
        saveFb.setVisibility(View.INVISIBLE);
        saveEdu.setVisibility(View.INVISIBLE);

        cancelLinkedin.setVisibility(View.INVISIBLE);
        cancelTwitter.setVisibility(View.INVISIBLE);
        cancelFb.setVisibility(View.INVISIBLE);
        cancelEdu.setVisibility(View.INVISIBLE);
    }

    private void disableLinks() {
        //Disable links
        linkedinET.setEnabled(false);
        twitterET.setEnabled(false);
        facebookET.setEnabled(false);
        educationET.setEnabled(false);
    }//End disableLinks

    private void onIconsClicked() {
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

        maroofImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddInfoDialog(0);
            }
        });


    }

    private void initStats() {
        FrWorkedOnProjects = findViewById(R.id.emp_AsFreelancer_numOfWorkedOnProjects);
        FrAvgResponseTime = findViewById(R.id.emp_AsFreelancer_AvgResponseTime);
        FrAvgQualityOfWork = findViewById(R.id.emp_AsFreelancer_avgQuality);

        EmpNumOfProjects = findViewById(R.id.emp_AsEmployer_NumOfProjects);
        EmpAvgResponseTime = findViewById(R.id.emp_AsEmployerr_AvgResponseTime);
        EmpAvgOTP = findViewById(R.id.emp_AsEmployer_AvgOtp);

    }

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
                        //todo:
                        // add editing completed dialog ??
                        finish();
                        overridePendingTransition(0, 0);
                        startActivity(getIntent());
                        overridePendingTransition(0, 0);

                    }
                });

                cancelLinkedin.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        linkedinET.setEnabled(false);
                        saveLinkedin.setVisibility(View.INVISIBLE);
                        cancelLinkedin.setVisibility(View.INVISIBLE);
                        if (user.getLinkedIn() != null)
                        linkedinET.setText(user.getLinkedIn());
//                        getLinkedIn();
//                        finish();
//                        overridePendingTransition(0, 0);
//                        startActivity(getIntent());
//                        overridePendingTransition(0, 0);
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
                        finish();
                        overridePendingTransition(0, 0);
                        startActivity(getIntent());
                        overridePendingTransition(0, 0);

                    }
                });

                cancelTwitter.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        twitterET.setEnabled(false);
                        saveTwitter.setVisibility(View.INVISIBLE);
                        cancelTwitter.setVisibility(View.INVISIBLE);
                        if (user.getTwitter() != null)
                            twitterET.setText(user.getTwitter());
//                        getTwitter();
//                        finish();
//                        overridePendingTransition(0, 0);
//                        startActivity(getIntent());
//                        overridePendingTransition(0, 0);
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
                        finish();
                        overridePendingTransition(0, 0);
                        startActivity(getIntent());
                        overridePendingTransition(0, 0);

                    }
                });

                cancelFb.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        facebookET.setEnabled(false);
                        saveFb.setVisibility(View.INVISIBLE);
                        cancelFb.setVisibility(View.INVISIBLE);
                        if (user.getFacebook() != null)
                            facebookET.setText(user.getFacebook());
                        /**
                         *THIS IS GOOOD.
//                         */
//                        finish();
//                        overridePendingTransition(0, 0);
//                        startActivity(getIntent());
//                        overridePendingTransition(0, 0);
//                        getFacebook();
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
                        finish();
                        overridePendingTransition(0, 0);
                        startActivity(getIntent());
                        overridePendingTransition(0, 0);

                    }

                });

                cancelEdu.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
//                        getEducation();
                        educationET.setEnabled(false);
                        saveEdu.setVisibility(View.INVISIBLE);
                        cancelEdu.setVisibility(View.INVISIBLE);
                        if (user.getEducation() != null)
                        educationET.setText(user.getEducation());
//                        else educationET.setText("No education added.");
//
//                        finish();
//                        overridePendingTransition(0, 0);
//                        startActivity(getIntent());
//                        overridePendingTransition(0, 0);

                    }
                });

            }//end onClick


        });
    }

    private void wrongInfoDialog(String msg) {

        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);

        // Setting Dialog Message
        alertDialog.setMessage(msg);


        //Setting Negative "ok" Button
        alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }//end onClick
        });//end setPositiveButton

        alertDialog.show();

    }//End wrongInfoDialog()

    private void fillCurrentUSerData(){
        String username = MySharedPreference.getString(EditProfileActivity.this, Constants.Keys.USER_NAME, "");
        String capitalizedName = username.substring(0,1).toUpperCase() + username.substring(1,username.length());

        name.setText(capitalizedName);

        nameAsFreelancer.setText(capitalizedName+" as Freelancer:");

        nameAsEmployer.setText(capitalizedName+ " as Employer:");

        initToolbar(capitalizedName);

    }

    private void executeAddLinkedInRequest(long id, String linkedin) {


        ApiClients.getAPIs().getPostLinkedInRequest(id, linkedin).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()){
                    user = response.body();
                } else {
                    Toast.makeText(EditProfileActivity.this, response.errorBody().toString(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(EditProfileActivity.this, t.getLocalizedMessage(), Toast.LENGTH_LONG).show();
            }
        });


    }//end add linkedin

    private void executeAddTwitterRequest(long id, String twitter){

        ApiClients.getAPIs().getPostTwitterRequest(id, twitter).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()){
                    user = response.body();
                    //todo either call calc tp or refresh activity.
                } else {
                    Toast.makeText(EditProfileActivity.this, response.errorBody().toString(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(EditProfileActivity.this, t.getLocalizedMessage(), Toast.LENGTH_LONG).show();
            }
        });

    }//end add twitter

    private void executeAddFacebookRequest(long id, String facebook){

        ApiClients.getAPIs().getPostFacebookRequest(id, facebook).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()){
                    user = response.body();
                } else {
                    Toast.makeText(EditProfileActivity.this, response.errorBody().toString(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(EditProfileActivity.this, t.getLocalizedMessage(), Toast.LENGTH_LONG).show();
            }
        });


    }//end add facebook

    private void executeAddEducationRequest(long id, String education) {

        ApiClients.getAPIs().getPostEducation(id, education).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()){
                    user = response.body();
                } else {
                    Toast.makeText(EditProfileActivity.this, response.errorBody().toString(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(EditProfileActivity.this, t.getLocalizedMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }



    /**
     * FOR CHOOSING IMAGE
     */

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


    //todo: check validity of phone number, maroof acc and national id.

    private void AddInfoDialog(int type) {
        /**
         * type 0 -> maroof
         * type 1 -> phone
         * type 2 -> nid
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
                if (fr.getMaarof_account() != null)
                    infoET.setText(fr.getMaarof_account());
                else
                    infoET.setHint("Maarof account");
                break;

            case 1:
                title.setText("Please enter your phone number");
                if (user.getPhone_number() != null)
                    //TODO: REMOVE AFTER VALIDATION
                    infoET.setText(user.getPhone_number());
                else
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
                            executeAddMaroofAccRequest(MySharedPreference.getLong(EditProfileActivity.this, Constants.Keys.FREELANCER_ID, -1), infoET.getText().toString());
                            dialogBuilder.dismiss();

                            break;

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

    //ZERO
    private void executeAddMaroofAccRequest(long id, String maroofAcc) {

        ApiClients.getAPIs().getPostMaroofAccountRequest(id, maroofAcc).enqueue(new Callback<Freelancer>() {
            @Override
            public void onResponse(Call<Freelancer> call, Response<Freelancer> response) {
                if (response.isSuccessful()){
                    fr = response.body();
                } else {
                    Toast.makeText(EditProfileActivity.this, response.errorBody().toString(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Freelancer> call, Throwable t) {
                Toast.makeText(EditProfileActivity.this, t.getLocalizedMessage(), Toast.LENGTH_LONG).show();
            }
        });

    }//end add maroof


    //ONE
    private void executeAddPhoneNumberRequest(long id, String phone) {


        ApiClients.getAPIs().getPostPhoneNumberRequest(id, phone).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()){
                    user = response.body();
                } else {
                    Toast.makeText(EditProfileActivity.this, response.errorBody().toString(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(EditProfileActivity.this, t.getLocalizedMessage(), Toast.LENGTH_LONG).show();
            }
        });

    }//end add phone

    //TWO
    private void executeAddNationalIdRequest(long id, String NationalId){

        ApiClients.getAPIs().getPostNationalIdRequest(id, NationalId).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()){
                    user = response.body();
                } else {
                    Toast.makeText(EditProfileActivity.this, response.errorBody().toString(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(EditProfileActivity.this, t.getLocalizedMessage(), Toast.LENGTH_LONG).show();
            }
        });

    }

    private void executeCalculateEmployerTotalRating(Employer employer_id){
        ApiClients.getAPIs().CalculateEmployerTotalRating(employer_id).enqueue(new Callback<Double>() {
            @Override
            public void onResponse(Call<Double> call, Response<Double> response) {
                if (response.isSuccessful()){
                    //None worked in presenting the value as double
                    // يقرب العدد بعدين يحطه :)
                    empTotalRates.setRating(Float.valueOf(String.valueOf(response.body())));
//                    empTotalRates.setRating(response.body().floatValue());
                }

            }

            @Override
            public void onFailure(Call<Double> call, Throwable t) {

            }
        });
    }

    private void calculateFreelancerTotalRates(Freelancer freelancer_id) {
        ApiClients.getAPIs().CalculateFreelancerTotalRating(freelancer_id).enqueue(new Callback<Double>() {
            @Override
            public void onResponse(Call<Double> call, Response<Double> response) {
                if (response.isSuccessful())
                    empTotalRates.setRating(Float.valueOf(String.valueOf(response.body())));

            }

            @Override
            public void onFailure(Call<Double> call, Throwable t) {

            }
        });
    }

    private void getEmployerByUserId(long user_id) {

        ApiClients.getAPIs().getEmployerByUserIdRequest(user_id).enqueue(new Callback<Employer>() {
            @Override
            public void onResponse(Call<Employer> call, Response<Employer> response) {
                if (response.isSuccessful()){
                    if(MySharedPreference.getString(EditProfileActivity.this,Constants.Keys.USER_CURRENT_TYPE,"0").equals("EMPLOYER")){
                        numberOfRatings.setText("("+response.body().getNum_of_ratings()+")");
                    }

                    //Filling As employer statistics
                    EmpNumOfProjects.setText(response.body().getNum_of_posted_Projects()+"");

                    double response_time = response.body().getResponse_time();
                    double num_of_ratings = response.body().getNum_of_ratings();
                    double avgRT =( ((response_time/num_of_ratings) /5) *100);
                    double total_otp = response.body().getTotal_on_time_payment();
                    double avgOTP = ((total_otp/num_of_ratings)/5) *100;
                    EmpAvgResponseTime.setText((int)avgRT+"%");
                    EmpAvgOTP.setText((int)avgOTP+"%");


                }
            }

            @Override
            public void onFailure(Call<Employer> call, Throwable t) {

            }
        });

    }

    private void getFreelancerByUserIDRequest(long user_id ){

        ApiClients.getAPIs().getFreelancerByUserIdRequest(user_id).enqueue(new Callback<Freelancer>() {
            @Override
            public void onResponse(Call<Freelancer> call, Response<Freelancer> response) {
                if (response.isSuccessful()){
                    fr = response.body();
                    if(MySharedPreference.getString(EditProfileActivity.this,Constants.Keys.USER_CURRENT_TYPE,"0").equals("FREELANCER")){
                        numberOfRatings.setText("("+response.body().getNum_of_ratings()+")");
                    }

                    FrWorkedOnProjects.setText(response.body().getNum_of_hired_projects()+"");
                    double response_time = response.body().getTotal_response_time();
                    double num_of_ratings = response.body().getNum_of_ratings();
                    double avgRT =( ((response_time/num_of_ratings) /5) *100);

                    double quality = response.body().getTotal_quality_of_work();
                    double avgQ =( (quality/num_of_ratings)/5 )*100;

                    FrAvgResponseTime.setText((int)avgRT+"%");
                    FrAvgQualityOfWork.setText((int)avgQ+"%");

                    Log.i(LOG,"onResponse: suc" + fr.toString());
                    skillsList = fr.getSkills();
                    Log.i(LOG,"onResponse: suc" + fr.getSkills());
                    Log.i(LOG,"onResponse: suc" + skillsList.toString());

                    skillText = findViewById(R.id.list_skills);
                    String skillToDisplay ="";

                    for (Skill subset : skillsList) {
                        skillToDisplay += " - " +subset.getName() ;
                    }

                    skillText.setText(skillToDisplay );





                } else{
                    Log.i(LOG,"onResponse: notSuc" + response.toString());
                }
            }

            @Override
            public void onFailure(Call<Freelancer> call, Throwable t) {
                Log.i(LOG,"onFailure :" + t.toString());
            }
        });






    }//End getFreelancerByUserIdRequest()

    private void CalculateEmpTP(long id){
        ApiClients.getAPIs().CalculateEmployerTrustPoints(id).enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                if (response.isSuccessful()){
                    totalTrustPoints_TV.setText(response.body()+"%");
                    trustPointsPB.setProgress(response.body());
                }

            }

            @Override
            public void onFailure(Call<Integer> call, Throwable t) {

            }
        });
    }//End CalculateEmpTP()

    private void CalculateFrTP(long id){
        ApiClients.getAPIs().CalculateFreelancerTrustPoints(id).enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                if (response.isSuccessful()){
                    totalTrustPoints_TV.setText(response.body()+"%");
                    trustPointsPB.setProgress(response.body());
                }
            }

            @Override
            public void onFailure(Call<Integer> call, Throwable t) {

            }
        });
    }

    private void getUserById(long id){
        ApiClients.getAPIs().findUserById(id).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()){
                    user = response.body();


                    //Set Education
                    if (user.getEducation() != null)
                        educationET.setText(user.getEducation());
                    else
                        educationET.setText("No education added.");

                    //Set LinkedIn
                    if (user.getLinkedIn() != null)
                        linkedinET.setText(user.getLinkedIn());
                    else
                        linkedinET.setText("NA");

                    //Set Twitter
                    if (user.getTwitter() != null)
                        twitterET.setText(user.getTwitter());
                    else
                        twitterET.setText("NA");

                    //Set Facebook
                    if (user.getFacebook() != null)
                        facebookET.setText(user.getFacebook());
                    else
                        facebookET.setText("NA");




                }
//                    user = response.body();
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

            }
        });
    }




}
