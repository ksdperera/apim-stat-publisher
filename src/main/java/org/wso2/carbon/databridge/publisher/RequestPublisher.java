/*
 * Copyright (c)  2017, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 *
 * WSO2 Inc. licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.wso2.carbon.databridge.publisher;

import org.wso2.carbon.apimgt.usage.publisher.dto.DataBridgeRequestPublisherDTO;
import org.wso2.carbon.apimgt.usage.publisher.dto.RequestPublisherDTO;
import org.wso2.carbon.databridge.agent.DataPublisher;
import org.wso2.carbon.databridge.commons.utils.DataBridgeCommonsUtils;

public class RequestPublisher extends AbstractPublisher {
    public RequestPublisher(DataPublisher publisher, int eventCount, int delay) {
        this.publisher = publisher;
        this.eventCount = eventCount;
        this.delay = delay;
        this.streamId = DataBridgeCommonsUtils.generateStreamId("org.wso2.apimgt.statistics.request", "1.1.0");
    }

    private RequestPublisher() {

    }

    @Override
    public Object[] generateData(int seed) {
        String consumerKey = "Md7pgErNWgtR6p6Cxlv016kzueI" + (seed % 30);
        String username = "admin";
        String applicationName = "app" + (seed % 50);
        String applicationId = seed + "";
        String applicationOwner = "admin";
        String tier = "Unlimited";
        String hostName = "Host";
        String userAgent = "Firefox";
        String apiPublisher = "admin";
        String tenantDomain = "tenant.domain." + (seed % 20);
        String api = "api" + seed;
        String version = "1.0.0";
        String resource = "/" + applicationName;
        String resourceTemplate = "/*";
        String method = "GET";
        boolean throttleOutHappened = false;
        long currentTime = System.currentTimeMillis();
        String clientIp = "127.0.0.1";
        String context = "/" + api + "/" + version;
        String apiVersion = apiPublisher + "--" + api + ":v" + version;

        RequestPublisherDTO requestPublisherDTO = new RequestPublisherDTO();
        requestPublisherDTO.setConsumerKey(consumerKey);
        requestPublisherDTO.setContext(context);
        requestPublisherDTO.setApiVersion(apiVersion);
        requestPublisherDTO.setApi(api);
        requestPublisherDTO.setVersion(version);
        requestPublisherDTO.setResourcePath(resource);
        requestPublisherDTO.setResourceTemplate(resourceTemplate);
        requestPublisherDTO.setMethod(method);
        requestPublisherDTO.setRequestTime(currentTime);
        requestPublisherDTO.setUsername(username);
        requestPublisherDTO.setTenantDomain(tenantDomain);
        requestPublisherDTO.setHostName(hostName);
        requestPublisherDTO.setApiPublisher(apiPublisher);
        requestPublisherDTO.setApplicationName(applicationName);
        requestPublisherDTO.setApplicationId(applicationId);
        requestPublisherDTO.setUserAgent(userAgent);
        requestPublisherDTO.setTier(tier);
        requestPublisherDTO.setContinuedOnThrottleOut(throttleOutHappened);
        requestPublisherDTO.setClientIp(clientIp);
        requestPublisherDTO.setApplicationOwner(applicationOwner);

        DataBridgeRequestPublisherDTO dt = new DataBridgeRequestPublisherDTO(requestPublisherDTO);
        return (Object[]) dt.createPayload();
    }
}
