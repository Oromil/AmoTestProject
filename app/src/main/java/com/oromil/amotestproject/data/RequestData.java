package com.oromil.amotestproject.data;

import com.oromil.amotestproject.data.model.LeadsListResponse;
import com.oromil.amotestproject.data.model.AccountInfoResponse;

import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by Oromil on 26.07.2017.
 */

public interface RequestData {

    @POST("/private/api/auth.php")
    Observable<Void> authorizationRequest(@Query("USER_LOGIN") String first, @Query("USER_HASH") String last, @Query("type") String type);

    @GET("/private/api/v2/json/leads/list")
    Observable<LeadsListResponse> requestDealsList();

    @GET("/private/api/v2/json/accounts/current")
    Observable<AccountInfoResponse> requestDealsStatuses();
}
