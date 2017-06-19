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
package com.pb.identify.identifyAddress;

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
import com.pb.identify.identifyAddress.IdentifyAddressService;
import com.pb.identify.identifyAddress.IdentifyAddressServiceImpl;
import com.pb.identify.identifyAddress.getPostalCodes.model.Record;
import com.pb.identify.identifyAddress.getPostalCodes.model.Options;
import com.pb.identify.identifyAddress.getPostalCodes.model.GetPostalCodesAPIResponse;
import com.pb.identify.identifyAddress.getPostalCodes.model.GetPostalCodesAPIResponseList;
import com.pb.identify.manager.IdentifyServiceManager;
import com.pb.identify.oauth.BasicAuthServiceImpl;

public class IdentifyAddressTestGetPostalCodes {
	private static final Logger _LOG = LoggerFactory.getLogger(IdentifyAddressTest.class);

	private IdentifyServiceManager identifyServiceManager;
	private IdentifyAddressService identifyAddressService;
	private static String TEST_URL = "";
	private static String TEST_TOKEN = "";
	private static final String GET_POSTAL_CODES_REQUEST_SRC = "\\src\\test\\resources\\getPostalCodesRequest.json";
	private static final String GET_POSTAL_CODES_BATCH_REQUEST_SRC = "\\src\\test\\resources\\getPostalCodesBatchRequest.json";

	@BeforeClass
	public static void loadProperties() {
		Properties props = new Properties();
		try {
			InputStream fileIn = IdentifyAddressTest.class.getResourceAsStream("/test.properties");
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
		identifyAddressService = new IdentifyAddressServiceImpl();
	}



	private List<Record> buildDummyAddresses() {

		Record record1 = new Record();
		record1.setCity("Corona");
		record1.setStateProvince("NY");
		UserField userField = new UserField();
		userField.setName("name1");
		userField.setValue("value1");
		UserField userField1 = new UserField();
		userField1.setName("name2");
		userField1.setValue("value2");
		record1.setUserFields(Arrays.asList(userField,userField1));

		Record record2 = new Record();
		record2.setCity("Los Angeles");
		record2.setStateProvince("California");

		List<Record> records = new ArrayList<Record>();
		records.add(record1);
		records.add(record2);

		return records; 

	} 

	private static RequestObserver getPostalCodesCallback(final List<GetPostalCodesAPIResponse> responses,
			final SdkException[] sdkException) {
		return new RequestObserver<GetPostalCodesAPIResponseList>() {
			@Override
			public void onSuccess(GetPostalCodesAPIResponseList responseList) {
				responses.addAll(responseList.getResponses());

			}

			@Override
			public void onFailure(SdkException e) {
				sdkException[0] = e;
			}
		};
	}

	@Test
	public void testWhetherSDKAndAPIResponsesMatchForGetPostalCodes() throws SdkException, JSONException, IOException {

		String jsonResponseFromAPI = TestUtility.getJSONResponseFromApiForPOSTRequest(GET_POSTAL_CODES_REQUEST_SRC, TEST_URL,
				"/identifyaddress/v1/rest/getpostalcodes/results.json");
		Options options = new Options();
		Record record = new Record();
		record.setCity("Corona");
		record.setStateProvince("NY");

		UserField userField1 = new UserField();
		userField1.setName("name1");
		userField1.setValue("value1");
		UserField userField2 = new UserField();
		userField2.setName("name2");
		userField2.setValue("value2");
		record.setUserFields(Arrays.asList(userField1,userField2));



		GetPostalCodesAPIResponseList response = identifyAddressService.getPostalCodes(Arrays.asList(record),options);
		Gson gson = new GsonBuilder().create();
		String jsonResponseFromSDK = gson.toJson(response);
		System.out.println(jsonResponseFromSDK);
		JSONAssert.assertEquals(jsonResponseFromAPI, jsonResponseFromSDK, JSONCompareMode.STRICT);

	}

	@Test
	public void testWhetherSDKAndAPIResponsesMatchForGetPostalCodesAsync()
			throws SdkException, JSONException, IOException, InterruptedException {

		String jsonResponseFromAPI = TestUtility.getJSONResponseFromApiForPOSTRequest(GET_POSTAL_CODES_REQUEST_SRC, TEST_URL,
				"/identifyaddress/v1/rest/getpostalcodes/results.json");

		Options options = new Options();
		Record record = new Record();
		record.setCity("Corona");
		record.setStateProvince("NY");

		UserField userField1 = new UserField();
		userField1.setName("name1");
		userField1.setValue("value1");
		UserField userField2 = new UserField();
		userField2.setName("name2");
		userField2.setValue("value2");
		record.setUserFields(Arrays.asList(userField1,userField2));

		final GetPostalCodesAPIResponseList getPostalCodesResponse = new GetPostalCodesAPIResponseList();
		identifyAddressService.getPostalCodesAsync(Arrays.asList(record),options,getPostalCodesCallback(getPostalCodesResponse.getResponses(), null));

		Thread.sleep(5000);

		Gson gson = new GsonBuilder().create();
		String jsonResponseFromSDK = gson.toJson(getPostalCodesResponse);
		JSONAssert.assertEquals(jsonResponseFromAPI, jsonResponseFromSDK, JSONCompareMode.STRICT);

	}

	@Test
	public void testWhetherSDKAndAPIResponsesMatchForGetPostalCodesBatch() throws SdkException, JSONException, IOException {

		String jsonResponseFromAPI = TestUtility.getJSONResponseFromApiForPOSTRequest(GET_POSTAL_CODES_BATCH_REQUEST_SRC,
				TEST_URL, "/identifyaddress/v1/rest/getpostalcodes/results.json");
		GetPostalCodesAPIResponseList response = identifyAddressService.getPostalCodes(buildDummyAddresses(), null);
		Gson gson = new GsonBuilder().create();
		String jsonResponseFromSDK = gson.toJson(response);
		JSONAssert.assertEquals(jsonResponseFromAPI, jsonResponseFromSDK, JSONCompareMode.STRICT);

	}

	@Test
	public void testWhetherSDKAndAPIResponsesMatchForGetPostalCodesBatchAsync()
			throws SdkException, JSONException, IOException, InterruptedException {

		String jsonResponseFromAPI = TestUtility.getJSONResponseFromApiForPOSTRequest(GET_POSTAL_CODES_BATCH_REQUEST_SRC,
				TEST_URL, "/identifyaddress/v1/rest/getpostalcodes/results.json");

		Options options = new Options();
		final GetPostalCodesAPIResponseList getPostalCodesResponse = new GetPostalCodesAPIResponseList();
		identifyAddressService.getPostalCodesAsync(buildDummyAddresses(),options,getPostalCodesCallback(getPostalCodesResponse.getResponses(), null));

		Thread.sleep(5000);

		Gson gson = new GsonBuilder().create();
		String jsonResponseFromSDK = gson.toJson(getPostalCodesResponse);
		JSONAssert.assertEquals(jsonResponseFromAPI, jsonResponseFromSDK, JSONCompareMode.STRICT);

	}

	@After
	public void tearDown() throws Exception {
		identifyServiceManager = null;
		identifyAddressService = null;
	}
}
