package com.example.seeker.Database;

import com.example.seeker.Model.Bid;
import com.example.seeker.Model.Category;
import com.example.seeker.Model.Certificate;
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
import com.example.seeker.Model.Responses.ApiResponse;
import com.example.seeker.Model.Skill;
import com.example.seeker.Model.User;
import com.example.seeker.Model.UserSocialMedia;
import com.example.seeker.Rating.EmployerRatesFreelancer;

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
        String POST_PROJECT = "project/post";
        String POST_PROJECT_WITH_ATTACHMENTS = "project/with-attachments";
        String POST_BID = "bid/post";
        String GET_PROJECT = "project/status/{status}";
        String GET_BIDS = "bid/find-all";
        //todo: hind delete them if the others worked. :)
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
        String DELETE_PROJECT="project/{project_id}";
//       String GET_ALL_USERS="user/findAll";
        String EXTEND_PROJECT="project/extend";
        String FIND_CHAT_BY_USER ="chat/user/{user_id}";
        String CREATE_CHAT_MESSAGE = "chatMessage";
        String FIND_CHAT = "chat/user/{user1_id}/{user2_id}";
        String DELETE_MILESTONE ="milestone/delete/{id}";
        String UPDATE_TOKEN = "user/updateToken/{token}/{id}";



        String GET_ALL_SKILLS = "skill/find-all";

        String POST_PHONE_NUMBER = "user/phone_number/{id}/{phone_number}";
        String POST_MAROOF_ACC = "freelancer/maarof_account/{id}/{maarof_account}";
        String POST_NATIONAL_ID = "user/national_id/{id}/{national_id}";

        String POST_LINKEDIN = "user/linkedin/{id}/{linkedIn}";
        String POST_TWITTER = "user/twitter/{id}/{twitter}";
        String POST_FACEBOOK = "user/facebook/{id}/{facebook}";

        String GET_LINKEDIN = "user/get_linkedin/{id}";
        String GET_TWITTER = "user/get_twitter/{id}";
        String GET_FACEBOOK = "user/get_fb/{id}";

        String GET_NID = "user/get_nid/{id}";
        String GET_PHONE = "user/get_phone/{id}";
        String GET_MAROOF = "freelancer/get_maarof/{id}";

        String POST_EDUCATION = "user/education/{id}/{education}";
        String GET_EDUCATION = "user/get_education/{id}";


        String POST_IMG = "user/img/{id}/{img}";

        String avat = "user//avatar";


        String GET_ALL_TP = "user/get_all_vals/{id}";

        String POST_EMPLOYER_TRUST_POINTS = "user/total_emp_tp/{id}";
        String POST_FREELANCER_TRUST_POINTS = "user/total_fr_tp/{id}";

        //EMPLOYER RATING METHODS
        String GET_EMPLOYER_RATING_VALUES = "employer/get_rating_values/{id}";
        String GET_EMPLOYER_TOTAL_RATINGS_VALUE = "employer/get_total_emp_rating/{id}";
        String SET_ALL_EMPLOYER_RATING_VALUES = "employer/rating_values";

        //employer uses it to rate the freelancer
        String ADD_NEW_FREELANCER_RATING = "freelancerRating/add";
        //freelancer uses it to rate the employer
        String ADD_NEW_EMPLOYER_RATING = "employerRating/add";

        String GET_FREELANCER_BY_ID = "freelancer/{id}";

        String UPDATE_FREELANCER_SKILLS="freelancer/skills/{id}";
        String GET_ALL_FREELANCERS="freelancer/findAll";

        String POST_CALC_EMP_TOTAL_RATINGS = "employerRating/emp";
        String GET_EMPLOYER_BY_ID = "employer/{id}";

        String POST_NUM_OF_POSTED_PROJECTS = "employer/posted_projs/{id}/{num_of_posted_Projects}";



        String DELETE_BID="bid/{bid_id}";

        String SWITCH_TYPE ="user/changeType/{id}" ;
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


    //
    @POST(Methods.POST_PHONE_NUMBER)
    Call<Void> getPostPhoneNumberRequest(@Path("id") long id, @Path("phone_number") String phone_number);

    @POST(Methods.POST_MAROOF_ACC)
    Call<Void> getPostMaroofAccountRequest(@Path("id") long id, @Path("maarof_account") String maarof_account);

    @POST(Methods.POST_NATIONAL_ID)
    Call<Void> getPostNationalIdRequest(@Path("id") long id, @Path("national_id") String national_id);



    @POST(Methods.POST_LINKEDIN)
    Call<Void> getPostLinkedInRequest(@Path("id") long id, @Path("linkedIn") String linkedIn);

    @POST(Methods.POST_TWITTER)
    Call<Void> getPostTwitterRequest(@Path("id") long id, @Path("twitter") String twitter);

    @POST(Methods.POST_FACEBOOK)
    Call<Void> getPostFacebookRequest(@Path("id") long id, @Path("facebook") String facebook);

    @POST(Methods.POST_EDUCATION)
    Call<Void> getPostEducation(@Path("id") long id, @Path("education") String education);

    @POST(Methods.POST_IMG)
    Call<Void> getPostImgRequest(@Path("id") long id, @Path("img") byte[] img);

    @POST(Methods.avat)
    Call<Void> postAvat(@Part("avatar") MultipartBody file);


    //NEW WORKING ONES

    //TODO: CALCULATE TRUST POINTS USING THIS API :) DELETE OLDER ONES :)
    @POST(Methods.POST_EMPLOYER_TRUST_POINTS)
    Call<Integer> calculateEmployerTrustPoints(@Path("id") long id);

    @POST(Methods.POST_FREELANCER_TRUST_POINTS)
    Call<Integer> calculateFreelancerTrustPoints(@Path("id") long id);

    @POST(Methods.ADD_NEW_FREELANCER_RATING)
    Call<ApiResponse> getRateFreelancerRequest(@Body FreelancerRating freelancerRating);

    @POST(Methods.ADD_NEW_EMPLOYER_RATING)
    Call<ApiResponse> getRateEmployerRequest (@Body EmployerRating employerRating);

    @PUT(Methods.SET_ALL_EMPLOYER_RATING_VALUES)
    Call<Void> setAllEmployerRatingValues(@Body Employer employer);

    @POST(Methods.POST_CALC_EMP_TOTAL_RATINGS)
    Call<Double> CalculateEmployerTotalRating(@Body Employer employer_id);

    @POST(Methods.POST_NUM_OF_POSTED_PROJECTS)
    Call<Void> setNumberOfPostedProjects(@Path("id") long id, @Path("num_of_posted_Projects") int num_of_posted_projects);



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

    @POST(Methods.CREATE_CHAT_MESSAGE)
    Call<ChatMessage> createChatMessage(@Body ChatMessage chatMessage);


    @GET(Methods.GET_ALL_SKILLS)
    Call<Set<Skill>> getAllSkills();


    @GET(Methods.GET_LINKEDIN)
    Call<String> getLinkedin(@Path("id") long id);

    @GET(Methods.GET_TWITTER)
    Call<String> getTwitter(@Path("id") long id);

    @GET(Methods.GET_FACEBOOK)
    Call<String> getFacebook(@Path("id") long id);

    @GET(Methods.GET_EDUCATION)
    Call<String> getEducation(@Path("id") long id);

    @GET(Methods.GET_NID)
    Call<String> getNationalId(@Path("id") long id);

    @GET(Methods.GET_PHONE)
    Call<String> getPhoneNumber(@Path("id") long id);

    @GET(Methods.GET_MAROOF)
    Call<String> getMaroofAcc(@Path("id") long id);

    @GET(Methods.GET_ALL_TP)
    Call<List<String>> getAllTPVals(@Path("id") long id);

    @GET(Methods.GET_EMPLOYER_RATING_VALUES)
    Call<List<Integer>> getEmployerRatingValues(@Path("id") long id);

    @GET(Methods.GET_EMPLOYER_TOTAL_RATINGS_VALUE)
    Call<Float> getEmployerTotalRating(@Path("id") long id);



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

}//End of ApiMethods interface
