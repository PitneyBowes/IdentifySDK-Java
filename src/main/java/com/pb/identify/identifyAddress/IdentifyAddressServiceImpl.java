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
import com.pb.identify.identifyAddress.validateMailingAddress.model.ValidateMailingAddressAPIRequest;
import com.pb.identify.identifyAddress.validateMailingAddress.model.ValidateMailingAddressAPIResponseList;
import com.pb.identify.identifyAddress.validateMailingAddressPremium.model.ValidateMailingAddressPremiumAPIRequest;
import com.pb.identify.identifyAddress.validateMailingAddressPremium.model.ValidateMailingAddressPremiumAPIResponseList;
import com.pb.identify.identifyAddress.validateMailingAddressPro.model.ValidateMailingAddressProAPIRequest;
import com.pb.identify.identifyAddress.validateMailingAddressPro.model.ValidateMailingAddressProAPIResponseList;
import com.pb.identify.identifyAddress.validateMailingAddressUSCAN.model.ValidateMailingAddressUSCANAPIRequest;
import com.pb.identify.identifyAddress.validateMailingAddressUSCAN.model.ValidateMailingAddressUSCANAPIResponseList;
import com.pb.identify.identifyAddress.getCityStateProvince.model.GetCityStateProvinceAPIRequest;
import com.pb.identify.identifyAddress.getCityStateProvince.model.GetCityStateProvinceAPIResponseList;
import com.pb.identify.identifyAddress.getPostalCodes.model.GetPostalCodesAPIRequest;
import com.pb.identify.identifyAddress.getPostalCodes.model.GetPostalCodesAPIResponseList;
import com.pb.identify.utils.UrlMaker;
import com.pb.identify.utils.Utility;

public class IdentifyAddressServiceImpl implements IdentifyAddressService {

	private static final Logger _LOG = LoggerFactory.getLogger(IdentifyAddressServiceImpl.class);
	
	private static final String validateMailingAddressURL = "/identifyaddress/v1/rest/validatemailingaddress/results.json";
	private static final String validateMailingAddressProURL = "/identifyaddress/v1/rest/validatemailingaddresspro/results.json";
	private static final String validateMailingAddressPremiumURL = "/identifyaddress/v1/rest/validatemailingaddresspremium/results.json";
	private static final String getCityStateProvinceURL = "/identifyaddress/v1/rest/getcitystateprovince/results.json";
	private static final String getPostalCodesURL = "/identifyaddress/v1/rest/getpostalcodes/results.json";
	private static final String validateMailingAddressUSCANURL = "/identifyaddress/v1/rest/validatemailingaddressuscan/results.json";

	@Override
	public void validateMailingAddressAsync(final List<com.pb.identify.identifyAddress.validateMailingAddress.model.Address> addresses,final com.pb.identify.identifyAddress.validateMailingAddress.model.Options options, final RequestObserver<ValidateMailingAddressAPIResponseList> requestObserver) {
		org.apache.log4j.Logger.getRootLogger().setLevel(Level.OFF);
		final ExecutorService executorService = Executors.newFixedThreadPool(1);
		executorService.submit(new Runnable() {
			@Override
			public void run() {
				try {
					ValidateMailingAddressAPIResponseList validateMailingAddressAPIResponseList = processValidateMailingAddressRequest(addresses,options);

					requestObserver.onSuccess(validateMailingAddressAPIResponseList);

				} catch (SdkException e) {
					requestObserver.onFailure(e);
				}
				
				finally{
					executorService.shutdown();					
				}
			}
		});
	}

	@Override
	public ValidateMailingAddressAPIResponseList validateMailingAddress(List<com.pb.identify.identifyAddress.validateMailingAddress.model.Address> addresses,com.pb.identify.identifyAddress.validateMailingAddress.model.Options options)
			throws SdkException {

		return processValidateMailingAddressRequest(addresses,options);
	}

	
	private ValidateMailingAddressAPIResponseList processValidateMailingAddressRequest(List<com.pb.identify.identifyAddress.validateMailingAddress.model.Address> addresses,com.pb.identify.identifyAddress.validateMailingAddress.model.Options options) throws SdkException {

		UrlMaker urlMaker = UrlMaker.getInstance();
		StringBuilder urlBuilder = new StringBuilder(urlMaker.getAbsoluteUrl(validateMailingAddressURL));

		ValidateMailingAddressAPIRequest request = new ValidateMailingAddressAPIRequest();

		request.getInputAddress().getAddresses().addAll(addresses);
		request.setOptions(options);
		Gson gson = new Gson();

		_LOG.debug("API URL : " + urlBuilder);
		Entity paramEntity = Entity.entity(gson.toJson(request), MediaType.APPLICATION_JSON_TYPE);

		return Utility.processPOSTRequest(paramEntity, urlBuilder.toString(), ValidateMailingAddressAPIResponseList.class);

	}
	
	@Override
	public void validateMailingAddressProAsync(final List<com.pb.identify.identifyAddress.validateMailingAddressPro.model.Address> addresses, final com.pb.identify.identifyAddress.validateMailingAddressPro.model.Options options, final RequestObserver<ValidateMailingAddressProAPIResponseList> requestObserver){
		org.apache.log4j.Logger.getRootLogger().setLevel(Level.OFF);
		final ExecutorService executorService = Executors.newFixedThreadPool(1);
		executorService.submit(new Runnable() {
			@Override
			public void run() {
				try {
					ValidateMailingAddressProAPIResponseList validateMailingAddressProAPIResponseList = processValidateMailingAddressProRequest(addresses,options);

					requestObserver.onSuccess(validateMailingAddressProAPIResponseList);

				} catch (SdkException e) {
					requestObserver.onFailure(e);
				}
				
				finally{
					executorService.shutdown();					
				}
			}
		});
	}
	
	@Override
	public ValidateMailingAddressProAPIResponseList validateMailingAddressPro(List<com.pb.identify.identifyAddress.validateMailingAddressPro.model.Address> addresses, com.pb.identify.identifyAddress.validateMailingAddressPro.model.Options options) 
			throws SdkException{
		return processValidateMailingAddressProRequest(addresses, options);
	}
	
	private ValidateMailingAddressProAPIResponseList processValidateMailingAddressProRequest(List<com.pb.identify.identifyAddress.validateMailingAddressPro.model.Address> addresses, com.pb.identify.identifyAddress.validateMailingAddressPro.model.Options options) throws SdkException{
	
		UrlMaker urlMaker = UrlMaker.getInstance();
		StringBuilder urlBuilder = new StringBuilder(urlMaker.getAbsoluteUrl(validateMailingAddressProURL));
		
		ValidateMailingAddressProAPIRequest request = new ValidateMailingAddressProAPIRequest();
		
		request.getInputAddressPro().getAddresses().addAll(addresses);
		request.setOptions(options);
		Gson gson = new Gson();
		
		_LOG.debug("API URL : " + urlBuilder);
		Entity paramEntity = Entity.entity(gson.toJson(request), MediaType.APPLICATION_JSON_TYPE);
		
		return Utility.processPOSTRequest(paramEntity, urlBuilder.toString(), ValidateMailingAddressProAPIResponseList.class);
	}
	
	@Override
	public void validateMailingAddressPremiumAsync(final List<com.pb.identify.identifyAddress.validateMailingAddressPremium.model.Address> addresses, final com.pb.identify.identifyAddress.validateMailingAddressPremium.model.Options options, final RequestObserver<ValidateMailingAddressPremiumAPIResponseList> requestObserver){
		org.apache.log4j.Logger.getRootLogger().setLevel(Level.OFF);
		final ExecutorService executorService = Executors.newFixedThreadPool(1);
		executorService.submit(new Runnable() {
			@Override
			public void run() {
				try {
					ValidateMailingAddressPremiumAPIResponseList validateMailingAddressPremiumAPIResponseList = processValidateMailingAddressPremiumRequest(addresses,options);

					requestObserver.onSuccess(validateMailingAddressPremiumAPIResponseList);

				} catch (SdkException e) {
					requestObserver.onFailure(e);
				}
				
				finally{
					executorService.shutdown();					
				}
			}
		});
	}
	
	@Override
	public ValidateMailingAddressPremiumAPIResponseList validateMailingAddressPremium(List<com.pb.identify.identifyAddress.validateMailingAddressPremium.model.Address> addresses, com.pb.identify.identifyAddress.validateMailingAddressPremium.model.Options options) 
			throws SdkException{
		return processValidateMailingAddressPremiumRequest(addresses, options);
	}
	
	private ValidateMailingAddressPremiumAPIResponseList processValidateMailingAddressPremiumRequest(List<com.pb.identify.identifyAddress.validateMailingAddressPremium.model.Address> addresses, com.pb.identify.identifyAddress.validateMailingAddressPremium.model.Options options) throws SdkException{
	
		UrlMaker urlMaker = UrlMaker.getInstance();
		StringBuilder urlBuilder = new StringBuilder(urlMaker.getAbsoluteUrl(validateMailingAddressPremiumURL));
		
		ValidateMailingAddressPremiumAPIRequest request = new ValidateMailingAddressPremiumAPIRequest();
		
		request.getInputAddressPremium().getAddresses().addAll(addresses);
		request.setOptions(options);
		Gson gson = new Gson();
		
		_LOG.debug("API URL : " + urlBuilder);
		Entity paramEntity = Entity.entity(gson.toJson(request), MediaType.APPLICATION_JSON_TYPE);
		
		return Utility.processPOSTRequest(paramEntity, urlBuilder.toString(), ValidateMailingAddressPremiumAPIResponseList.class);
	}
	
	@Override
	public void getCityStateProvinceAsync(final List<com.pb.identify.identifyAddress.getCityStateProvince.model.Record> records, final com.pb.identify.identifyAddress.getCityStateProvince.model.Options options, final RequestObserver<GetCityStateProvinceAPIResponseList> requestObserver){
		org.apache.log4j.Logger.getRootLogger().setLevel(Level.OFF);
		final ExecutorService executorService = Executors.newFixedThreadPool(1);
		executorService.submit(new Runnable() {
			@Override
			public void run() {
				try {
					GetCityStateProvinceAPIResponseList getCityStateProvinceAPIResponseList = processGetCityStateProvinceRequest(records,options);

					requestObserver.onSuccess(getCityStateProvinceAPIResponseList);

				} catch (SdkException e) {
					requestObserver.onFailure(e);
				}
				
				finally{
					executorService.shutdown();					
				}
			}
		});
	}
	
	@Override
	public GetCityStateProvinceAPIResponseList getCityStateProvince(List<com.pb.identify.identifyAddress.getCityStateProvince.model.Record> records, com.pb.identify.identifyAddress.getCityStateProvince.model.Options options) 
			throws SdkException{
		return processGetCityStateProvinceRequest(records, options);
	}
	
	private GetCityStateProvinceAPIResponseList processGetCityStateProvinceRequest(List<com.pb.identify.identifyAddress.getCityStateProvince.model.Record> records, com.pb.identify.identifyAddress.getCityStateProvince.model.Options options) throws SdkException{
	
		UrlMaker urlMaker = UrlMaker.getInstance();
		StringBuilder urlBuilder = new StringBuilder(urlMaker.getAbsoluteUrl(getCityStateProvinceURL));
		
		GetCityStateProvinceAPIRequest request = new GetCityStateProvinceAPIRequest();
		
		request.getInputRecord().getRecords().addAll(records);
		request.setOptions(options);
		Gson gson = new Gson();
		
		_LOG.debug("API URL : " + urlBuilder);
		Entity paramEntity = Entity.entity(gson.toJson(request), MediaType.APPLICATION_JSON_TYPE);
		
		return Utility.processPOSTRequest(paramEntity, urlBuilder.toString(), GetCityStateProvinceAPIResponseList.class);
	}
	
	@Override
	public void getPostalCodesAsync(final List<com.pb.identify.identifyAddress.getPostalCodes.model.Record> records, final com.pb.identify.identifyAddress.getPostalCodes.model.Options options, final RequestObserver<GetPostalCodesAPIResponseList> requestObserver){
		org.apache.log4j.Logger.getRootLogger().setLevel(Level.OFF);
		final ExecutorService executorService = Executors.newFixedThreadPool(1);
		executorService.submit(new Runnable() {
			@Override
			public void run() {
				try {
					GetPostalCodesAPIResponseList getPostalCodesAPIResponseList = processGetPostalCodesRequest(records,options);

					requestObserver.onSuccess(getPostalCodesAPIResponseList);

				} catch (SdkException e) {
					requestObserver.onFailure(e);
				}
				
				finally{
					executorService.shutdown();					
				}
			}
		});
	}
	
	@Override
	public GetPostalCodesAPIResponseList getPostalCodes(List<com.pb.identify.identifyAddress.getPostalCodes.model.Record> records, com.pb.identify.identifyAddress.getPostalCodes.model.Options options) 
			throws SdkException{
		return processGetPostalCodesRequest(records, options);
	}
	
	private GetPostalCodesAPIResponseList processGetPostalCodesRequest(List<com.pb.identify.identifyAddress.getPostalCodes.model.Record> records, com.pb.identify.identifyAddress.getPostalCodes.model.Options options) throws SdkException{
	
		UrlMaker urlMaker = UrlMaker.getInstance();
		StringBuilder urlBuilder = new StringBuilder(urlMaker.getAbsoluteUrl(getPostalCodesURL));
		
		GetPostalCodesAPIRequest request = new GetPostalCodesAPIRequest();
		
		request.getInputRecord().getRecords().addAll(records);
		request.setOptions(options);
		Gson gson = new Gson();
		
		_LOG.debug("API URL : " + urlBuilder);
		Entity paramEntity = Entity.entity(gson.toJson(request), MediaType.APPLICATION_JSON_TYPE);
		
		return Utility.processPOSTRequest(paramEntity, urlBuilder.toString(), GetPostalCodesAPIResponseList.class);
	}
	
	@Override
	public void validateMailingAddressUSCANAsync(final List<com.pb.identify.identifyAddress.validateMailingAddressUSCAN.model.Address> addresses, final com.pb.identify.identifyAddress.validateMailingAddressUSCAN.model.Options options, final RequestObserver<ValidateMailingAddressUSCANAPIResponseList> requestObserver){
		org.apache.log4j.Logger.getRootLogger().setLevel(Level.OFF);
		final ExecutorService executorService = Executors.newFixedThreadPool(1);
		executorService.submit(new Runnable() {
			@Override
			public void run() {
				try {
					ValidateMailingAddressUSCANAPIResponseList validateMailingAddressUSCANAPIResponseList = processValidateMailingAddressUSCANRequest(addresses,options);

					requestObserver.onSuccess(validateMailingAddressUSCANAPIResponseList);

				} catch (SdkException e) {
					requestObserver.onFailure(e);
				}
				
				finally{
					executorService.shutdown();					
				}
			}
		});
	}
	
	@Override
	public ValidateMailingAddressUSCANAPIResponseList validateMailingAddressUSCAN(List<com.pb.identify.identifyAddress.validateMailingAddressUSCAN.model.Address> addresses, com.pb.identify.identifyAddress.validateMailingAddressUSCAN.model.Options options) 
			throws SdkException{
		return processValidateMailingAddressUSCANRequest(addresses, options);
	}
	
	private ValidateMailingAddressUSCANAPIResponseList processValidateMailingAddressUSCANRequest(List<com.pb.identify.identifyAddress.validateMailingAddressUSCAN.model.Address> addresses, com.pb.identify.identifyAddress.validateMailingAddressUSCAN.model.Options options) throws SdkException{
	
		UrlMaker urlMaker = UrlMaker.getInstance();
		StringBuilder urlBuilder = new StringBuilder(urlMaker.getAbsoluteUrl(validateMailingAddressUSCANURL));
		
		ValidateMailingAddressUSCANAPIRequest request = new ValidateMailingAddressUSCANAPIRequest();
		
		request.getInputAddressUSCAN().getAddresses().addAll(addresses);
		request.setOptions(options);
		Gson gson = new Gson();
		
		_LOG.debug("API URL : " + urlBuilder);
		Entity paramEntity = Entity.entity(gson.toJson(request), MediaType.APPLICATION_JSON_TYPE);
		
		return Utility.processPOSTRequest(paramEntity, urlBuilder.toString(), ValidateMailingAddressUSCANAPIResponseList.class);
	}


}