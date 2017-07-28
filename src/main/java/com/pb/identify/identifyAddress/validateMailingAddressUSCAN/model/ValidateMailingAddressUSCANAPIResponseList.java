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
package com.pb.identify.identifyAddress.validateMailingAddressUSCAN.model;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.SerializedName;

/**
 * This class represents the response from ValidateMailingAddressUSCAN API
 *
 */
public class ValidateMailingAddressUSCANAPIResponseList {

	@SerializedName(value="Output")
	protected List<ValidateMailingAddressUSCANAPIResponse> responses;
	
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
     * {@link ValidateMailingAddressUSCANAPIResponse }
     * 
     * 
     */
	public List<ValidateMailingAddressUSCANAPIResponse> getResponses(){
		if (responses == null){
			responses = new ArrayList<ValidateMailingAddressUSCANAPIResponse>();
		}
		return this.responses;
	}
}
