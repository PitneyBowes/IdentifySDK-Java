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
package com.pb.identify.identifyAddress.common;

/**
 * 
 * Enum to specify whether to extract the firm name from AddressLine1 through AddressLine4 and place it in the FirmName output field.
 * 
 */
public enum ExtractFirm {

	/**
	 * No, do not extract the firm name. Default.
	 */
	N,
	/**
	 * Yes, extract the firm name.
	 */
	Y
}
