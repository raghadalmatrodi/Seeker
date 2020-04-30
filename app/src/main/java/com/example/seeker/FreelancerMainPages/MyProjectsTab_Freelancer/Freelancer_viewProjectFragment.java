package com.example.seeker.FreelancerMainPages.MyProjectsTab_Freelancer;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.Image;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.seeker.Contract.ContractFragment;
import com.example.seeker.Database.ApiClients;
import com.example.seeker.EmployerMainPages.AcceptBidConfirmation;
import com.example.seeker.EmployerMainPages.AccountRelatedActivities.SampleWorkAdapter;
import com.example.seeker.EmployerMainPages.AccountRelatedActivities.ViewAttachmentActivity;
import com.example.seeker.EmployerMainPages.Chat_Emp.Emp_ChatMessages;
import com.example.seeker.EmployerMainPages.MyProjectsTab_Emp.ProjectsStatusFragments.Emp_MyProjects_Pending_Fragment;
import com.example.seeker.Model.Bid;
import com.example.seeker.Model.Chat;
import com.example.seeker.Model.Contract;
import com.example.seeker.Model.Employer;
import com.example.seeker.Model.EmployerRating;
import com.example.seeker.Model.Freelancer;
import com.example.seeker.Model.Project;
import com.example.seeker.Model.Responses.ApiResponse;
import com.example.seeker.Model.Skill;
import com.example.seeker.Model.StorageDocument;
import com.example.seeker.Model.User;
import com.example.seeker.PostBid.BidsAdapter;
import com.example.seeker.PostBid.PostBidActivity;
import com.example.seeker.PostBid.ViewFullBid;
import com.example.seeker.R;
import com.example.seeker.SharedPref.Constants;
import com.example.seeker.SharedPref.MySharedPreference;
import com.example.seeker.ViewProfileActivity;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Freelancer_viewProjectFragment extends Fragment implements  Emp_MyProjects_Pending_Fragment.ProjectListener ,BidsAdapter.BidsAdapterListener   , SampleWorkAdapter.OnItemClickListener{

    private static final String LOG = Freelancer_viewProjectFragment.class.getSimpleName();

    Project project ;
    TextView title;
    TextView descreption;
    TextView budget;
    TextView type;
    TextView skills;
    TextView deadline;
    ImageView backButton;
    TextView employerName;
    LinearLayout EmployerView;
    ImageView chat;
    Contract contract;
    TextView createdAt;

    Button placeBidBtn;
    ImageView employerPic;
//    boolean hasBid = false;
    long currentFreelancer = MySharedPreference.getLong(getContext(), Constants.Keys.FREELANCER_ID, -1);
    List<StorageDocument> files;
    private SampleWorkAdapter sampleWorkAdapter;

    String checkFlag = "";
    boolean did_fr_rate = false;
    long empId, frId;

    ImageView contractImg;
    User user;

    Emp_MyProjects_Pending_Fragment emp_myProjects_pending_fragment;
    private RecyclerView recyclerView;
    private BidsAdapter adapter;
    private List<Chat> chatList;

    View view;
    List<Bid> bids;
    //todo hind remove if didnt work
    TextView number_of_bids;
    int isPending=0;

    int counter = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

         view = inflater.inflate(R.layout.freelancer_fragment_view_project, container, false);
        chatList = new ArrayList<>();

         init();

        emp_myProjects_pending_fragment = new Emp_MyProjects_Pending_Fragment();

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            project = (Project) bundle.getSerializable("project");
             isPending = bundle.getInt("pending" ,0);
            checkFlag= bundle.getString("flag");
        }

        did_fr_rate = project.isDid_fr_rate();
        findFrId();
        empId = project.getEmployer().getId();

        if (checkFlag != null)
            if (checkFlag.equals("FC") && counter == 0){

//            wrongInfoDialog("TEST WORKED!");
                if (!did_fr_rate){
                    //show rating dialog
                    ShowRatingCustomDialog(getContext());
                    counter++;
                    //update the project.
//                didEmployerRate();
                }
            }

        setProjectInformation();

        contractImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Fragment fragment = new ContractFragment();
                Bundle bundle = new Bundle();
                bundle.putSerializable("project",project);
                fragment.setArguments(bundle);
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frame_container_freelancer, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

//        bids = project.getBids();
        number_of_bids = view.findViewById(R.id.numberOfBids);
        findBidsByProject();

//        if (bids != null)
//        number_of_bids.setText("("+ (bids.size() )+ ")");


        emp_myProjects_pending_fragment.setListener(this);
          backButton.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                  FragmentManager fm = getFragmentManager();
                  if (fm.getBackStackEntryCount() > 0) {
                      Log.i("MainActivity", "popping backstack");
                      fm.popBackStack();
                  } else {
                      Log.i("MainActivity", "nothing on backstack, calling super");
                      //super.onBackPressed();
                  }
              }
          });

          chat.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                  if(project.getEmployer() != null)
                excecuteChatRequest();
              }
          });

//        setTheAdapter();

        HidingPlaceBidBtn();

        onPostBidClicked(project);
        setTheAttachmentAdapter();


        return view;

    }


    @Override
    public void onResume() {
        super.onResume();
        getUser();
        findBidsByProject();

        if (checkFlag != null)
            if (checkFlag.equals("FC") && counter == 0){

//            wrongInfoDialog("TEST WORKED!");
                if (!did_fr_rate){
                    //show rating dialog
                    ShowRatingCustomDialog(getContext());
                    counter++;
                    //update the project.
//                didEmployerRate();
                }
            }

    }

    private void findBidsByProject() {
        ApiClients.getAPIs().findBidsByProjectId(project.getId()).enqueue(new Callback<List<Bid>>() {
            @Override
            public void onResponse(Call<List<Bid>> call, Response<List<Bid>> response) {
                if (response.isSuccessful()) {
                    bids = response.body();
                    if (bids != null) {
                        number_of_bids.setText("(" + (bids.size()) + ")");
                        setTheAdapter(bids);

                    }
                }
            }

            @Override
            public void onFailure(Call<List<Bid>> call, Throwable t) {

            }
        });
    }

    private boolean HasBid(List<Bid> bidList) {
        boolean hasBid = false;
        //Loop to check whether current freelancer has bade on this project or not
        for (int i = 0; i< bidList.size(); i++){
            if (bidList.get(i).getFreelancer() != null )
                if (bidList.get(i).getFreelancer().getId() == currentFreelancer) {
//                    Toast.makeText(getContext(),"BID FOUND!",Toast.LENGTH_SHORT).show();
                    hasBid =  true;
//                    hasBid = true;
                }
        }

        return hasBid;
    }

    private void onPostBidClicked(Project project) {

        placeBidBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (user.getIsEnabled().equals("1")) {



                if (HasBid(bids))
                        wrongInfoDialog("Sorry..",getString(R.string.already_has_bid_msg));
                    else {

                        if (project.getEmployer().getId() == MySharedPreference.getLong(getContext(), Constants.Keys.EMPLOYER_ID, -1)){
                            wrongInfoDialog("We're sorry..","You can't place a bid on your project.");
                        }else {
                            if (project.getType().equals("1"))
                                onFieldBidsDialog("Warning!","We're irresponsible for the payments of on-field projects.");

                            else {
                                /**
                                 * INTENT TO POST BID WITH CURRENT PROJECT
                                 */
                                Intent intent = new Intent(getActivity(), PostBidActivity.class);
                                intent.putExtra("currentProjObj", project);
                                startActivity(intent);

                            }



                        }

                }//else(HasBid(bids))
                }//if not enabled
                else{
                    wrongInfoDialog("","Your Account has been deactivated  \n to further information contact the support");
                }


            }


        });

    }
    private void getUser() {

        long user_id = MySharedPreference.getLong(getContext(), Constants.Keys.USER_ID, -1);

        ApiClients.getAPIs().findUserById(user_id).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()){

                    user = response.body();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

            }
        });
    }

    private void HidingPlaceBidBtn() {
        if (project.getStatus().equals("1") || project.getStatus().equals("2"))
            placeBidBtn.setVisibility(View.GONE);
    }//End HidingPlaceBidBtn()

    private void excecuteChatRequest() {
        Long user1_id = MySharedPreference.getLong(getContext(),Constants.Keys.USER_ID,-1);
        Long user2_id = new Long(project.getEmployer().getUser().getId());
        ApiClients.getAPIs().findChat(user1_id,user2_id).enqueue(new Callback<Chat>() {
            @Override
            public void onResponse(Call<Chat> call, Response<Chat> response) {
                if(response.isSuccessful()) {
                    Log.i(LOG, "onResponse  suc: " + response.body().toString());

                    Chat chat = response.body();
                    executeIntent(chat);


                }else {
                    Log.i(LOG, "onResponse not suc: " + response.toString());

                }
            }

            @Override
            public void onFailure(Call<Chat> call, Throwable t) {
                Log.i(LOG, "onFailure not suc: " + t.getMessage());

            }
        });

    }

    private void executeIntent(Chat chat) {
        Log.i(LOG, "onClick : " );

        Intent intent = new Intent(getActivity(),Emp_ChatMessages.class);
        intent.putExtra("chat" , chat);
        startActivity(intent);
    }


    private void init() {
        backButton = view.findViewById(R.id.project_view_back);
        title = view.findViewById(R.id.project_title);
        descreption = view.findViewById(R.id.project_des);
        deadline = view.findViewById(R.id.project_date);
        budget = view.findViewById(R.id.project_budget);
        type = view.findViewById(R.id.project_type);
        skills = view.findViewById(R.id.project_skills);
        employerName = view.findViewById(R.id.employer_name);
        contractImg = view.findViewById(R.id.contract_img);
        EmployerView = view.findViewById(R.id.employer_row);
        chat = view.findViewById(R.id.chat);
        createdAt = view.findViewById(R.id.createdAt_project);
        placeBidBtn = view.findViewById(R.id.place_bid_btn);
        employerPic = view.findViewById(R.id.employer_picture);



    }

    @Override
    public void onProjectItemSelected(Project project) {
    this.project = project;

    }
    public void setProjectInformation(){

        if(project.getEmployer()!=null)
        if(project.getEmployer().getId() == MySharedPreference.getLong(getContext(),Constants.Keys.EMPLOYER_ID,-1)){
            EmployerView.setVisibility(View.GONE);
        }


        if(project.getEmployer().getUser().getAvatar()!= null)
            Glide.with(getActivity())
                    .load(project.getEmployer().getUser().getAvatar())
                    .placeholder(R.drawable.user).apply(RequestOptions.circleCropTransform())
                    .into(employerPic);

        if( (project.getTitle() != null))
            if(!project.getTitle().trim().equals(""))
                title.setText(project.getTitle());


        if(project.getDescription() != null )
            if(!project.getDescription().trim().equals(""))
                descreption.setText(project.getDescription());

        if(!(project.getBudget() == 0))
            budget.setText(project.getBudget() +"");

        if(project.getType() != null)
        if(!project.getType().trim().equals("") || !(project.getType() == null)){

            if(project.getType().equals("0")){
                type.setText("Online");
            }
            else {
                type.setText("On-field");
            }

        }


        String skill ="";

        if( project.getSkills() !=null){

            if (!project.getSkills().isEmpty()) {
                for (Skill s : project.getSkills()) {
                    skill += s.getName() + " \n";
                }
            }
            skills.setText(skill);
        }

        if(project.getEmployer() != null)
        employerName.setText(project.getEmployer().getUser().getUsername());

        if(project.getDeadline() != null){
            deadline.setText(project.getDeadline().substring(0,10));
        }
        if(project.getCreatedAt() != null){
            createdAt.setText(project.getCreatedAt().substring(0,10));
        }


        if(project.getStatus() != null)
        if(project.getStatus().equals("0")){
            contractImg.setVisibility(View.INVISIBLE);
        }else{
            performContractAPIRequest(project.getId());


        }

        onEmpClicked();
    }

    private void onEmpClicked() {
        EmployerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ViewProfileActivity.class);
                intent.putExtra("myuser", project.getEmployer().getUser());
                startActivity(intent);
            }
        });
    }

    public void setTheAdapter(List<Bid> bidd){
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view_b);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new BidsAdapter(getContext(),bidd, project);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
        adapter.setListener(this);
        recyclerView.setNestedScrollingEnabled(true);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);


    }
    public void setTheAttachmentAdapter()  {
        files = project.getAttachments();
        if(!files.isEmpty()) {
            TextView noAttachments = view.findViewById(R.id.no_attachment);
            noAttachments.setVisibility(View.GONE);
        }
        recyclerView =  view.findViewById(R.id.attachment_recycle_view);
        sampleWorkAdapter = new SampleWorkAdapter(getContext(), files);
        LinearLayoutManager horizontalLayoutManagaer = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(horizontalLayoutManagaer);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        sampleWorkAdapter.setOnItemClickListener(this);
        recyclerView.setAdapter(sampleWorkAdapter);
    }

    @Override
    public void onItemClick(StorageDocument storageDocument) {
        List<String> images = new ArrayList<>();
        List<Image> images1 =null;
        project.getAttachments().forEach(sampleWork -> {
            images.add(sampleWork.getUrl());
        });

        Intent intent = new Intent(getContext() , ViewAttachmentActivity.class);
        intent.putExtra("image" , storageDocument.getUrl());
        startActivity(intent);
    }


    @Override
    public void onBidItemClick(Bid bid) {

        Intent i = new Intent(getContext(), ViewFullBid.class);
        i.putExtra("bidObj", bid);
        startActivity(i);
    }



    @Override
    public void onAcceptBidItemClick(Bid bid) {

        Fragment fragment = new AcceptBidConfirmation();
        Bundle bundle = new Bundle();
        bundle.putSerializable("bid",bid);
        fragment.setArguments(bundle);
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_container_emp, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();

    }
    public void performContractAPIRequest(long project_id){


        ApiClients.getAPIs().getContractByProjectIdRequest(project_id).enqueue(new Callback<Contract>() {

            @Override
            public void onResponse(Call<Contract> call, Response<Contract> response) {


                if (response.isSuccessful()){

                    contract = response.body();

                    if(contract != null){

                        if(contract.getFreelancer().getId() == MySharedPreference.getLong(getContext(), Constants.Keys.FREELANCER_ID,-1)){
                            contractImg.setVisibility(View.VISIBLE);
                        }
                    }



                    Log.i("on Response: contract suc", response.message());

                }
                Log.i("on Response: contract Notsuc", response.message());
            }

            @Override
            public void onFailure(Call<Contract> call, Throwable t) {
                Log.i("on Response: contract fail", t.getMessage());
            }
        });

    }


    private void wrongInfoDialog(String title, String msg) {
        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(getContext());

        alertDialog.setTitle(title);
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

    private void onFieldBidsDialog(String title, String msg) {
        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(getContext());

        alertDialog.setTitle(title);
        // Setting Dialog Message
        alertDialog.setMessage(msg);

        //Setting Negative "ok" Button
        alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                Intent intent = new Intent(getActivity(), PostBidActivity.class);
                intent.putExtra("currentProjObj", project);
                startActivity(intent);
            }//end onClick
        });//end setPositiveButton
        alertDialog.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        alertDialog.show();

    }//End onFieldBidsDialog()


    /**
     *  RATING RELATED METHODS
     */
    private int professionalismE , onTimePaymentE , responseTimeE ;




    float empTotalRates;
    //Values will be returned in this order
    private int  num_of_ratings;
    private int  response_time;
    private int  total_on_time_payment;

    private void findFrId(){
        for (int i = 0; i < project.getBids().size(); i++){
            if (project.getBids().get(i).getStatus().equals("accepted"))
                frId = project.getBids().get(i).getFreelancer().getId();
        }
    }


    TextView q1, q2, q3;
    RatingBar r1, r2, r3;
    Button finish;

    public void ShowRatingCustomDialog(Context c) {

        Dialog rankDialog = new Dialog(c);
        rankDialog.setContentView(R.layout.fr_rate_dialog);
        rankDialog.setCancelable(false);
        rankDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));



        r1 = rankDialog.findViewById(R.id.rbq_one);
        r2 = rankDialog.findViewById(R.id.rbq_two);
        r3  = rankDialog.findViewById(R.id.rbq_three);

        q1 = rankDialog.findViewById(R.id.rq_one);
        q2 = rankDialog.findViewById(R.id.rq_two);
        q3 = rankDialog.findViewById(R.id.rq_three);

        finish = rankDialog.findViewById(R.id.finish);

        setUpQs();

        getRates();



        Employer employer = new Employer(empId);
        Freelancer freelancer = new Freelancer(frId);

        getEmployerTotalRatingVal(empId);
        getRatingValues(empId);



        finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                RateTheEmp(professionalismE , onTimePaymentE , responseTimeE);

                performRateEmployerRequest(new EmployerRating(responseTimeE, professionalismE, onTimePaymentE, freelancer, employer));

                didFrRate();
                rankDialog.dismiss();
            }
        });

        rankDialog.show();

    }

    private void getRates() {
        r1.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                professionalismE = (int)ratingBar.getRating();
            }
        });

        r2.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                onTimePaymentE = (int)ratingBar.getRating();
            }
        });


        r3.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                responseTimeE = (int)ratingBar.getRating();
            }
        });

    }


    private void setUpQs() {

        q1.setText(R.string.emp_professionalisim_rating_q);
        q2.setText(R.string.emp_otp_rating_q);
        q3.setText(R.string.emp_response_time_rating_q);

    }






    private void getEmployerTotalRatingVal(long id){
        ApiClients.getAPIs().getEmployerTotalRating(id).enqueue(new Callback<Float>() {
            @Override
            public void onResponse(Call<Float> call, Response<Float> response) {
                if (response.isSuccessful()) {
                    empTotalRates = response.body();
//                    Toast.makeText(FreelancerRatesEmployer.this,"success",Toast.LENGTH_LONG).show();

                } else {
//                    Toast.makeText(FreelancerRatesEmployer.this,"not success: "+response.errorBody().toString(),Toast.LENGTH_LONG).show();

                }
            }

            @Override
            public void onFailure(Call<Float> call, Throwable t) {
//                Toast.makeText(FreelancerRatesEmployer.this,"failure: "+t.getLocalizedMessage(),Toast.LENGTH_LONG).show();

            }
        });
    }

    private void getRatingValues(long id){

        ApiClients.getAPIs().getEmployerRatingValues(id).enqueue(new Callback<List<Integer>>() {
            @Override
            public void onResponse(Call<List<Integer>> call, Response<List<Integer>> response) {
                if (response.isSuccessful()) {

                    num_of_ratings =  response.body().get(0);
                    response_time = response.body().get(1);
                    total_on_time_payment = response.body().get(2);

//                    Toast.makeText(FreelancerRatesEmployer.this,"success",Toast.LENGTH_LONG).show();

                } else {
//                    Toast.makeText(FreelancerRatesEmployer.this,"not success: "+response.errorBody().toString(),Toast.LENGTH_LONG).show();

                }

            }

            @Override
            public void onFailure(Call<List<Integer>> call, Throwable t) {
//                Toast.makeText(FreelancerRatesEmployer.this,"failure: "+t.getLocalizedMessage(),Toast.LENGTH_LONG).show();

            }
        });
    }



    public void RateTheEmp(int professionalismE , int onTimePaymentE, int responseTimeE ){
        float ratingEmp;
        float avg;
        //Q1 -> professionalism
        //Q2 -> on time payment
        //Q3 -> response time

        ratingEmp = ((float)professionalismE + (float)onTimePaymentE + (float)responseTimeE)/3;
        empTotalRates+= ratingEmp;
        num_of_ratings+= 1;

        response_time+= responseTimeE;
        total_on_time_payment+= onTimePaymentE;

        avg = empTotalRates/ ((float)num_of_ratings);

        setRatingValues(new Employer(empId, num_of_ratings, response_time, total_on_time_payment, empTotalRates));


    }




    private void setRatingValues(Employer employer){
        ApiClients.getAPIs().setAllEmployerRatingValues(employer, project.getId()).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful())
                    Toast.makeText(getContext(),"success",Toast.LENGTH_LONG).show();
                else
                    Toast.makeText(getContext(),"not success: "+response.errorBody().toString(),Toast.LENGTH_LONG).show();

            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(getContext(),"set values failure: "+t.getLocalizedMessage(),Toast.LENGTH_LONG).show();

            }
        });
    }


    private void performRateEmployerRequest(EmployerRating employerRating){

        ApiClients.getAPIs().getRateEmployerRequest(employerRating).enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                if (response.isSuccessful())
                    Toast.makeText(getContext(), "Successful", Toast.LENGTH_LONG).show();
                else
                    Toast.makeText(getContext(), "Not Successful: "+ response.errorBody().toString(), Toast.LENGTH_LONG).show();

            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                Toast.makeText(getContext(), t.getLocalizedMessage(), Toast.LENGTH_LONG).show();

            }
        });

    }

    private void didFrRate(){
        ApiClients.getAPIs().DidFreelancerRate(project.getId(), true).enqueue(new Callback<Project>() {
            @Override
            public void onResponse(Call<Project> call, Response<Project> response) {
                if (response.isSuccessful())
                    project = response.body();
            }

            @Override
            public void onFailure(Call<Project> call, Throwable t) {

            }
        });
    }

//    @Override
//    public void onResume() {
//        super.onResume();
//        findBidsByProject();
//    }

}
