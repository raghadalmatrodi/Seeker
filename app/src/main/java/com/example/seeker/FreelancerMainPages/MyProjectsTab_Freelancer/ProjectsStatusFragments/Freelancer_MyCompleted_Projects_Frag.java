package com.example.seeker.FreelancerMainPages.MyProjectsTab_Freelancer.ProjectsStatusFragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.seeker.Database.ApiClients;
import com.example.seeker.FreelancerMainPages.MyProjectsTab_Freelancer.Freelancer_viewProjectFragment;
import com.example.seeker.Model.Project;
import com.example.seeker.R;
import com.example.seeker.SharedPref.Constants;
import com.example.seeker.SharedPref.MySharedPreference;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Freelancer_MyCompleted_Projects_Frag extends Fragment implements Serializable, FRProjectAdapter.ProjectAdapterListener {
    private View view;
    private RecyclerView recyclerView;
    private FRProjectAdapter adapter;
    private List<Project> projectList = new ArrayList<>();
    private Freelancer_MyCompleted_Projects_Frag.ProjectListener projectListener;
    private TextView completedText;
    long currentFreelancer = MySharedPreference.getLong(getContext(), Constants.Keys.FREELANCER_ID,-1);
    private ProgressBar mProgressBar;





    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.freelancer_completed_projects, container, false);
        completedText = view.findViewById(R.id.fr_completed_txt);
        mProgressBar = view.findViewById(R.id.myDataLoaderProgressBar);
        mProgressBar.setVisibility(View.VISIBLE);


        return view;
    }//End onCreateView()

    public interface ProjectListener{

        void onProjectItemSelected(Project project);
    }//End of interface

    @Override
    public void onProjectItemClick(Project project) {

        Fragment fragment = new Freelancer_viewProjectFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("project",project);
        bundle.putString("flag", "FC");
        fragment.setArguments(bundle);
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_container_freelancer, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();


    }

    public void setListener (Freelancer_MyCompleted_Projects_Frag.ProjectListener projectListener)
    {
        this.projectListener = projectListener;

    }

    @Override
    public void onResume() {
        super.onResume();

        ApiClients.getAPIs().getProjectsByStatusOnly("2").enqueue(new Callback<List<Project>>() {
            @Override
            public void onResponse(Call<List<Project>> call, Response<List<Project>> response) {
                mProgressBar.setVisibility(View.GONE);

                if(response.isSuccessful()){
                    Toast.makeText(getContext(),"SUCCESS",Toast.LENGTH_LONG).show();

                    int responseSize = response.body().size();
                    int bidSize = 0;

                    //Projects loop
                    for (int k = 0; k < responseSize; k++){

                        bidSize = response.body().get(k).getBids().size();

                        //Bids on project k loop
                        for (int h = 0; h < bidSize; h++ ){

                            if (response.body().get(k).getBids().get(h).getFreelancer() != null)
                                if (response.body().get(k).getBids().get(h).getStatus().equals("accepted"))
                                    if (response.body().get(k).getBids().get(h).getFreelancer().getId() == currentFreelancer)
                                        projectList.add(response.body().get(k));

                        }//Bids loop
                    }//Projects loop.

                    if (!projectList.isEmpty())
                        completedText.setVisibility(View.GONE);
                    else completedText.setText("There are no completed projects yet..");
                    setUpRecyclerView();


                }else{
                    Toast.makeText(getContext(),"not success",Toast.LENGTH_LONG).show();
                    completedText.setText("No projects.");
                }
            }

            @Override
            public void onFailure(Call<List<Project>> call, Throwable t) {
                Toast.makeText(getContext(),"fail",Toast.LENGTH_LONG).show();
                completedText.setText("Error, Something went wrong..");
            }
        });


    }//End onResume()

    private void setUpRecyclerView() {
        recyclerView = view.findViewById(R.id.fr_completed_recycler_view);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        adapter = new FRProjectAdapter(projectList);
        projectList = new ArrayList<>();
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
        adapter.setListener(Freelancer_MyCompleted_Projects_Frag.this);
        recyclerView.setNestedScrollingEnabled(true);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);
    }



}
