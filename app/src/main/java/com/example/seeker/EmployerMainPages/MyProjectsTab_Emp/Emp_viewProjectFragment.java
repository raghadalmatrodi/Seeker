package com.example.seeker.EmployerMainPages.MyProjectsTab_Emp;

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
import android.view.WindowManager;
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
import com.example.seeker.EmployerMainPages.AccountRelatedActivities.EditProfileActivity;
import com.example.seeker.EmployerMainPages.AccountRelatedActivities.SampleWorkAdapter;
import com.example.seeker.EmployerMainPages.AccountRelatedActivities.ViewAttachmentActivity;
import com.example.seeker.EmployerMainPages.Chat_Emp.Emp_ChatMessages;
import com.example.seeker.EmployerMainPages.MyProjectsTab_Emp.ProjectsStatusFragments.Emp_MyProjects_Pending_Fragment;
import com.example.seeker.Model.Bid;
import com.example.seeker.Model.Chat;
import com.example.seeker.Model.Contract;
import com.example.seeker.Model.Employer;
import com.example.seeker.Model.Freelancer;
import com.example.seeker.Model.FreelancerRating;
import com.example.seeker.Model.Project;
import com.example.seeker.Model.Responses.ApiResponse;
import com.example.seeker.Model.Skill;
import com.example.seeker.Model.StorageDocument;
import com.example.seeker.PostBid.BidsAdapter;
import com.example.seeker.R;
import com.example.seeker.SharedPref.Constants;
import com.example.seeker.SharedPref.MySharedPreference;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Emp_viewProjectFragment extends Fragment implements  Emp_MyProjects_Pending_Fragment.ProjectListener ,BidsAdapter.BidsAdapterListener   , SampleWorkAdapter.OnItemClickListener{

    private static final String LOG = Emp_viewProjectFragment.class.getSimpleName();

    Project project ;
    TextView title;
    TextView descreption;
    TextView budget;
    TextView type;
    TextView skills;
    TextView deadline;
    ImageView backButton;
    TextView employerName;
    TextView createdAt;
    LinearLayout EmployerView;
    ImageView chat;
    Contract contract;
    ImageView employerPic;
    String checkFlag = "";
    boolean did_emp_rate = false;



    ImageView contractImg;
    List<StorageDocument> files;


    Emp_MyProjects_Pending_Fragment emp_myProjects_pending_fragment;
    private RecyclerView recyclerView;
    private BidsAdapter adapter;
    private List<Chat> chatList;
    private SampleWorkAdapter sampleWorkAdapter;

    View view;
    List<Bid> bids;
    int isPending=0;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

         view = inflater.inflate(R.layout.fragment_view_project, container, false);
        chatList = new ArrayList<>();

         init();

        emp_myProjects_pending_fragment = new Emp_MyProjects_Pending_Fragment();
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING);

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            project = (Project) bundle.getSerializable("project");
             isPending = bundle.getInt("pending" ,0);
             checkFlag= bundle.getString("flag");

        }
        did_emp_rate = project.isDid_emp_rate();
        findFrId();

        if (checkFlag != null)
        if (checkFlag.equals("EC")){
//            wrongInfoDialog("TEST WORKED!");
            if (!did_emp_rate){
                //show rating dialog
                ShowCustomDialog(getContext());
                //update the project.
//                didEmployerRate();
            }
        }

        setProjectInformation();

        contractImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(contract.getProject().getEmployer().getId() == MySharedPreference.getLong(getContext(),Constants.Keys.EMPLOYER_ID, -1)){


                Fragment fragment = new ContractFragment();
                Bundle bundle = new Bundle();
                bundle.putSerializable("project",project);
                fragment.setArguments(bundle);
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frame_container_emp, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
                }

            }
        });

        bids = project.getBids();
        TextView number_of_bids = view.findViewById(R.id.numberOfBids);
        number_of_bids.setText("("+ (bids.size() )+ ")");


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

        setTheAdapter();
        setTheAttachmentAdapter();


        return view;

    }


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
    }


    public void setTheAdapter(){

        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view_b);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new BidsAdapter(getContext(),bids, project);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
        adapter.setListener(this);
        recyclerView.setNestedScrollingEnabled(true);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);
        if(isPending==1)
        adapter.showAccept();

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

                        if((contract.getProject().getEmployer().getId() == MySharedPreference.getLong(getContext(),Constants.Keys.EMPLOYER_ID,-1))){
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


    /**
     *  RATING RELATED METHODS
     */


    TextView q1, q2, q3, q4, q5;
    RatingBar r1, r2, r3, r4, r5;
    Button finish;
    //TODO: UPDATE VALS
    long empId;
    long frId;

    private int professionalismF , respectOfDeadlinesF, responseTimeF, budgetF, qualityF ;
    private int  num_of_ratings;
    private int  total_response_time;
    private int  total_quality_of_work;

    public void ShowCustomDialog(Context c) {

        Dialog rankDialog = new Dialog(c);
        rankDialog.setContentView(R.layout.emp_rate_fr_dialog);
        rankDialog.setCancelable(false);
        rankDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));


//        total = findViewById(R.id.tv);

        q1 = rankDialog.findViewById(R.id.q_one);
        r1 = rankDialog.findViewById(R.id.rb_one);

        q2 = rankDialog.findViewById(R.id.q_two);
        r2 = rankDialog.findViewById(R.id.rb_two);

        q3 = rankDialog.findViewById(R.id.q_three);
        r3 = rankDialog.findViewById(R.id.rb_three);

        q4 = rankDialog.findViewById(R.id.q_four);
        r4 = rankDialog.findViewById(R.id.rb_four);

        q5 = rankDialog.findViewById(R.id.q_five);
        r5 = rankDialog.findViewById(R.id.rb_five);

        finish = rankDialog.findViewById(R.id.emp_finish);

        setUpQs();

        getRates();


        Employer employer = new Employer(project.getEmployer().getId());

        Freelancer freelancer = new Freelancer(frId);

        getFreelancerRatingsValues(frId);




        finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                RateTheFreelancer(qualityF, professionalismF, respectOfDeadlinesF, budgetF, responseTimeF);

                NewFreelancerRating(new FreelancerRating(responseTimeF, professionalismF, respectOfDeadlinesF, qualityF, budgetF, freelancer, employer));

                didEmployerRate();
                rankDialog.dismiss();
            }
        });

        rankDialog.show();

    }

    private void getRates() {
        r1.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                professionalismF = (int)ratingBar.getRating();
            }
        });

        r2.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                respectOfDeadlinesF = (int)ratingBar.getRating();
            }
        });


        r3.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                budgetF = (int)ratingBar.getRating();
            }
        });

        r4.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                responseTimeF = (int)ratingBar.getRating();
            }
        });

        r5.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                qualityF = (int)ratingBar.getRating();
            }
        });
    }

    private void setUpQs() {
        q1.setText(R.string.how_well_do_u_rate_fr_prof);
        q2.setText(R.string.how_well_do_u_rate_fr_respect_deadlines);
        q3.setText(R.string.how_well_do_u_rate_fr_budget);
        q4.setText(R.string.how_well_do_u_rate_fr_res_time);
        q5.setText(R.string.how_well_do_u_rate_fr_quality);

    }//End setUpQs()

    private void findFrId(){
        for (int i = 0; i < project.getBids().size(); i++){
            if (project.getBids().get(i).getStatus().equals("accepted"))
                frId = project.getBids().get(i).getFreelancer().getId();
        }
    }

    public void RateTheFreelancer(int qualityOfWork, int professionalismF, int respectOfDeadlines , int selectedBudget, int responseTimeF){
        float rateFR;

        rateFR = ((float)qualityOfWork + (float)professionalismF + (float)respectOfDeadlines + (float)selectedBudget + (float)responseTimeF) /5;

        num_of_ratings+= 1;
        total_response_time+= responseTimeF;
        total_quality_of_work+= qualityOfWork;

        setRatingValues(new Freelancer(frId, num_of_ratings, total_quality_of_work, total_response_time));

    }

    private void setRatingValues(Freelancer freelancer){
        ApiClients.getAPIs().setAllFreelancerRatingValues(freelancer).enqueue(new Callback<Void>() {
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


    private void NewFreelancerRating(FreelancerRating freelancerRating){

        ApiClients.getAPIs().getRateFreelancerRequest(freelancerRating).enqueue(new Callback<ApiResponse>() {
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

    private void getFreelancerRatingsValues(long id) {
        ApiClients.getAPIs().findFreelancerById(id).enqueue(new Callback<Freelancer>() {
            @Override
            public void onResponse(Call<Freelancer> call, Response<Freelancer> response) {
                if (response.isSuccessful()){
                    num_of_ratings = response.body().getNum_of_ratings();
                    total_response_time = response.body().getTotal_response_time();
                    total_quality_of_work = response.body().getTotal_quality_of_work();

                }
            }

            @Override
            public void onFailure(Call<Freelancer> call, Throwable t) {

            }
        });



    }

    private void didEmployerRate(){
        ApiClients.getAPIs().DidEmployerRate(project.getId(), true).enqueue(new Callback<Project>() {
            @Override
            public void onResponse(Call<Project> call, Response<Project> response) {
                if (response.isSuccessful())
                project = response.body();
//                did_emp_rate = project.isDid_emp_rate();
            }

            @Override
            public void onFailure(Call<Project> call, Throwable t) {

            }
        });
    }


}
