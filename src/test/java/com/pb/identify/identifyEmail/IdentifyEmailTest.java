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
package com.pb.identify.identifyEmail;

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
import com.pb.identify.identifyEmail.IdentifyEmailService;
import com.pb.identify.identifyEmail.IdentifyEmailServiceImpl;
import com.pb.identify.identifyEmail.validateEmailAddress.model.*;

import com.pb.identify.manager.IdentifyServiceManager;
import com.pb.identify.oauth.BasicAuthServiceImpl;

/**
 * Test Case for Identify Email SDK, This test case hits the QA environment to
 * validate SDK and compares responses returned by API and SDK.
 */
public class IdentifyEmailTest {

	private static final Logger _LOG = LoggerFactory.getLogger(IdentifyEmailTest.class);

	private IdentifyServiceManager identifyServiceManager;
	private IdentifyEmailService identifyEmailService;
	private static String TEST_URL = "";
	private static String TEST_TOKEN = "";
	private static final String VALIDATE_EMAIL_ADDRESS_REQUEST_SRC = "\\src\\test\\resources\\validateEmailAddressRequest.json";
	private static final String VALIDATE_EMAIL_ADDRESS_BATCH_REQUEST_SRC = "\\src\\test\\resources\\validateEmailAddressBatchRequest.json";

	@BeforeClass
	public static void loadProperties() {
		Properties props = new Properties();
		try {
			InputStream fileIn = IdentifyEmailTest.class.getResourceAsStream("/test.properties");
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
		identifyEmailService = new IdentifyEmailServiceImpl();
	}
	
	private List<Record> buildDummyRecords() {
		
		Record record1 = new Record();
		record1.setEmailAddress("support@pb.com");
		
		UserField userField = new UserField();
		userField.setName("name1");
		userField.setValue("value1");
		UserField userField1 = new UserField();
		userField1.setName("name2");
		userField1.setValue("value2");
		record1.setUserFields(Arrays.asList(userField,userField1));
		
		Record record2 = new Record();
		record2.setEmailAddress("xyz@abc.com");
		
		List<Record> records = new ArrayList<Record>();
		records.add(record1);
		records.add(record2);
		
		return records; 

	}
	
	private static RequestObserver validateEmailAddressCallBack(final List<ValidateEmailAddressAPIResponse> responses,
			final SdkException[] sdkException){
		return new RequestObserver<ValidateEmailAddressAPIResponseList>() {
			@Override
			public void onSuccess(ValidateEmailAddressAPIResponseList responseList){
				responses.addAll(responseList.getResponses());
			}
			
			@Override
			public void onFailure(SdkException e) {
				sdkException[0] = e;
			}
		};
	}
	
	@Test
	public void testWhetherSDKAndAPIResponsesMatchForValidateEmailAddress() throws SdkException, JSONException, IOException {

		String jsonResponseFromAPI = TestUtility.getJSONResponseFromApiForPOSTRequest(VALIDATE_EMAIL_ADDRESS_REQUEST_SRC, TEST_URL,
				"/identifyemail/v1/rest/validateemailaddress/results.json");
		jsonResponseFromAPI = TestUtility.getJSONResponseFromApiForPOSTRequest(VALIDATE_EMAIL_ADDRESS_REQUEST_SRC, TEST_URL,
				"/identifyemail/v1/rest/validateemailaddress/results.json");
		System.out.println(jsonResponseFromAPI);
		Record record1 = new Record();
		record1.setEmailAddress("support@pb.com");
		record1.setBogus("true");
		UserField userField = new UserField();
		userField.setName("name1");
		userField.setValue("value1");
		UserField userField1 = new UserField();
		userField1.setName("name2");
		userField1.setValue("value2");
		record1.setUserFields(Arrays.asList(userField,userField1));

				
		
		ValidateEmailAddressAPIResponseList response = identifyEmailService.validateEmailAddress(Arrays.asList(record1));
		Gson gson = new GsonBuilder().create();
		String jsonResponseFromSDK = gson.toJson(response);
		
		JSONAssert.assertEquals(jsonResponseFromAPI, jsonResponseFromSDK, JSONCompareMode.STRICT);

	}

	@Test
	public void testWhetherSDKAndAPIResponsesMatchForValidateEmailAddressAsync()
			throws SdkException, JSONException, IOException, InterruptedException {

		String jsonResponseFromAPI = TestUtility.getJSONResponseFromApiForPOSTRequest(VALIDATE_EMAIL_ADDRESS_REQUEST_SRC, TEST_URL,
				"/identifyemail/v1/rest/validateemailaddress/results.json");
		
		Record record1 = new Record();
		record1.setEmailAddress("support@pb.com");
		record1.setBogus("true");
		UserField userField = new UserField();
		userField.setName("name1");
		userField.setValue("value1");
		UserField userField1 = new UserField();
		userField1.setName("name2");
		userField1.setValue("value2");
		record1.setUserFields(Arrays.asList(userField,userField1));

		
		final ValidateEmailAddressAPIResponseList validateEmailAddressResponse = new ValidateEmailAddressAPIResponseList();
		identifyEmailService.validateEmailAddressAsync(Arrays.asList(record1),validateEmailAddressCallBack(validateEmailAddressResponse.getResponses(), null));

		Thread.sleep(5000);

		Gson gson = new GsonBuilder().create();
		String jsonResponseFromSDK = gson.toJson(validateEmailAddressResponse);
		JSONAssert.assertEquals(jsonResponseFromAPI, jsonResponseFromSDK, JSONCompareMode.STRICT);

	}

	@Test
	public void testWhetherSDKAndAPIResponsesMatchForValidateEmailAddressBatch() throws SdkException, JSONException, IOException {

		String jsonResponseFromAPI = TestUtility.getJSONResponseFromApiForPOSTRequest(VALIDATE_EMAIL_ADDRESS_BATCH_REQUEST_SRC, TEST_URL,
				"/identifyemail/v1/rest/validateemailaddress/results.json");
		
		ValidateEmailAddressAPIResponseList response = identifyEmailService.validateEmailAddress(buildDummyRecords());
		Gson gson = new GsonBuilder().create();
		String jsonResponseFromSDK = gson.toJson(response);
		JSONAssert.assertEquals(jsonResponseFromAPI, jsonResponseFromSDK, JSONCompareMode.STRICT);

	}

	@Test
	public void testWhetherSDKAndAPIResponsesMatchForValidateEmailAddressBatchAsync()
			throws SdkException, JSONException, IOException, InterruptedException {

		String jsonResponseFromAPI = TestUtility.getJSONResponseFromApiForPOSTRequest(VALIDATE_EMAIL_ADDRESS_BATCH_REQUEST_SRC, TEST_URL,
				"/identifyemail/v1/rest/validateemailaddress/results.json");
		
		final ValidateEmailAddressAPIResponseList response = new ValidateEmailAddressAPIResponseList();
		identifyEmailService.validateEmailAddressAsync(buildDummyRecords(),validateEmailAddressCallBack(response.getResponses(), null));

		Thread.sleep(5000);

		Gson gson = new GsonBuilder().create();
		String jsonResponseFromSDK = gson.toJson(response);
		JSONAssert.assertEquals(jsonResponseFromAPI, jsonResponseFromSDK, JSONCompareMode.STRICT);

	}

	@After
	public void tearDown() throws Exception {
		identifyServiceManager = null;
		identifyEmailService = null;
	}

}
