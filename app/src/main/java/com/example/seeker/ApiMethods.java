package com.example.seeker;

import com.example.seeker.Model.ApiResponse;
import com.example.seeker.Model.Login;
import com.example.seeker.Model.User;
import com.example.seeker.Model.UserResponse;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;


public interface ApiMethods {


    interface Methods {

        String SIGNUP = "account/register";
        String LOGIN ="account/login";


    }//End of Methods interface


    //POST Methods
    // حطيت يرجع تي عشان يمكن يصير يوزر ويمكن يصير ايرور

    @POST(Methods.SIGNUP)
    Call<ApiResponse> getSignUpRequest(@Body User user);

    @POST(Methods.LOGIN)
    Call<String> getLoginRequest(@Body Login login);






}//End of ApiMethods interface
