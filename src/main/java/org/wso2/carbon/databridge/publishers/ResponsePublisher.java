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

package org.wso2.carbon.databridge.publishers;

import org.wso2.carbon.databridge.agent.DataPublisher;
import org.wso2.carbon.databridge.commons.utils.DataBridgeCommonsUtils;

import java.util.Random;

public class ResponsePublisher extends AbstractPublisher {
    private Random random = new Random();
    private int[] responseCodes = new int[]{200, 200, 400, 500};

    public ResponsePublisher(DataPublisher publisher, int eventCount, int delay) {
        this.publisher = publisher;
        this.eventCount = eventCount;
        this.delay = delay;
        this.streamId = DataBridgeCommonsUtils.generateStreamId("org.wso2.apimgt.statistics.response", "1.1.0");
    }

    private ResponsePublisher() {

    }

    @Override
    public Object[] generateData(int seed) {
        String consumerKey = "Md7pgErNWgtR6p6Cxlv016kzueI" + seed;
        String username = "admin";
        String applicationName = "app" + seed % 50;
        String applicationId = seed + "";
        String hostName = "Host";
        String apiPublisher = "admin";
        String tenantDomain = "tenant.domain." + seed % 20;
        String api = "api" + seed;
        String version = "1.0.0";
        String resource = "/" + applicationName;
        String resourceTemplate = "/*";
        String method = "GET";
        String clientIp = "127.0.0.1";
        String context = "/" + api + "/" + version;
        String apiVersion = apiPublisher + "--" + api + ":v" + version;
        return new Object[]{
                consumerKey,
                context,
                apiVersion,
                api,
                resource,
                resourceTemplate,
                method,
                version,
                random.nextInt((seed % 4) + 1),
                Long.valueOf(random.nextInt((seed % 4) + 1)),
                Long.valueOf(random.nextInt((seed % 4) + 1)),
                Long.valueOf(random.nextInt((seed % 4) + 1)),
                username,
                Long.valueOf(random.nextInt((seed % 4) + 1)),
                tenantDomain,
                hostName,
                apiPublisher,
                applicationName,
                applicationId,
                false,
                Long.valueOf(random.nextInt((seed % 20) + 1)),
                "http",
                responseCodes[random.nextInt(responseCodes.length)],
                clientIp
        };

    }
}
