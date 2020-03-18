package com.example.seeker.Database;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClients {


    //if you are using a real phone
    public static final String BASE_URL = "http://192.168.8.199:8080/api/";

    //if you are using emulator
//    public static final String BASE_URL = "http://10.0.2.2:8080/api/";



    private static ApiMethods apiMethods = null;
    final static OkHttpClient   okHttpClient = new OkHttpClient.Builder().readTimeout(1, TimeUnit.MINUTES)
            .writeTimeout(1, TimeUnit.MINUTES).connectTimeout(1, TimeUnit.MINUTES).build();
    private static Gson gson = new GsonBuilder().setLenient().create();
    private static Retrofit retrofit = ApiClients.getInstant();


    private ApiClients() {

    }//End of ApiClients() Constructor

    public static ApiMethods getAPIs() {

        if (apiMethods == null) {
            getInstant();

            apiMethods = retrofit.create(ApiMethods.class);

        }//End of if

        return apiMethods;

    }//End of getAPIs

    public static Retrofit getInstant(){
        if(retrofit==null){

            retrofit=  new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create(gson))
                    .client(okHttpClient).build();
        }
        return retrofit;
    }

}//End of class ApiClients
