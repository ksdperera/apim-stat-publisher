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
import org.wso2.carbon.databridge.agent.exception.DataEndpointException;
import org.wso2.carbon.databridge.commons.Event;

public abstract class AbstractPublisher implements Runnable {
    DataPublisher publisher = null;
    String streamId = null;
    int eventCount = 0;

    @Override
    public void run() {
        try {
            for (int i = 0; i < eventCount; i++) {
                if (publisher != null && streamId != null && !streamId.isEmpty()) {
                    Event event = new Event(streamId, System.currentTimeMillis(), new Object[]{"external"}, null, generateData(i));
                    publisher.publish(event);
                    System.out.println("Event published : " + event);
                    Thread.sleep(10);
                }
            }
        } catch (Exception e) {
            System.out.println("An error occurred while sending events to : " + streamId);
        } finally {
            try {
                if (publisher != null) {
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        // do nothing
                    }
                    publisher.shutdown();
                }
            } catch (DataEndpointException e) {
                System.out.println("An error occurred while trying to shutdown publisher for : " + streamId);
            }
        }
    }

    public abstract Object[] generateData(int seed);
}
