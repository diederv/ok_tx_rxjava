package com.okit.rxjava;

import com.okit.transaction.SingleLineItemTransaction;
import com.okit.transaction.Transaction;
import com.okit.transaction.TransactionInitiation;

import io.reactivex.Observable;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import io.reactivex.functions.Function;
//import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
//import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class OkServiceFactory {

    private static OkServiceFix instance;

    public static OkService getInstance(String baseUrl, String username) {
        if (OkServiceFactory.instance == null) {

            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.HEADERS);

            BasicAuthInterceptor authInterceptor = new BasicAuthInterceptor(username, "" );

            OkHttpClient okHttpclient = new OkHttpClient.Builder()
                    .addInterceptor(new RequestHeaderInterceptor())
                    .addInterceptor(authInterceptor)
                    .addInterceptor(loggingInterceptor)
                    .build();

            //https://futurestud.io/tutorials/retrofit-how-to-integrate-xml-converter
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .client(okHttpclient)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();

            OkServiceFactory.instance = retrofit.create(OkServiceFix.class);
        }
        return new OkServiceImpl(OkServiceFactory.instance);
    }
    
    private static class OkServiceImpl implements OkService {
    	
    	private final OkServiceFix okService;
    	
    	public OkServiceImpl(OkServiceFix okService) {
    		this.okService = okService;
    	}

		public Observable<Transaction> initiateTransactionRx(TransactionInitiation transactionInitiation) {
			if (transactionInitiation.getLineItems().size() == 1) {
				return okService.initiateSingleLineItemTransactionRx(transactionInitiation).map(new Function<SingleLineItemTransaction, Transaction>() {					
					public Transaction apply(SingleLineItemTransaction slitx) throws Exception {						
						return slitx.convert();					
					}
				});
			} else {
				return okService.initiateTransactionRx(transactionInitiation);
			}
		}

		public Observable<Transaction> checkTransactionRx(String guid) {
			// TODO Auto-generated method stub
			return okService.checkTransactionRx(guid).onExceptionResumeNext(okService.checkSingleLineItemTransactionRx(guid));
		}
    	
    }

}
