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

public class PerMinRequestPublisher extends AbstractPublisher {
    public PerMinRequestPublisher(DataPublisher publisher, int eventCount, int delay) {
        this.publisher = publisher;
        this.eventCount = eventCount;
        this.delay = delay;
        this.streamId = DataBridgeCommonsUtils.generateStreamId("org.wso2.apimgt.statistics.perMinuteRequest", "1.0.0");
    }

    private PerMinRequestPublisher() {

    }

    @Override
    public Object[] generateData(int seed) {
        return new Object[]{
                2017,
                5,
                22,
                23,
                59,
                "fdk8OjTViNTalMc5B8d1KscZt4ga",
                "/api/device-mgt/android/v1.0/devices/1.0.0",
                "apiclouduser_sec--Android Device Management:v1.0.0",
                "Android Device Management",
                "1.0.0",
                1495522747000L,
                "namiraux@lives1.fr@lives1",
                "gateway.api.cloud.wso2.com",
                "apiclouduser_sec",
                1L,
                "/{id}/pending-operations",
                "PUT",
                "cdmf_android_43d16c80c44bf600",
                "carbon.super",
                "Synapse-PT-HttpComponents-NIO",
                "/android/v1.0/devices/1.0.0/43d16c80c44bf600/pending-operations",
                1,
                "10709",
                "Unlimited",
                false,
                "37.171.239.145",
                "namiraux@lives1.fr@lives1",
                System.currentTimeMillis()
        };
    }
}
