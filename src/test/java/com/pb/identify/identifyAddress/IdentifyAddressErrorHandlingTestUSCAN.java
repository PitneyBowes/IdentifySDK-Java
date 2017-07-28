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
import com.pb.identify.identifyAddress.validateMailingAddressUSCAN.model.Address;
import com.pb.identify.identifyAddress.validateMailingAddressUSCAN.model.ValidateMailingAddressUSCANAPIResponseList;
import com.pb.identify.manager.IdentifyServiceManager;
import com.pb.identify.oauth.BasicAuthServiceImpl;

public class IdentifyAddressErrorHandlingTestUSCAN {

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
	public void testVaildateMailingAddressUSCANInvalidToken() throws SdkException, JSONException, IOException {
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
			ValidateMailingAddressUSCANAPIResponseList response = identifyAddressService.validateMailingAddressUSCAN(Arrays.asList(address),null);
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
	public void testValidateMailingAddressUSCANInvalidKey() {
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
			ValidateMailingAddressUSCANAPIResponseList response = identifyAddressService.validateMailingAddressUSCAN(Arrays.asList(address),null);
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
