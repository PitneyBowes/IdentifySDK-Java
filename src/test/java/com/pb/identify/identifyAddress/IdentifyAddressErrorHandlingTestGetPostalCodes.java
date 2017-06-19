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

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Properties;

import org.json.JSONException;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pb.identify.exception.SdkException;
import com.pb.identify.identifyAddress.getPostalCodes.model.Record;
import com.pb.identify.identifyAddress.getPostalCodes.model.GetPostalCodesAPIResponseList;
import com.pb.identify.manager.IdentifyServiceManager;
import com.pb.identify.oauth.BasicAuthServiceImpl;

/**
 * Test Case for Identify Address SDK, This test case hits the QA environment to
 * validate SDK and compares responses returned by API and SDK.
 */
public class IdentifyAddressErrorHandlingTestGetPostalCodes {
	private static final Logger _LOG = LoggerFactory.getLogger(IdentifyAddressErrorHandlingTest.class);

	private IdentifyServiceManager identifyServiceManager;
	private IdentifyAddressService identifyAddressService;
	private static String TEST_URL = "";
	private static String TEST_TOKEN = "";

	@BeforeClass
	public static void loadProperties() {
		Properties props = new Properties();
		try {
			InputStream fileIn = IdentifyAddressErrorHandlingTest.class.getResourceAsStream("/test.properties");
			props.load(fileIn);
			TEST_URL = props.getProperty("TEST_URL");
			fileIn.close();
		} catch (Exception e) {
			_LOG.info("Unable to load test.properties");
		}
	}

	@Test
	public void testGetPostalCodesInvalidToken() throws SdkException, JSONException, IOException {

		TEST_TOKEN="JUNK";
		identifyServiceManager = IdentifyServiceManager.getInstance(new BasicAuthServiceImpl(TEST_TOKEN, TEST_URL));
		identifyAddressService = identifyServiceManager.getIdentifyAddressService();
		int httpStatusCode = 0;
		String reason=null;
		Record record = new Record();
		record.setCity("Corona");
		record.setStateProvince("NY");
		String errorJsonResponse = null;
		try {
			GetPostalCodesAPIResponseList response = identifyAddressService.getPostalCodes(Arrays.asList(record),null);
		} catch (SdkException e) {
			httpStatusCode = e.getIdentifyApiErrors().getHttpStatusCode();
			reason = e.getIdentifyApiErrors().getReason();
		}
		identifyServiceManager = null;
		identifyAddressService = null;
		assertEquals(401, httpStatusCode);
		assertEquals("Unauthorized",reason);
	}

	@Test
	public void testPostalCodesInvalidKey() {
		String API_KEY = "JUNK";
		String API_SECRET= "JUNK";
		identifyServiceManager = IdentifyServiceManager.getInstance(API_KEY,API_SECRET);
		identifyAddressService = identifyServiceManager.getIdentifyAddressService();
		int httpStatusCode = 0;
		String reason=null;
		Record record = new Record();
		record.setCity("Corona");
		record.setStateProvince("NY");
		try {
			GetPostalCodesAPIResponseList response = identifyAddressService.getPostalCodes(Arrays.asList(record),null);
		} catch (SdkException e) {
			httpStatusCode = e.getIdentifyApiErrors().getHttpStatusCode();
			reason = e.getIdentifyApiErrors().getReason();
		}
		identifyServiceManager = null;
		identifyAddressService = null;
		assertEquals(401, httpStatusCode);
		assertEquals("Unauthorized",reason);
	}	
}
