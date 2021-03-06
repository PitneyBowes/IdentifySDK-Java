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
 * Enum to specify whether or not to return the city alias when the alias is in the input address.
 * 
 *
 */
public enum CanOutputCityAlias {
	/**
	 * Never output the city alias even if it is in the input.
	 */
	N,
	/**
	 * Output the city alias when the city alias is in the input. Default.
	 */
	Y
}