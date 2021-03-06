package com.example.seeker.EmployerMainPages.SearchTab_Emp.SearchFragments;


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
import com.example.seeker.EmployerMainPages.SearchTab_Emp.ProjectSearchAdapter;
import com.example.seeker.Model.Category;
import com.example.seeker.Model.Project;
import com.example.seeker.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Emp_Search_InnerProjects_Fragment extends Fragment implements Emp_Search_Projects_Fragment.CategoryListener, ProjectSearchAdapter.ProjectSearchAdapterListener {


    private View view;
    private RecyclerView recyclerView;
    private ProjectSearchAdapter adapter;
    private List<Project> projectList = new ArrayList<>();
    private Category category;
    ImageView backBtn;
    TextView categoryTitle, pendintTxt;
    private Emp_Search_Projects_Fragment emp_search_projects_fragment;

    private static final String LOG = Emp_Search_InnerProjects_Fragment.class.getSimpleName();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_search_emp_projects, container, false);
        backBtn = view.findViewById(R.id.category_back);
        categoryTitle = view.findViewById(R.id.category_title);
        pendintTxt = view.findViewById(R.id.emp_search_projects_inner_text);
        emp_search_projects_fragment = new Emp_Search_Projects_Fragment();

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            category = (Category) bundle.getSerializable("category");
            executeRequest(category);
            categoryTitle.setText(category.getTitle());
        }
        emp_search_projects_fragment.setListener(this);

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


    public void executeRequest(Category category) {

        ApiClients.getAPIs().getProjectsByCategory(category).enqueue(new Callback<List<Project>>() {
            @Override
            public void onResponse(Call<List<Project>> call, Response<List<Project>> response) {
                if (response.isSuccessful()) {

                    if (response.body() == null)
                        wrongInfoDialog("There is no projects in this category");
                    else {
                        projectList = (List) response.body();
                        settheAdapter();
                    }


                } else {
                    pendintTxt.setText("No projects yet");
                    Log.i(LOG, "onResponse not suc: " + response.toString());
                }
            }

            @Override
            public void onFailure(Call<List<Project>> call, Throwable t) {


            }
        });


    }


    @Override
    public void onCategoryTypeItemSelected(Category category) {
        this.category = category;
    }

    @Override
    public void onProjectItemSelectedAdapter(Project project) {

        Fragment fragment = new Emp_viewProjectFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("project", project);
        fragment.setArguments(bundle);
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_container_emp, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();


    }

    public void settheAdapter() {
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_emp_search_projects);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new ProjectSearchAdapter(projectList);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
        if (!projectList.isEmpty())
            adapter.setListener(this);
        recyclerView.setNestedScrollingEnabled(true);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);
    }


    public void setProjectAdapter() {
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_emp_search_projects);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new ProjectSearchAdapter(projectList);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
        if (!projectList.isEmpty())
            adapter.setListener(this);
        recyclerView.setNestedScrollingEnabled(true);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);
    }


}
