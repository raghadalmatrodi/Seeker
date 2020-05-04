package com.example.seeker.Admin;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.seeker.Database.ApiClients;
import com.example.seeker.Model.Project;
import com.example.seeker.Model.User;
import com.example.seeker.R;
import com.example.seeker.ViewProfileActivity;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.MyViewHolder> implements Filterable {

    private List<User> userSearchList;
    private List<User> userList;
    private Context context;
    private List<User> filteredUserSearchList;


    public class MyViewHolder extends RecyclerView.ViewHolder {

        public ImageView avatar;
        public TextView deactivate;
        public TextView title;
        public TextView rating;
        public LinearLayout linearLayout;

        public MyViewHolder(View view) {
            super(view);

            title = view.findViewById(R.id.row_username);
            rating = view.findViewById(R.id.row_user_rating);
            deactivate = view.findViewById(R.id.row_user_deactivate);
            linearLayout = view.findViewById(R.id.user_admin_layout);

            linearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, ViewProfileActivity.class);
                    User user = userList.get(getAdapterPosition());
                    intent.putExtra("myuser", user);
                    context.startActivity(intent);

                }
            });





        }//End of MyViewHolder()
    }//Enf of class MyViewHolder



    public UsersAdapter(List<User> userList, Context context) {

        this.userList = userList;
        this.userSearchList=userList;
        this.context =context;

        setHasStableIds(true);
    }//End of CategorySearchAdapter()

    @Override
    public UsersAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_users_admin, parent, false);

        MyViewHolder evh = new MyViewHolder(itemView);
        return evh;
    }//End of onCreateViewHolder

    @Override
    public void onBindViewHolder(final UsersAdapter.MyViewHolder holder, int position) {


        User user = userList.get(position);
        holder.title.setText(user.getUsername());

        holder.deactivate.setTag(position);
        if(user.getIsEnabled() != null) {
            if (user.getIsEnabled().equals("0")) {

                holder.deactivate.setText("Re-activate");
                holder.deactivate.setBackgroundColor(Color.parseColor("#288F2C"));
            }else{

                holder.deactivate.setText("Deactivate");
                holder.deactivate.setBackgroundColor(Color.parseColor("#A30707"));

            }

        }else{

            holder.deactivate.setText("Deactivate");
            holder.deactivate.setBackgroundColor(Color.parseColor("#A30707"));
        }

        holder.deactivate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Integer pos = (Integer) holder.deactivate.getTag();
                Log.d("user before",  user.getIsEnabled());
                User user = userList.get(pos);
                Dialog(user.getIsEnabled(), user);
                notifyDataSetChanged();

                Log.d("user after",  user.getIsEnabled());
                if(user.getIsEnabled() != null) {
                    if (user.getIsEnabled().equals("0")) {

                        holder.deactivate.setText("Re-activate");
                        holder.deactivate.setBackgroundColor(Color.parseColor("#288F2C"));
                    }else{

                        holder.deactivate.setText("Deactivate");
                        holder.deactivate.setBackgroundColor(Color.parseColor("#A30707"));

                    }

                }else{

                    holder.deactivate.setText("Deactivate");
                    holder.deactivate.setBackgroundColor(Color.parseColor("#A30707"));
                }



                }


        });

    }//End of onBindViewHolder



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

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }
    private void Dialog(String enabled, User user) {

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
        // Setting Dialog Message
        if(enabled.equals("0")){
            alertDialog.setTitle("Are you sure you want to Activate This user?");

        }else{
            alertDialog.setTitle("Are you sure you want to Deactivate this user?");

        }


        //Setting positive "ok" Button
        alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

                long id = Long.valueOf(user.getId());
                    changeEnabled(id,user);



                    dialog.dismiss();

            }//end onClick
        });//end setPositiveButton


        alertDialog.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();

            }
        });
        alertDialog.show();

    }//End of Dialog()

    private void   changeEnabled(long id,User user) {



        ApiClients.getAPIs().changeIsEnabled(id).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if(response.isSuccessful()){

                   String var = response.body();
                    user.setIsEnabled(var);
                    Log.d("var inside", var);
                    notifyDataSetChanged();


                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });

    }


}
