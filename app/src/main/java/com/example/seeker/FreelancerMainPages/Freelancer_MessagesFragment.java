package com.example.seeker.FreelancerMainPages;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.seeker.Database.ApiClients;
import com.example.seeker.FreelancerMainPages.MyProjectsTab_Freelancer.ProjectsStatusFragments.FRProjectAdapter;
import com.example.seeker.FreelancerMainPages.MyProjectsTab_Freelancer.ProjectsStatusFragments.Freelancer_MyProjects_Pending_Fragment;
import com.example.seeker.Model.Bid;
import com.example.seeker.Model.Freelancer;
import com.example.seeker.Model.Project;
import com.example.seeker.PostBid.ViewBid;
import com.example.seeker.R;
import com.example.seeker.SharedPref.Constants;
import com.example.seeker.SharedPref.MySharedPreference;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Freelancer_MessagesFragment extends Fragment implements Serializable, FRProjectAdapter.ProjectAdapterListener {

    private View view;
    private RecyclerView recyclerView;
    private FRProjectAdapter adapter;
    private List<Project> projectList = new ArrayList<>();
    private Freelancer_MessagesFragment.ProjectListener projectListener;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_freelancer_messages, container, false);

        return view;
    }

    public interface ProjectListener{

        void onProjectItemSelected(Project project);
    }//End of interface

    @Override
    public void onProjectItemClick(Project project) {

//        projectListener.onProjectItemSelected(project);
//        todo 3? hind
//        Intent i = new Intent(getContext(), ViewBid.class);
//
//        //casting to serializable didn't work, so i let class bid implements the serializable and it worked :)
//        i.putExtra("projectObj", project);
//        startActivity(i);
        Intent i = new Intent(getContext(), ViewBid.class);
        i.putExtra("projectObj", project);
        startActivity(i);


    }

    public void setListener (Freelancer_MessagesFragment.ProjectListener projectListener)
    {
        this.projectListener = projectListener;

    }


    @Override
    public void onResume() {
        super.onResume();

        ApiClients.getAPIs().getProjectsByStatusOnly("0").enqueue(new Callback<List<Project>>() {
            @Override
            public void onResponse(Call<List<Project>> call, Response<List<Project>> response) {

                if(response.isSuccessful()){

                    /**
                     *     int responseSize = response.body().size();
                     *                     for (int i = 0; i< responseSize; i++){
                     *                         if (response.body().get(i).getFreelancer() != null)
                     *                         if (response.body().get(i).getFreelancer().getId() == 252){
                     *                             bidList.add(response.body().get(i));
                     *                         }
                     *                     }
                     */

//                    int responseSize = response.body().size();
//                    List<Bid> testBidList = new ArrayList<>();
//                    List<Project> testP = new ArrayList<>();
//
//                    int tryy = -1;
//                    long currFree = MySharedPreference.getLong(getContext(), Constants.Keys.FREELANCER_ID,-1);
//                    Freelancer currf = new Freelancer(currFree);
//
//
//                    for (int i = 0; i< responseSize; i++){
////                        if (response.body().get(i).getBids() != null)
//
//                        if (response.body().get(i).getBids().size() > 0){
////                                tryy++;
//                            testP.add(response.body().get(i));
//                            testBidList = response.body().get(i).getBids();
//                        }
//
//                    }
//                    int bidlistSize = testBidList.size();
//                    int testPSize = testP.size();
//                    for (int k = 0; k< testPSize; k++){
////                        if (response.body().get(k).getBids().get(k).getFreelancer() != null)
//                        for (int h = 0; h<bidlistSize; h++){
//                            if (testP.get(k).getBids().get(h).getFreelancer() != null)
//                                if (testP.get(k).getBids().get(h).getFreelancer().getId() == currFree){
//                                    tryy++;
//                                    projectList.add(testP.get(k));
//                                }
//                        }
//
//                    }

                    projectList =  response.body();
                    recyclerView = (RecyclerView) view.findViewById(R.id.fr_pending_recycler_view_inMSG);

                    recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

                    adapter = new FRProjectAdapter(projectList);
                    projectList = new ArrayList<>();
                    recyclerView.setItemAnimator(new DefaultItemAnimator());
                    recyclerView.setAdapter(adapter);
                    adapter.setListener(Freelancer_MessagesFragment.this);
                    recyclerView.setNestedScrollingEnabled(true);
                    DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL);
                    recyclerView.addItemDecoration(dividerItemDecoration);


                }else{

                }
            }

            @Override
            public void onFailure(Call<List<Project>> call, Throwable t) {

            }
        });



    }

}
