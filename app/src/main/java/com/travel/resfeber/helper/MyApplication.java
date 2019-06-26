package com.travel.resfeber.helper;

import android.app.Application;

import com.facebook.stetho.Stetho;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.travel.resfeber.api.ApiConstant;
import com.travel.resfeber.payumoney.AppEnvironment;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by its7 on 11/1/18.
 */

public class MyApplication extends Application {
    private static Retrofit retrofit;
    private static Gson gson;
    private AppEnvironment appEnvironment;

    @Override
    public void onCreate() {
        super.onCreate();


        initRetrofit();
        initGson();
        initStetho();
        initPayuMoney();
    }

    private void initPayuMoney() {
        appEnvironment = AppEnvironment.SANDBOX;
    }

    public AppEnvironment getAppEnvironment() {
        return appEnvironment;
    }

    public void setAppEnvironment(AppEnvironment appEnvironment) {
        this.appEnvironment = appEnvironment;
    }

    private void initStetho() {
        Stetho.initialize(Stetho.newInitializerBuilder(this)
                .enableWebKitInspector(Stetho.defaultInspectorModulesProvider(this))
                .build());

    }

    private void initRetrofit() {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .readTimeout(60, TimeUnit.SECONDS)
                .connectTimeout(60, TimeUnit.SECONDS)
                .build();

        retrofit = new Retrofit.Builder()
                .baseUrl(ApiConstant.BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();


    }

    private void initGson() {
        gson = new GsonBuilder()
                .setLenient()
                .create();
    }


    public static Retrofit getRetrofit() {
        return retrofit;
    }

    public static Gson getGson() {
        return gson;
    }
}
