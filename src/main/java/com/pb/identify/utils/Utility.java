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
package com.pb.identify.utils;

import java.net.URLEncoder;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.pb.identify.RequestObserver;
import com.pb.identify.common.model.ErrorInfo;
import com.pb.identify.common.model.SdkInternalError;
import com.pb.identify.exception.SdkException;
import com.pb.identify.oauth.OAuthFactory;

/**
 * This is a utility class with common methods.
 */
public class Utility {

    private static final Logger _LOG = LoggerFactory.getLogger(Utility.class);

    public static <T> void processAPIRequestAsync(final String url, final Class<T> responseClass, final RequestObserver<T> requestObserver) {

        final ExecutorService executorService = Executors.newFixedThreadPool(1);
        executorService.submit(new Runnable() {
            @Override
            public void run() {
                try {
                    T response = processAPIRequest(url, responseClass);
                    requestObserver.onSuccess(response);
                } catch (SdkException e) {
                    requestObserver.onFailure(e);
                }finally{
					executorService.shutdown();					
				}
            }
        });
    }

    public static <T> T processAPIRequest(String url, Class<T> responseClass) throws SdkException {
        SdkException exception;
        try {
            String accessToken = OAuthFactory.getOAuthService().getAuthenticationToken();
            Client client = ClientBuilder.newClient();
            WebTarget target = client.target(url);
            Response apiResponse = target.request(MediaType.APPLICATION_JSON).header(Constants.AUTH_HEADER, accessToken).buildGet().invoke();
            Gson gson = new Gson();
            String jsonResponse = apiResponse.readEntity(String.class);
            if (apiResponse.getStatus() == Response.Status.OK.getStatusCode()) {
                _LOG.info("Got a valid response from API", jsonResponse);
                return gson.fromJson(jsonResponse, responseClass);
            } else {
                _LOG.info("Got an error response from API");
                ErrorInfo apiError;

                if (apiResponse.getStatus() != Response.Status.BAD_REQUEST.getStatusCode()) {
                    apiError = new ErrorInfo();
                } else // Only in this case we can expect ErrorInfo
                {
                    apiError = gson.fromJson(jsonResponse, ErrorInfo.class);
                }

                apiError.setHttpStatusCode(apiResponse.getStatus());
                apiError.setReason(apiResponse.getStatusInfo().getReasonPhrase());
                apiError.setJsonResponse(jsonResponse);

                exception = new SdkException(apiError);
                _LOG.error("Error: " + getObjectAsJson(exception.getIdentifyApiErrors()));
            }
        } catch (Exception e) {
            _LOG.error("Unexpected Error: " + e);
            exception = new SdkException(new SdkInternalError(Constants.ERROR_MSG_API_PROCESSING, e));
        }

        throw exception;
    }

    public static String getObjectAsJson(Object object) {
        Gson gson = new Gson();
        return gson.toJson(object);
    }
    
    public static <T> T processPOSTRequest(Entity paramEntity, String url, Class<T> responseClass) throws SdkException {
        SdkException exception;
       
        try {
            String accessToken = OAuthFactory.getOAuthService().getAuthenticationToken();
            Client client = ClientBuilder.newClient();
            WebTarget target = client.target(url);
            Response apiResponse = target.request(MediaType.APPLICATION_JSON).header(Constants.AUTH_HEADER, Constants.BEARER+accessToken).buildPost(paramEntity).invoke();
            Gson gson = new Gson();
            String jsonResponse = apiResponse.readEntity(String.class);
            if (apiResponse.getStatus() == Response.Status.OK.getStatusCode()) {
                _LOG.info("Got a valid response from API", jsonResponse);
                return gson.fromJson(jsonResponse, responseClass);
            } else {
                _LOG.info("Got an error response from API");
                ErrorInfo apiError;

                if (apiResponse.getStatus() != Response.Status.BAD_REQUEST.getStatusCode()) {
                    apiError = new ErrorInfo();
                } else // Only in this case we can expect ErrorInfo
                {
                    apiError = gson.fromJson(jsonResponse, ErrorInfo.class);
                }

                apiError.setHttpStatusCode(apiResponse.getStatus());
                apiError.setReason(apiResponse.getStatusInfo().getReasonPhrase());
                apiError.setJsonResponse(jsonResponse);

                exception = new SdkException(apiError);
                _LOG.error("Error: " + getObjectAsJson(exception.getIdentifyApiErrors()));
            }
        } catch (Exception e) {
            _LOG.error("Unexpected Error: " + e);
            exception = new SdkException(new SdkInternalError(Constants.ERROR_MSG_API_PROCESSING, e));
        }

        throw exception;
    }
    
}