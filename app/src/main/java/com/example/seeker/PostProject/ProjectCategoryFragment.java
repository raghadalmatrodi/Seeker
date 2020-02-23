package com.example.seeker.PostProject;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.seeker.Model.Category;
import com.example.seeker.R;

import java.util.ArrayList;
import java.util.List;

public class ProjectCategoryFragment extends Fragment implements CategoryAdapter.CategoryAdapterListener {

    private View view;
    private RecyclerView recyclerView;
    private CategoryAdapter adapter;
    private List<Category> categoryList = new ArrayList<>();
    private CategoryListener categoryListener;
    private BackCategoryListener backCategoryListener;
    private ImageView backBtn;





    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_project_category, container, false);

        backBtn = view.findViewById(R.id.project_category_back);
        categoryList.add(new Category("Test","Test to Test"));
        categoryList.add(new Category("Reema", "My Test"));
        categoryList.add(new Category("HHHHH", "Test Me Me"));

        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new CategoryAdapter(categoryList);
        categoryList = new ArrayList<>();
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
        adapter.setListener(this);

        recyclerView.setNestedScrollingEnabled(true);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                backCategoryListener.onBacCategorySelected();
            }
        });




        return view;
    }//End of onCreateView()

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

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
       // categoryListener = (CategoryListener) getActivity();
    }

    public interface BackCategoryListener{

        void onBacCategorySelected();
    }


}
