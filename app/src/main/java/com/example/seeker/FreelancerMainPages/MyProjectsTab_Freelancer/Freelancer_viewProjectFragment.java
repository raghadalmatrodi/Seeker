package com.example.seeker.FreelancerMainPages.MyProjectsTab_Freelancer;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.seeker.Contract.ContractFragment;
import com.example.seeker.Database.ApiClients;
import com.example.seeker.EmployerMainPages.AcceptBidConfirmation;
import com.example.seeker.EmployerMainPages.Chat_Emp.Emp_ChatMessages;
import com.example.seeker.EmployerMainPages.MyProjectsTab_Emp.ProjectsStatusFragments.Emp_MyProjects_Pending_Fragment;
import com.example.seeker.Model.Bid;
import com.example.seeker.Model.Chat;
import com.example.seeker.Model.Contract;
import com.example.seeker.Model.Project;
import com.example.seeker.Model.Skill;
import com.example.seeker.PostBid.BidsAdapter;
import com.example.seeker.PostBid.PostBidActivity;
import com.example.seeker.PostBid.ViewFullBid;
import com.example.seeker.R;
import com.example.seeker.SharedPref.Constants;
import com.example.seeker.SharedPref.MySharedPreference;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Freelancer_viewProjectFragment extends Fragment implements  Emp_MyProjects_Pending_Fragment.ProjectListener ,BidsAdapter.BidsAdapterListener {

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
//    boolean hasBid = false;
    long currentFreelancer = MySharedPreference.getLong(getContext(), Constants.Keys.FREELANCER_ID, -1);


    ImageView contractImg;

    Emp_MyProjects_Pending_Fragment emp_myProjects_pending_fragment;
    private RecyclerView recyclerView;
    private BidsAdapter adapter;
    private List<Chat> chatList;

    View view;
    List<Bid> bids;
    int isPending=0;
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

        HidingPlaceBidBtn();

        onPostBidClicked(project);


        return view;

    }

    private boolean HasBid(List<Bid> bidList) {
        boolean hasBid = false;
        //Loop to check whether current freelancer has bade on this project or not
        for (int i = 0; i< bidList.size(); i++){
            if (bidList.get(i).getFreelancer() != null )
                if (bidList.get(i).getFreelancer().getId() == currentFreelancer) {
                    Toast.makeText(getContext(),"BID FOUND!",Toast.LENGTH_SHORT).show();
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

//                if (HasBid(bids))
//                        wrongInfoDialog("Sorry, you already have placed a bid on this project..\nDelete the current bid to bid again. ");
//                    else {
                        /**
                         * INTENT TO POST BID WITH CURRENT PROJECT
                         */
                    Intent intent = new Intent(getActivity(), PostBidActivity.class);
                    intent.putExtra("currentProjObj" , project);
                    startActivity(intent);

//                    findProjectById(project.getId());
//                    setTheAdapter();



//                }

            }
        });
//        finish();
//        overridePendingTransition(0, 0);
//        startActivity(getIntent());
//        overridePendingTransition(0, 0);
//        getFragmentManager().beginTransaction().detach(this).attach(this).commit();

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
        adapter = new BidsAdapter(bids, project);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
        adapter.setListener(this);
        recyclerView.setNestedScrollingEnabled(true);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);
        if(isPending==1)
        adapter.showAccept();


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

                        if((contract.getProject().getEmployer().getId() == MySharedPreference.getLong(getContext(),Constants.Keys.EMPLOYER_ID,-1)) || (contract.getFreelancer().getId() == MySharedPreference.getLong(getContext(), Constants.Keys.FREELANCER_ID,-1))){
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


    private void wrongInfoDialog(String msg) {
        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(getContext());

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

    private void findProjectById(long project_id){
        ApiClients.getAPIs().findProjectById(project_id).enqueue(new Callback<Project>() {
            @Override
            public void onResponse(Call<Project> call, Response<Project> response) {
                if (response.isSuccessful()){
                    project = response.body();
                    bids = response.body().getBids();
                }
            }

            @Override
            public void onFailure(Call<Project> call, Throwable t) {

            }
        });
    }

}