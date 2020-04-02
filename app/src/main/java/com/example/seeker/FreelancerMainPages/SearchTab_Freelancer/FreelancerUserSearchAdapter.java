package com.example.seeker.FreelancerMainPages.SearchTab_Freelancer;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.seeker.Model.User;
import com.example.seeker.R;

import java.util.ArrayList;
import java.util.List;

public class FreelancerUserSearchAdapter extends RecyclerView.Adapter<FreelancerUserSearchAdapter.MyViewHolder> implements Filterable {


    private List<User> userSearchList;
    private List<User> userList;

    FreelancerUserSearchAdapterListener listener;
    private List<User> filteredUserSearchList;

    public void setListener(FreelancerUserSearchAdapterListener listener) {
        this.listener = listener;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public ImageView avatar;
        public ImageView arrow;
        public TextView title;
        public TextView rating;

        public MyViewHolder(View view) {
            super(view);

            title = view.findViewById(R.id.row_username);
            rating = view.findViewById(R.id.row_user_rating);
            arrow = view.findViewById(R.id.row_user_arrow);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener != null)
                    listener.onUserItemSelectedAdapter(userList.get(getAdapterPosition()));
                }
            });
        }//End of MyViewHolder()
    }//Enf of class MyViewHolder


    public FreelancerUserSearchAdapter(List<User> userList) {

        this.userList = userList;
        this.userSearchList=userList;
    }//End of CategorySearchAdapter()

    @Override
    public FreelancerUserSearchAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_user, parent, false);

        MyViewHolder evh = new MyViewHolder(itemView);
        return evh;
    }//End of onCreateViewHolder

    @Override
    public void onBindViewHolder(final FreelancerUserSearchAdapter.MyViewHolder holder, int position) {
        User user = userList.get(position);
        holder.title.setText(user.getUsername());
       holder.rating.setText(user.getRating());


    }//End of onBindViewHolder


    public interface FreelancerUserSearchAdapterListener {
        void onUserItemSelectedAdapter(User user);
    }
    @Override
    public int getItemCount() {
        return userList.size();
    }



    //search view


    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String charSequenceString = constraint.toString();
                if (charSequenceString.isEmpty()) {
                   userList=userSearchList;
                    filteredUserSearchList = userList;
                } else {
                   // filteredProjectSearchList.clear();
                    List<User> filteredList = new ArrayList<>();
                    for (User user : userList) {
                        if (user.getUsername().toLowerCase().contains(charSequenceString.toLowerCase())) {
                            filteredList.add(user);
                        }


                        userList = filteredList;
                    }

                }
                FilterResults results = new FilterResults();
                results.values = filteredUserSearchList;
                return results;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {


                filteredUserSearchList = (List<User>) results.values;
                notifyDataSetChanged();
            }
        };
    }


}
