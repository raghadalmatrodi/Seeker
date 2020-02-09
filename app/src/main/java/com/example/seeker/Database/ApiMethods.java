package com.example.seeker.Database;

import com.example.seeker.Model.Login;
import com.example.seeker.Model.Project;
import com.example.seeker.Model.Responses.ApiResponse;
import com.example.seeker.Model.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;


public interface ApiMethods {


    interface Methods {

        String SIGNUP = "account/register";
        String LOGIN ="account/login";
        String POST_PROJECT = "project/post";

    }//End of Methods interface


    //POST Methods
    // حطيت يرجع تي عشان يمكن يصير يوزر ويمكن يصير ايرور

    @POST(Methods.SIGNUP)
    Call<ApiResponse> getSignUpRequest(@Body User user);

    @POST(Methods.LOGIN)
    Call<String> getLoginRequest(@Body Login login);

    @POST(Methods.POST_PROJECT)
    Call<ApiResponse> getPostProjectRequest(@Body Project project);




}//End of ApiMethods interface
