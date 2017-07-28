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
import com.pb.identify.identifyAddress.validateMailingAddressUSCAN.model.Address;
import com.pb.identify.identifyAddress.validateMailingAddressUSCAN.model.Options;
import com.pb.identify.identifyAddress.validateMailingAddressUSCAN.model.ValidateMailingAddressUSCANAPIResponse;
import com.pb.identify.identifyAddress.validateMailingAddressUSCAN.model.ValidateMailingAddressUSCANAPIResponseList;
import com.pb.identify.manager.IdentifyServiceManager;
import com.pb.identify.oauth.BasicAuthServiceImpl;

/**
 * Test Case for Identify Address SDK, This test case hits the QA environment to
 * validate SDK and compares responses returned by API and SDK for USCAN Service.
 */
public class IdentifyAddressTestUSCAN {

	private static final Logger _LOG = LoggerFactory.getLogger(IdentifyAddressTest.class);

	private IdentifyServiceManager identifyServiceManager;
	private IdentifyAddressService identifyAddressService;
	private static String TEST_URL = "";
	private static String TEST_TOKEN = "";
	private static final String VALIDATE_MAILING_ADDRESS_USCAN_REQUEST_SRC = "\\src\\test\\resources\\validateMailingAddressUSCANRequest.json";
	private static final String VALIDATE_MAILING_ADDRESS_USCAN_BATCH_REQUEST_SRC = "\\src\\test\\resources\\validateMailingAddressUSCANBatchRequest.json";

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

	private List<Address> buildDummyAddressesUSCAN(){

		Options options = new Options();

		Address address1 = new Address();
		address1.setAddressLine1("50 water st.");
		address1.setCity("lee");
		address1.setStateProvince("ma");
		address1.setCountry("US");
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

	private static RequestObserver validateMailingAddressUSCANCallBack(final List<ValidateMailingAddressUSCANAPIResponse> responses,
			final SdkException[] sdkException) {
		return new RequestObserver<ValidateMailingAddressUSCANAPIResponseList>() {
			@Override
			public void onSuccess(ValidateMailingAddressUSCANAPIResponseList responseList) {
				responses.addAll(responseList.getResponses());

			}

			@Override
			public void onFailure(SdkException e) {
				sdkException[0] = e;
			}
		};
	}

	@Test
	public void testWhetherSDKAndAPIResponsesMatchForValidateMailingAddressUSCAN() throws SdkException, JSONException, IOException {

		String jsonResponseFromAPI = TestUtility.getJSONResponseFromApiForPOSTRequest(VALIDATE_MAILING_ADDRESS_USCAN_REQUEST_SRC, TEST_URL,
				"/identifyaddress/v1/rest/validatemailingaddressuscan/results.json");
		Options options = new Options();
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



		ValidateMailingAddressUSCANAPIResponseList response = identifyAddressService.validateMailingAddressUSCAN(Arrays.asList(address),options);
		Gson gson = new GsonBuilder().create();
		String jsonResponseFromSDK = gson.toJson(response);
		System.out.println(jsonResponseFromSDK);
		JSONAssert.assertEquals(jsonResponseFromAPI, jsonResponseFromSDK, JSONCompareMode.STRICT);

	}

	@Test
	public void testWhetherSDKAndAPIResponsesMatchForValidateMailingAddressUSCANAsync()
			throws SdkException, JSONException, IOException, InterruptedException {

		String jsonResponseFromAPI = TestUtility.getJSONResponseFromApiForPOSTRequest(VALIDATE_MAILING_ADDRESS_USCAN_REQUEST_SRC, TEST_URL,
				"/identifyaddress/v1/rest/validatemailingaddressuscan/results.json");

		Options options = new Options();
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

		final ValidateMailingAddressUSCANAPIResponseList validateMailingAddressUSCANResponse = new ValidateMailingAddressUSCANAPIResponseList();
		identifyAddressService.validateMailingAddressUSCANAsync(Arrays.asList(address),options,validateMailingAddressUSCANCallBack(validateMailingAddressUSCANResponse.getResponses(), null));

		Thread.sleep(5000);

		Gson gson = new GsonBuilder().create();
		String jsonResponseFromSDK = gson.toJson(validateMailingAddressUSCANResponse);
		JSONAssert.assertEquals(jsonResponseFromAPI, jsonResponseFromSDK, JSONCompareMode.STRICT);

	}

	@Test
	public void testWhetherSDKAndAPIResponsesMatchForValidateMailingAddressUSCANBatch() throws SdkException, JSONException, IOException {

		String jsonResponseFromAPI = TestUtility.getJSONResponseFromApiForPOSTRequest(VALIDATE_MAILING_ADDRESS_USCAN_BATCH_REQUEST_SRC,
				TEST_URL, "/identifyaddress/v1/rest/validatemailingaddressuscan/results.json");
		ValidateMailingAddressUSCANAPIResponseList response = identifyAddressService.validateMailingAddressUSCAN(buildDummyAddressesUSCAN(), null);
		Gson gson = new GsonBuilder().create();
		String jsonResponseFromSDK = gson.toJson(response);
		JSONAssert.assertEquals(jsonResponseFromAPI, jsonResponseFromSDK, JSONCompareMode.STRICT);

	}

	@Test
	public void testWhetherSDKAndAPIResponsesMatchForValidateMailingAddressUSCANBatchAsync()
			throws SdkException, JSONException, IOException, InterruptedException {

		String jsonResponseFromAPI = TestUtility.getJSONResponseFromApiForPOSTRequest(VALIDATE_MAILING_ADDRESS_USCAN_BATCH_REQUEST_SRC,
				TEST_URL, "/identifyaddress/v1/rest/validatemailingaddressuscan/results.json");

		Options options = new Options();
		final ValidateMailingAddressUSCANAPIResponseList validateMailingAddressUSCANResponse = new ValidateMailingAddressUSCANAPIResponseList();
		identifyAddressService.validateMailingAddressUSCANAsync(buildDummyAddressesUSCAN(),options,validateMailingAddressUSCANCallBack(validateMailingAddressUSCANResponse.getResponses(), null));

		Thread.sleep(5000);

		Gson gson = new GsonBuilder().create();
		String jsonResponseFromSDK = gson.toJson(validateMailingAddressUSCANResponse);
		JSONAssert.assertEquals(jsonResponseFromAPI, jsonResponseFromSDK, JSONCompareMode.STRICT);

	}
	//End of test case for pro

	@After
	public void tearDown() throws Exception {
		identifyServiceManager = null;
		identifyAddressService = null;
	}
}
