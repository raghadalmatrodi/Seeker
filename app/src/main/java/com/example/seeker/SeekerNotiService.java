package com.example.seeker;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.example.seeker.Database.ApiClients;
import com.example.seeker.Model.ChatMessage;
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

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SeekerNotiService extends FirebaseMessagingService {
    String TAG = SeekerNotiService.class.getSimpleName();
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

        // TODO(developer): Handle FCM messages here.
        // Not getting messages here? See why this may be: https://goo.gl/39bRNJ
        Log.d(TAG, "From: " + remoteMessage.getFrom());

        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            Log.d(TAG, "Message data payload: " + remoteMessage.getData());

            Map<String, String> map = remoteMessage.getData();
            JSONObject jsonObject = new JSONObject(remoteMessage.getData());

            String finalJson = null;
            try {


                finalJson = jsonObject.get("message").toString().replaceAll("\\\\", "").replace("\"{", "{").replace("}\"", "}");
            } catch (JSONException e) {
                e.printStackTrace();
            }


            ChatMessage message = new Gson().fromJson(finalJson, ChatMessage.class);



            Intent intent = new Intent("NewChatMessage");
            intent.putExtra("message", message);
            sendBroadcast(intent);

////            message.setId(Long.parseLong(map.get("id")));
//            User user = new User();
//            user.setId(new Long (map.get("id")));
//
//            message.setMessage(map.get("message"));
//            message.setSender(user);

        }

        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {
            Log.d(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());
        }

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
