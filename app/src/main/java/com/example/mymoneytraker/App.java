package com.example.mymoneytraker;

import android.app.Application;

import com.google.firebase.database.FirebaseDatabase;

//import com.example.mymoneytraker.api.Api;
//import com.google.gson.FieldNamingPolicy;
//import com.google.gson.Gson;
//import com.google.gson.GsonBuilder;
//
//import okhttp3.OkHttpClient;
//import okhttp3.logging.HttpLoggingInterceptor;
//import retrofit2.Retrofit;
//import retrofit2.converter.gson.GsonConverterFactory;

public class App extends Application {

//    private Api api;
//    private final static String BASE_URL = "http://loftschoolandroid1117.getsandbox.com/";

    @Override
    public void onCreate() {
        super.onCreate();

        FirebaseDatabase.getInstance().setPersistenceEnabled(true);


//        Gson gson = new GsonBuilder()
//                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
//                .setDateFormat("yyyy-MM-dd HH:mm:ss")
//                .create();
//
////        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
////        HttpLoggingInterceptor.Level loggingLevel = BuildConfig.DEBUG ?
////                HttpLoggingInterceptor.Level.BODY :
////                HttpLoggingInterceptor.Level.NONE;
////
////        interceptor.setLevel(loggingLevel);
//
//        //TODO: ПРОВЕРИТЬ КЛИЕНТ КОТОРЫЙ НИЖЕ
////        OkHttpClient client = new OkHttpClient.Builder()
////                .addInterceptor(interceptor)
////                .build();
//
//        OkHttpClient client = new OkHttpClient.Builder()
//                .addInterceptor(new HttpLoggingInterceptor()
//                .setLevel(BuildConfig.DEBUG ?
//                HttpLoggingInterceptor.Level.BODY : HttpLoggingInterceptor.Level.NONE))
//                .build();
//
//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl(BASE_URL)
//                .addConverterFactory(GsonConverterFactory.create(gson))
//                .client(client)
//                .build();
//
//        api = retrofit.create(Api.class);
    }
//
//    public Api getApi() {
//        return api;
//    }
}
