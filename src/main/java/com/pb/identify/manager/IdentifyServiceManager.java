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
package com.pb.identify.manager;

import org.apache.log4j.Level;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pb.identify.identifyAddress.IdentifyAddressService;
import com.pb.identify.identifyAddress.IdentifyAddressServiceImpl;
import com.pb.identify.identifyEmail.IdentifyEmailService;
import com.pb.identify.identifyEmail.IdentifyEmailServiceImpl;
import com.pb.identify.identifyEntity.IdentifyEntityService;
import com.pb.identify.identifyEntity.IdentifyEntityServiceImpl;
import com.pb.identify.identifyRisk.IdentifyRiskService;
import com.pb.identify.identifyRisk.IdentifyRiskServiceImpl;
import com.pb.identify.oauth.BaseOAuthService;
import com.pb.identify.oauth.BaseOAuthServiceImpl;
import com.pb.identify.oauth.OAuthFactory;
import com.pb.identify.oauth.OAuthService;

/**
 * IdentifyServiceManager class is responsible for providing entry point all IDENTIFY specific
 * interfaces.It provides singleton object for this class
 * 
 */
public class IdentifyServiceManager {
	private static final Logger _LOG = LoggerFactory.getLogger(IdentifyServiceManager.class);

	private static IdentifyServiceManager _IdentifyServiceManager = null;

	private IdentifyServiceManager() {

	}
	
	/**
	 * @param baseOAuthService Instance of baseOAuthService implementation
	 * @return {@link IdentifyServiceManager}
	 */
	public static final IdentifyServiceManager getInstance(BaseOAuthService baseOAuthService) {
		org.apache.log4j.Logger.getRootLogger().setLevel(Level.OFF);
		if (_IdentifyServiceManager == null) {
			 OAuthFactory.setInstance(baseOAuthService);
			_IdentifyServiceManager = new IdentifyServiceManager();
			_LOG.trace("IDENTIFY Service instance has been created");
		}
		_LOG.trace("IDENTIFY Service instance has been already created");
		return _IdentifyServiceManager;
	}
	
	/**
	 * Method to initialize IDENTIFY interfaces with user login credentials.
	 * @param consumerKey Identify API user key
	 * @param consumerSecretKey Identify API secret key
	 * @return {@link IdentifyServiceManager}
	 */
	public static final IdentifyServiceManager getInstance(String consumerKey, String consumerSecretKey ) {
		org.apache.log4j.Logger.getRootLogger().setLevel(Level.OFF);
		if (_IdentifyServiceManager == null) {
			 OAuthFactory.setInstance(new BaseOAuthServiceImpl(consumerKey, consumerSecretKey));
            _IdentifyServiceManager = new IdentifyServiceManager();
            _LOG.trace("IDENTIFY Service instance has been created");
		}
		_LOG.trace("IDENTIFY Service instance has been already created");
		return _IdentifyServiceManager;

	}
	
	/**
	 * Method to initialize IDENTIFY services with a user supplied token.
	 * @param token OAuth token generated using key and secret
	 * @return {@link IdentifyServiceManager}
	 */
	public static final IdentifyServiceManager getInstance(String token) {
		if (_IdentifyServiceManager == null) {
			 OAuthFactory.setInstance(new BaseOAuthServiceImpl(token));
			_IdentifyServiceManager = new IdentifyServiceManager();
			_LOG.trace("IDENTIFY Service instance has been created");
		}
		_LOG.trace("IDENTIFY Service instance has been already created");
		return _IdentifyServiceManager;

	}

	/**
	 * OAuth Service APIs Handler
	 * 
	 * @return an instance of BaseOAuthService
	 */
	public BaseOAuthService getBaseAuthService() {
		return new OAuthService();
	}

	/**
	 * IdentifyAddress Service APIs Handler
	 * 
	 * @return an instance of IdentifyAddressService
	 */
	public IdentifyAddressService getIdentifyAddressService(){
		return  new IdentifyAddressServiceImpl();
	}
	
	/**
	 * IdentifyRisk Service APIs Handler
	 * 
	 * @return an instance of IdentifyRiskService
	 */
	public IdentifyRiskService getIdentifyRiskService(){
		return  new IdentifyRiskServiceImpl();
	}

	public IdentifyEmailService getIdentifyEmailService(){
		return new IdentifyEmailServiceImpl();
	}
	
	/**
	 * IdentifyExtract Service APIs Handler
	 * 
	 * @return an instance of IdentifyRiskService
	 */
	public IdentifyEntityService getIdentifyEntityService(){
		return  new IdentifyEntityServiceImpl();
	}
	
	/**
	 * To invalidate the IDENTIFY static reference
	 */
	public void invalidateIdentifyServiceManagerInstance() {
		if (_IdentifyServiceManager != null) {
			_IdentifyServiceManager = null;
			_LOG.trace("IDENTIFY instance has been invalidated");
		}
	}

}
