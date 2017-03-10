package com.pb.identify.identifyAddress.validateMailingAddressPro.model;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.SerializedName;

/**
 * This class is used to create the request body for the ValidateMailingAddressPro API
 *
 */
public class ValidateMailingAddressProAPIRequest {

	protected Options options = new Options();
	
	public Options getOptions() {
		return options;
	}

	public void setOptions(Options options) {
		this.options = options;
	}

	public InputAddressPro getInput() {
		return input;
	}

	public void setInput(InputAddressPro input) {
		this.input = input;
	}

	@SerializedName(value="Input")
	protected InputAddressPro input = new InputAddressPro();
	public class InputAddressPro{
		@SerializedName(value="Row")
		protected List<Address> row;
		
		public List<Address> getAddresses(){
			if (row == null){
				row = new ArrayList<Address>();
			}
			return this.row;
		}
	}
	
	public InputAddressPro getInputAddressPro(){
		return input;
	}
}
