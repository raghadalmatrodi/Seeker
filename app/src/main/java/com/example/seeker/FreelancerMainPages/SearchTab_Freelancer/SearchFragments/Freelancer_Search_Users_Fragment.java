package com.example.seeker.FreelancerMainPages.SearchTab_Freelancer.SearchFragments;

import android.content.DialogInterface;
import android.content.Intent;
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
import com.example.seeker.FreelancerMainPages.SearchTab_Freelancer.FreelancerUserSearchAdapter;
import com.example.seeker.Model.Category;
import com.example.seeker.Model.User;
import com.example.seeker.R;
import com.example.seeker.Search.CategorySearchAdapter;
import com.example.seeker.ViewProfileActivity;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Freelancer_Search_Users_Fragment extends Fragment
        implements CategorySearchAdapter.CategoryAdapterListener, FreelancerUserSearchAdapter.FreelancerUserSearchAdapterListener {




    private View view;
    private RecyclerView recyclerView,recyclerViewUser;
    private CategorySearchAdapter adapter;
    private SearchView searchView;
    private List<Category> categorySearchSearchList;
    private List<User> userList;

    private static final String LOG = Freelancer_Search_Users_Fragment.class.getSimpleName();
    private Freelancer_Search_Users_Fragment.CategoryListener categoryListener;
    private FreelancerUserSearchAdapter freelancerUserSearchAdapter;





    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        view = inflater.inflate(R.layout.fragment_freelancer_by_user_search, container, false);
        searchView= view.findViewById(R.id.SearchView_freelancer_byuser_search);





        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {


            @Override
            public boolean onQueryTextSubmit(String queryString) {

                recyclerView.setVisibility(View.GONE);
                recyclerViewUser.setVisibility(View.VISIBLE);

                freelancerUserSearchAdapter.getFilter().filter(queryString);

                return false;
            }

            @Override
            public boolean onQueryTextChange(String queryString) {

                recyclerView.setVisibility(View.GONE);
                recyclerViewUser.setVisibility(View.VISIBLE);
                freelancerUserSearchAdapter.getFilter().filter(queryString);


                return false;
            }
        });

        // Inflate the layout for this fragment
        return view;
    }


    //no need
    @Override
    public void onUserItemSelectedAdapter(User user) {
        Intent intent = new Intent(getActivity(), ViewProfileActivity.class);
        intent.putExtra("myuser", user);
        startActivity(intent);
    }


    public interface CategoryListener {
        void onCategoryTypeItemSelected(Category category);
    }


    public void setListener(Freelancer_Search_Users_Fragment.CategoryListener categoryListener){
        this.categoryListener =categoryListener;
    }


    /**
     * Adding few images for testing
     */
    private void prepareCategories() {
        int[] covers = new int[]{
                R.drawable.wesite_and_it,
                R.drawable.mobile,
                R.drawable.writing,
                R.drawable.art_and_design,
                R.drawable.data_entry,
                R.drawable.music_and_audio,
                R.drawable.wesite_and_it,
                R.drawable.mobile,
                R.drawable.writing,
                R.drawable.art_and_design,
                R.drawable.data_entry,
                R.drawable.music_and_audio,
                R.drawable.wesite_and_it,
                R.drawable.mobile,
                R.drawable.writing,
                R.drawable.art_and_design,
                R.drawable.data_entry,
                R.drawable.music_and_audio,
                R.drawable.wesite_and_it,
                R.drawable.mobile,
                R.drawable.writing,
                R.drawable.art_and_design,
                R.drawable.data_entry,
                R.drawable.music_and_audio,
                R.drawable.wesite_and_it,
                R.drawable.mobile,
                R.drawable.writing,
                R.drawable.art_and_design,
                R.drawable.data_entry,
                R.drawable.music_and_audio
        };



        for(int i=0; i<categorySearchSearchList.size();i++){
            // قاعد يسوي لي كراش بدون هالشرط
            if(categorySearchSearchList.get(i).getCategory_type()!= null)
                if(categorySearchSearchList.get(i).getCategory_type().equals("1"))
                    categorySearchSearchList.get(i).setTitle(categorySearchSearchList.get(i).getTitle()+ "\n (On-Field) ");

           // categorySearchSearchList.get(i).setImage(covers[i]);



        }
        adapter.notifyDataSetChanged();
    }


    @Override
    public void onResume() {
        super.onResume();
        getAllCategory();
        getAllUsers();


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


        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view_freelancer_byuser_search);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        adapter = new CategorySearchAdapter(getActivity(), categorySearchSearchList);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
        recyclerView.setNestedScrollingEnabled(true);
        adapter.setListener(this);

        recyclerView.addItemDecoration(new com.example.seeker.FreelancerMainPages.SearchTab_Freelancer.
                SearchFragments.Freelancer_Search_Users_Fragment.
                GridSpacingItemDecoration(2, dpToPx(10), true));

        prepareCategories();
    }



    private void setUserRecyclerView() {


        recyclerViewUser = (RecyclerView) view.findViewById(R.id.recycler_view_freelancer_byuser_search_user);
        recyclerViewUser.setLayoutManager(new LinearLayoutManager(getActivity()));
        freelancerUserSearchAdapter = new FreelancerUserSearchAdapter(getContext(), userList);
        recyclerViewUser.setItemAnimator(new DefaultItemAnimator());
        recyclerViewUser.setAdapter(freelancerUserSearchAdapter);
        if (!userList.isEmpty())
            freelancerUserSearchAdapter.setListener(this);
        recyclerViewUser.setNestedScrollingEnabled(true);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerViewUser.getContext(), DividerItemDecoration.VERTICAL);
        recyclerViewUser.addItemDecoration(dividerItemDecoration);
        recyclerViewUser.setVisibility(View.GONE);

    }

    private void getAllUsers() {

        ApiClients.getAPIs().getAllUsers().enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                if(response.isSuccessful()){

                    userList = (List) response.body();
                    setUserRecyclerView();

                }
                else{

                    Log.i(LOG, "onResponse not suc: " + response.toString());
                }
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {

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

        Fragment fragment=new Freelancer_Search_InnerUsers_Fragment();

        Bundle bundle=new Bundle();
        bundle.putSerializable("category",category);
        fragment.setArguments(bundle);
        FragmentManager fragmentManager=getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_container_freelancer,fragment);

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
