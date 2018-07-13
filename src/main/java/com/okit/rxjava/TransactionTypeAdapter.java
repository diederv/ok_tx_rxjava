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
import com.okit.client.LineItem;
import com.okit.client.Transaction;

public class TransactionTypeAdapter extends TypeAdapter<Transaction> {
	
	private final static String BEGIN_OBJECT 	= "BEGIN_OBJECT";
	private final static String BEGIN_ARRAY 	= "BEGIN_ARRAY";
	
	private Gson gson = new Gson();
	
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
	      				parseLineItem(in, tx);				      			
	      			} else if (in.peek().name().equals(BEGIN_ARRAY)) {
	      				in.beginArray();
	      				while(in.hasNext()) {
	      					parseLineItem(in, tx);
	      				}
	      				in.endArray();
	      			}
	      			List<LineItem> l = new ArrayList<LineItem>();		      		
	      			tx.setLineItems(l);
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
	      		case "-xmlns:ns2":
	      			tx.setXmlnsNs2(in.nextString());
	      			break;
	      		case "-xmlns:ns3":
	      			tx.setXmlnsNs3(in.nextString());
	      			break;
	      		case "-xmlns:ns4":
	      			tx.setXmlnsNs4(in.nextString());
	      			break;
	      		case "-xmlns:ns5":
	      			tx.setXmlnsNs5(in.nextString());
	      			break;	      			
	      		default:  	      			
	      			in.skipValue();
	    	}
	    }
	    in.endObject();
		return tx;
	}
	
	private void parseAttribute(JsonReader in, Transaction tx) throws IOException {				
		String key = "", label = "", required = "", type = "";	
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
					default:
						in.skipValue();
				}
			}
			in.endObject();
			Attribute attr = new Attribute(key, label, required, type);
			List<Attribute> attributes = tx.getAttributes();
			if (attributes == null) {
				attributes = new ArrayList<Attribute>();
			}
			attributes.add(attr);
			tx.setAttributes(attributes);
	}
	
	private void parseLineItem(JsonReader in, Transaction tx) throws IOException {
		LineItem li = new LineItem();
		in.beginObject();
		while (in.hasNext()) {
			String s = null;
			JsonToken jt = in.peek();
			try {
				s = in.nextName();
			}
			catch(Exception e) {
				System.out.println("  E: "+ in.getPath() + "   ");				
			}
			switch (s) {
				case "allowCampaigns":
					li.setAllowCampaigns(in.nextString());
					break;
				case "amount":
					li.setAmount(in.nextString());
					break;
				case "currency":
					li.setCurrency(in.nextString());
					break;
				case "description":
					li.setDescription(in.nextString());
					break;
				case "excludedFromCampaigns":
					li.setExcludedFromCampaigns(in.nextString());
					break;
				case "externalId":
					li.setExternalId(in.nextString());
					break;
				case "id":
					li.setId(in.nextString());
					break;
				case "quantity":
					li.setQuantity(in.nextString());
					break;
				case "totalAmount":
					li.setTotalAmount(in.nextString());
					break;
				case "totalCurrency":
					li.setTotalCurrency(in.nextString());
					break;
				case "vat":
					li.setVat(in.nextString());
					break;
				case "productCode":
					li.setProductCode(in.nextString());
					break;
				default:
					in.skipValue();
			}
		}
		in.endObject();
		List<LineItem> lineItems = tx.getLineItems();
		if (lineItems == null) {
			lineItems = new ArrayList<LineItem>();
		}
		lineItems.add(li);
		tx.setLineItems(lineItems);
	}
}
