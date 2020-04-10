package com.example.seeker.EmployerMainPages.Chat_Emp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;


import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.seeker.ChatMessageBroadcast;
import com.example.seeker.Database.ApiClients;
import com.example.seeker.EmployerMainPages.EmployerMainActivity;
import com.example.seeker.Model.Chat;
import com.example.seeker.Model.ChatMessage;
import com.example.seeker.Model.User;
import com.example.seeker.R;
import com.example.seeker.SharedPref.Constants;
import com.example.seeker.SharedPref.MySharedPreference;
import com.google.gson.Gson;
import com.stfalcon.chatkit.commons.ImageLoader;
import com.stfalcon.chatkit.messages.MessageInput;
import com.stfalcon.chatkit.messages.MessagesList;
import com.stfalcon.chatkit.messages.MessagesListAdapter;

import android.app.FragmentManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.nio.channels.Channel;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Emp_ChatMessages extends AppCompatActivity {
    private static final String LOG = Emp_ChatMessages.class.getSimpleName();
    MessagesList messagesList;
    ImageView backButton;
    Chat chat;
    TextView name;
    MessageInput inputView;
    MessagesListAdapter<ChatMessage> adapter;
    User sender;
    // Broadcast
    private BroadcastReceiver broadcastReceiver = new ChatMessageBroadcast() {
        @Override
        public void onReceive(Context context, Intent intent) {
            super.onReceive(context, intent);
            ChatMessage message = (ChatMessage) intent.getSerializableExtra("chatM");
//            Alerter.create(Emp_ChatMessages.this)
//                    .setTitle("New Message")
//                    .setText(message.getMessage())
//                    .setBackgroundColorRes(R.color.colorPrimary)
//                    .setDuration(3000)
//                    .show();

//            Toast.makeText(Emp_ChatMessages.this, message.getMessage(), Toast.LENGTH_LONG).show();
            if(message.getChat().getId() == chat.getId()){
                adapter.addToStart(message,true);
            }
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        this.unregisterReceiver(broadcastReceiver);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        setContentView(R.layout.activity_emp__chat_messages);
        messagesList = findViewById(R.id.messagesList);
        backButton = findViewById(R.id.chat_back);
        name = findViewById(R.id.user_name);
        inputView = findViewById(R.id.input);
            if(getIntent().hasExtra("chatM")){
                ChatMessage chatMessage = (ChatMessage)  getIntent().getSerializableExtra("chatM");
                chat = chatMessage.getChat();
                ApiClients.getAPIs().findChatById(chat.getId()).enqueue(new Callback<Chat>() {
                    @Override
                    public void onResponse(Call<Chat> call, Response<Chat> response) {
                        if(response.isSuccessful()){
                            chat = response.body();
                            setTheAdapter();

                        }else{

                        }
                    }

                    @Override
                    public void onFailure(Call<Chat> call, Throwable t) {

                    }
                });

            }

        if(getIntent().hasExtra("chat"))
            chat = (Chat) getIntent().getSerializableExtra("chat");
        Log.i(LOG,"the object is :" + chat.toString());


        initToolBar();
        setTheAdapter();

        inputView.setInputListener(new MessageInput.InputListener() {
            @Override
            public boolean onSubmit(CharSequence input) {
                String createdAt = getTime();
                long id = UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE;

                ChatMessage message = new ChatMessage(id, createdAt, input.toString(), sender,chat );

                boolean isSent= excecuteApiRequest( message);

                adapter.addToStart(message , true);
                adapter.notifyDataSetChanged();
               // adapter.notifyAll();



                return true;
            }
        });


        IntentFilter intentFilter = new IntentFilter("NewChatMessage");
        this.registerReceiver(broadcastReceiver, intentFilter);
    }


    private boolean excecuteApiRequest(ChatMessage message) {
        final boolean[] sent = {false};
      ApiClients.getAPIs().createChatMessage(message).enqueue(new Callback<ChatMessage>() {
          @Override
          public void onResponse(Call<ChatMessage> call, Response<ChatMessage> response) {
              if(response.isSuccessful()){
                  sent[0] = true;
                  Log.i(LOG,"onResponse :" + response.toString());

              }else{
                  Log.i(LOG,"onResponse :" + response.toString());

              }
          }

          @Override
          public void onFailure(Call<ChatMessage> call, Throwable t) {
              Log.i(LOG,"onFailure :" + t.toString() + t.getLocalizedMessage() + t.getMessage());

          }
      });

      return sent[0];
    }


    public void setTheAdapter(){


        adapter = new MessagesListAdapter<>(MySharedPreference.getLong(getApplicationContext(), Constants.Keys.USER_ID,-1)+""
                , null);
        messagesList.setAdapter(adapter);
        adapter.addToEnd(chat.getChatMessages(),false);
    //    adapter.addToStart(chat.getChatMessages().get(0), true);

    }

    private LocalDateTime convertStringToLocalDateTime(String dateString) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        return LocalDateTime.parse(dateString, formatter);
    }

    private String getTime() {

        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

        String dateString = formatter.format(date);
        LocalDateTime time = convertStringToLocalDateTime(dateString);
        return time.toString();
    }


    public void initToolBar(){
        Long chatsId = new Long(chat.getFirstUser().getId());
        if(MySharedPreference.getLong(getApplicationContext(), Constants.Keys.USER_ID,-1) == chatsId){

            name.setText(chat.getSecondUser().getUsername());
            sender= chat.getFirstUser();
        }else{
            name.setText(chat.getFirstUser().getUsername());
            sender = chat.getSecondUser();
        }

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), EmployerMainActivity.class);
                startActivity(intent);
            }
        });

    }

}
