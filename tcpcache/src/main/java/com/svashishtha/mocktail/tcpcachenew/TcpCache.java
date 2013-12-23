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

package com.svashishtha.mocktail.tcpcachenew;

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

    /**
     * Field port
     */
    int port;

    /**
     * Field pleaseStop
     */
    boolean pleaseStop = false;

    private int targetPort;

    private String targetHost;

    private Class<?> clientClass;

    private MocktailMode mocktailMode;

    private String methodName;

    /**
     * Constructor SocketWaiter
     * 
     * @param listenPort
     * @param targetHost
     * @param targetPort
     * @param clientClass
     * @param methodName
     * @param mocktailMode
     */
    public TcpCache(int listenPort, String targetHost, int targetPort,
            Class<?> clientClass, String methodName, MocktailMode mocktailMode) {
        port = listenPort;
        this.targetHost = targetHost;
        this.targetPort = targetPort;
        this.clientClass = clientClass;
        this.methodName = methodName;
        this.mocktailMode = mocktailMode;
        start();
    }

    /**
     * Method run
     */
    public void run() {
        try {
            sSocket = new ServerSocket(port);
            Socket inSocket = sSocket.accept();

            Connection connection = new Connection(inSocket, targetHost,
                    targetPort, port, clientClass.getName(), methodName,
                    mocktailMode);
            connection.start();
            inSocket.close();
        } catch (Exception exp) {
            exp.printStackTrace();
        }
    }

}
