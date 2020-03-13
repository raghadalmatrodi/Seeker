package com.example.seeker.Search;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.seeker.Model.Category;
import com.example.seeker.R;

import java.util.List;

public class CategorySearchAdapter extends RecyclerView.Adapter<CategorySearchAdapter.MyViewHolder>  {

    private Context mContext;
    private List<Category> categorySearchList;

    //2
    private CategoryAdapterListener listener;
    //3
    public void setListener(CategoryAdapterListener listener) {
        this.listener = listener;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title;
        public ImageView thumbnail;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title);
            thumbnail = (ImageView) view.findViewById(R.id.thumbnail);
//4
            thumbnail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    listener.onCategoryItemClick(categorySearchList.get(getAdapterPosition()));

                }
            });

            title.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    listener.onCategoryItemClick(categorySearchList.get(getAdapterPosition()));

                }
            });

        }


    }


    public CategorySearchAdapter(Context mContext, List<Category> categorySearchList) {
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
        Category category = categorySearchList.get(position);
        holder.title.setText(category.getTitle());

        // loading album cover using Glide library
        Glide.with(mContext).load(category.getImage()).into(holder.thumbnail);
    }

    @Override
    public int getItemCount() {
        return categorySearchList.size();
    }


//1
    public interface CategoryAdapterListener {
        void onCategoryItemClick(Category category);
    }
}