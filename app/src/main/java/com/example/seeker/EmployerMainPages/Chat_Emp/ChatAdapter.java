package com.example.seeker.EmployerMainPages.Chat_Emp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.seeker.Model.Chat;
import com.example.seeker.R;
import com.example.seeker.SharedPref.Constants;
import com.example.seeker.SharedPref.MySharedPreference;

import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.MyViewHolder> {

    private Context mContext;
    private List<Chat> chats;

    private OnItemClickListener mListener;


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView thumbnail;
        public RecyclerView recyclerView;
        public TextView name ;
        public TextView email;



        public MyViewHolder(View view , final OnItemClickListener listener) {
            super(view);
            name =  view.findViewById(R.id.user_name);
           email =  view.findViewById(R.id.user_email);

            thumbnail = view.findViewById(R.id.student_image);
            recyclerView =  view.findViewById(R.id.chats_recyclerview);
            //todo

            view.setOnClickListener(new View.OnClickListener() {
                @Override

                public void onClick(View v) {
                    String name;
                    name = chats.get(getAdapterPosition()).getSecondUser().getUsername();
                    String email = chats.get(getAdapterPosition()).getSecondUser().getEmail();
                    if(listener!=null)
                        listener.onItemClick(chats.get(getAdapterPosition()));

                }


            });
            thumbnail.setOnClickListener(new View.OnClickListener() {
                @Override

                public void onClick(View v) {
                    String name;
                    name = chats.get(getAdapterPosition()).getSecondUser().getUsername();
                    String email = chats.get(getAdapterPosition()).getSecondUser().getEmail();
                    if(listener!=null)
                        listener.onItemClick(chats.get(getAdapterPosition()));

                }


            });

        } //end MyViewHolder

    }
    public ChatAdapter(Context mContext, List<Chat> chats) {
        this.mContext = mContext;
        this.chats = chats;
    } // end ClassAdapter

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_chat,parent,false);

        return new MyViewHolder(itemView ,mListener);
    } // end onCreateViewHolder

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        Chat album = chats.get(position);


        if(MySharedPreference.getLong(mContext, Constants.Keys.USER_ID,-1) == chats.get(position).getFirstUser().getId()){

            holder.name.setText(chats.get(position).getSecondUser().getUsername());
            holder.email.setText(chats.get(position).getSecondUser().getEmail());
        }else{
            holder.name.setText(chats.get(position).getFirstUser().getUsername());
            holder.email.setText(chats.get(position).getFirstUser().getEmail());
        }


    } //  end onBindViewHolder

    public interface OnItemClickListener {
        void onItemClick(Chat chat);
    } // end OnItemClickListener

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    } // end OnItemClickListener

    @Override
    public int getItemCount() {
        return chats.size();
    } // end getItemCount
}