package com.pb.identify;
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


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.pb.identify.common.model.ErrorInfo;
import com.pb.identify.common.model.UserField;
import com.pb.identify.exception.SdkException;
import com.pb.identify.identifyRisk.IdentifyRiskService;
import com.pb.identify.identifyRisk.checkGlobalWatchList.model.CheckGlobalWatchListAPIResponseList;
import com.pb.identify.identifyRisk.checkGlobalWatchList.model.Record;
import com.pb.identify.manager.IdentifyServiceManager;
import com.pb.identify.utils.Utility;

/**
 * Sample Code for accessing checkGlobalWatchList API
 * Java SDK. To make it work, make sure to update your API KEY and SECRET before
 * running this code.
 */
public class CheckGlobalWatchListMain {

	// FIXME Assign your API_KEY here
	private static final String API_KEY = "MipdDYiCPrJfDm7GobDOhKvKlkVM9PbO"; //"<API_KEY>";
	// FIXME Assign your Secret here
	private static final String SECRET = "ZKITFM7GNBuVNM6g"; //"<SECRET>";

	public static void main(String[] args) {

		String key = (args.length > 1 && args[0] != null) ? args[0] : API_KEY;
		String secret = (args.length > 1 && args[1] != null) ? args[1] : SECRET;

		// This initializes the authentication mechanism
		IdentifyServiceManager identifyServiceManager = IdentifyServiceManager.getInstance(key, secret);
		IdentifyRiskService identifyRiskService = identifyServiceManager.getIdentifyRiskService();

		try {
			Record record1 = new Record();
			record1.setAddressLine1("701 Pine Drift Dr");
			record1.setName("John Smith");
			record1.setCountry("usa");
			UserField userField = new UserField();
			userField.setName("name1");
			userField.setValue("value1");
			UserField userField1 = new UserField();
			userField1.setName("name2");
			userField1.setValue("value2");
			record1.setUserFields(Arrays.asList(userField,userField1));

			Record record2 = new Record();
			record2.setFirstname("Migual");
			record2.setLastname("Batista");

			List<Record> records = new ArrayList<Record>();
			records.add(record1);
			records.add(record2);

			CheckGlobalWatchListAPIResponseList response = identifyRiskService.checkGlobalWatchList(records);
			String responseAsJson = Utility.getObjectAsJson(response);
			System.out.println(responseAsJson);
		} catch (SdkException e) {
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
	}
}