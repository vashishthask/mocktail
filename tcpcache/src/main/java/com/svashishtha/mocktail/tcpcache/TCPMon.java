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

import java.util.ResourceBundle;

import com.svashishtha.mocktail.MocktailMode;

/**
 * Proxy that sniffs and shows HTTP messages and responses, both SOAP and plain
 * HTTP.
 */

public class TCPMon {

	/**
	 * Field DEFAULT_HOST
	 */
	static final String DEFAULT_HOST = "127.0.0.1";

	private Listener l;

	/**
	 * Constructor
	 * 
	 * @param listenPort
	 * @param targetHost
	 * @param targetPort
	 * @param methodName 
	 */
	public TCPMon(int listenPort, String targetHost, int targetPort, Class<?> class1, String methodName, MocktailMode mocktailMode) {
		if (listenPort != 0) {
			if (targetHost == null) {
				l = new Listener(listenPort, targetHost, targetPort, class1, methodName,mocktailMode,  true);
			} else {
				l = new Listener(listenPort, targetHost, targetPort, class1, methodName,mocktailMode, false);
			}
		}
	}

	private static ResourceBundle messages = null;

	/**
	 * Get the message with the given key. There are no arguments for this
	 * message.
	 * 
	 * @param key
	 * @param defaultMsg
	 * @return string
	 */
	public static String getMessage(String key, String defaultMsg) {
		try {
			if (messages == null) {
				initializeMessages();
			}
			return messages.getString(key);
		} catch (Throwable t) {

			// If there is any problem whatsoever getting the internationalized
			// message, return the default.
			return defaultMsg;
		}
	}

	/**
	 * Load the resource bundle messages from the properties file. This is ONLY
	 * done when it is needed. If no messages are printed (for example, only
	 * Wsdl2java is being run in non- verbose mode) then there is no need to
	 * read the properties file.
	 */
	private static void initializeMessages() {
		messages = ResourceBundle
				.getBundle("org.apache.ws.commons.tcpmon.tcpmon");
	}

	public void halt() {
		l.halt();
	}

}
