package com.okit.rxjava;

import com.okit.transaction.TransactionInitiation;

import io.reactivex.Observable;

import com.okit.transaction.SingleLineItemTransaction;
import com.okit.transaction.Transaction;

import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface OkServiceFix {
	
    @Headers({
	        "content-type: application/json",
	})
	@POST("/works/api/v2/payment/transactions.json")
	abstract Observable<Transaction> initiateTransactionRx(@Body TransactionInitiation transactionInitiation);
	
	
	@Headers({
	        "content-type: application/json",
	})
	@GET("/works/api/v2/payment/transactions/{guid}.json")
	abstract Observable<Transaction> checkTransactionRx(@Path("guid") String guid);

//------------

    @Headers({
        "content-type: application/json",
    })
    @POST("/works/api/v2/payment/transactions.json")
    abstract Observable<SingleLineItemTransaction> initiateSingleLineItemTransactionRx(@Body TransactionInitiation transactionInitiation);
    
    
    @Headers({
	        "content-type: application/json",
	})
	@GET("/works/api/v2/payment/transactions/{guid}.json")
    abstract Observable<SingleLineItemTransaction> checkSingleLineItemTransactionRx(@Path("guid") String guid);

}
