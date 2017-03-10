package com.pb.identify.identifyEntity;

import java.util.List;

import com.pb.identify.RequestObserver;
import com.pb.identify.exception.SdkException;
import com.pb.identify.identifyEntity.extractEntities.model.ExtractEntitiesAPIResponseList;
import com.pb.identify.identifyEntity.extractEntities.model.Options;
import com.pb.identify.identifyEntity.extractEntities.model.Record;

public interface IdentifyEntityService {

	/**
	 * This service provides functionality that takes plain text and extracts entities from it such as names and addresses.
	 * 
	 * @param records List of records to match
	 * @param options List of options
	 * @return ExtractEntitiesAPIResponseList
	 * @throws SdkException
	 */
	public ExtractEntitiesAPIResponseList extractEntities(List<Record> records, Options options) 
			throws SdkException;
	
	/**
	 * This service provides asynchronous functionality that takes plain text and extracts entities from it such as names and addresses.
	 * 
	 * @param records List of records to match
	 * @param options List of options
	 * @param requestObserver
	 */
	public void extractEntitiesAsync(List<Record> records, Options options,
			RequestObserver<ExtractEntitiesAPIResponseList> requestObserver);
}
