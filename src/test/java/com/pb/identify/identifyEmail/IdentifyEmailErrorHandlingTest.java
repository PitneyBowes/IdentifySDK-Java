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

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import org.json.JSONException;
import org.junit.After;
import org.junit.Assert;
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
import com.pb.identify.identifyEmail.validateEmailAddress.model.Record;
import com.pb.identify.identifyEmail.validateEmailAddress.model.ValidateEmailAddressAPIResponseList;
import com.pb.identify.manager.IdentifyServiceManager;
import com.pb.identify.oauth.BasicAuthServiceImpl;

/**
 * Test Case for Identify Email Address SDK, This test case hits the QA environment to
 * validate SDK and compares responses returned by API and SDK.
 */
public class IdentifyEmailErrorHandlingTest {
	private static final Logger _LOG = LoggerFactory.getLogger(IdentifyEmailErrorHandlingTest.class);

	private IdentifyServiceManager identifyServiceManager;
	private IdentifyEmailService identifyEmailService;
	private static String TEST_URL = "";
	private static String TEST_TOKEN = "";

	@BeforeClass
	public static void loadProperties() {
		Properties props = new Properties();
		try {
			InputStream fileIn = IdentifyEmailErrorHandlingTest.class.getResourceAsStream("/test.properties");
			props.load(fileIn);
			TEST_URL = props.getProperty("TEST_URL");
			fileIn.close();
		} catch (Exception e) {
			_LOG.info("Unable to load test.properties");
		}
	}

	@Test
	public void testValidateEmailAddressInvalidToken() throws SdkException, JSONException, IOException {

		TEST_TOKEN="JUNK";
		identifyServiceManager = IdentifyServiceManager.getInstance(new BasicAuthServiceImpl(TEST_TOKEN, TEST_URL));
		identifyEmailService = identifyServiceManager.getIdentifyEmailService();
		int httpStatusCode = 0;
		String reason=null;
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
		String errorJsonResponse = null;
		try {
			ValidateEmailAddressAPIResponseList response = identifyEmailService.validateEmailAddress(Arrays.asList(record1));
		} catch (SdkException e) {
			httpStatusCode = e.getIdentifyApiErrors().getHttpStatusCode();
			reason = e.getIdentifyApiErrors().getReason();
		}
		identifyServiceManager = null;
		identifyEmailService = null;
		assertEquals(401, httpStatusCode);
		assertEquals("Unauthorized",reason);
	}
	
	@Test
	public void testValidateEmailAddressInvalidKey() {
		String API_KEY = "JUNK";
		String API_SECRET= "JUNK";
		identifyServiceManager = IdentifyServiceManager.getInstance(API_KEY,API_SECRET);
		
		identifyEmailService = identifyServiceManager.getIdentifyEmailService();
		int httpStatusCode = 0;
		String reason=null;
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
		String errorJsonResponse = null;
		try {
			ValidateEmailAddressAPIResponseList response = identifyEmailService.validateEmailAddress(Arrays.asList(record1));
			} catch (SdkException e) {
			httpStatusCode = e.getIdentifyApiErrors().getHttpStatusCode();
			reason = e.getIdentifyApiErrors().getReason();
		}
		identifyServiceManager = null;
		identifyEmailService = null;
		assertEquals(401, httpStatusCode);
		assertEquals("Unauthorized",reason);
	}
}
