package com.example.seeker.Database;

import com.example.seeker.Model.Bid;
import com.example.seeker.Model.Category;
import com.example.seeker.Model.Chat;
import com.example.seeker.Model.ChatMessage;
import com.example.seeker.Model.Contract;
import com.example.seeker.Model.Employer;
import com.example.seeker.Model.EmployerRating;
import com.example.seeker.Model.Freelancer;
import com.example.seeker.Model.FreelancerRating;
import com.example.seeker.Model.Login;
import com.example.seeker.Model.Milestone;
import com.example.seeker.Model.Project;
import com.example.seeker.Model.PurchaseOrder;
import com.example.seeker.Model.Responses.ApiResponse;
import com.example.seeker.Model.Skill;
import com.example.seeker.Model.StorageDocument;
import com.example.seeker.Model.User;
import com.example.seeker.Model.UserSocialMedia;
import com.google.firebase.annotations.PublicApi;

import java.util.List;
import java.util.Set;

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
        String LOGOUT = "user/logout/{id}";
        String POST_PROJECT = "project/post";
        String POST_PROJECT_WITH_ATTACHMENTS = "project/with-attachments";
        String POST_BID = "bid/post";
        String GET_PROJECT = "project/status/{status}";
        String GET_BIDS = "bid/find-all";
        //todo: hind delete them if the others worked. :)

        String GET_USER_BY_EMAIL = "user/email/{email}";
        String GET_EMPLOYER_BY_USER_ID = "employer/user_id/{user_id}";
        String GET_ALL_CATEGORY = "category/find-all";
        String GET_FREELANCER_BY_USER_ID = "freelancer/user_id/{user_id}";
        String GET_BIDS_BY_STATUS = "bid/status/{status}";
        String GET_PROJECTS_BY_STATUS = "project/statuses/{status}";
        String GET_CERTIFICATES = "certificate/find-all";
        String ACCEPT_BID ="bid/accept-bid/{id}";
        String GET_PROJECTS_BY_CATEGORY = "project/category";
        String GET_CONTRACT_BY_PROJECT_ID = "contract/project_id/{project_id}";
        String CREATE_MILESTONE = "milestone/create";
        String GET_ALL_PROJECTS = "project/findAll";
        String GET_ALL_USERS="user/findAll";
        String DELETE_PROJECT="project/{project_id}";
        String DELETE_PROJECT_ADMIN="project/d/{project_id}";

        //       String GET_ALL_USERS="user/findAll";
        String EXTEND_PROJECT="project/extend";
        String FIND_CHAT_BY_USER ="chat/user/{user_id}";
        String CREATE_CHAT_MESSAGE = "chatMessage";
        String FIND_CHAT = "chat/user/{user1_id}/{user2_id}";
        String DELETE_MILESTONE ="milestone/delete/{id}";
        String UPDATE_TOKEN = "user/updateToken/{token}/{id}";

        String CHANGE_IS_ENABLED="user/userID/{id}";

        String GET_ALL_SKILLS = "skill/find-all";

        String POST_PHONE_NUMBER = "user/phone_number/{id}/{phone_number}";
        String POST_MAROOF_ACC = "freelancer/maarof_account/{id}/{maarof_account}";
        String POST_NATIONAL_ID = "user/national_id/{id}/{national_id}";

        String POST_LINKEDIN = "user/linkedin/{id}/{linkedIn}";
        String POST_TWITTER = "user/twitter/{id}/{twitter}";
        String POST_FACEBOOK = "user/facebook/{id}/{facebook}";

        String POST_EDUCATION = "user/education/{id}/{education}";
        String GET_EDUCATION = "user/get_education/{id}";


        String POST_IMG = "user/img/{id}/{img}";

       // String avat = "user//avatar";



        //EMPLOYER RATING METHODS
        String GET_EMPLOYER_RATING_VALUES = "employer/get_rating_values/{id}";
        String GET_EMPLOYER_TOTAL_RATINGS_VALUE = "employer/get_total_emp_rating/{id}";
        String SET_ALL_EMPLOYER_RATING_VALUES = "employer/rating_values/{project_id}";
        String SET_ALL_FREELANCER_RATING_VALUES = "freelancer/rating_values";

        //employer uses it to rate the freelancer
        String ADD_NEW_FREELANCER_RATING = "freelancerRating/add";
        //freelancer uses it to rate the employer
        String ADD_NEW_EMPLOYER_RATING = "employerRating/add";

        String GET_FREELANCER_BY_ID = "freelancer/{id}";

        String UPDATE_FREELANCER_SKILLS="freelancer/skills/{id}";
        String GET_ALL_FREELANCERS="freelancer/findAll";

        String POST_CALC_EMP_TOTAL_RATINGS = "employerRating/emp";
        String GET_EMPLOYER_BY_ID = "employer/{id}";

        String CALC_FR_TOTAL_RATINGS = "freelancerRating/total";

        String CALC_EMP_TP = "user/total_emp_tp/{id}";
        String CALC_FR_TP = "user/total_fr_tp/{id}";


        String DELETE_BID="bid/{bid_id}";
        String DELETE_BID_ADMIN="bid/d/{bid_id}";

        //غيرت الباث لان كان نفس فايند يوزر كذا اذا جيت ادور يوزر بيحذفه
        String DELETE_USER="user/deleteUser/{user_id}";

        String SWITCH_TYPE ="user/changeType/{id}" ;

        String FIND_PROJECT_BY_ID = "project/{id}";
        String FIND_USER_BY_ID = "user/{id}";
        String CALC_NUM_OF_POSTED_PROJECTS = "employer/posted_projects/{id}";
        String CALC_NUM_HIRED_PROJS = "freelancer/hired_projs/{id}";

        String UPLOAD_AVATAR = "user/avatar/{id}";
        String PURCHASE_ORDER ="order";
        String FIND_CHAT_BY_ID ="chat/{id}";

        String UPLOAD_SAMPLE_WORK = "user/add-work/{id}";
        String DELETE_SAMPLE_WORK = "user/{id}/delete-work/{attachmentId}";

        String DID_EMP_RATE = "project/emp_rated/{id}/{rated}";
        String DID_FR_RATE = "project/fr_rated/{id}/{rated}";

        String COMPARE_RATING = "user/compare/{id}";
        String FIND_BIDS_BY_PROJECT_ID = "bid/find_projs/{project_id}";

        String CHANGE_PASSWORD="user/reset";
        String RESET_PASSWORD= "account/resetPassword/{email}";


        String POST_IBAN_NUMBER = "freelancer/Iban";

        String setEnableProjectExpiryNoti ="user/setEnableProjectExpiryNoti/{id}/{value}";
        String SetEnableProjectSkillNoti = "user/SetEnableProjectSkillNoti/{id}/{value}";
        String SetEnableAcceptBidNoti = "user/SetEnableAcceptBidNoti/{id}/{value}";
        String SetEnableMilestoneDLNoti = "user/SetEnableMilestoneDLNoti/{id}/{value}";





    }//End of Methods interface


    //POST Methods
    // حطيت يرجع تي عشان يمكن يصير يوزر ويمكن يصير ايرور

    @POST(Methods.SIGNUP)
    Call<ApiResponse> getSignUpRequest(@Body User user);
    @POST(Methods.CHANGE_PASSWORD)
    Call<ApiResponse> changePassRequest(@Body User user);

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


    @POST(Methods.GET_BIDS_BY_STATUS)
    Call<List<Bid>> getBidByStatus(@Path("status") String status);

    @POST(Methods.GET_PROJECTS_BY_STATUS)
    Call<List<Project>> getProjectsByStatusOnly(@Path("status") String status);


    @PUT(Methods.ACCEPT_BID)
    Call<ApiResponse> acceptBid(@Path("id") long id);


    @POST(Methods.GET_PROJECTS_BY_CATEGORY)
    Call<List<Project>> getProjectsByCategory(@Body Category category);

    @POST(Methods.CREATE_MILESTONE)
    Call<Milestone> createMilestoneRequest(@Body Milestone milestone);


    //
    @POST(Methods.POST_PHONE_NUMBER)
    Call<User> getPostPhoneNumberRequest(@Path("id") long id, @Path("phone_number") String phone_number);

    @POST(Methods.POST_MAROOF_ACC)
    Call<Freelancer> getPostMaroofAccountRequest(@Path("id") long id, @Path("maarof_account") String maarof_account);

    @POST(Methods.POST_NATIONAL_ID)
    Call<User> getPostNationalIdRequest(@Path("id") long id, @Path("national_id") String national_id);



    @POST(Methods.POST_LINKEDIN)
    Call<User> getPostLinkedInRequest(@Path("id") long id, @Path("linkedIn") String linkedIn);

    @POST(Methods.POST_TWITTER)
    Call<User> getPostTwitterRequest(@Path("id") long id, @Path("twitter") String twitter);

    @POST(Methods.POST_FACEBOOK)
    Call<User> getPostFacebookRequest(@Path("id") long id, @Path("facebook") String facebook);


    @POST(Methods.POST_EDUCATION)
    Call<User> getPostEducation(@Path("id") long id, @Body User education);

    @POST(Methods.POST_IMG)
    Call<Void> getPostImgRequest(@Path("id") long id, @Path("img") byte[] img);
//
//    @POST(Methods.avat)
//    Call<Void> postAvat(@Part("avatar") MultipartBody file);


    //NEW WORKING ONES

    @POST(Methods.POST_IBAN_NUMBER)
    Call<Void>  setFreelancerIban(@Body Freelancer freelancer);

    @POST(Methods.ADD_NEW_FREELANCER_RATING)
    Call<ApiResponse> getRateFreelancerRequest(@Body FreelancerRating freelancerRating);

    @POST(Methods.ADD_NEW_EMPLOYER_RATING)
    Call<ApiResponse> getRateEmployerRequest (@Body EmployerRating employerRating);

    @PUT(Methods.SET_ALL_EMPLOYER_RATING_VALUES)
    Call<Void> setAllEmployerRatingValues(@Body Employer employer, @Path("project_id") long project_id);

    @POST(Methods.POST_CALC_EMP_TOTAL_RATINGS)
    Call<Double> CalculateEmployerTotalRating(@Body Employer employer_id);


    @PUT(Methods.SET_ALL_FREELANCER_RATING_VALUES)
    Call<Void> setAllFreelancerRatingValues(@Body Freelancer freelancer);

    @POST(Methods.CALC_FR_TOTAL_RATINGS)
    Call<Double> CalculateFreelancerTotalRating(@Body Freelancer freelancer_id);

    @POST(Methods.CALC_EMP_TP)
    Call<Integer> CalculateEmployerTrustPoints(@Path("id") long id);

    @POST(Methods.CALC_FR_TP)
    Call<Integer> CalculateFreelancerTrustPoints(@Path("id") long id);

    @POST(Methods.CALC_NUM_OF_POSTED_PROJECTS)
    Call<Void> CalculateNumberOfPostedProjects(@Path("id") long id);

    @POST(Methods.CALC_NUM_HIRED_PROJS)
    Call<Void> CalculateNumberOfWorkedOnProjects(@Path("id") long id);

    @POST(Methods.DID_EMP_RATE)
    Call<Project> DidEmployerRate(@Path("id") long id, @Path("rated") boolean rated);

    @POST(Methods.DID_FR_RATE)
    Call<Project> DidFreelancerRate(@Path("id") long id, @Path("rated") boolean rated);


    /**
     * GET METHODS
     */


    @GET(Methods.GET_BIDS)
    Call<List<Bid>> getAllBids();

    @GET(Methods.GET_USER_BY_EMAIL)
    Call<User> findUSerByEmailRequest(@Path("email") String email);

    @GET(Methods.GET_EMPLOYER_BY_USER_ID)
    Call<Employer> getEmployerByUserIdRequest(@Path("user_id") long user_id);

    @GET(Methods.GET_ALL_CATEGORY)
    Call<List<Category>> getALLCategoryRequest();

    @GET(Methods.GET_FREELANCER_BY_USER_ID)
    Call<Freelancer> getFreelancerByUserIdRequest(@Path("user_id") long user_id);


    @GET(Methods.GET_CONTRACT_BY_PROJECT_ID)
    Call<Contract> getContractByProjectIdRequest(@Path("project_id") long project_id);

    @GET(Methods.GET_ALL_PROJECTS)
    Call<List<Project>> getAllProjects();

    @GET(Methods.GET_ALL_USERS)
    Call<List<User>> getAllUsers();




    @DELETE(Methods.DELETE_PROJECT)
    Call<ApiResponse> deleteProject(@Path("project_id") long project_id);
    @DELETE(Methods.DELETE_PROJECT_ADMIN)
    Call<ApiResponse> deleteProjectAdmin(@Path("project_id") long project_id);

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

    @POST(Methods.CREATE_CHAT_MESSAGE)
    Call<ChatMessage> createChatMessage(@Body ChatMessage chatMessage);


    @GET(Methods.GET_ALL_SKILLS)
    Call<Set<Skill>> getAllSkills();

    @GET(Methods.GET_EMPLOYER_RATING_VALUES)
    Call<List<Integer>> getEmployerRatingValues(@Path("id") long id);

    @GET(Methods.GET_EMPLOYER_TOTAL_RATINGS_VALUE)
    Call<Float> getEmployerTotalRating(@Path("id") long id);

    @GET(Methods.CHANGE_IS_ENABLED)
    Call<String> changeIsEnabled(@Path("id") long id);

    @GET(Methods.FIND_CHAT)
    Call<Chat> findChat(@Path("user1_id") Long user1_id , @Path("user2_id") Long user2_id);

    @DELETE(Methods.DELETE_MILESTONE)
    Call<ApiResponse> deleteMilestone(@Path("id") long id);

    @PUT(Methods.UPDATE_TOKEN)
    Call<ApiResponse> updateToken(@Path("token") String token , @Path("id") long id);
    @GET(Methods.GET_FREELANCER_BY_ID)
    Call<Freelancer> findFreelancerById(@Path("id") long id);

    @GET(Methods.GET_EMPLOYER_BY_ID)
    Call<Employer> getEmployer(@Path("id") long id);
    //new change type user

    @PUT(Methods.SWITCH_TYPE)
    Call<Void> switchType(@Path("id") long id);

    @POST(Methods.UPDATE_FREELANCER_SKILLS)
    Call<ApiResponse> updateFreelancerSkill(@Path("id") long id , @Body Set<Skill> skills);

    @GET(Methods.GET_ALL_FREELANCERS)
    Call<List<Freelancer>> getALLFreelancersRequest();

    @DELETE(Methods.DELETE_BID)
    Call<ApiResponse> deleteBid(@Path("bid_id") long bid_id);

    @DELETE(Methods.DELETE_BID_ADMIN)
    Call<ApiResponse> deleteBidAdmin(@Path("bid_id") long bid_id);


    @DELETE(Methods.DELETE_USER)
    Call<Void> deleteUserById(@Path("user_id") long user_id);




    @GET(Methods.FIND_PROJECT_BY_ID)
    Call<Project> findProjectById(@Path("id") long id);

    @GET(Methods.FIND_USER_BY_ID)
    Call<User> findUserById(@Path("id") long id);


    @Multipart
    @POST(Methods.UPLOAD_AVATAR)
    Call<StorageDocument> uploadAvatar(@Path ("id") long id, @Part MultipartBody.Part Avatar);

    @Multipart
    @POST(Methods.UPLOAD_SAMPLE_WORK)
    Call<StorageDocument> uploadSampleWork ( @Path("id") long id, @Part MultipartBody.Part attachment);

    @DELETE(Methods.DELETE_SAMPLE_WORK)
    Call<Void> deleteSampleWork (@Path("id") long id ,@Path("attachmentId") long attachmentId );

    @POST(Methods.PURCHASE_ORDER)
    Call<PurchaseOrder> purchaseOrder(@Body PurchaseOrder purchaseOrder);

    @GET(Methods.FIND_CHAT_BY_ID)
    Call<Chat> findChatById(@Path("id") long id);

    @GET(Methods.COMPARE_RATING)
    Call<Integer> compareUserRatings(@Path("id") long id);

    @GET(Methods.FIND_BIDS_BY_PROJECT_ID)
    Call<List<Bid>> findBidsByProjectId(@Path("project_id") long project_id);

    @PUT(Methods.RESET_PASSWORD)
    Call<Void> forgetPassword(@Path("email") String email);

    @PUT(Methods.setEnableProjectExpiryNoti)
    Call<Void> setEnableProjectExpiryNoti(@Path("id") Long id, @Path("value") boolean value);

    @PUT(Methods.SetEnableAcceptBidNoti)
    Call<Void> SetEnableAcceptBidNoti(@Path("id") Long id, @Path("value") boolean value);

    @PUT(Methods.SetEnableMilestoneDLNoti)
    Call<Void> SetEnableMilestoneDLNoti(@Path("id") Long id, @Path("value") boolean value);

    @PUT(Methods.SetEnableProjectSkillNoti)
    Call<Void> SetEnableProjectSkillNoti(@Path("id") Long id, @Path("value") boolean value);

    @PUT(Methods.LOGOUT)
    Call<Void> logout (@Path("id") long id);



}//End of ApiMethods interface
