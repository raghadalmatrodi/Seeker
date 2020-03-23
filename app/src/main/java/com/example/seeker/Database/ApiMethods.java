package com.example.seeker.Database;

import com.example.seeker.Activities.Contract.ContractFragment;
import com.example.seeker.Model.Bid;
import com.example.seeker.Model.Category;
import com.example.seeker.Model.Certificate;
import com.example.seeker.Model.Chat;
import com.example.seeker.Model.Contract;
import com.example.seeker.Model.Employer;
import com.example.seeker.Model.Freelancer;
import com.example.seeker.Model.Login;
import com.example.seeker.Model.Milestone;
import com.example.seeker.Model.Project;
import com.example.seeker.Model.Responses.ApiResponse;
import com.example.seeker.Model.User;
import com.example.seeker.Model.UserSocialMedia;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
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
        String GET_USER_BY_EMAIL = "user/email/{email}";
        String GET_EMPLOYER_BY_USER_ID = "employer/user_id/{user_id}";
        String GET_ALL_CATEGORY = "category/find-all";
        String GET_FREELANCER_BY_USER_ID = "freelancer/user_id/{user_id}";
        String GET_BIDS_BY_STATUS = "bid/status/{status}";
        String GET_PROJECTS_BY_STATUS = "project/statuses/{status}";
        String POST_CERTIFICATES = "certificate/create";
        String GET_CERTIFICATES = "certificate/find-all";
        String ACCEPT_BID ="bid/accept-bid/{id}";
        String GET_PROJECTS_BY_CATEGORY = "project/category";
        String GET_CONTRACT_BY_PROJECT_ID = "contract/project_id/{project_id}";
        String CREATE_MILESTONE = "milestone/create";

        String GET_ALL_PROJECTS = "project/findAll";
       String GET_ALL_USERS="user/findAll";
        String DELETE_PROJECT="project/{id}";
        String EXTEND_PROJECT="project/extend";
        String FIND_CHAT_BY_USER ="chat/user/{user_id}";


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

    @POST(Methods.GET_BIDS_BY_STATUS)
    Call<List<Bid>> getBidByStatus(@Path("status") String status);

    @POST(Methods.GET_PROJECTS_BY_STATUS)
    Call<List<Project>> getProjectsByStatusOnly(@Path("status") String status);

    @POST(Methods.POST_CERTIFICATES)
    Call<ApiResponse> getPostCertificatesRequest(@Body Certificate certificate);

    @PUT(Methods.ACCEPT_BID)
    Call<ApiResponse> acceptBid(@Path("id") long id);


    @POST(Methods.GET_PROJECTS_BY_CATEGORY)
    Call<List<Project>> getProjectsByCategory(@Body Category category);

    @POST(Methods.CREATE_MILESTONE)
    Call<Milestone> createMilestoneRequest(@Body Milestone milestone);

    /**
     * GET METHODS
     */


    @GET(Methods.GET_BIDS)
    Call<List<Bid>> getAllBids();


    @GET(Methods.GET_SOCIAL_MEDIA)
    Call<List<UserSocialMedia>> getAllSocialMedia();

    @GET(Methods.GET_USER_BY_EMAIL)
    Call<User> findUSerByEmailRequest(@Path("email") String email);

    @GET(Methods.GET_EMPLOYER_BY_USER_ID)
    Call<Employer> getEmployerByUserIdRequest(@Path("user_id") long user_id);

    @GET(Methods.GET_ALL_CATEGORY)
    Call<List<Category>> getALLCategoryRequest();

    @GET(Methods.GET_FREELANCER_BY_USER_ID)
    Call<Freelancer> getFreelancerByUserIdRequest(@Path("user_id") long user_id);

    @GET(Methods.GET_CERTIFICATES)
    Call<List<Certificate>> getAllCertificates();

    @GET(Methods.GET_CONTRACT_BY_PROJECT_ID)
    Call<Contract> getContractByProjectIdRequest(@Path("project_id") long project_id);

    @GET(Methods.GET_ALL_PROJECTS)
    Call<List<Project>> getAllProjects();

    @GET(Methods.GET_ALL_USERS)
    Call<List<User>> getAllUsers();




    @DELETE(Methods.DELETE_PROJECT)
    Call<ApiResponse> deleteProject(@Path("project_id") long project_id);

    @PUT(Methods.EXTEND_PROJECT)
    Call<ApiResponse> updateExpiryDate(@Body Project project);

//    @PUT(Methods.ACCEPT_BID)
//    Call<ApiResponse> acceptBid(@Path("id") long id);
//
//
//    @POST(Methods.GET_PROJECTS_BY_CATEGORY)
//    Call<List<Project>> getProjectsByCategory(@Body Category category);

    @GET(Methods.FIND_CHAT_BY_USER)
    Call<List<Chat>> findChatsByUser(@Path("user_id") Long user_id);

}//End of ApiMethods interface
