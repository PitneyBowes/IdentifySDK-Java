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
import com.pb.identify.identifyAddress.validateMailingAddress.model.Address;
import com.pb.identify.identifyAddress.validateMailingAddress.model.Options;
import com.pb.identify.identifyAddress.common.OutputCasing;
import com.pb.identify.identifyAddress.validateMailingAddress.model.ValidateMailingAddressAPIResponse;
import com.pb.identify.identifyAddress.validateMailingAddress.model.ValidateMailingAddressAPIResponseList;
import com.pb.identify.manager.IdentifyServiceManager;
import com.pb.identify.oauth.BasicAuthServiceImpl;

/**
 * Test Case for Identify Address SDK, This test case hits the QA environment to
 * validate SDK and compares responses returned by API and SDK.
 */
public class IdentifyAddressTest {

	private static final Logger _LOG = LoggerFactory.getLogger(IdentifyAddressTest.class);

	private IdentifyServiceManager identifyServiceManager;
	private IdentifyAddressService identifyAddressService;
	private static String TEST_URL = "";
	private static String TEST_TOKEN = "";
	private static final String VALIDATE_MAILING_ADDRESS_REQUEST_SRC = "\\src\\test\\resources\\validateMailingAddressRequest.json";
	private static final String VALIDATE_MAILING_ADDRESS_BATCH_REQUEST_SRC = "\\src\\test\\resources\\validateMailingAddressBatchRequest.json";
	
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



	private List<Address> buildDummyAddresses() {
		
		Options options = new Options(OutputCasing.M);
		
		Address address1 = new Address();
		address1.setAddressLine1("50 water st.");
		address1.setCity("lee");
		address1.setStateProvince("ma");
		UserField userField = new UserField();
		userField.setName("name1");
		userField.setValue("value1");
		UserField userField1 = new UserField();
		userField1.setName("name2");
		userField1.setValue("value2");
		address1.setUserFields(Arrays.asList(userField,userField1));

		Address address2 = new Address();
		address2.setAddressLine1("1 Global View");
		address2.setCity("troy");
		address2.setStateProvince("NY");
		address2.setCountry("US");
		address2.setFirmName("Pitney Bowes Software");

		List<Address> addresses = new ArrayList<Address>();
		addresses.add(address1);
		addresses.add(address2);

		return addresses; 

	} 
	
	private static RequestObserver validateMailingAddressCallBack(final List<ValidateMailingAddressAPIResponse> responses,
			final SdkException[] sdkException) {
		return new RequestObserver<ValidateMailingAddressAPIResponseList>() {
			@Override
			public void onSuccess(ValidateMailingAddressAPIResponseList responseList) {
				responses.addAll(responseList.getResponses());

			}

			@Override
			public void onFailure(SdkException e) {
				sdkException[0] = e;
			}
		};
	}
	
	@Test
	public void testWhetherSDKAndAPIResponsesMatchForVaildateMailingAddress() throws SdkException, JSONException, IOException {

		String jsonResponseFromAPI = TestUtility.getJSONResponseFromApiForPOSTRequest(VALIDATE_MAILING_ADDRESS_REQUEST_SRC, TEST_URL,
				"/identifyaddress/v1/rest/validatemailingaddress/results.json");
		Options options = new Options(OutputCasing.M);
		Address address = new Address();
		address.setAddressLine1("1 Global View");
		address.setCity("troy");
		address.setCountry("us");
		address.setStateProvince("NY");
		address.setFirmName("Pitney Bowes Software");
		
		UserField userField1 = new UserField();
		userField1.setName("name1");
		userField1.setValue("value1");
		UserField userField2 = new UserField();
		userField2.setName("name2");
		userField2.setValue("value2");
		address.setUserFields(Arrays.asList(userField1,userField2));
		
		
		
		ValidateMailingAddressAPIResponseList response = identifyAddressService.validateMailingAddress(Arrays.asList(address),options);
		Gson gson = new GsonBuilder().create();
		String jsonResponseFromSDK = gson.toJson(response);
		System.out.println(jsonResponseFromSDK);
		JSONAssert.assertEquals(jsonResponseFromAPI, jsonResponseFromSDK, JSONCompareMode.STRICT);

	}

	@Test
	public void testWhetherSDKAndAPIResponsesMatchForVaildateMailingAddressAsync()
			throws SdkException, JSONException, IOException, InterruptedException {

		String jsonResponseFromAPI = TestUtility.getJSONResponseFromApiForPOSTRequest(VALIDATE_MAILING_ADDRESS_REQUEST_SRC, TEST_URL,
				"/identifyaddress/v1/rest/validatemailingaddress/results.json");

		Options options = new Options(OutputCasing.M);
		Address address = new Address();
		address.setAddressLine1("1 Global View");
		address.setCity("troy");
		address.setCountry("us");
		address.setStateProvince("NY");
		address.setFirmName("Pitney Bowes Software");
		
		UserField userField1 = new UserField();
		userField1.setName("name1");
		userField1.setValue("value1");
		UserField userField2 = new UserField();
		userField2.setName("name2");
		userField2.setValue("value2");
		address.setUserFields(Arrays.asList(userField1,userField2));

		final ValidateMailingAddressAPIResponseList validateMailingAddressResponse = new ValidateMailingAddressAPIResponseList();
		identifyAddressService.validateMailingAddressAsync(Arrays.asList(address),options,validateMailingAddressCallBack(validateMailingAddressResponse.getResponses(), null));

		Thread.sleep(5000);

		Gson gson = new GsonBuilder().create();
		String jsonResponseFromSDK = gson.toJson(validateMailingAddressResponse);
		JSONAssert.assertEquals(jsonResponseFromAPI, jsonResponseFromSDK, JSONCompareMode.STRICT);

	}

	@Test
	public void testWhetherSDKAndAPIResponsesMatchForVaildateMailingAddressBatch() throws SdkException, JSONException, IOException {

		String jsonResponseFromAPI = TestUtility.getJSONResponseFromApiForPOSTRequest(VALIDATE_MAILING_ADDRESS_BATCH_REQUEST_SRC,
				TEST_URL, "/identifyaddress/v1/rest/validatemailingaddress/results.json");
		ValidateMailingAddressAPIResponseList response = identifyAddressService.validateMailingAddress(buildDummyAddresses(), null);
		Gson gson = new GsonBuilder().create();
		String jsonResponseFromSDK = gson.toJson(response);
		JSONAssert.assertEquals(jsonResponseFromAPI, jsonResponseFromSDK, JSONCompareMode.STRICT);

	}

	@Test
	public void testWhetherSDKAndAPIResponsesMatchForVaildateMailingAddressBatchAsync()
			throws SdkException, JSONException, IOException, InterruptedException {

		String jsonResponseFromAPI = TestUtility.getJSONResponseFromApiForPOSTRequest(VALIDATE_MAILING_ADDRESS_BATCH_REQUEST_SRC,
				TEST_URL, "/identifyaddress/v1/rest/validatemailingaddress/results.json");

		Options options = new Options(OutputCasing.M);
		final ValidateMailingAddressAPIResponseList validateMailingAddressResponse = new ValidateMailingAddressAPIResponseList();
		identifyAddressService.validateMailingAddressAsync(buildDummyAddresses(),options,validateMailingAddressCallBack(validateMailingAddressResponse.getResponses(), null));

		Thread.sleep(5000);

		Gson gson = new GsonBuilder().create();
		String jsonResponseFromSDK = gson.toJson(validateMailingAddressResponse);
		JSONAssert.assertEquals(jsonResponseFromAPI, jsonResponseFromSDK, JSONCompareMode.STRICT);

	}
	
	@After
	public void tearDown() throws Exception {
		identifyServiceManager = null;
		identifyAddressService = null;
	}

}