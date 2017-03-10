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
package com.pb.identify.identifyEmail;

import java.util.List;

import com.pb.identify.RequestObserver;
import com.pb.identify.exception.SdkException;
import com.pb.identify.identifyEmail.validateEmailAddress.model.ValidateEmailAddressAPIResponseList;
import com.pb.identify.identifyEmail.validateEmailAddress.model.Record;

public interface IdentifyEmailService {

	/**
	 * This service provides functionality that takes an email address and determines if it is valid. It can fix common typos to return a valid email address. It also identifies problematic email addresses.
	 * 
	 * @param records List of records to match
	 * @return ValidateEmailAddressAPIResponseList
	 * @throws SdkException
	 */
	public ValidateEmailAddressAPIResponseList validateEmailAddress(List<Record> records) 
			throws SdkException;
	
	/**
	 * This service provides asynchronous functionality that takes an email address and determines if it is valid. It can fix common typos to return a valid email address. It also identifies problematic email addresses.
	 * 
	 * @param records List of records to match
	 * @param requestObserver
	 */
	public void validateEmailAddressAsync(List<Record> records, 
			RequestObserver<ValidateEmailAddressAPIResponseList> requestObserver);
}
