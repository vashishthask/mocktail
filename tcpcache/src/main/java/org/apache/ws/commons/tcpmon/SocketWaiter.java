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
 * wait for incoming connections, spawn a connection thread when
 * stuff comes in.
 */
class SocketWaiter extends Thread {

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

private String className;

private MocktailMode mocktailMode;

private String methodName;

private Connection connection;

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
   public SocketWaiter(Listener l, int p, String targetHost, int targetPort, String className, String methodName, MocktailMode mocktailMode) {
       port = p;
       this.targetHost = targetHost;
       this.targetPort = targetPort;
	this.className = className;
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
           for (; ;) {
               Socket inSocket = sSocket.accept();
               if (pleaseStop) {
                   break;
               }
               connection = new Connection(inSocket,  targetHost, targetPort, port, className, methodName, mocktailMode);
               inSocket = null;
           }
       } catch (Exception exp) {
    	   exp.printStackTrace();
       }
       System.out.println("RUN DONE");
   }

   /**
    * force a halt by connecting to self and then closing the server socket
    */
   public void halt() {
       try {
    	   System.err.println("SocketWaiter.halt() called");
    	   connection.halt();
           pleaseStop = true;
           new Socket("127.0.0.1", port);
           if (sSocket != null) {
               sSocket.close();
           }
       } catch (Exception e) {
           e.printStackTrace();
       }
   }
}
