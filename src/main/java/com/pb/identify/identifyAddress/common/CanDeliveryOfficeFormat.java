/*******************************************************************************
 * Copyright 2016, 2017 Pitney Bowes Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an
 *  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  
 *  See the License for the specific language governing permissions and limitations under the License.
 *******************************************************************************/
package com.pb.identify.identifyAddress.common;

/**
 * 
 * Enum to specify where to place station information. 
 * 
 *
 */
public enum CanDeliveryOfficeFormat {
	/**
	 * Place station information in the same location as it is in the input address. Default. 
	 */
	I,
	/**
	 * Place station information on the same line as the address, after the address information. 
	 */
	A,
	/**
	 * Place station information on a separate address line 
	 */
	S
}