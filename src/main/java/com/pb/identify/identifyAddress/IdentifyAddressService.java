/*******************************************************************************
 *  Copyright 2016 Pitney Bowes Inc
 *  Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an
 *   "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  
 *   See the License for the specific language governing permissions and limitations under the License.
 *******************************************************************************/
package com.pb.identify.identifyAddress;

import java.util.List;

import com.pb.identify.RequestObserver;
import com.pb.identify.exception.SdkException;
import com.pb.identify.identifyAddress.validateMailingAddress.model.ValidateMailingAddressAPIResponseList;
import com.pb.identify.identifyAddress.validateMailingAddressPremium.model.ValidateMailingAddressPremiumAPIResponseList;
import com.pb.identify.identifyAddress.validateMailingAddressPro.model.ValidateMailingAddressProAPIResponseList;

public interface IdentifyAddressService {
	
	/**
	 * Validate and format the batch of input addresses and fills the missing details
	 * 
	 * @param addresses List of addresses to validate
	 * @param options List of options
	 * @return ValidateMailingAddressAPIResponseList
	 * @throws SdkException  SdkException
	 */
	public ValidateMailingAddressAPIResponseList validateMailingAddress(List<com.pb.identify.identifyAddress.validateMailingAddress.model.Address> addresses,com.pb.identify.identifyAddress.validateMailingAddress.model.Options options)
			throws SdkException;

	/**
	 * Validate and format the batch of input addresses and fills the missing details asynchronously
	 * 
	 * @param addresses List of addresses to validate
	 * @param options List of options
	 * @param requestObserver Observer interface for request
	 
	 */
	public void validateMailingAddressAsync(List<com.pb.identify.identifyAddress.validateMailingAddress.model.Address> addresses,com.pb.identify.identifyAddress.validateMailingAddress.model.Options options,
			RequestObserver<ValidateMailingAddressAPIResponseList> requestObserver);
	
	/**
	 * Validate and format the batch of input addresses and fills the missing details for ValidateMailingAddressPro
	 * 
	 * @param addresses List of addresses to validate
	 * @param options List of options
	 * @return ValidateMailingAddressProAPIResponseList
	 * @throws SdkException  SdkException
	 */
	public ValidateMailingAddressProAPIResponseList validateMailingAddressPro(List<com.pb.identify.identifyAddress.validateMailingAddressPro.model.Address> addresses, com.pb.identify.identifyAddress.validateMailingAddressPro.model.Options options) 
			throws SdkException;
	
	/**
	 * Validate and format the batch of input addresses and fills the missing details asynchronously for ValidateMailingAddressPro
	 * 
	 * @param addresses List of addresses to validate
	 * @param options List of options
	 * @param requestObserver Observer interface for request
	 
	 */
	public void validateMailingAddressProAsync(List<com.pb.identify.identifyAddress.validateMailingAddressPro.model.Address> addresses, com.pb.identify.identifyAddress.validateMailingAddressPro.model.Options options, 
			RequestObserver<ValidateMailingAddressProAPIResponseList> requestObserver);
	
	/**
	 * Validate and format the batch of input addresses and fills the missing details for ValidateMailingAddressPremium
	 * 
	 * @param addresses List of addresses to validate
	 * @param options List of options
	 * @return ValidateMailingAddressPremiumAPIResponseList
	 * @throws SdkException  SdkException
	 */
	public ValidateMailingAddressPremiumAPIResponseList validateMailingAddressPremium(List<com.pb.identify.identifyAddress.validateMailingAddressPremium.model.Address> addresses, com.pb.identify.identifyAddress.validateMailingAddressPremium.model.Options options) 
			throws SdkException;
	
	/**
	 * Validate and format the batch of input addresses and fills the missing details asynchronously for ValidateMailingAddressPremium
	 * 
	 * @param addresses List of addresses to validate
	 * @param options List of options
	 * @param requestObserver Observer interface for request
	 
	 */
	public void validateMailingAddressPremiumAsync(List<com.pb.identify.identifyAddress.validateMailingAddressPremium.model.Address> addresses, com.pb.identify.identifyAddress.validateMailingAddressPremium.model.Options options, 
			RequestObserver<ValidateMailingAddressPremiumAPIResponseList> requestObserver);

}