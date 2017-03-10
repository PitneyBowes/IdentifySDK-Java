package com.pb.identify.identifyAddress.validateMailingAddressPremium.model;

import com.google.gson.annotations.SerializedName;
import com.pb.identify.identifyAddress.common.BaseAddress;

/**
 * Model class for ValidateMailingAddressPremium input
 *
 */
public class Address extends BaseAddress {

	@SerializedName(value="AddressLine3")
    private String addressLine3;
    @SerializedName(value="AddressLine4")
    private String addressLine4;
    @SerializedName(value="AddressLine5")
    private String addressLine5;
	public String getAddressLine3() {
		return addressLine3;
	}
	public void setAddressLine3(String addressLine3) {
		this.addressLine3 = addressLine3;
	}
	public String getAddressLine4() {
		return addressLine4;
	}
	public void setAddressLine4(String addressLine4) {
		this.addressLine4 = addressLine4;
	}
	public String getAddressLine5() {
		return addressLine5;
	}
	public void setAddressLine5(String addressLine5) {
		this.addressLine5 = addressLine5;
	}
    
    
}
