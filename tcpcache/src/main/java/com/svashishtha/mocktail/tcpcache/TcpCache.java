/*
 * Copyright 2004,2005 The Apache Software Foundation.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.svashishtha.mocktail.tcpcache;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import org.slf4j.LoggerFactory;

import com.svashishtha.mocktail.MocktailMode;

/**
 * wait for incoming connections, spawn a connection thread when stuff comes in.
 */
public class TcpCache extends Thread {
    private static final org.slf4j.Logger log = LoggerFactory
            .getLogger(TcpCache.class);

    /**
     * Field sSocket
     */
    ServerSocket sSocket = null;

    private Connection connection;

    private Configuration config;

    /**
     * Constructor SocketWaiter
     * 
     * @param l
     * @param p
     * @param targetPort
     * @param targetHost
     * @param mocktailMode
     * @param className
     * @param methodName
     */
    public TcpCache(int localPort, String targetHost, int targetPort, Class<?> testClass,
            String testMethodName, MocktailMode mocktailMode) {
        config = new Configuration(localPort, targetHost, targetPort, testClass, testMethodName, mocktailMode);
    }

    /**
     * Method run
     */
    public void run() {
        try {
            sSocket = new ServerSocket(config.getLocalPort());
            Socket inSocket = sSocket.accept();
            connection = new Connection(inSocket, config);
            connection.join();
            inSocket.close();
        } catch (Exception exp) {
            exp.printStackTrace();
        } finally {
            if (sSocket != null && !sSocket.isClosed()) {
                try {
                    sSocket.close();
                } catch (IOException e) {
                    // ignored
                }
            }
        }
    }

}
