package com.example.seeker.Search;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.seeker.R;

import java.util.List;

public class CategorySearchAdapter extends RecyclerView.Adapter<CategorySearchAdapter.MyViewHolder> {

    private Context mContext;
    private List<CategorySearch> categorySearchList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title;
        public ImageView thumbnail;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title);
            thumbnail = (ImageView) view.findViewById(R.id.thumbnail);
        }
    }


    public CategorySearchAdapter(Context mContext, List<CategorySearch> categorySearchList) {
        this.mContext = mContext;
        this.categorySearchList = categorySearchList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.category_search_card, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        CategorySearch categorySearch = categorySearchList.get(position);
        holder.title.setText(categorySearch.getName());

        // loading album cover using Glide library
        Glide.with(mContext).load(categorySearch.getThumbnail()).into(holder.thumbnail);
    }

    @Override
    public int getItemCount() {
        return categorySearchList.size();
    }
}