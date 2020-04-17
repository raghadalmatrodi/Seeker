package com.example.seeker.EmployerMainPages.SearchTab_Emp;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.seeker.Database.ApiClients;
import com.example.seeker.EmployerMainPages.SearchTab_Emp.SearchFragments.Emp_Search_InnerUsers_Fragment;
import com.example.seeker.Model.Project;
import com.example.seeker.Model.User;
import com.example.seeker.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserSearchAdapter extends RecyclerView.Adapter<UserSearchAdapter.MyViewHolder> implements Filterable {


    private List<User> userSearchList;
    private List<User> userList;

    UserSearchAdapterListener listener;
    private List<User> filteredUserSearchList;
    private Context mContext;


    public void setListener(UserSearchAdapterListener listener) {
        this.listener = listener;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public ImageView avatar;
        public ImageView arrow;
        public TextView title, numOfRatings;
        public RatingBar rating;

        public MyViewHolder(View view) {
            super(view);

            title = view.findViewById(R.id.row_username);
            rating = view.findViewById(R.id.row_user_rating);
            arrow = view.findViewById(R.id.row_user_arrow);
            rating.setIsIndicator(true);
            numOfRatings = view.findViewById(R.id.view_others_profile_numOfRatings);
            avatar = view.findViewById(R.id.s_user_image);


            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener != null)
                    listener.onUserItemSelectedAdapter(userList.get(getAdapterPosition()));
                }
            });
        }//End of MyViewHolder()
    }//Enf of class MyViewHolder


    public UserSearchAdapter(Context context, List<User> userList) {
        this.mContext = context;
        this.userList = userList;
        this.userSearchList=userList;
    }//End of CategorySearchAdapter()

    @Override
    public UserSearchAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_user, parent, false);

        MyViewHolder evh = new MyViewHolder(itemView);
        return evh;
    }//End of onCreateViewHolder

    @Override
    public void onBindViewHolder(final UserSearchAdapter.MyViewHolder holder, int position) {

        User user = userList.get(position);
        String capitalizedName = user.getName().substring(0,1).toUpperCase() + user.getName().substring(1,user.getName().length());
        holder.title.setText(capitalizedName);
        compareRatings(user, holder);
        if(user.getAvatar()!=null)
            Glide.with(mContext)
                    .load(user.getAvatar())
                    .placeholder(R.drawable.user).apply(RequestOptions.circleCropTransform())
                    .into(holder.avatar);



    }//End of onBindViewHolder


    public interface UserSearchAdapterListener {
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

    private void compareRatings(User myuser, UserSearchAdapter.MyViewHolder holder){
        ApiClients.getAPIs().compareUserRatings(Long.valueOf(myuser.getId())).enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                if (response.isSuccessful()) {
//                    user =
                    if (myuser.getRating()!=null){
                        holder.rating.setRating(Float.valueOf(myuser.getRating()));
                        Log.d("user rating = ",""+Float.valueOf(myuser.getRating()));
                        holder.numOfRatings.setText("("+response.body()+")");
                    }

                } else {

                }
            }

            @Override
            public void onFailure(Call<Integer> call, Throwable t) {

            }
        });
    }



}
