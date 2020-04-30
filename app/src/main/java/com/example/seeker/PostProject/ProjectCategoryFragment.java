package com.example.seeker.PostProject;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.seeker.Activities.SignupActivity;
import com.example.seeker.Database.ApiClients;
import com.example.seeker.Model.Category;
import com.example.seeker.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProjectCategoryFragment extends Fragment implements CategoryAdapter.CategoryAdapterListener {

    private View view;
    private RecyclerView recyclerView;
    private CategoryAdapter adapter;
    private List<Category> categoryList = new ArrayList<>();
    private List<Category> lastList = new ArrayList<>();
    private CategoryListener categoryListener;
    private BackCategoryListener backCategoryListener;
    private ImageView backBtn;
    private static final String LOG= ProjectCategoryFragment.class.getSimpleName();







    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_project_category, container, false);

        backBtn = view.findViewById(R.id.project_category_back);


        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);


        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                backCategoryListener.onBacCategorySelected();
            }
        });

        getArray();



        return view;
    }//End of onCreateView()

    private void getArray() {


        ApiClients.getAPIs().getALLCategoryRequest().enqueue(new Callback<List<Category>>() {
            @Override
            public void onResponse(Call<List<Category>> call, Response<List<Category>> response) {
                if (response.isSuccessful()) {

                    categoryList = response.body();


                }
            }

            @Override
            public void onFailure(Call<List<Category>> call, Throwable t) {

            }
        });
    }


    public interface CategoryListener{

        void onCategoryTypeItemSelected(Category category);
    }//End of interface

    @Override
    public void onCategoryItemClick(Category category) {

        categoryListener.onCategoryTypeItemSelected(category);

    }

    public void setListener (CategoryListener categoryListener, BackCategoryListener backCategoryListener)
    {
        this.categoryListener = categoryListener;
        this.backCategoryListener = backCategoryListener;
    }

    public interface BackCategoryListener{

        void onBacCategorySelected();
    }

    public void setData(String projectType) {

        lastList = new ArrayList<>();
        setRecyclerView(projectType);
    }



    private void setRecyclerView(String projectType) {



        for(Category c : categoryList){
            if(c.getCategory_type().equals(projectType))
            {
                lastList.add(c);
            }

        }

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new CategoryAdapter(lastList);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
        adapter.setListener(this);
        recyclerView.setNestedScrollingEnabled(true);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);
    }
}
