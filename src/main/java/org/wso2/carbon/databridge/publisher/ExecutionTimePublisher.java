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

import org.wso2.carbon.databridge.agent.DataPublisher;
import org.wso2.carbon.databridge.commons.utils.DataBridgeCommonsUtils;

import java.util.Random;

public class ExecutionTimePublisher extends AbstractPublisher {
    private Random random = new Random();

    public ExecutionTimePublisher(DataPublisher publisher, int eventCount) {
        this.publisher = publisher;
        this.eventCount = eventCount;
        this.streamId = DataBridgeCommonsUtils.generateStreamId("org.wso2.apimgt.statistics.execution.time", "1.0.0");
    }

    private ExecutionTimePublisher() {

    }

    @Override
    public Object[] generateData(int seed) {
        String apiPublisher = "admin";
        String tenantDomain = "tenant.domain." + seed % 20;
        String api = "api" + seed;
        String version = "1.0.0";
        String context = "/" + api + "/" + version;
        String apiVersion = apiPublisher + "--" + api + ":v" + version;

        return new Object[]{
                api,
                apiVersion,
                tenantDomain,
                apiPublisher,
                random.nextInt((seed % 10) + 1),
                context,
                random.nextInt((seed % 2) + 1),
                random.nextInt((seed % 2) + 1),
                random.nextInt((seed % 2) + 1),
                random.nextInt((seed % 2) + 1),
                random.nextInt((seed % 2) + 1),
                random.nextInt((seed % 2) + 1),
                random.nextInt((seed % 2) + 1),
        };

    }
}
