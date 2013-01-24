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

package org.apache.ws.commons.tcpmon;

import java.net.ServerSocket;
import java.net.Socket;

import org.mocktail.xml.domain.MocktailMode;

/**
 * this is one of the tabbed panels that acts as the actual proxy
 */
class Listener {

	/**
	 * Field inputSocket
	 */
	public Socket inputSocket = null;

	/**
	 * Field outputSocket
	 */
	public Socket outputSocket = null;

	/**
	 * Field sSocket
	 */
	public ServerSocket sSocket = null;

	/**
	 * Field sw
	 */
	public SocketWaiter sw = null;

	/**
	 * Field HTTPProxyHost
	 */
	public String HTTPProxyHost = null;

	/**
	 * Field HTTPProxyPort
	 */
	public int HTTPProxyPort = 80;

	/**
	 * Field delayBytes
	 */
	public int delayBytes = 0;

	/**
	 * Field delayTime
	 */
	public int delayTime = 0;

	private int listenPort;

	private String targetHost;

	private int targetPort;

	private MocktailMode mocktailMode;

	private String className;

	private String methodName;

	/**
	 * create a listener
	 * 
	 * @param _notebook
	 * @param name
	 * @param listenPort
	 * @param host
	 * @param targetPort
	 * @param mocktailMode 
	 * @param className 
	 * @param methodName 
	 * @param isProxy
	 * @param slowLink
	 *            optional reference to a slow connection
	 */
	public Listener(int listenPort, String targetHost, int targetPort,
			String className, String methodName, MocktailMode mocktailMode, boolean isProxy) {
		this.listenPort = listenPort;
		this.targetHost = targetHost;
		this.targetPort = targetPort;
		this.className = className;
		this.methodName = methodName;
		this.mocktailMode = mocktailMode;
		start();
	}

	public void start() {
		sw = new SocketWaiter(this, listenPort, targetHost, targetPort, className, methodName, mocktailMode);
	}
	
	/**
     * Method stop
     */
    public void halt() {
    	System.err.println("Listener.halt() called");
        try {
            sw.halt();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
