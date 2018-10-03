package com.okit.rxjava;

import java.util.List;

import com.okit.client.Authorisation;
import com.okit.client.AuthorisationRequest;
import com.okit.client.Transaction;
import com.okit.client.TransactionRequest;
import com.okit.client.ticket.TSPTicketsRequest;
import com.okit.client.ticket.Ticket;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface OkService {

	//-- Transaction 
	
	@POST("/works/api/v2/payment/transactions.json")
	abstract Observable<Transaction> requestTransaction(@Body TransactionRequest transactionInitiation);

	@POST("/works/api/v2/payment/transactions.json?sync=true")
	abstract Observable<Transaction> requestTransactionSynchronously(@Body TransactionRequest transactionInitiation);

	@GET("/works/api/v2/payment/transactions/{guid}.json")
    abstract Observable<Transaction> checkTransactionStatus(@Path("guid") String guid);

    @GET("/works/api/v2/payment/transactions/{guid}/refunds.json")
    abstract Observable<Transaction> refundTransaction(@Body String amount, @Path("guid") String guid);    
    
    
    // --- Authentication and Authorisation

	@POST("/works/api/v2/open/authorisationrequests.json")
	abstract Observable<Authorisation> requestAuthorisation(@Body AuthorisationRequest authorisationRequest);

	@GET("/works/api/v2/open/authorisationrequests/{guid}.json")
	abstract Observable<Authorisation> checkAuthorisationStatus(@Path("guid") String guid);

	@POST("/works/api/v2/open/authorisationrequests.json?sync=true")
	abstract Observable<Authorisation> requestAuthorisationSynchronously(@Body AuthorisationRequest authorisationRequest);


	//-- Ticketing

    @POST("/works/api/v2/ticketing/tickets.json")
    abstract Observable<List<Ticket>> createTickets(@Body List<Ticket> tickets);

    @POST("/works/api/v2/ticketing/tickets/push.json")
    abstract Observable<APIResult> pushTickets(@Body List<Ticket> tickets);    

    @POST("/works/api/v2/okticket/tickets/push")
    abstract Observable<APIResult> createAndPushTickets(@Body TSPTicketsRequest externalTickets);
}
