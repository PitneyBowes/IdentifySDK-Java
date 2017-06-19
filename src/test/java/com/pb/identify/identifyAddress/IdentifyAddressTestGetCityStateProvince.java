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
import com.pb.identify.identifyAddress.getCityStateProvince.model.Record;
import com.pb.identify.identifyAddress.getCityStateProvince.model.Options;
import com.pb.identify.identifyAddress.getCityStateProvince.model.GetCityStateProvinceAPIResponse;
import com.pb.identify.identifyAddress.getCityStateProvince.model.GetCityStateProvinceAPIResponseList;
import com.pb.identify.manager.IdentifyServiceManager;
import com.pb.identify.oauth.BasicAuthServiceImpl;

public class IdentifyAddressTestGetCityStateProvince {
	private static final Logger _LOG = LoggerFactory.getLogger(IdentifyAddressTest.class);

	private IdentifyServiceManager identifyServiceManager;
	private IdentifyAddressService identifyAddressService;
	private static String TEST_URL = "";
	private static String TEST_TOKEN = "";
	private static final String GET_CITY_STATE_PROVINCE_REQUEST_SRC = "\\src\\test\\resources\\getCityStateProvinceRequest.json";
	private static final String GET_CITY_STATE_PROVINCE_BATCH_REQUEST_SRC = "\\src\\test\\resources\\getCityStateProvinceBatchRequest.json";

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
		record1.setPostalCode("11368");
		UserField userField = new UserField();
		userField.setName("name1");
		userField.setValue("value1");
		UserField userField1 = new UserField();
		userField1.setName("name2");
		userField1.setValue("value2");
		record1.setUserFields(Arrays.asList(userField,userField1));

		Record record2 = new Record();
		record2.setPostalCode("11208");

		List<Record> records = new ArrayList<Record>();
		records.add(record1);
		records.add(record2);

		return records; 

	} 

	private static RequestObserver getCityStateProvinceCallback(final List<GetCityStateProvinceAPIResponse> responses,
			final SdkException[] sdkException) {
		return new RequestObserver<GetCityStateProvinceAPIResponseList>() {
			@Override
			public void onSuccess(GetCityStateProvinceAPIResponseList responseList) {
				responses.addAll(responseList.getResponses());

			}

			@Override
			public void onFailure(SdkException e) {
				sdkException[0] = e;
			}
		};
	}

	@Test
	public void testWhetherSDKAndAPIResponsesMatchForGetCityStateProvince() throws SdkException, JSONException, IOException {

		String jsonResponseFromAPI = TestUtility.getJSONResponseFromApiForPOSTRequest(GET_CITY_STATE_PROVINCE_REQUEST_SRC, TEST_URL,
				"/identifyaddress/v1/rest/getcitystateprovince/results.json");
		Options options = new Options();
		Record record = new Record();
		record.setPostalCode("11368");

		UserField userField1 = new UserField();
		userField1.setName("name1");
		userField1.setValue("value1");
		UserField userField2 = new UserField();
		userField2.setName("name2");
		userField2.setValue("value2");
		record.setUserFields(Arrays.asList(userField1,userField2));



		GetCityStateProvinceAPIResponseList response = identifyAddressService.getCityStateProvince(Arrays.asList(record),options);
		Gson gson = new GsonBuilder().create();
		String jsonResponseFromSDK = gson.toJson(response);
		System.out.println(jsonResponseFromSDK);
		JSONAssert.assertEquals(jsonResponseFromAPI, jsonResponseFromSDK, JSONCompareMode.STRICT);

	}

	@Test
	public void testWhetherSDKAndAPIResponsesMatchForGetCityStateProvinceAsync()
			throws SdkException, JSONException, IOException, InterruptedException {

		String jsonResponseFromAPI = TestUtility.getJSONResponseFromApiForPOSTRequest(GET_CITY_STATE_PROVINCE_REQUEST_SRC, TEST_URL,
				"/identifyaddress/v1/rest/getcitystateprovince/results.json");

		Options options = new Options();
		Record record = new Record();
		record.setPostalCode("11368");

		UserField userField1 = new UserField();
		userField1.setName("name1");
		userField1.setValue("value1");
		UserField userField2 = new UserField();
		userField2.setName("name2");
		userField2.setValue("value2");
		record.setUserFields(Arrays.asList(userField1,userField2));

		final GetCityStateProvinceAPIResponseList getCityStateProvinceResponse = new GetCityStateProvinceAPIResponseList();
		identifyAddressService.getCityStateProvinceAsync(Arrays.asList(record),options,getCityStateProvinceCallback(getCityStateProvinceResponse.getResponses(), null));

		Thread.sleep(5000);

		Gson gson = new GsonBuilder().create();
		String jsonResponseFromSDK = gson.toJson(getCityStateProvinceResponse);
		JSONAssert.assertEquals(jsonResponseFromAPI, jsonResponseFromSDK, JSONCompareMode.STRICT);

	}

	@Test
	public void testWhetherSDKAndAPIResponsesMatchForGetCityStateProvinceBatch() throws SdkException, JSONException, IOException {

		String jsonResponseFromAPI = TestUtility.getJSONResponseFromApiForPOSTRequest(GET_CITY_STATE_PROVINCE_BATCH_REQUEST_SRC,
				TEST_URL, "/identifyaddress/v1/rest/getcitystateprovince/results.json");
		GetCityStateProvinceAPIResponseList response = identifyAddressService.getCityStateProvince(buildDummyAddresses(), null);
		Gson gson = new GsonBuilder().create();
		String jsonResponseFromSDK = gson.toJson(response);
		JSONAssert.assertEquals(jsonResponseFromAPI, jsonResponseFromSDK, JSONCompareMode.STRICT);

	}

	@Test
	public void testWhetherSDKAndAPIResponsesMatchForGetCityStateProvinceBatchAsync()
			throws SdkException, JSONException, IOException, InterruptedException {

		String jsonResponseFromAPI = TestUtility.getJSONResponseFromApiForPOSTRequest(GET_CITY_STATE_PROVINCE_BATCH_REQUEST_SRC,
				TEST_URL, "/identifyaddress/v1/rest/getcitystateprovince/results.json");

		Options options = new Options();
		final GetCityStateProvinceAPIResponseList getCityStateProvinceResponse = new GetCityStateProvinceAPIResponseList();
		identifyAddressService.getCityStateProvinceAsync(buildDummyAddresses(),options,getCityStateProvinceCallback(getCityStateProvinceResponse.getResponses(), null));

		Thread.sleep(5000);

		Gson gson = new GsonBuilder().create();
		String jsonResponseFromSDK = gson.toJson(getCityStateProvinceResponse);
		JSONAssert.assertEquals(jsonResponseFromAPI, jsonResponseFromSDK, JSONCompareMode.STRICT);

	}

	@After
	public void tearDown() throws Exception {
		identifyServiceManager = null;
		identifyAddressService = null;
	}
}
