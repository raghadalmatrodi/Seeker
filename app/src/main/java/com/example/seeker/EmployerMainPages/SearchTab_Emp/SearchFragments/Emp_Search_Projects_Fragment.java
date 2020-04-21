package com.example.seeker.EmployerMainPages.SearchTab_Emp.SearchFragments;

import android.content.DialogInterface;
import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.seeker.Database.ApiClients;
import com.example.seeker.EmployerMainPages.MyProjectsTab_Emp.Emp_viewProjectFragment;
import com.example.seeker.EmployerMainPages.SearchTab_Emp.ProjectSearchAdapter;
import com.example.seeker.Model.Category;
import com.example.seeker.Model.Project;
import com.example.seeker.R;
import com.example.seeker.Search.CategorySearchAdapter;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Emp_Search_Projects_Fragment extends Fragment
        implements CategorySearchAdapter.CategoryAdapterListener, ProjectSearchAdapter.ProjectSearchAdapterListener {




    private View view;
    private RecyclerView recyclerView,recyclerViewProject;
    private CategorySearchAdapter adapter;
    private SearchView searchView;
    private List<Category> categorySearchSearchList;
    private List<Project> projectList;

    private static final String LOG = Emp_Search_Projects_Fragment.class.getSimpleName();
   private CategoryListener categoryListener;
    private ProjectSearchAdapter projectSearchAdapter;





    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        view = inflater.inflate(R.layout.fragment_emp_by_category_search, container, false);
       searchView= view.findViewById(R.id.SearchView_emp_bycategory_search);





        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {


            @Override
            public boolean onQueryTextSubmit(String queryString) {

                recyclerView.setVisibility(View.GONE);
                recyclerViewProject.setVisibility(View.VISIBLE);

                    projectSearchAdapter.getFilter().filter(queryString);

                return false;
            }

            @Override
            public boolean onQueryTextChange(String queryString) {

                recyclerView.setVisibility(View.GONE);
                setProjectRecyclerView();
                recyclerViewProject.setVisibility(View.VISIBLE);

                projectSearchAdapter.getFilter().filter(queryString);


                return false;
            }
        });



        // Inflate the layout for this fragment
        return view;
    }


    //no need
    @Override
    public void onProjectItemSelectedAdapter(Project project) {
        Fragment fragment = new Emp_viewProjectFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("project", project);
     //   bundle.putInt("pending", 1);
        fragment.setArguments(bundle);
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_container_emp, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();

    }


    public interface CategoryListener {
        void onCategoryTypeItemSelected(Category category);
    }


    public void setListener(CategoryListener categoryListener){
        this.categoryListener =categoryListener;
    }


    /**
     * Adding few images for testing
     */
    private void prepareCategories() {
        int[] covers = new int[]{
                R.drawable.wesite_and_it,
                R.drawable.mobile,
                R.drawable.swdevlpoment,
                R.drawable.writing,
                R.drawable.art_and_design,
                R.drawable.data_entry,
                R.drawable.marketing,
                R.drawable.music_and_audio,
                R.drawable.editing,
                R.drawable.teaching,//---
                R.drawable.construction,
                R.drawable.organizing,
                R.drawable.housekeeping,//------
                R.drawable.delivery,
                R.drawable.music_and_audio,
                R.drawable.makeup,
                R.drawable.cooking,
                R.drawable.art_and_design,
                R.drawable.planning,
                R.drawable.photography,
                R.drawable.training,
                R.drawable.styling
//                R.drawable.art_and_design,
//                R.drawable.data_entry,
//                R.drawable.music_and_audio,
//                R.drawable.wesite_and_it,
//                R.drawable.mobile,
//                R.drawable.writing,
//                R.drawable.art_and_design,
//                R.drawable.data_entry,
//                R.drawable.music_and_audio
        };



for(int i=0; i<categorySearchSearchList.size();i++){
    // قاعد يسوي لي كراش بدون هالشرط
    if(categorySearchSearchList.get(i).getCategory_type()!= null)
if(categorySearchSearchList.get(i).getCategory_type().equals("1"))
    categorySearchSearchList.get(i).setTitle(categorySearchSearchList.get(i).getTitle()+ "\n (On-Field) ");

//    categorySearchSearchList.get(i).setImage(covers[i]);



        }
        adapter.notifyDataSetChanged();
    }


    @Override
    public void onResume() {
        super.onResume();
getAllCategory();
        getAllProjects();


    }//end on resume

    private void getAllCategory() {

        ApiClients.getAPIs().getALLCategoryRequest().enqueue(new Callback<List<Category>>() {
            @Override
            public void onResponse(Call<List<Category>> call, Response<List<Category>> response) {
                if(response.isSuccessful()){

                    categorySearchSearchList = (List) response.body();
                    setRecyclerView();

                }
                else{

                    Log.i(LOG, "onResponse not suc: " + response.toString());
                }
            }

            @Override
            public void onFailure(Call<List<Category>> call, Throwable t) {

                Log.i(LOG, "Fail");
            }
        });
    }

    private void setRecyclerView() {


        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view_emp_bycategory_search);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        adapter = new CategorySearchAdapter(getActivity(), categorySearchSearchList);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
        recyclerView.setNestedScrollingEnabled(true);
        adapter.setListener(this);

        recyclerView.addItemDecoration(new com.example.seeker.EmployerMainPages.SearchTab_Emp.
                SearchFragments.Emp_Search_Projects_Fragment.
                GridSpacingItemDecoration(2, dpToPx(10), true));

        prepareCategories();
    }



    private void setProjectRecyclerView() {


//recyclerView.clearDisappearingChildren();
        recyclerViewProject = (RecyclerView) view.findViewById(R.id.recycler_view_emp_bycategory_search_project);
        recyclerViewProject.setLayoutManager(new LinearLayoutManager(getActivity()));
        projectSearchAdapter = new ProjectSearchAdapter(projectList);
        recyclerViewProject.setItemAnimator(new DefaultItemAnimator());
        recyclerViewProject.setAdapter(projectSearchAdapter);
        if (!projectList.isEmpty())
            projectSearchAdapter.setListener(this);
        recyclerViewProject.setNestedScrollingEnabled(true);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerViewProject.getContext(), DividerItemDecoration.VERTICAL);
        recyclerViewProject.addItemDecoration(dividerItemDecoration);
        recyclerViewProject.setVisibility(View.GONE);

    }

    private void getAllProjects() {

        ApiClients.getAPIs().getAllProjects().enqueue(new Callback<List<Project>>() {
            @Override
            public void onResponse(Call<List<Project>> call, Response<List<Project>> response) {
                if(response.isSuccessful()){

                    projectList = (List) response.body();
                    setProjectRecyclerView();

                }
                else{

                    Log.i(LOG, "onResponse not suc: " + response.toString());
                }
            }

            @Override
            public void onFailure(Call<List<Project>> call, Throwable t) {

                Log.i(LOG, "Fail");
            }
        });




    }


    private void wrongInfoDialog(String msg) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getContext());
        // Setting Dialog Title
        alertDialog.setTitle("Warning");

        // Setting Dialog Message
        alertDialog.setMessage(msg);

        //Setting Negative "ok" Button
        alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

            }//end onClick
        });//end setPositiveButton

        alertDialog.show();

    }//end wrongInfoDialog()

    @Override
    public void onCategoryItemClick(Category category) {

        Fragment fragment=new Emp_Search_InnerProjects_Fragment();

    Bundle bundle=new Bundle();
    bundle.putSerializable("category",category);
    fragment.setArguments(bundle);
        FragmentManager fragmentManager=getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_container_emp,fragment);

        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();


    }

    public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

    private int spanCount;
    private int spacing;
    private boolean includeEdge;

    public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
        this.spanCount = spanCount;
        this.spacing = spacing;
        this.includeEdge = includeEdge;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        int position = parent.getChildAdapterPosition(view); // item position
        int column = position % spanCount; // item column

        if (includeEdge) {
            outRect.left = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
            outRect.right = (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)

            if (position < spanCount) { // top edge
                outRect.top = spacing;
            }
            outRect.bottom = spacing; // item bottom
        } else {
            outRect.left = column * spacing / spanCount; // column * ((1f / spanCount) * spacing)
            outRect.right = spacing - (column + 1) * spacing / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
            if (position >= spanCount) {
                outRect.top = spacing; // item top
            }
        }
    }
}

    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }

}
