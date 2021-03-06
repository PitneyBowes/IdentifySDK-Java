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
package com.pb.identify.common.model;

/**
 * Represents errors internal to SDK.
 *
 */
public class SdkInternalError {
	private String errorMessage;
	private Exception nestedException;

    public SdkInternalError(String errorMessage){
		this.errorMessage = errorMessage;
	}
	
	public SdkInternalError(String errorMessage, Exception nestedException){
		this.errorMessage = errorMessage;
		this.nestedException = nestedException;
	}

    /**
     * @return errorMessage
     * This method returns the error message.
     */
	public String getErrorMessage() {
		return errorMessage;
	}
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

    /**
     * @return nestedException
     * This method returns the exception which occurred in the SDK.
     */
	public Exception getNestedException() {
		return nestedException;
	}
	public void setNestedException(Exception nestedException) {
		this.nestedException = nestedException;
	}

	
}
