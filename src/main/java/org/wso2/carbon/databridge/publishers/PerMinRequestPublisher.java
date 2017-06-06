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
                1,
                1,
                1,
                1,
                1,
                "a",
                "a",
                "apiclouduser_sec--Android Device Management:v1.0.0",
                "a",
                "a",
                1L,
                "a",
                "a",
                "a",
                1L,
                "a",
                "a",
                "a",
                "carbon.super",
                "a",
                "a",
                1,
                "a",
                "a",
                false,
                "a",
                "a",
                System.currentTimeMillis()
        };
    }
}
