package com.pb.identify.identifyAddress.validateMailingAddressPremium.model;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.SerializedName;

/**
 * This class represents the response from ValidateMailingAddressPremium API
 *
 */
public class ValidateMailingAddressPremiumAPIResponseList {

	@SerializedName(value="Output")
	protected List<ValidateMailingAddressPremiumAPIResponse> responses;
	
	/**
     * Gets the value of the responses property.
     * 
     * @return responses
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot.
     * This is why there is not a <CODE>set</CODE> method for the responses property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getResponses().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ValidateMailingAddressPremiumAPIResponse }
     * 
     * 
     */
	public List<ValidateMailingAddressPremiumAPIResponse> getResponses(){
		if (responses == null){
			responses = new ArrayList<ValidateMailingAddressPremiumAPIResponse>();
		}
		return this.responses;
	}
}
