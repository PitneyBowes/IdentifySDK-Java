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
package com.pb.identify.identifyRisk;

import java.util.List;

import com.pb.identify.RequestObserver;
import com.pb.identify.exception.SdkException;
import com.pb.identify.identifyRisk.checkGlobalWatchList.model.CheckGlobalWatchListAPIResponseList;
import com.pb.identify.identifyRisk.checkGlobalWatchList.model.Record;

public interface IdentifyRiskService {
	
	/**
	 * This service provides functionality to matches transactions against government-provided watch lists that contain data from various countries.
	 * 
	 * @param records List of records to match
	 * @return CheckGlobalWatchListAPIResponseList
	 * @throws SdkException SdkException
	 */
	public CheckGlobalWatchListAPIResponseList checkGlobalWatchList(List<Record> records)
			throws SdkException;

	/**
	 * This service provides functionality to matches transactions against government-provided watch lists that contain data from various countries.
	 * 
	 * @param records List of records to match
	 * @param requestObserver Observer interface for request
	 */
	public void checkGlobalWatchListAsync(List<Record> records,
			RequestObserver<CheckGlobalWatchListAPIResponseList> requestObserver);

}