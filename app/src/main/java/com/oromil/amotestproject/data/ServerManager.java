package com.oromil.amotestproject.data;

import com.google.gson.GsonBuilder;
import com.oromil.amotestproject.AmoTestApplication;
import com.oromil.amotestproject.R;
import com.oromil.amotestproject.data.model.LeadsListResponse;
import com.oromil.amotestproject.data.model.AccountInfoResponse;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;

/**
 * Created by Oromil on 26.07.2017.
 */

public class ServerManager {

    private static final ServerManager ourInstance = new ServerManager();
    private List<Cookie> cookies;

    public static ServerManager getInstance() {
        return ourInstance;
    }

    private ServerManager() {
        createRetrofit();
    }

    Retrofit retrofit;
    RequestData request;

    private void createRetrofit() {
        retrofit = new Retrofit.Builder()
                .baseUrl(AmoTestApplication.getInstance().getString(R.string.base_URL))
                .client(getClient())
                .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().setLenient().create()))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        request = retrofit.create(RequestData.class);
    }

    private OkHttpClient getClient(){

        HttpLoggingInterceptor logger = new HttpLoggingInterceptor();
        logger.setLevel(HttpLoggingInterceptor.Level.BODY);
        return new OkHttpClient.Builder()
                .cookieJar(new CookieJar() {
                    @Override
                    public void saveFromResponse(HttpUrl url, List<Cookie> cookies1) {

                        if (cookies == null)
                            cookies = cookies1;
                    }

                    @Override
                    public List<Cookie> loadForRequest(HttpUrl url) {
                        if (cookies != null)
                            return cookies;
                        return new ArrayList<Cookie>();
                    }
                })
                .addInterceptor(logger).build();
    }

    public Observable<Void> requestAuthorization(String login, String password){
        return request.authorizationRequest(login, password, "json");
    }

    public Observable<LeadsListResponse> requestDealsList(){
        return request.requestDealsList();
    }

    public Observable<AccountInfoResponse> requestDealsStatuses(){
        return request.requestDealsStatuses();
    }
}
