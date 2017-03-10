package com.pb.identify.identifyAddress.validateMailingAddressPro.model;

import com.google.gson.annotations.SerializedName;
import com.pb.identify.identifyAddress.common.BaseResponse;

/**
 * This class represents a single validated address in the response from ValidateMailingAddressPro API
 *
 */
public class ValidateMailingAddressProAPIResponse extends BaseResponse {

	@SerializedName(value="CouldNotValidate")
	private String couldNotValidate;
	@SerializedName(value="AddressQuality")
	private String addressQuality;
	@SerializedName(value="Deliverability")
	private String deliverability;
	@SerializedName(value="AddressType")
	private String addressType;
	@SerializedName(value="Locality")
	private String locality;
	@SerializedName(value="ChangeScore")
	private String changeScore;
	@SerializedName(value="Suburb")
	private String suburb;
	public String getCouldNotValidate() {
		return couldNotValidate;
	}
	public void setCouldNotValidate(String couldNotValidate) {
		this.couldNotValidate = couldNotValidate;
	}
	public String getAddressQuality() {
		return addressQuality;
	}
	public void setAddressQuality(String addressQuality) {
		this.addressQuality = addressQuality;
	}
	public String getDeliverability() {
		return deliverability;
	}
	public void setDeliverability(String deliverability) {
		this.deliverability = deliverability;
	}
	public String getAddressType() {
		return addressType;
	}
	public void setAddressType(String addressType) {
		this.addressType = addressType;
	}
	public String getLocality() {
		return locality;
	}
	public void setLocality(String locality) {
		this.locality = locality;
	}
	public String getChangeScore() {
		return changeScore;
	}
	public void setChangeScore(String changeScore) {
		this.changeScore = changeScore;
	}
	public String getSuburb() {
		return suburb;
	}
	public void setSuburb(String suburb) {
		this.suburb = suburb;
	}
	
}
