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

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.MyViewHolder>{

    private Context mContext;
    private List<Category> categoryList;

    private OnItemClickListener mListener;


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public RecyclerView recyclerView;
        public ImageView arrow;
        public TextView title;
        public TextView description;





        public MyViewHolder(View view, final OnItemClickListener listener) {
            super(view);
            title = view.findViewById(R.id.row_title);

            description = view.findViewById(R.id.row_description);
            arrow = view.findViewById(R.id.row_arrow);
            recyclerView = view.findViewById(R.id.recycler_view);
            title.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onItemClick(position);
                        } // end if
                    } // end outer if
                }
            });

        } //end MyViewHolder

    }

    public CategoryAdapter(Context mContext, List<Category> categoryList) {
        this.mContext = mContext;
        this.categoryList = categoryList;
    } // end DepartmentAdapter

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_category, parent, false);

        return new MyViewHolder(itemView, mListener);
    } // end onCreateViewHolder

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        Category category = categoryList.get(position);
        holder.title.setText(category.getTitle());
        holder.description.setText(category.getDescription());



    } //  end onBindViewHolder

    public interface OnItemClickListener {
        void onItemClick(int position);
    } // end OnItemClickListener

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    } // end OnItemClickListener

    @Override
    public int getItemCount() {
        return categoryList.size();
    } // end getItemCount
}
