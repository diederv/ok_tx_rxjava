package com.okit.rxjava;

import com.okit.transaction.TransactionRequest;

import io.reactivex.Observable;

import com.okit.transaction.Authorisation;
import com.okit.transaction.AuthorisationRequest;
import com.okit.transaction.Transaction;

import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface OkService {

    @Headers({
	        "content-type: application/json",
	})
	@POST("/works/api/v2/payment/transactions.json")
	abstract Observable<Transaction> requestTransactionRx(@Body TransactionRequest transactionInitiation);
    
    @Headers({
	        "content-type: application/json",
	})
	@GET("/works/api/v2/payment/transactions/{guid}.json")
    abstract Observable<Transaction> checkTransactionRx(@Path("guid") String guid);

    @Headers({
        "content-type: application/json",
	})
	@POST("/works/api/v2/open/authorisationrequests.json")
	abstract Observable<Authorisation> requestAuthorisation(@Body AuthorisationRequest authorisationRequest);
	
	@Headers({
	        "content-type: application/json",
	})
	@GET("/works/api/v2/open/authorisationrequests/{guid}.json")
	abstract Observable<Authorisation> checkAuthorisation(@Path("guid") String guid);

}
