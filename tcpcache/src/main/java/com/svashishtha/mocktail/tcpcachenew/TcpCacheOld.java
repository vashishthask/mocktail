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

import com.svashishtha.mocktail.MocktailMode;

/**
 * Proxy that sniffs and shows HTTP messages and responses, both SOAP and plain
 * HTTP.
 */

public class TcpCacheOld {

	/**
	 * Field DEFAULT_HOST
	 */
	static final String DEFAULT_HOST = "127.0.0.1";


	/**
	 * Constructor
	 * 
	 * @param listenPort
	 * @param targetHost
	 * @param targetPort
	 * @param clientMethodName 
	 */
	public TcpCacheOld(int listenPort, String targetHost, int targetPort, Class<?> clientClass, String clientMethodName, MocktailMode mocktailMode) {
//	    SocketWaiter sw = new SocketWaiter(listenPort, targetHost, targetPort, clientClass.getName(), clientMethodName, mocktailMode);
//	    sw.start();
	}

}
