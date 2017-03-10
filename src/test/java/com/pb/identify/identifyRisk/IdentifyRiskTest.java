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

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import org.json.JSONException;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.pb.identify.RequestObserver;
import com.pb.identify.TestUtility;
import com.pb.identify.common.model.UserField;
import com.pb.identify.exception.SdkException;

import com.pb.identify.identifyRisk.IdentifyRiskService;
import com.pb.identify.identifyRisk.IdentifyRiskServiceImpl;
import com.pb.identify.identifyRisk.checkGlobalWatchList.model.CheckGlobalWatchListAPIRequest;
import com.pb.identify.identifyRisk.checkGlobalWatchList.model.*;

import com.pb.identify.manager.IdentifyServiceManager;
import com.pb.identify.oauth.BasicAuthServiceImpl;

/**
 * Test Case for Identify Risk SDK, This test case hits the QA environment to
 * validate SDK and compares responses returned by API and SDK.
 */
public class IdentifyRiskTest {

	private static final Logger _LOG = LoggerFactory.getLogger(IdentifyRiskTest.class);

	private IdentifyServiceManager identifyServiceManager;
	private IdentifyRiskService identifyRiskService;
	private static String TEST_URL = "";
	private static String TEST_TOKEN = "";
	private static final String CHECK_GLOBAL_WATCHLIST_REQUEST_SRC = "\\src\\test\\resources\\checkGlobalWatchListRequest.json";
	private static final String CHECK_GLOBAL_WATCHLIST_BATCH_REQUEST_SRC = "\\src\\test\\resources\\checkGlobalWatchListBatchRequest.json";

	@BeforeClass
	public static void loadProperties() {
		Properties props = new Properties();
		try {
			InputStream fileIn = IdentifyRiskTest.class.getResourceAsStream("/test.properties");
			props.load(fileIn);
			TEST_URL = props.getProperty("TEST_URL");
			TEST_TOKEN = props.getProperty("TEST_TOKEN");
			fileIn.close();
		} catch (Exception e) {
			_LOG.info("Unable to load test.properties");
		}
	}

	@Before
	public void setUp() throws Exception {
		identifyServiceManager = IdentifyServiceManager.getInstance(new BasicAuthServiceImpl(TEST_TOKEN, TEST_URL));
		identifyRiskService = new IdentifyRiskServiceImpl();
	}



	private List<Record> buildDummyRecords() {
		
		
		
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
		record2.setFirstname("migual");
		record2.setLastname("batista");

		List<Record> records = new ArrayList<Record>();
		records.add(record1);
		records.add(record2);

		return records; 

	} 

	private static RequestObserver checkGlobalWatchListCallBack(final List<CheckGlobalWatchListAPIResponse> responses,
			final SdkException[] sdkException) {
		return new RequestObserver<CheckGlobalWatchListAPIResponseList>() {
			@Override
			public void onSuccess(CheckGlobalWatchListAPIResponseList responseList) {
				responses.addAll(responseList.getResponses());

			}

			@Override
			public void onFailure(SdkException e) {
				sdkException[0] = e;
			}
		};
	}

	@Test
	public void testWhetherSDKAndAPIResponsesMatchForCheckGlobalWatchList() throws SdkException, JSONException, IOException {

		String jsonResponseFromAPI = TestUtility.getJSONResponseFromApiForPOSTRequest(CHECK_GLOBAL_WATCHLIST_REQUEST_SRC, TEST_URL,
				"/identifyrisk/v1/rest/checkglobalwatchlist/results.json");
		jsonResponseFromAPI = TestUtility.getJSONResponseFromApiForPOSTRequest(CHECK_GLOBAL_WATCHLIST_REQUEST_SRC, TEST_URL,
				"/identifyrisk/v1/rest/checkglobalwatchlist/results.json");
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

				
		
		CheckGlobalWatchListAPIResponseList response = identifyRiskService.checkGlobalWatchList(Arrays.asList(record1));
		Gson gson = new GsonBuilder().create();
		String jsonResponseFromSDK = gson.toJson(response);
		JSONAssert.assertEquals(jsonResponseFromAPI, jsonResponseFromSDK, JSONCompareMode.STRICT);

	}

	@Test
	public void testWhetherSDKAndAPIResponsesMatchForCheckGlobalWatchListAsync()
			throws SdkException, JSONException, IOException, InterruptedException {

		String jsonResponseFromAPI = TestUtility.getJSONResponseFromApiForPOSTRequest(CHECK_GLOBAL_WATCHLIST_REQUEST_SRC, TEST_URL,
				"/identifyrisk/v1/rest/checkglobalwatchlist/results.json");
		
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

		
		final CheckGlobalWatchListAPIResponseList checkGlobalWatchListResponse = new CheckGlobalWatchListAPIResponseList();
		identifyRiskService.checkGlobalWatchListAsync(Arrays.asList(record1),checkGlobalWatchListCallBack(checkGlobalWatchListResponse.getResponses(), null));

		Thread.sleep(5000);

		Gson gson = new GsonBuilder().create();
		String jsonResponseFromSDK = gson.toJson(checkGlobalWatchListResponse);
		JSONAssert.assertEquals(jsonResponseFromAPI, jsonResponseFromSDK, JSONCompareMode.STRICT);

	}

	@Test
	public void testWhetherSDKAndAPIResponsesMatchForCheckGlobalWatchListBatch() throws SdkException, JSONException, IOException {

		String jsonResponseFromAPI = TestUtility.getJSONResponseFromApiForPOSTRequest(CHECK_GLOBAL_WATCHLIST_BATCH_REQUEST_SRC, TEST_URL,
				"/identifyrisk/v1/rest/checkglobalwatchlist/results.json");
		
		CheckGlobalWatchListAPIResponseList response = identifyRiskService.checkGlobalWatchList(buildDummyRecords());
		Gson gson = new GsonBuilder().create();
		String jsonResponseFromSDK = gson.toJson(response);
		JSONAssert.assertEquals(jsonResponseFromAPI, jsonResponseFromSDK, JSONCompareMode.STRICT);

	}

	@Test
	public void testWhetherSDKAndAPIResponsesMatchForCheckGlobalWatchListBatchAsync()
			throws SdkException, JSONException, IOException, InterruptedException {

		String jsonResponseFromAPI = TestUtility.getJSONResponseFromApiForPOSTRequest(CHECK_GLOBAL_WATCHLIST_BATCH_REQUEST_SRC, TEST_URL,
				"/identifyrisk/v1/rest/checkglobalwatchlist/results.json");
		
		final CheckGlobalWatchListAPIResponseList response = new CheckGlobalWatchListAPIResponseList();
		identifyRiskService.checkGlobalWatchListAsync(buildDummyRecords(),checkGlobalWatchListCallBack(response.getResponses(), null));

		Thread.sleep(5000);

		Gson gson = new GsonBuilder().create();
		String jsonResponseFromSDK = gson.toJson(response);
		JSONAssert.assertEquals(jsonResponseFromAPI, jsonResponseFromSDK, JSONCompareMode.STRICT);

	}

	@After
	public void tearDown() throws Exception {
		identifyServiceManager = null;
		identifyRiskService = null;
	}

}