package com.pb.identify.identifyAddress;
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
import com.pb.identify.identifyAddress.validateMailingAddressPro.model.Address;
import com.pb.identify.identifyAddress.validateMailingAddressPro.model.ValidateMailingAddressProAPIResponse;
import com.pb.identify.identifyAddress.validateMailingAddressPro.model.ValidateMailingAddressProAPIResponseList;
import com.pb.identify.manager.IdentifyServiceManager;
import com.pb.identify.oauth.BasicAuthServiceImpl;

/**
 * Test Case for Identify Address SDK, This test case hits the QA environment to
 * validate SDK and compares responses returned by API and SDK for Pro service.
 */
public class IdentifyAddressErrorHandlingTestPro {

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
	public void testVaildateMailingAddressProInvalidToken() throws SdkException, JSONException, IOException {
		TEST_TOKEN="JUNK";
		identifyServiceManager = IdentifyServiceManager.getInstance(new BasicAuthServiceImpl(TEST_TOKEN, TEST_URL));
		identifyAddressService = identifyServiceManager.getIdentifyAddressService();
		int httpStatusCode = 0;
		String reason=null;
		Address address = new Address();
		address.setAddressLine1("50 water st");
		address.setCity("lee");
		address.setCountry("us");
		address.setStateProvince("ma");
		String errorJsonResponse = null;
		try {
			ValidateMailingAddressProAPIResponseList response = identifyAddressService.validateMailingAddressPro(Arrays.asList(address),null);
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
	public void testValidateMailingAddressProInvalidKey() {
		String API_KEY = "JUNK";
		String API_SECRET= "JUNK";
		identifyServiceManager = IdentifyServiceManager.getInstance(API_KEY,API_SECRET);
		identifyAddressService = identifyServiceManager.getIdentifyAddressService();
		int httpStatusCode = 0;
		String reason=null;
		Address address = new Address();
		address.setAddressLine1("50 water st");
		address.setCity("lee");
		address.setCountry("us");
		address.setStateProvince("ma");
		try {
			ValidateMailingAddressProAPIResponseList response = identifyAddressService.validateMailingAddressPro(Arrays.asList(address),null);
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
