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
import com.pb.identify.identifyAddress.validateMailingAddressUSCAN.model.ValidateMailingAddressUSCANAPIResponseList;
import com.pb.identify.identifyAddress.getCityStateProvince.model.GetCityStateProvinceAPIResponseList;
import com.pb.identify.identifyAddress.getPostalCodes.model.GetPostalCodesAPIResponseList;

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
	
	
	
	/**
	 * Returns a city and state/province for a given input postal code.
	 * 
	 * @param records List of postal codes to retrieve city and state province
	 * @param options List of options
	 * @return GetCityStateProvinceAPIResponseList
	 * @throws SdkException  SdkException
	 */
	public GetCityStateProvinceAPIResponseList getCityStateProvince(List<com.pb.identify.identifyAddress.getCityStateProvince.model.Record> records, com.pb.identify.identifyAddress.getCityStateProvince.model.Options options) 
			throws SdkException;
	
	/**
	 * Returns a city and state/province for a given input postal code asynchronously.
	 * 
	 * @param records List of postal codes to retrieve city and state province
	 * @param options List of options
	 * @param requestObserver Observer interface for request
	 
	 */
	public void getCityStateProvinceAsync(List<com.pb.identify.identifyAddress.getCityStateProvince.model.Record> records, com.pb.identify.identifyAddress.getCityStateProvince.model.Options options, 
			RequestObserver<GetCityStateProvinceAPIResponseList> requestObserver);
	
	/**
	 * Returns a postal code for a given city and state/province.
	 * 
	 * @param records List of records to retrieve postal codes
	 * @param options List of options
	 * @return GetPostalCodesAPIResponseList
	 * @throws SdkException  SdkException
	 */
	public GetPostalCodesAPIResponseList getPostalCodes(List<com.pb.identify.identifyAddress.getPostalCodes.model.Record> records, com.pb.identify.identifyAddress.getPostalCodes.model.Options options) 
			throws SdkException;
	
	/**
	 * Returns a postal code for a given city and state/province asynchronously.
	 * 
	 * @param records List of records to retrieve postal codes
	 * @param options List of options
	 * @param requestObserver Observer interface for request
	 
	 */
	public void getPostalCodesAsync(List<com.pb.identify.identifyAddress.getPostalCodes.model.Record> records, com.pb.identify.identifyAddress.getPostalCodes.model.Options options, 
			RequestObserver<GetPostalCodesAPIResponseList> requestObserver);
	
	/**
	 * Validate and format the batch of input addresses and fills the missing details for ValidateMailingAddressUSCAN
	 * 
	 * @param addresses List of addresses to validate
	 * @param options List of options
	 * @return ValidateMailingAddressUSCANAPIResponseList
	 * @throws SdkException  SdkException
	 */
	public ValidateMailingAddressUSCANAPIResponseList validateMailingAddressUSCAN(List<com.pb.identify.identifyAddress.validateMailingAddressUSCAN.model.Address> addresses, com.pb.identify.identifyAddress.validateMailingAddressUSCAN.model.Options options) 
			throws SdkException;
	
	/**
	 * Validate and format the batch of input addresses and fills the missing details asynchronously for ValidateMailingAddressUSCAN
	 * 
	 * @param addresses List of addresses to validate
	 * @param options List of options
	 * @param requestObserver Observer interface for request
	 
	 */
	public void validateMailingAddressUSCANAsync(List<com.pb.identify.identifyAddress.validateMailingAddressUSCAN.model.Address> addresses, com.pb.identify.identifyAddress.validateMailingAddressUSCAN.model.Options options, 
			RequestObserver<ValidateMailingAddressUSCANAPIResponseList> requestObserver);
	

}