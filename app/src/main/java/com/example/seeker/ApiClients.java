package com.example.seeker;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClients {

    public static final String BASE_URL = "http://localhost:8080/api/";

    private static ApiMethods apiMethods = null;


    private ApiClients() {

    }//End of ApiClients() Constructor

    public static ApiMethods getAPIs() {

        if (apiMethods == null) {

            final OkHttpClient okHttpClient = new OkHttpClient.Builder().readTimeout(1, TimeUnit.MINUTES).writeTimeout(1, TimeUnit.MINUTES).connectTimeout(1, TimeUnit.MINUTES).build();

            Gson gson = new GsonBuilder().setLenient().create();

            Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create(gson)).client(okHttpClient).build();

            apiMethods = retrofit.create(ApiMethods.class);

        }//End of if

        return apiMethods;

    }//End of getAPIs

}//End of class ApiClients
