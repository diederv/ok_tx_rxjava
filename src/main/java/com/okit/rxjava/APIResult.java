package com.okit.rxjava;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class APIResult {
	
    @SerializedName("value")
    @Expose
	private String value;
    
    public String getValue() {
    	return value;
    }
}
