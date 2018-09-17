package com.okit.rxjava;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import com.okit.client.Attribute;
import com.okit.client.Authorisation;
import com.okit.client.Transaction;

public class AuthorisationTypeAdapter extends TypeAdapter<Authorisation> {
	
	private final static String BEGIN_OBJECT 	= "BEGIN_OBJECT";
	private final static String BEGIN_ARRAY 	= "BEGIN_ARRAY";
	
	private Gson gson = new Gson();
	
	@Override
	public void write(JsonWriter out, Authorisation value) throws IOException {
		gson.toJson(value, Transaction.class, out);
	}

	@Override
	public Authorisation read(JsonReader in) throws IOException {
		Authorisation a = new Transaction();
	    in.beginObject();			    
	    while (in.hasNext()) {
	    	switch (in.nextName()) {
	      		case "attributes":			      			
	      			if (in.peek().name().equals(BEGIN_OBJECT)) {
	      				parseAttribute(in, a);				      			
	      			} else if (in.peek().name().equals(BEGIN_ARRAY)) {
	      				in.beginArray();			      				
	      				while(in.hasNext()) {
	      					parseAttribute(in, a);
	      				}
	      				in.endArray();
	      			}	      	
	      			break;
	      		case "account":
	      			a.setAccount(in.nextString());
	      			break;
	      		case "guid":
	      			a.setGuid(in.nextString());
	      			break;
	      		case "id":
	      			a.setId(in.nextString());
	      			break;
	      		case "landingPageUrl":
	      			a.setLandingPageUrl(in.nextString());
	      			break;
	      		case "permissions": 
	      			a.setPermissions(in.nextString());
	      			break;
	      		case "reference":
	      			a.setReference(in.nextString());
	      			break;
	      		case "state":
	      			a.setState(in.nextString());
	      			break;
	      		case "timestamp":
	      			a.setTimestamp(in.nextString());
	      			break;      			
	      		default:  	      			
	      			in.skipValue();
	    	}
	    }
	    in.endObject();
		return a;
	}
	
	private void parseAttribute(JsonReader in, Authorisation a) throws IOException {				
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
		List<Attribute> attributes = a.getAttributes();
		if (attributes == null) {
			attributes = new ArrayList<Attribute>();
		}
		attributes.add(attr);
		a.setAttributes(attributes);
	}

}
