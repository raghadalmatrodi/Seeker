package com.example.seeker;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.Fragment;

import com.example.seeker.Database.ApiClients;
import com.example.seeker.EmployerMainPages.Chat_Emp.Emp_ChatMessages;
import com.example.seeker.EmployerMainPages.EmployerMainActivity;
import com.example.seeker.FreelancerMainPages.FreelancerMainActivity;
import com.example.seeker.FreelancerMainPages.MyProjectsTab_Freelancer.Freelancer_viewProjectFragment;
import com.example.seeker.Model.Chat;
import com.example.seeker.Model.ChatMessage;
import com.example.seeker.Model.Project;
import com.example.seeker.Model.Responses.ApiResponse;
import com.example.seeker.Model.User;
import com.example.seeker.SharedPref.Constants;
import com.example.seeker.SharedPref.MySharedPreference;
import com.google.android.gms.common.api.Api;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SeekerNotiService extends FirebaseMessagingService {
    String TAG = SeekerNotiService.class.getSimpleName();
    public static final String NOTIFICATION_CHANNEL_ID = "10001" ;
    private final static String default_notification_channel_id = "123" ;
    public SeekerNotiService() {
    }
//
//    @Override
//    public IBinder onBind(Intent intent) {
//        // TODO: Return the communication channel to the service.
//        throw new UnsupportedOperationException("Not yet implemented");
//    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        // ...
//
//        // TODO(developer): Handle FCM messages here.
//        // Not getting messages here? See why this may be: https://goo.gl/39bRNJ
//        Log.d(TAG, "From: " + remoteMessage.getFrom());
//
//        // Check if message contains a data payload.
//        if (remoteMessage.getData().size() > 0) {
//            Log.d(TAG, "Message data payload: " + remoteMessage.getData());

            Map<String, String> map = remoteMessage.getData();
            JSONObject jsonObject = new JSONObject(remoteMessage.getData());

            String finalJson = null;
            try {


                finalJson = jsonObject.get("message").toString().replaceAll("\\\\", "").replace("\"{", "{").replace("}\"", "}");
            } catch (JSONException e) {
                e.printStackTrace();
            }


//            ChatMessage message = new Gson().fromJson(finalJson, ChatMessage.class);



//
//////            message.setId(Long.parseLong(map.get("id")));
////            User user = new User();
////            user.setId(new Long (map.get("id")));
////
////            message.setMessage(map.get("message"));
////            message.setSender(user);
//
//        }
//
//        // Check if message contains a notification payload.
//        if (remoteMessage.getNotification() != null) {
//            Log.d(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());
//        }
        String type = remoteMessage.getData().get("type");
        Intent notificationIntent = new Intent(getApplicationContext() , Emp_ChatMessages. class ) ;
        String target = "";

        if(type.equals("skills")){

            notificationIntent = new Intent(getApplicationContext(), FreelancerMainActivity.class);
            target = "project";
           // notificationIntent.putExtra( "NotificationMessage" , new Gson().fromJson(finalJson, Project.class) ) ;
            notificationIntent.putExtra("project",new Gson().fromJson(finalJson, Project.class) );

        }else if(type.equals("chat")){

            notificationIntent = new Intent(getApplicationContext(), Emp_ChatMessages.class);
            target = "chat";
            //notificationIntent.putExtra( "NotificationMessage" , new Gson().fromJson(finalJson, Chat.class) ) ;
            notificationIntent.putExtra("chatM",new Gson().fromJson(finalJson, ChatMessage.class) );
            Intent intent = new Intent("NewChatMessage");
            intent.putExtra("chatM", new Gson().fromJson(finalJson, ChatMessage.class));
            sendBroadcast(intent);
        }else if(type.equals("expiry")){

        }else if(type.equals("acceptBid")){

            notificationIntent = new Intent(getApplicationContext(), FreelancerMainActivity.class);
//            target = "project";
            // notificationIntent.putExtra( "NotificationMessage" , new Gson().fromJson(finalJson, Project.class) ) ;
//            notificationIntent.putExtra("project",new Gson().fromJson(finalJson, Project.class) );

        }else if(type.equals("milestone")){

        }else{

        }

        notificationIntent.putExtra("Fragment",  target);
//        notificationIntent.addCategory(Intent. CATEGORY_LAUNCHER ) ;
//        notificationIntent.setAction(Intent. ACTION_MAIN ) ;
        notificationIntent.setFlags(Intent. FLAG_ACTIVITY_CLEAR_TOP | Intent. FLAG_ACTIVITY_SINGLE_TOP ) ;
        PendingIntent resultIntent = PendingIntent. getActivity (getApplicationContext() , 0 , notificationIntent , PendingIntent.FLAG_UPDATE_CURRENT ) ;
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(getApplicationContext() ,
                default_notification_channel_id )
                .setSmallIcon(R.drawable. ic_launcher_foreground )
                .setContentTitle( remoteMessage.getData().get("title") )
                .setContentText( remoteMessage.getData().get("body"))
                .setAutoCancel(true)
                .setContentIntent(resultIntent) ;
        NotificationManagerCompat mNotificationManager = NotificationManagerCompat.from(getApplicationContext());
        if (android.os.Build.VERSION. SDK_INT >= android.os.Build.VERSION_CODES. O ) {
            int importance = NotificationManager. IMPORTANCE_HIGH ;
            NotificationChannel notificationChannel = new
                    NotificationChannel( NOTIFICATION_CHANNEL_ID , "NOTIFICATION_CHANNEL_NAME" , importance) ;
            mBuilder.setChannelId( NOTIFICATION_CHANNEL_ID ) ;
            assert mNotificationManager != null;
            mNotificationManager.createNotificationChannel(notificationChannel) ;
        }
        assert mNotificationManager != null;
        mNotificationManager.notify(( int ) System. currentTimeMillis () ,
                mBuilder.build()) ;

        // Also if you intend on generating your own notifications as a result of a received FCM
        // message, here is where that should be initiated. See sendNotification method below.
    }
    public static <T> T parseObjectFromString(String s, Class<T> clazz) throws Exception {
        return clazz.getConstructor(new Class[] {String.class }).newInstance(s);
    }

    /**
     * Called if InstanceID token is updated. This may occur if the security of
     * the previous token had been compromised. Note that this is called when the InstanceID token
     * is initially generated so this is where you would retrieve the token.
     */
    @Override
    public void onNewToken(String token) {
        Log.d(TAG, "Refreshed token: " + token);

        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // Instance ID token to your app server.
        Long userId = MySharedPreference.getLong(getApplicationContext(),Constants.Keys.USER_ID,-1);

        //TODO save the token in shared prefrencess
        MySharedPreference.putString(getApplicationContext(), Constants.Keys.TOKEN_ID,token);
     if(MySharedPreference.getBoolean(getApplicationContext(),Constants.Keys.IS_LOGIN,false)){
         ApiClients.getAPIs().updateToken(token ,userId ).enqueue(new Callback<ApiResponse>() {
             @Override
             public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                 if(response.isSuccessful()){
                     Log.d(TAG, "successful token: " + response.message());

                 }else{

                 }
             }

             @Override
             public void onFailure(Call<ApiResponse> call, Throwable t) {
                 Log.d(TAG, "Failure : " + t.getMessage());

             }
         });
     }
       // sendRegistrationToServer(token);
    }
}
