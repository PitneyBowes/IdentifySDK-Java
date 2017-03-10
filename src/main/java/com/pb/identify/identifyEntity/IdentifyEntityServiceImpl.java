package com.pb.identify.identifyEntity;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;

import org.apache.log4j.Level;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.pb.identify.RequestObserver;
import com.pb.identify.exception.SdkException;
import com.pb.identify.identifyEntity.extractEntities.model.ExtractEntitiesAPIRequest;
import com.pb.identify.identifyEntity.extractEntities.model.ExtractEntitiesAPIResponseList;
import com.pb.identify.identifyEntity.extractEntities.model.Options;
import com.pb.identify.identifyEntity.extractEntities.model.Record;
import com.pb.identify.utils.UrlMaker;
import com.pb.identify.utils.Utility;

public class IdentifyEntityServiceImpl implements IdentifyEntityService {

private static final Logger _LOG = LoggerFactory.getLogger(IdentifyEntityServiceImpl.class);
	
	private static final String extractEntitiesURL = "/identifyentity/v1/rest/extractentities/results.json";
	
	@Override
	public ExtractEntitiesAPIResponseList extractEntities(
			List<Record> records,Options options) throws SdkException {
		
		return processExtractEntitiesRequest(records,options);
	}

	@Override
	public void extractEntitiesAsync(final List<Record> records,final Options options,
			final RequestObserver<ExtractEntitiesAPIResponseList> requestObserver) {
		org.apache.log4j.Logger.getRootLogger().setLevel(Level.OFF);
		final ExecutorService executorService = Executors.newFixedThreadPool(1);
		executorService.submit(new Runnable() {
			@Override
			public void run() {
				try {
					ExtractEntitiesAPIResponseList extractEntitiesAPIResponseList = processExtractEntitiesRequest(records,options);
					
					requestObserver.onSuccess(extractEntitiesAPIResponseList);
				}catch(SdkException e)
				{
					requestObserver.onFailure(e);
				}
				finally{
					executorService.shutdown();					
				}
			}
		});
	}
	
	private ExtractEntitiesAPIResponseList processExtractEntitiesRequest(List<Record> records,Options options) throws SdkException{
		
		UrlMaker urlMaker = UrlMaker.getInstance();
		StringBuilder urlBuilder = new StringBuilder(urlMaker.getAbsoluteUrl(extractEntitiesURL));
		
		ExtractEntitiesAPIRequest request = new ExtractEntitiesAPIRequest();
		
		request.getInputRecord().getRecords().addAll(records);
		if(options!=null)
			request.setOptions(options);
		Gson gson = new Gson();

		_LOG.debug("API URL : " + urlBuilder);
		Entity paramEntity = Entity.entity(gson.toJson(request), MediaType.APPLICATION_JSON_TYPE);
		
		return Utility.processPOSTRequest(paramEntity, urlBuilder.toString(), ExtractEntitiesAPIResponseList.class);
	}

}
