package com.example.seeker.EmployerMainPages.MyProjectsTab_Emp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

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
import com.example.seeker.R;
import com.example.seeker.SharedPref.Constants;
import com.example.seeker.SharedPref.MySharedPreference;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Emp_viewProjectFragment extends Fragment implements  Emp_MyProjects_Pending_Fragment.ProjectListener ,BidsAdapter.BidsAdapterListener {

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

         view = inflater.inflate(R.layout.fragment_view_project, container, false);
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
                }else{

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

}
