package com.example.seeker.FreelancerMainPages.SearchTab_Freelancer.SearchFragments;


import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.seeker.Database.ApiClients;
import com.example.seeker.EmployerMainPages.MyProjectsTab_Emp.Emp_viewProjectFragment;
import com.example.seeker.EmployerMainPages.SearchTab_Emp.SearchFragments.Emp_Search_Users_Fragment;
import com.example.seeker.EmployerMainPages.SearchTab_Emp.UserSearchAdapter;
import com.example.seeker.FreelancerMainPages.SearchTab_Freelancer.FreelancerUserSearchAdapter;
import com.example.seeker.Model.Category;
import com.example.seeker.Model.Freelancer;
import com.example.seeker.Model.Project;
import com.example.seeker.Model.Skill;
import com.example.seeker.Model.User;
import com.example.seeker.R;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Freelancer_Search_InnerUsers_Fragment extends Fragment implements Freelancer_Search_Users_Fragment.CategoryListener, FreelancerUserSearchAdapter.FreelancerUserSearchAdapterListener {


    private View view;
    private RecyclerView recyclerView;
    private FreelancerUserSearchAdapter adapter;
    private List<User> userList = new ArrayList<>();
    private List<Freelancer> freelancersList = new ArrayList<>();
    private List<Freelancer> freelancers=new ArrayList<>();
    private Category category;
    ImageView backBtn;
    TextView categoryTitle, pendintTxt;
    private Freelancer_Search_Users_Fragment freelancer_search_users_fragment;

    private static final String LOG = Freelancer_Search_InnerUsers_Fragment.class.getSimpleName();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_search_freelancer_users, container, false);
        backBtn = view.findViewById(R.id.category_back_users_freelancer);
        categoryTitle = view.findViewById(R.id.category_title_users_freelancer);
        pendintTxt = view.findViewById(R.id.freelancer_search_users_inner_text);
        freelancer_search_users_fragment = new Freelancer_Search_Users_Fragment();

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            category = (Category) bundle.getSerializable("category");
            executeRequest(category);
            categoryTitle.setText(category.getTitle());
        }
        freelancer_search_users_fragment.setListener(this);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFragmentManager().popBackStackImmediate();
            }
        });


        return view;
    }


    private void wrongInfoDialog(String msg) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getContext());
        // Setting Dialog Title
        alertDialog.setTitle("Warning");

        // Setting Dialog Message
        alertDialog.setMessage(msg);

        // Setting Icon to Dialog
//        alertDialog.setIcon(R.drawable.exclamation);
        //Setting Negative "ok" Button
        alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

            }//end onClick
        });//end setPositiveButton

        alertDialog.show();

    }//end wrongInfoDialog()



    //todo change to user

    public void executeRequest(Category category) {
        Set<Skill> skillSet= category.getSkills();

        ApiClients.getAPIs().getALLFreelancersRequest().enqueue(new Callback<List<Freelancer>>() {
            @Override
            public void onResponse(Call<List<Freelancer>> call, Response<List<Freelancer>> response) {
                if (response.isSuccessful()) {

                    if (response.body() == null)
                        wrongInfoDialog("There is no freelancers");
                    else {
                        freelancers = (List) response.body();
                        checkSkills(skillSet,freelancers);
                        //settheAdapter();
                    }


                } else {

                    Log.i(LOG, "onResponse not suc: " + response.toString());
                }
            }

            @Override
            public void onFailure(Call<List<Freelancer>> call, Throwable t) {


            }
        });

    }






    private void checkSkills(Set<Skill> skillSet, List<Freelancer> freelancers) {

        if (!freelancers.isEmpty()){
            // pendintTxt.setText("adding not empty.");

        }

        for (Iterator<Skill> it = skillSet.iterator(); it.hasNext(); ) {
            Skill skill = it.next();

            for (int j = 0; j < freelancers.size(); j++) {
                Set<Skill> skillSetToCheck = freelancers.get(j).getSkills();


                for (Iterator<Skill> itToCheck = skillSetToCheck.iterator(); itToCheck.hasNext(); ) {
                    Skill skillNew = itToCheck.next();
                    if (skill.getId()==skillNew.getId()) {

                        freelancersList.add(freelancers.get(j));
                        break;
                        // pendintTxt.setText("adding.");

                    }
                }
            }
        }





        if (freelancersList.isEmpty()){
            // pendintTxt.setText("No freelancers in this category.");
        }
        else
            findUserList();




    }




    private void findUserList() {
        userList=new ArrayList<>();
        //give list of freelancers and get list of users
        for(int i=0;i<freelancersList.size();i++)

            if(!userList.contains(freelancersList.get(i).getUser()))
            { userList.add(freelancersList.get(i).getUser());
            }

//on success
        if(!userList.isEmpty())
            setUserAdapter();


    }


    @Override
    public void onCategoryTypeItemSelected(Category category) {
        this.category = category;
    }
//todo
    @Override
    public void onUserItemSelectedAdapter(User user) {

//        Fragment fragment = new Freelancer_viewProjectFragment();
//        Bundle bundle = new Bundle();
//        bundle.putSerializable("user", user);
//        fragment.setArguments(bundle);
//        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
//        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
      //  fragmentTransaction.replace(R.id.frame_container_freelancer, fragment);
//        fragmentTransaction.addToBackStack(null);
//        fragmentTransaction.commit();


    }

    public void settheAdapter() {
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_freelancer_search_users);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new FreelancerUserSearchAdapter(userList);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
        if (!userList.isEmpty())
            adapter.setListener(this);
        recyclerView.setNestedScrollingEnabled(true);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);
    }


    public void setUserAdapter() {
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_freelancer_search_users);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new FreelancerUserSearchAdapter(userList);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
        if (!userList.isEmpty())
            adapter.setListener(this);
        recyclerView.setNestedScrollingEnabled(true);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);
    }


}
