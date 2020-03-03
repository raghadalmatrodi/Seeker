package com.example.seeker.Database;

import com.example.seeker.Model.Bid;
import com.example.seeker.Model.Employer;
import com.example.seeker.Model.Login;
import com.example.seeker.Model.Project;
import com.example.seeker.Model.Responses.ApiResponse;
import com.example.seeker.Model.Responses.ProjectResponse;
import com.example.seeker.Model.User;
import com.example.seeker.Model.UserSocialMedia;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;


public interface ApiMethods {


    interface Methods {

        String SIGNUP = "account/register";
        String LOGIN ="account/login";
        String POST_PROJECT = "project/post";
        String POST_PROJECT_WITH_ATTACHMENTS = "project/with-attachments";
        String POST_BID = "bid/post";
        String GET_PROJECT = "project/status/{status}";
        String GET_BIDS = "bid/find-all";
        String POST_SOCIAL_MEDIA = "userSocialMedia/add-accounts";
        String GET_SOCIAL_MEDIA = "userSocialMedia/find-all";
        String FIND_USER_BY_EMAIL = "user/email/{email}";
        String GET_EMPLOYER_BY_USER_ID = "employer/user_id/{user_id}";



    }//End of Methods interface


    //POST Methods
    // حطيت يرجع تي عشان يمكن يصير يوزر ويمكن يصير ايرور

    @POST(Methods.SIGNUP)
    Call<ApiResponse> getSignUpRequest(@Body User user);

    @POST(Methods.LOGIN)
    Call<String> getLoginRequest(@Body Login login);

    @POST(Methods.POST_PROJECT)
    Call<ApiResponse> getPostProjectRequest(@Body Project project);

    @Multipart
    @POST(Methods.POST_PROJECT_WITH_ATTACHMENTS)
    Call<ApiResponse> getPostProjectWithAttachmentsRequest(@Part("project") RequestBody project , @Part List<MultipartBody.Part> attachments);

    @POST(Methods.POST_BID)
    Call<ApiResponse> getPostBidRequest(@Body Bid bid);


    @POST(Methods.GET_PROJECT)
    Call<List<Project>> getProjectByStatus(@Path("status") String status, @Body Employer employer);

    @POST(Methods.POST_SOCIAL_MEDIA)
    Call<ApiResponse> getPostSocialMediaRequest(@Body UserSocialMedia userSocialMedia);


    //GET METHODS

    @GET(Methods.GET_BIDS)
    Call<List<Bid>> getAllBids();


    @GET(Methods.GET_SOCIAL_MEDIA)
    Call<List<UserSocialMedia>> getAllSocialMedia();

    @GET(Methods.FIND_USER_BY_EMAIL)
    Call<User> findUSerByEmailRequest(@Path("email") String email);

    @GET(Methods.GET_EMPLOYER_BY_USER_ID)
    Call<Employer> getEmployerByUserIdRequest(@Path("user_id") long user_id);





}//End of ApiMethods interface
