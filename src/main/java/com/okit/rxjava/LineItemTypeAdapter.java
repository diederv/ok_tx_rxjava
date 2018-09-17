package com.okit.rxjava;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import com.okit.client.LineItem;
import com.okit.client.SubItem;

public class LineItemTypeAdapter extends TypeAdapter<LineItem> {
	
	private final static String BEGIN_OBJECT 	= "BEGIN_OBJECT";
	private final static String BEGIN_ARRAY 	= "BEGIN_ARRAY";
	
	private Gson gson = new Gson();

	@Override
	public void write(JsonWriter out, LineItem value) throws IOException {

	    gson.toJson(value, LineItem.class, out);
	}

	@Override
	public LineItem read(JsonReader in) throws IOException {
		LineItem li = new LineItem();
	    in.beginObject();
	    while (in.hasNext()) {
	    	switch (in.nextName()) {
		      	case "subItems":
		      		if (in.peek().name().equals(BEGIN_OBJECT)) {
		      			parseSubItems(in, li);				      			
		      		} else if (in.peek().name().equals(BEGIN_ARRAY)) {
		      			in.beginArray();			      				
		      			while(in.hasNext()) {
		      				parseSubItems(in, li);
		      			}
		      			in.endArray();
		      		}
		      		break;
		      	case "amount":
		      		li.setAmount(in.nextString());
		      		break;
		      	case "currency":
		      		li.setCurrency(in.nextString());
		      		break;
		      	case "vat":
		      		li.setVat(in.nextString());
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
		      	default:
		      		in.skipValue();
			  }
	    }
	    in.endObject();
		return li;
	}

	private void parseSubItems(JsonReader in, LineItem li) throws IOException {
		String allowCampaigns = "false", excludedFromCampaigns = "false";
		String amount = "0", quantity = "0", totalAmount = "0";
		String campaignCode = "", campaignItemCode = "", currency = "", description = "", externalId = "", id = "", totalCurrency = "", type = "";
		in.beginObject();
		while (in.hasNext()) {
			String s = in.nextName();
			switch (s) {
				case "allowCampaigns":
					allowCampaigns = in.nextString();
					break;
				case "excludedFromCampaigns":
					excludedFromCampaigns = in.nextString();
					break;
				case "amount":
					amount = in.nextString();
					break;
				case "quantity":
					quantity = in.nextString();
					break;
				case "totalAmount":
					totalAmount = in.nextString();
					break;
				case "campaignCode":
					campaignCode = in.nextString();
					break;
				case "campaignItemCode":
					campaignItemCode = in.nextString();
					break;
				case "currency":
					currency = in.nextString();
					break;
				case "description":
					description = in.nextString();
					break;
				case "externalId":
					externalId = in.nextString();
					break;
				case "id":
					id = in.nextString();
					break;
				case "totalCurrency":
					totalCurrency = in.nextString();
					break;
				case "type":			
					type = in.nextString();
					break;
				default:
					in.skipValue();
			}
		}
		in.endObject();
		SubItem subItem = 
				new SubItem(allowCampaigns, excludedFromCampaigns,
						amount, quantity, totalAmount, campaignCode,
						campaignItemCode, currency, description,	
						externalId, id, totalCurrency, type);
		List<SubItem> subItems = li.getSubItems();
		if (subItems == null) {
			subItems = new ArrayList<SubItem>();
		}
		subItems.add(subItem);
		li.setSubItems(subItems);
	}

}
