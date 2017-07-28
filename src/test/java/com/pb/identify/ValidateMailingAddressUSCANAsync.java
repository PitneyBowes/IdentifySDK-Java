/*******************************************************************************
 *
 *   Copyright 2016 Pitney Bowes Inc
 *   Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *  
 *       http://www.apache.org/licenses/LICENSE-2.0
 *  
 *    Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an
 *    "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  
 *    See the License for the specific language governing permissions and limitations under the License.
 *  
 *******************************************************************************/

package com.pb.identify;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.pb.identify.RequestObserver;
import com.pb.identify.common.model.ErrorInfo;
import com.pb.identify.common.model.UserField;
import com.pb.identify.exception.SdkException;
import com.pb.identify.identifyAddress.IdentifyAddressService;
import com.pb.identify.identifyAddress.validateMailingAddressUSCAN.model.Address;
import com.pb.identify.identifyAddress.validateMailingAddressUSCAN.model.Options;
import com.pb.identify.identifyAddress.common.OutputCasing;
import com.pb.identify.identifyAddress.validateMailingAddressUSCAN.model.ValidateMailingAddressUSCANAPIResponseList;
import com.pb.identify.manager.IdentifyServiceManager;
import com.pb.identify.utils.Utility;

/**
 * Sample Code for accessing Identify Validate Mailing Address USCAN API
 * Java SDK. To make it work, make sure to update your API KEY and SECRET before
 * running this code.
 */
public class ValidateMailingAddressUSCANAsync {
	// FIXME Assign your API_KEY here
	private static final String API_KEY = "";
	// FIXME Assign your Secret here
	private static final String SECRET = "";

	public static void main(String[] args) throws InterruptedException {
		String key = (args.length > 1 && args[0] != null) ? args[0] : API_KEY;
		String secret = (args.length > 1 && args[1] != null) ? args[1] : SECRET;

		// This initializes the authentication mechanism
		//IdentifyServiceManager identifyServiceManager = IdentifyServiceManager.getInstance(key, secret);
		IdentifyServiceManager identifyServiceManager = IdentifyServiceManager.getInstance("BavHftcoHI0e95cQVQ19UpJqNXyd");
		IdentifyAddressService identifyAddressService = identifyServiceManager.getIdentifyAddressService();

		RequestObserver<ValidateMailingAddressUSCANAPIResponseList> requestObserver = new RequestObserver<ValidateMailingAddressUSCANAPIResponseList>() {
			@Override
			public void onSuccess(ValidateMailingAddressUSCANAPIResponseList response) {
				System.out.println(Utility.getObjectAsJson(response));
			}

			@Override
			public void onFailure(SdkException e) {

				// Error from API
				if (e.getErrorResponseType() == SdkException.ErrorResponseType.IDENTIFY) {
					// Get the error info from Identify API
					ErrorInfo identifyApiErrors = e.getIdentifyApiErrors();
					System.out.println(Utility.getObjectAsJson(identifyApiErrors));
				} else if (e.getErrorResponseType() == SdkException.ErrorResponseType.INTERNAL) // Internal
					// SDK
					// Error
				{
					// Get the original exception
					Exception nestedException = e.getSdkInternalError().getNestedException();
					System.out.println(nestedException.getMessage());
				}
			}
		};

		Address address1 = new Address();
		address1.setAddressLine1("50 water st.");
		address1.setCity("lee");
		address1.setStateProvince("ma");
		address1.setCountry("US");
		UserField userField = new UserField();
		userField.setName("name1");
		userField.setValue("value1");
		UserField userField1 = new UserField();
		userField1.setName("name2");
		userField1.setValue("value2");
		address1.setUserFields(Arrays.asList(userField,userField1));

		Address address2 = new Address();
		address2.setAddressLine1("1 Global View");
		address2.setCity("troy");
		address2.setStateProvince("NY");
		address2.setCountry("US");
		address2.setFirmName("Pitney Bowes Software");

		List<Address> addresses = new ArrayList<Address>();
		addresses.add(address1);
		addresses.add(address2);

		identifyAddressService.validateMailingAddressUSCANAsync(addresses,null,requestObserver);
	}
}
