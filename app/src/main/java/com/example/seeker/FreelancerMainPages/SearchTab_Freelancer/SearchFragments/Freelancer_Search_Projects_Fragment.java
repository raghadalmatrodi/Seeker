package com.example.seeker.FreelancerMainPages.SearchTab_Freelancer.SearchFragments;

import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.seeker.R;
import com.example.seeker.Search.CategorySearch;
import com.example.seeker.Search.CategorySearchAdapter;

import java.util.ArrayList;
import java.util.List;

public class Freelancer_Search_Projects_Fragment extends Fragment {




    private View view;
    private RecyclerView recyclerView;
    private CategorySearchAdapter adapter;
    private List<CategorySearch> categorySearchSearchList;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        view = inflater.inflate(R.layout.fragment_freelancer_by_category_search, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view_freelancer_bycategory_search);


        categorySearchSearchList = new ArrayList<>();
        adapter = new CategorySearchAdapter(getActivity(), categorySearchSearchList);

        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));

        recyclerView.addItemDecoration(new Freelancer_Search_Projects_Fragment.
                GridSpacingItemDecoration(2, dpToPx(10), true));

        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

        prepareCategories();


        // Inflate the layout for this fragment
        return view;
    }


    /**
     * Adding few albums for testing
     */
    private void prepareCategories() {
        int[] covers = new int[]{
                R.drawable.wesite_and_it,
                R.drawable.mobile,
                R.drawable.writing,
                R.drawable.art_and_design,
                R.drawable.data_entry,
                R.drawable.music_and_audio};

        CategorySearch a = new CategorySearch("Website & IT", covers[0]);
        categorySearchSearchList.add(a);

        a = new CategorySearch("Mobile", covers[1]);
        categorySearchSearchList.add(a);

        a = new CategorySearch("Writing", covers[2]);
        categorySearchSearchList.add(a);
        a = new CategorySearch("Art & Design", covers[3]);
        categorySearchSearchList.add(a);

        a = new CategorySearch("Data Entry", covers[4]);
        categorySearchSearchList.add(a);
        a = new CategorySearch("Music & Audio", covers[5]);
        categorySearchSearchList.add(a);


        adapter.notifyDataSetChanged();
    }

/**
 * RecyclerView item decoration - give equal margin around grid item
 */
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

    /**
     * Converting dp to pixel
     */
    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }
}
