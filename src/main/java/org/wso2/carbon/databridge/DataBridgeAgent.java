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
package org.wso2.carbon.databridge;

import org.apache.log4j.PropertyConfigurator;
import org.wso2.carbon.databridge.agent.AgentHolder;
import org.wso2.carbon.databridge.agent.DataPublisher;
import org.wso2.carbon.databridge.agent.exception.DataEndpointAgentConfigurationException;
import org.wso2.carbon.databridge.agent.exception.DataEndpointAuthenticationException;
import org.wso2.carbon.databridge.agent.exception.DataEndpointConfigurationException;
import org.wso2.carbon.databridge.agent.exception.DataEndpointException;
import org.wso2.carbon.databridge.commons.exception.TransportException;
import org.wso2.carbon.databridge.publishers.AbstractPublisher;
import org.wso2.carbon.databridge.publishers.ExecutionTimePublisher;
import org.wso2.carbon.databridge.publishers.PerMinRequestPublisher;
import org.wso2.carbon.databridge.publishers.RequestPublisher;
import org.wso2.carbon.databridge.publishers.ResponsePublisher;
import org.wso2.carbon.databridge.publishers.RequestPublisherTest;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.google.common.util.concurrent.ThreadFactoryBuilder;


public class DataBridgeAgent {
    private static final int defaultThriftPort = 7611;
    private static final int defaultBinaryPort = 9611;
    private static final ExecutorService executorService = Executors
            .newScheduledThreadPool(
                    3, new ThreadFactoryBuilder()
                            .setNameFormat("Publisher-scheduler-thread-%d")
                            .build()
            );

    public static void main(String[] args) throws DataEndpointAuthenticationException,
            DataEndpointAgentConfigurationException,
            TransportException,
            DataEndpointException,
            DataEndpointConfigurationException,
            FileNotFoundException,
            SocketException,
            UnknownHostException {

        String log4jConfPath = "./src/main/resources/log4j.properties";
        PropertyConfigurator.configure(log4jConfPath);

        System.out.println("Starting Agent");
        String currentDir = System.getProperty("user.dir");
        System.setProperty("javax.net.ssl.trustStore", currentDir + "/src/main/resources/client-truststore.jks");
        System.setProperty("javax.net.ssl.trustStorePassword", "wso2carbon");

        AgentHolder.setConfigPath(getDataAgentConfigPath());

        int count;
        if (args[0] == null || args[0].isEmpty() || args[0].equals("count")) {
            count = 1000;
        } else {
            count = Integer.parseInt(args[0]);
        }

        int delay;
        if (args[1] == null || args[1].isEmpty() || args[1].equals("delay")) {
            delay = 0;
        } else {
            delay = Integer.parseInt(args[1]);
        }

        String host;
        if (args[2] == null || args[2].isEmpty() || args[2].equals("host")) {
            host = getLocalAddress().getHostAddress();
        } else {
            host = args[2];
        }

        int offset;
        if (args[3] == null || args[3].isEmpty() || args[3].equals("offset")) {
            offset = 0;
        } else {
            offset = Integer.parseInt(args[3]);
        }

        String type = getProperty("type", "Thrift");
        int receiverPort = defaultThriftPort;
        if (type.equals("Binary")) {
            receiverPort = defaultBinaryPort;
        }
        int securePort = receiverPort + 100;
        receiverPort += offset;
        securePort += offset;

        String url = getProperty("url", "tcp://" + host + ":" + receiverPort);
        String authURL = getProperty("authURL", "ssl://" + host + ":" + securePort);
        String username = getProperty("username", "admin");
        String password = getProperty("password", "admin");

        List<AbstractPublisher> publishers = new ArrayList<>();
//        publishers.add(new RequestPublisher(new DataPublisher(type, url, authURL, username, password), count, 1000));
//        publishers.add(new ResponsePublisher(new DataPublisher(type, url, authURL, username, password), count, 1000));
//        publishers.add(new ExecutionTimePublisher(new DataPublisher(type, url, authURL, username, password), count, 1000));
//        publishers.add(new PerMinRequestPublisher(new DataPublisher(type, url, authURL, username, password), count, delay));
        publishers.add(new RequestPublisherTest(new DataPublisher(type, url, authURL, username, password), count, delay));
        for (AbstractPublisher publisher : publishers) {
            executorService.execute(publisher);
        }
        executorService.shutdown();
    }

    private static String getDataAgentConfigPath() {
        File filePath = new File("src" + File.separator + "main" + File.separator + "resources");
        if (!filePath.exists()) {
            filePath = new File("test" + File.separator + "resources");
        }
        if (!filePath.exists()) {
            filePath = new File("resources");
        }
        if (!filePath.exists()) {
            filePath = new File("test" + File.separator + "resources");
        }
        return filePath.getAbsolutePath() + File.separator + "data-agent-conf.xml";
    }

    private static InetAddress getLocalAddress() throws SocketException, UnknownHostException {
        Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
        while (interfaces.hasMoreElements()) {
            NetworkInterface iface = interfaces.nextElement();
            Enumeration<InetAddress> addresses = iface.getInetAddresses();

            while (addresses.hasMoreElements()) {
                InetAddress addr = addresses.nextElement();
                if (addr instanceof Inet4Address && !addr.isLoopbackAddress()) {
                    return addr;
                }
            }
        }
        return InetAddress.getLocalHost();
    }

    private static String getProperty(String name, String def) {
        String result = System.getProperty(name);
        if (result == null || result.length() == 0 || result.equals("")) {
            result = def;
        }
        return result;
    }
}