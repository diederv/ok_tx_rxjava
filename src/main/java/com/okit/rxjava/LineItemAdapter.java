package com.okit.rxjava;

import java.io.IOException;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import com.okit.transaction.LineItem;

public class LineItemAdapter extends TypeAdapter<LineItem> {

	@Override
	public void write(JsonWriter out, LineItem value) throws IOException {
	    out.beginObject();
	    out.name("amount").value(value.getAmount());
	    out.name("currency").value(value.getCurrency());
	    out.name("vat").value(value.getVat());
	    out.name("quantity").value(value.getQuantity());
	    out.name("totalAmount").value(value.getTotalAmount());
	    out.name("totalCurrency").value(value.getTotalCurrency());
	    out.endObject();		
	}

	@Override
	public LineItem read(JsonReader in) throws IOException {
		LineItem li = new LineItem();
	    in.beginObject();
	    while (in.hasNext()) {
	      switch (in.nextName()) {
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
		  }
	    }
	    in.endObject();
		return null;
	}

}
