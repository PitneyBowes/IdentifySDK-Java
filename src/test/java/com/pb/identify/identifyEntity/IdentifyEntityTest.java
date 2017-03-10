package com.pb.identify.identifyEntity;

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
import com.pb.identify.identifyEntity.IdentifyEntityService;
import com.pb.identify.identifyEntity.IdentifyEntityServiceImpl;
import com.pb.identify.identifyEntity.extractEntities.model.EntityList;
import com.pb.identify.identifyEntity.extractEntities.model.ExtractEntitiesAPIResponse;
import com.pb.identify.identifyEntity.extractEntities.model.ExtractEntitiesAPIResponseList;
import com.pb.identify.identifyEntity.extractEntities.model.Options;
import com.pb.identify.identifyEntity.extractEntities.model.Record;
import com.pb.identify.manager.IdentifyServiceManager;
import com.pb.identify.oauth.BasicAuthServiceImpl;

/**
 * Test Case for Identify Extract, This test case hits the QA environment to
 * validate SDK and compares responses returned by API and SDK.
 */
public class IdentifyEntityTest {

	private static final Logger _LOG = LoggerFactory.getLogger(IdentifyEntityTest.class);

	private IdentifyServiceManager identifyServiceManager;
	private IdentifyEntityService identifyEntityService;
	private static String TEST_URL = "";
	private static String TEST_TOKEN = "";
	private static final String EXTRACT_ENTITIES_REQUEST_SRC = "\\src\\test\\resources\\extractEntitiesRequest.json";
	private static final String EXTRACT_ENTITIES_BATCH_REQUEST_SRC = "\\src\\test\\resources\\extractEntitiesBatchRequest.json";

	@BeforeClass
	public static void loadProperties() {
		Properties props = new Properties();
		try {
			InputStream fileIn = IdentifyEntityTest.class.getResourceAsStream("/test.properties");
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
		identifyEntityService = new IdentifyEntityServiceImpl();
	}



	private List<Record> buildDummyRecords() {
		
		
		
		Record record1 = new Record();
		record1.setPlainText("The United Nations (UN) is an intergovernmental organization to promote international co-operation. The organization was established on 24 October 1945 after World War II in order to prevent another such conflict. The text of the Declaration by United Nations was drafted by President Franklin Roosevelt, British Prime Minister Winston Churchill, and Roosevelt aide Harry Hopkins, while meeting at the White House on 29 December 1941. Its headquarters are situated at 1st Avenue between 42nd and 48th Street, New York, NY 10017, USA. WebAddress of the organization is www.un.org Phone number for public enquiries is 212-963-4475");
		UserField userField = new UserField();
		userField.setName("name1");
		userField.setValue("value1");
		UserField userField1 = new UserField();
		userField1.setName("name2");
		userField1.setValue("value2");
		record1.setUserFields(Arrays.asList(userField));

	//	Record record2 = new Record();
	//	record2.setPlainText("Contact Pitney Bowes at support@pb.com");


		return Arrays.asList(record1); 

	} 
	
	private Options buildDummyOptions(){
		List<EntityList> set = new ArrayList<EntityList>(Arrays.asList(EntityList.Person,EntityList.Location,EntityList.Address,EntityList.Date,EntityList.Organization,EntityList.Phone,EntityList.WebAddress,EntityList.ZipCode));
		Options options = new Options(set);
		
		return options;
	}

	private static RequestObserver extractEntitiesCallBack(final List<ExtractEntitiesAPIResponse> responses,
			final SdkException[] sdkException) {
		return new RequestObserver<ExtractEntitiesAPIResponseList>() {
			@Override
			public void onSuccess(ExtractEntitiesAPIResponseList responseList) {
				responses.addAll(responseList.getResponses());

			}

			@Override
			public void onFailure(SdkException e) {
				sdkException[0] = e;
			}
		};
	}

	@Test
	public void testWhetherSDKAndAPIResponsesMatchForExtractEntities() throws SdkException, JSONException, IOException {

		String jsonResponseFromAPI = TestUtility.getJSONResponseFromApiForPOSTRequest(EXTRACT_ENTITIES_REQUEST_SRC, TEST_URL,
				"/identifyentity/v1/rest/extractentities/results.json");
		System.out.println("api:"+jsonResponseFromAPI);
		List<EntityList> set = new ArrayList<EntityList>(Arrays.asList(EntityList.Person,EntityList.Location,EntityList.Address,EntityList.Date,EntityList.Organization,EntityList.Phone,EntityList.WebAddress,EntityList.ZipCode));
		Options options = new Options(set);
		Record record = new Record();
		record.setPlainText("The United Nations (UN) is an intergovernmental organization to promote international co-operation. The organization was established on 24 October 1945 after World War II in order to prevent another such conflict. The text of the Declaration by United Nations was drafted by President Franklin Roosevelt, British Prime Minister Winston Churchill, and Roosevelt aide Harry Hopkins, while meeting at the White House on 29 December 1941. Its headquarters are situated at 1st Avenue between 42nd and 48th Street, New York, NY 10017, USA. WebAddress of the organization is www.un.org Phone number for public enquiries is 212-963-4475");
		
		UserField userField1 = new UserField();
		userField1.setName("name1");
		userField1.setValue("value1");
		UserField userField2 = new UserField();
		userField2.setName("name2");
		userField2.setValue("value2");
		record.setUserFields(Arrays.asList(userField1,userField2));
		
		
		
		ExtractEntitiesAPIResponseList response = identifyEntityService.extractEntities(Arrays.asList(record),options);
		Gson gson = new GsonBuilder().create();
		String jsonResponseFromSDK = gson.toJson(response);
		System.out.println("sdk:"+jsonResponseFromSDK);
		JSONAssert.assertEquals(jsonResponseFromAPI, jsonResponseFromSDK, JSONCompareMode.STRICT);

	}

	@Test
	public void testWhetherSDKAndAPIResponsesMatchForExtractEntitiesAsync()
			throws SdkException, JSONException, IOException, InterruptedException {

		String jsonResponseFromAPI = TestUtility.getJSONResponseFromApiForPOSTRequest(EXTRACT_ENTITIES_REQUEST_SRC, TEST_URL,
				"/identifyentity/v1/rest/extractentities/results.json");

		List<EntityList> set = new ArrayList<EntityList>(Arrays.asList(EntityList.Person,EntityList.Location,EntityList.Address,EntityList.Date,EntityList.Organization,EntityList.Phone,EntityList.WebAddress,EntityList.ZipCode));
		Options options = new Options(set);
		Record record = new Record();
		record.setPlainText("The United Nations (UN) is an intergovernmental organization to promote international co-operation. The organization was established on 24 October 1945 after World War II in order to prevent another such conflict. The text of the Declaration by United Nations was drafted by President Franklin Roosevelt, British Prime Minister Winston Churchill, and Roosevelt aide Harry Hopkins, while meeting at the White House on 29 December 1941. Its headquarters are situated at 1st Avenue between 42nd and 48th Street, New York, NY 10017, USA. WebAddress of the organization is www.un.org Phone number for public enquiries is 212-963-4475");
		
		UserField userField1 = new UserField();
		userField1.setName("name1");
		userField1.setValue("value1");
		UserField userField2 = new UserField();
		userField2.setName("name2");
		userField2.setValue("value2");
		record.setUserFields(Arrays.asList(userField1));

		final ExtractEntitiesAPIResponseList extractEntitiesResponse = new ExtractEntitiesAPIResponseList();
		identifyEntityService.extractEntitiesAsync(Arrays.asList(record),options,extractEntitiesCallBack(extractEntitiesResponse.getResponses(), null));

		Thread.sleep(5000);

		Gson gson = new GsonBuilder().create();
		String jsonResponseFromSDK = gson.toJson(extractEntitiesResponse);
		JSONAssert.assertEquals(jsonResponseFromAPI, jsonResponseFromSDK, JSONCompareMode.STRICT);

	}

	@Test
	public void testWhetherSDKAndAPIResponsesMatchForExtractEntitiesBatch() throws SdkException, JSONException, IOException {

		String jsonResponseFromAPI = TestUtility.getJSONResponseFromApiForPOSTRequest(EXTRACT_ENTITIES_BATCH_REQUEST_SRC,
				TEST_URL, "/identifyentity/v1/rest/extractentities/results.json");
		ExtractEntitiesAPIResponseList response = identifyEntityService.extractEntities(buildDummyRecords(), buildDummyOptions());
		Gson gson = new GsonBuilder().create();
		String jsonResponseFromSDK = gson.toJson(response);
		JSONAssert.assertEquals(jsonResponseFromAPI, jsonResponseFromSDK, JSONCompareMode.STRICT);

	}

	@Test
	public void testWhetherSDKAndAPIResponsesMatchForExtractEntitiesBatchAsync()
			throws SdkException, JSONException, IOException, InterruptedException {

		String jsonResponseFromAPI = TestUtility.getJSONResponseFromApiForPOSTRequest(EXTRACT_ENTITIES_BATCH_REQUEST_SRC,
				TEST_URL, "/identifyentity/v1/rest/extractentities/results.json");

	//	Options options = new Options(Arrays.asList(EntityList.Person,EntityList.Location));
		final ExtractEntitiesAPIResponseList extractEntitiesResponse = new ExtractEntitiesAPIResponseList();
		identifyEntityService.extractEntitiesAsync(buildDummyRecords(),buildDummyOptions(),extractEntitiesCallBack(extractEntitiesResponse.getResponses(), null));

		Thread.sleep(5000);

		Gson gson = new GsonBuilder().create();
		String jsonResponseFromSDK = gson.toJson(extractEntitiesResponse);
		JSONAssert.assertEquals(jsonResponseFromAPI, jsonResponseFromSDK, JSONCompareMode.STRICT);

	}

	@After
	public void tearDown() throws Exception {
		identifyServiceManager = null;
		identifyEntityService = null;
	}

}