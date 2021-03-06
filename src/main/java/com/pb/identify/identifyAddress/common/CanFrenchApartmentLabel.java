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
 * Enum to Specify For French addresses, specifies the default apartment label to use in the output if there is no apartment label in the input address. 
 * 
 *
 */
public enum CanFrenchApartmentLabel {
	/**
	 * Use "Apt" as the label. Default
	 */
	Apt,
	/**
	 * Use "Apartment" as the label.
	 */
	Appartement,
	/**
	 *Use "Bureau" as the label.
	 */
	Bureau,
	/**
	 *Use "Suite" as the label.
	 */
	Suite,
	/**
	 *Use "Unite" as the label.
	 */
	Unite
}