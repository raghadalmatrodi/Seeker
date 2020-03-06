package com.example.seeker.PostProject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.seeker.Model.Category;
import com.example.seeker.R;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.MyViewHolder> {

    private List<Category> categoryList;
    //2
    private CategoryAdapterListener listener;
//3
    public void setListener(CategoryAdapterListener listener) {
        this.listener = listener;
    }
//------------------
    public class MyViewHolder extends RecyclerView.ViewHolder {


        public ImageView arrow;
        public TextView title;
        public TextView description;

        public MyViewHolder(View view) {
            super(view);

            title = view.findViewById(R.id.row_title);
            description = view.findViewById(R.id.row_description);
            arrow = view.findViewById(R.id.row_arrow);

//4
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    listener.onCategoryItemClick(categoryList.get(getAdapterPosition()));

                }
            });

        }//End of MyViewHolder()


    }//Enf of class MyViewHolder


//1
    public interface CategoryAdapterListener {
        void onCategoryItemClick(Category category);
    }
    //-------

    public CategoryAdapter(List<Category> categoryList) {

        this.categoryList = categoryList;
    }//End of CategorySearchAdapter()

    @Override
    public CategoryAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_category, parent, false);

        MyViewHolder evh = new MyViewHolder(itemView);
        return evh;
    }//End of onCreateViewHolder

    @Override
    public void onBindViewHolder(final CategoryAdapter.MyViewHolder holder, int position) {
        Category category = categoryList.get(position);
        holder.title.setText(category.getTitle());
        holder.description.setText(category.getDescription());


    }//End of onBindViewHolder


    @Override
    public int getItemCount() {
        return categoryList.size();
    }
}


