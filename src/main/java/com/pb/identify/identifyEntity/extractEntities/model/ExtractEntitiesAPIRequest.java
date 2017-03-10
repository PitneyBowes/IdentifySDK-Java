package com.pb.identify.identifyEntity.extractEntities.model;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.SerializedName;

/**
 * This class is used to create the request body for the ExtractEntities API
 *
 */
public class ExtractEntitiesAPIRequest {
	
	protected Options options; 
	public Options getOptions() {
		return options;
	}
	public void setOptions(Options options) {
		this.options=options;
	}
	
	@SerializedName("Input")
	protected InputRecord input = new InputRecord();
	public class InputRecord{
		@SerializedName("Row")
		 protected List<Record> row;
		 
		  public List<Record> getRecords() {
		       if (row == null) {
		    	   row = new ArrayList<Record>();
		       }
		       return this.row;
		   }
	}
	public InputRecord getInputRecord(){
		return input;
	}
}