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
package com.pb.identify.oauth;

import com.pb.identify.oauth.BaseOAuthService;
import com.pb.identify.oauth.OAuthService;
import com.pb.identify.utils.UrlMaker;
import com.pb.identify.utils.UrlMaker.UrlStrategy;


/**
 * This class is used for returning token for Basic Auth. 
 *
 * 
 */
public class BasicAuthServiceImpl extends OAuthService implements BaseOAuthService {

    
	private String authToken;
	
	/**
	 * @param token
	 * @param url
	 */
	public BasicAuthServiceImpl(String token, final String url) {
		authToken = token;
		
		UrlMaker.getInstance().setUrlStrategy(new UrlStrategy(){
			private String BASE_URL = url;
		});
		
	}
    @Override
    public String getAuthenticationToken() {
    	 setAuthToken(authToken);
    	 return authToken;
    }

    @Override
    public void invalidateAuthenticationToken() {

       
    }

}
