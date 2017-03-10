package com.pb.identify.identifyAddress.validateMailingAddressPremium.model;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.SerializedName;

/**
 * This class is used to create the request body for the ValidateMailingAddressPremium API
 *
 */
public class ValidateMailingAddressPremiumAPIRequest {

	protected Options options = new Options();
	public Options getOptions(){
		return options;
	}
	
	public void setOptions(Options options){
		this.options=options;
	}
	
	@SerializedName(value="Input")
	protected InputAddressPremium input = new InputAddressPremium();
	public class InputAddressPremium{
		@SerializedName("Row")
		protected List<Address> row;

		public List<Address> getAddresses() {
			if (row == null) {
				row = new ArrayList<Address>();
			}
			return this.row;
		}
	}
	public InputAddressPremium getInputAddressPremium(){
		return input;
	}
}
