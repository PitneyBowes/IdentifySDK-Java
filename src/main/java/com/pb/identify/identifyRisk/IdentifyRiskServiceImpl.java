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

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;

import org.apache.log4j.Level;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.pb.identify.RequestObserver;
import com.pb.identify.exception.SdkException;
import com.pb.identify.identifyRisk.checkGlobalWatchList.model.CheckGlobalWatchListAPIRequest;
import com.pb.identify.identifyRisk.checkGlobalWatchList.model.CheckGlobalWatchListAPIResponseList;
import com.pb.identify.identifyRisk.checkGlobalWatchList.model.Record;
import com.pb.identify.utils.UrlMaker;
import com.pb.identify.utils.Utility;

public class IdentifyRiskServiceImpl implements IdentifyRiskService {

	private static final Logger _LOG = LoggerFactory.getLogger(IdentifyRiskServiceImpl.class);
	
	private static final String checkGlobalWatchListURL = "/identifyrisk/v1/rest/checkglobalwatchlist/results.json";

	@Override
	public void checkGlobalWatchListAsync(final List<Record> records, final RequestObserver<CheckGlobalWatchListAPIResponseList> requestObserver) {
		org.apache.log4j.Logger.getRootLogger().setLevel(Level.OFF);
		final ExecutorService executorService = Executors.newFixedThreadPool(1);
		executorService.submit(new Runnable() {
			@Override
			public void run() {
				try {
					CheckGlobalWatchListAPIResponseList checkGlobalWatchListAPIResponseList = processCheckGlobalWatchListRequest(records);

					requestObserver.onSuccess(checkGlobalWatchListAPIResponseList);

				} catch (SdkException e) {
					requestObserver.onFailure(e);
				}
				
				finally{
					executorService.shutdown();					
				}
			}
		});
	}

	@Override
	public CheckGlobalWatchListAPIResponseList checkGlobalWatchList(List<Record> records)
			throws SdkException {

		return processCheckGlobalWatchListRequest(records);
	}

	
	private CheckGlobalWatchListAPIResponseList processCheckGlobalWatchListRequest(List<Record> records) throws SdkException {

		UrlMaker urlMaker = UrlMaker.getInstance();
		StringBuilder urlBuilder = new StringBuilder(urlMaker.getAbsoluteUrl(checkGlobalWatchListURL));

		CheckGlobalWatchListAPIRequest request = new CheckGlobalWatchListAPIRequest();

		request.getInputRecord().getRecords().addAll(records);
		
		Gson gson = new Gson();

		_LOG.debug("API URL : " + urlBuilder);
		Entity paramEntity = Entity.entity(gson.toJson(request), MediaType.APPLICATION_JSON_TYPE);

		return Utility.processPOSTRequest(paramEntity, urlBuilder.toString(), CheckGlobalWatchListAPIResponseList.class);

	}

}