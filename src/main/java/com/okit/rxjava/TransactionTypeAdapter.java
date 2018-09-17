package com.okit.rxjava;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import com.okit.client.Attribute;
import com.okit.client.Authorisation;
import com.okit.client.AuthorisationResult;
import com.okit.client.LineItem;
import com.okit.client.Location;
import com.okit.client.PaymentTransaction;
import com.okit.client.Transaction;

public class TransactionTypeAdapter extends TypeAdapter<Transaction> {
	
	private final static String BEGIN_OBJECT 	= "BEGIN_OBJECT";
	private final static String BEGIN_ARRAY 	= "BEGIN_ARRAY";
	
	private final LineItemTypeAdapter lineItemTypeAdapter;
	
	private Gson gson = new Gson();
	
	public TransactionTypeAdapter() {
		lineItemTypeAdapter = new LineItemTypeAdapter();
	}
	
	@Override
	public void write(JsonWriter out, Transaction value) throws IOException {
		gson.toJson(value, Transaction.class, out);
	}

	@Override
	public Transaction read(JsonReader in) throws IOException {
		Transaction tx = new Transaction();
	    in.beginObject();			    
	    while (in.hasNext()) {
	    	switch (in.nextName()) {
	      		case "lineItems":
	      			if (in.peek().name().equals(BEGIN_OBJECT)) {
	      				//parseLineItem(in, tx);
	      				tx.addLineItem(lineItemTypeAdapter.read(in));
	      			} else if (in.peek().name().equals(BEGIN_ARRAY)) {
	      				in.beginArray();
	      				while(in.hasNext()) {
	      					//parseLineItem(in, tx);
	      					tx.addLineItem(lineItemTypeAdapter.read(in));
	      				}
	      				in.endArray();
	      			}
	      			break;
	      		case "attributes":			      			
	      			if (in.peek().name().equals(BEGIN_OBJECT)) {
	      				parseAttribute(in, tx);				      			
	      			} else if (in.peek().name().equals(BEGIN_ARRAY)) {
	      				in.beginArray();			      				
	      				while(in.hasNext()) {
	      					parseAttribute(in, tx);
	      				}
	      				in.endArray();
	      			}	      	
	      			break;
	      		case "paymentTransactions":	      			
	      			if (in.peek().name().equals(BEGIN_OBJECT)) {
	      				parsePaymentTransactions(in, tx);	      			
	      			} else if (in.peek().name().equals(BEGIN_ARRAY)) {
	      				in.beginArray();			      				
	      				while(in.hasNext()) {
	      					parsePaymentTransactions(in, tx);
	      				}
	      				in.endArray();
	      			}
	      			break;
	      		case "authorisationResult":
	      			parseAuthorisationResult(in, tx);
	      			break;
	      		case "account":
	      			tx.setAccount(in.nextString());
	      			break;
	      		case "amount":
	      			tx.setAmount(in.nextString());
	      			break;
	      		case "billingType":
	      			tx.setBillingType(in.nextString());
	      			break;
	      		case "currency":
	      			tx.setCurrency(in.nextString());
	      			break;
	      		case "guid":
	      			tx.setGuid(in.nextString());
	      			break;
	      		case "token":
	      			tx.setToken(in.nextString());
	      			break;
	      		case "barcode":
	      			tx.setBarcode(in.nextString());
	      			break;
	      		case "id":
	      			tx.setId(in.nextString());
	      			break;
	      		case "landingPageUrl":
	      			tx.setLandingPageUrl(in.nextString());
	      			break;
	      		case "permissions": 
	      			tx.setPermissions(in.nextString());
	      			break;
	      		case "reference":
	      			tx.setReference(in.nextString());
	      			break;
	      		case "service":
	      			tx.setService(in.nextString());
	      			break;
	      		case "state":
	      			tx.setState(in.nextString());
	      			break;
	      		case "timestamp":
	      			tx.setTimestamp(in.nextString());
	      			break;
	      		case "type":
	      			tx.setType(in.nextString());
	      			break;
	      		case "action":
	      			tx.setAction(in.nextString());
	      			break;	 
	      		case "description":
	      			tx.setDescription(in.nextString());
	      			break;
	      		case "paymentMethod":
	      			tx.setPaymentMethod(in.nextString());
	      			break;	      			
	      		default:  	      			
	      			in.skipValue();
	    	}
	    }
	    in.endObject();
		return tx;
	}
	
	private void parseAuthorisationResult(JsonReader in, Transaction tx) throws IOException {
		in.beginObject();
		AuthorisationResult ar = new AuthorisationResult();
		while (in.hasNext()) {
			String s = in.nextName();
			switch (s) {	
				case "amount":
					ar.setAmount(in.nextString());
					break;
				case "location":
					parseLocation(in, ar);
					break;
				case "reference":
					ar.setReference(in.nextString());
					break;
				case "result":
					ar.setResult(in.nextString());
					break;
				case "timestamp":
					ar.setTimestamp(in.nextString());
					break;
				default:
					in.skipValue();
			}
		}
		in.endObject();
		tx.setAuthorisationResult(ar);
	}
	
	private void parseLocation(JsonReader in, AuthorisationResult ar) throws IOException {
		in.beginObject();
		Location location = new Location();
		while (in.hasNext()) {
			String s = in.nextName();
			switch (s) {	
				case "lat":
					location.setLat(in.nextString());
					break;
				case "lon":
					location.setLon(in.nextString());
					break;
				default:
					in.skipValue();
			}
		}
		in.endObject();
		ar.setLocation(location);
	}

	private void parsePaymentTransactions(JsonReader in, Transaction tx) throws IOException {
		in.beginObject();
		PaymentTransaction pTx = new PaymentTransaction();
		while (in.hasNext()) {
			String s = in.nextName();
			switch (s) {	
				case "@type":
					pTx.setType(in.nextString());
					break;
				case "amount":
					pTx.setAmount(in.nextString());
					break;
				case "externalId":
					pTx.setExternalId(in.nextString());
					break;
				case "id":
					pTx.setId(in.nextString());
					break;
				case "merchantAccountId":
					pTx.setMerchantAccountId(in.nextString());
					break;
				case "method":
					pTx.setMethod(in.nextString());
					break;
				case "reference":
					pTx.setReference(in.nextString());
					break;
				case "state":
					pTx.setState(in.nextString());
					break;
				case "timestamp":
					pTx.setTimestamp(in.nextString());
					break;
				case "tokenId":
					pTx.setTokenId(in.nextString());
					break;
				default:
					in.skipValue();
			}
		}
		in.endObject();
		List<PaymentTransaction> paymentTransactions = tx.getPaymentTransactions();
		if (paymentTransactions == null) {
			paymentTransactions = new ArrayList<PaymentTransaction>();
		}
		paymentTransactions.add(pTx);
		tx.setPaymentTransactions(paymentTransactions);
	}
	
	private void parseAttribute(JsonReader in, Transaction tx) throws IOException {				
		String key = "", label = "", required = "", type = "", value = "";	
			in.beginObject();
			while (in.hasNext()) {
				String s = in.nextName();
				switch (s) {
					case "key":
						key = in.nextString();
						break;
					case "label":
						label = in.nextString();
						break;
					case "required":
						required = in.nextString();
						break;
					case "type":
						type = in.nextString();
						break;	
					case "value":
						value = in.nextString();
						break;						
					default:
						in.skipValue();
				}
			}
			in.endObject();
			Attribute attr = new Attribute(key, label, required, type, value);
			List<Attribute> attributes = tx.getAttributes();
			if (attributes == null) {
				attributes = new ArrayList<Attribute>();
			}
			attributes.add(attr);
			tx.setAttributes(attributes);
	}	
}
