package com.pb.identify.identifyEntity;

import static org.junit.Assert.*;

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
import com.pb.identify.identifyAddress.IdentifyAddressService;
import com.pb.identify.identifyAddress.IdentifyAddressServiceImpl;
import com.pb.identify.identifyAddress.validateMailingAddress.model.Address;
import com.pb.identify.identifyAddress.validateMailingAddress.model.ValidateMailingAddressAPIResponse;
import com.pb.identify.identifyAddress.validateMailingAddress.model.ValidateMailingAddressAPIResponseList;
import com.pb.identify.identifyEntity.IdentifyEntityService;
import com.pb.identify.identifyEntity.extractEntities.model.ExtractEntitiesAPIResponseList;
import com.pb.identify.identifyEntity.extractEntities.model.Record;
import com.pb.identify.manager.IdentifyServiceManager;
import com.pb.identify.oauth.BasicAuthServiceImpl;

/**
 * Test Case for Identify Address SDK, This test case hits the QA environment to
 * validate SDK and compares responses returned by API and SDK.
 */
public class IdentifyEntityErrorHandlingTest {

	private static final Logger _LOG = LoggerFactory.getLogger(IdentifyEntityErrorHandlingTest.class);

	private IdentifyServiceManager identifyServiceManager;
	private IdentifyEntityService identifyEntityService;
	private static String TEST_URL = "";
	private static String TEST_TOKEN = "";

	@BeforeClass
	public static void loadProperties() {
		Properties props = new Properties();
		try {
			InputStream fileIn = IdentifyEntityErrorHandlingTest.class.getResourceAsStream("/test.properties");
			props.load(fileIn);
			TEST_URL = props.getProperty("TEST_URL");
			fileIn.close();
		} catch (Exception e) {
			_LOG.info("Unable to load test.properties");
		}
	}

	@Test
	public void testVaildateMailingAddressInvalidToken() throws SdkException, JSONException, IOException {

		TEST_TOKEN="JUNK";
		identifyServiceManager = IdentifyServiceManager.getInstance(new BasicAuthServiceImpl(TEST_TOKEN, TEST_URL));
		identifyEntityService = identifyServiceManager.getIdentifyEntityService();
		int httpStatusCode = 0;
		String reason=null;
		Record record = new Record();
		record.setPlainText("Some text in NY");
		String errorJsonResponse = null;
		try {
			ExtractEntitiesAPIResponseList response = identifyEntityService.extractEntities(Arrays.asList(record),null);
		} catch (SdkException e) {
			httpStatusCode = e.getIdentifyApiErrors().getHttpStatusCode();
			reason = e.getIdentifyApiErrors().getReason();
		}
		identifyServiceManager = null;
		identifyEntityService = null;
		assertEquals(401, httpStatusCode);
		assertEquals("Unauthorized",reason);
	}
	
	@Test
	public void testValidateMailingAddressInvalidKey() {
		String API_KEY = "JUNK";
		String API_SECRET= "JUNK";
		identifyServiceManager = IdentifyServiceManager.getInstance(API_KEY,API_SECRET);
		identifyEntityService = identifyServiceManager.getIdentifyEntityService();
		int httpStatusCode = 0;
		String reason=null;
		Record record = new Record();
		record.setPlainText("Michael Phelps");
		
		try {
			ExtractEntitiesAPIResponseList response = identifyEntityService.extractEntities(Arrays.asList(record),null);
		} catch (SdkException e) {
			httpStatusCode = e.getIdentifyApiErrors().getHttpStatusCode();
			reason = e.getIdentifyApiErrors().getReason();
		}
		identifyServiceManager = null;
		identifyEntityService = null;
		assertEquals(401, httpStatusCode);
		assertEquals("Unauthorized",reason);
	}

}