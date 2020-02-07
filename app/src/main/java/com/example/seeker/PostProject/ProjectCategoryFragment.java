package com.example.seeker.PostProject;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.seeker.Model.Category;
import com.example.seeker.R;

import java.util.ArrayList;
import java.util.List;

public class ProjectCategoryFragment extends Fragment {

    private View view;
    private RecyclerView recyclerView;
    private CategoryAdapter adapter;
    private List<Category> categoryList;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_project_category, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);

        categoryList = new ArrayList<>();
        adapter = new CategoryAdapter(getContext(), categoryList);


        adapter.setOnItemClickListener(new CategoryAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {

                // we will do something


            }
        }); //end  setOnItemClickListener

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 1);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);




        return view;
    }//End of onCreateView()
}
