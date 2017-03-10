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
package com.pb.identify.common.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents Error received from Identify API call.
 */
public class ErrorInfo {

    private List<ErrorCode> errors;
    private int httpStatusCode;
    private String reason;
    private String jsonResponse;

    /**
     * @return List of ErrorCode
     * This is the error information from the Identify APIs. This gets populated when a bad request is fired.
     */
    public List<ErrorCode> getErrors() {
        if (errors == null) {
            errors = new ArrayList<ErrorCode>();
        }
        return this.errors;
    }

    public void setErrors(List<ErrorCode> errors) {
        this.errors = errors;
    }

    /**
     * @return httpStatusCode
     * This is the http status code returned from the call.
     */
    public int getHttpStatusCode() {
        return httpStatusCode;
    }

    public void setHttpStatusCode(int httpStatusCode) {
        this.httpStatusCode = httpStatusCode;
    }

    /**
     *  @return reason
     * This represents the verbal description for the status code.
     */
    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    /**
     *  @return jsonResponse
     * This represents the raw json response from the server.
     */
    public String getJsonResponse() {
        return jsonResponse;
    }

    public void setJsonResponse(String jsonResponse) {
        this.jsonResponse = jsonResponse;
    }
}
