package com.example.seeker.EmployerMainPages.Chat_Emp;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.seeker.Model.Chat;
import com.example.seeker.Model.ChatMessage;
import com.example.seeker.R;
import com.example.seeker.SharedPref.Constants;
import com.example.seeker.SharedPref.MySharedPreference;

import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.MyViewHolder> {

    private Context mContext;
    private List<Chat> chats;

    private OnItemClickListener mListener;
    private TextView textView;


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView thumbnail;
        public RecyclerView recyclerView;
        public TextView name ;
        public TextView email;



        public MyViewHolder(View view , final OnItemClickListener listener) {
            super(view);
            name =  view.findViewById(R.id.user_name);
           email =  view.findViewById(R.id.user_last_chat);

            thumbnail = view.findViewById(R.id.user_image);
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
        List<ChatMessage> chatMessages = chats.get(position).getChatMessages();


        Long chatsId = new Long(chats.get(position).getFirstUser().getId());
        if(MySharedPreference.getLong(mContext, Constants.Keys.USER_ID,-1) == chatsId){

            holder.name.setText(chats.get(position).getSecondUser().getUsername());

            if( chats.get(position).getSecondUser().getAvatar() != null ){
                Glide.with(mContext)
                        .load(chats.get(position).getSecondUser().getAvatar())
                        .placeholder(R.drawable.user).apply(RequestOptions.circleCropTransform())
                        .into(holder.thumbnail);
            }

        }else{
            holder.name.setText(chats.get(position).getFirstUser().getUsername());

            if( chats.get(position).getFirstUser().getAvatar() != null ){
                Glide.with(mContext)
                        .load(chats.get(position).getFirstUser().getAvatar())
                        .placeholder(R.drawable.user).apply(RequestOptions.circleCropTransform())
                        .into(holder.thumbnail);
            }
        }

       if(chatMessages!=null){
           if(!chatMessages.isEmpty()){
               ChatMessage message = chatMessages.get(chatMessages.size()-1);
               long userId = new Long(message.getSender().getId());
               if(MySharedPreference.getLong(mContext, Constants.Keys.USER_ID,-1) == userId ) {
                   holder.email.setText("You: " + message.getMessage());
               }else{
                   holder.email.setText(message.getMessage());
               }

       }}

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