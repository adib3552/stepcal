package com.example.stepcal.Retrofit;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    public static Retrofit retrofit;
<<<<<<< HEAD
    private static final String base_url="http://172.20.133.88:4006";
=======
    private static final String base_url="http://192.168.0.111:4006";
>>>>>>> 6921f50d6a53defddece405a9e2112eded278a2d

    public static Retrofit getRetrofit(){
        if(retrofit==null){
            retrofit=new Retrofit.Builder().baseUrl(base_url).addConverterFactory(GsonConverterFactory.create()).build();
        }
        return retrofit;
    }
}
