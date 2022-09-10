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

package in.malonus.tcpcache;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import org.slf4j.LoggerFactory;

/**
 * this class handles the pumping of data from the incoming socket to the
 * outgoing socket
 */
class SocketRR {
    private static final org.slf4j.Logger log = LoggerFactory
            .getLogger(SocketRR.class);

    /**
     * Field inSocket
     */
    Socket inputStreamSocket = null;

    /**
     * Field outSocket
     */
    Socket outputStreamSocket = null;

    /**
     * Field in
     */
    InputStream inputStream = null;

    /**
     * Field out
     */
    OutputStream outputStream = null;

    /**
     * Field done
     */
    volatile boolean done = false;

    /**
     * Field type
     */
    String type = null;

    /**
     * Field myConnection
     */
    Connection myConnection = null;

    private String response = "";

    private Configuration config;

    public SocketRR(Connection c, Socket inputStreamSocket,
            InputStream inputStream, Socket outputSocket,
            OutputStream outputStream,

            final String type, Configuration config) {

        this.inputStreamSocket = inputStreamSocket;
        this.inputStream = inputStream;
        this.outputStreamSocket = outputSocket;
        this.outputStream = outputStream;
        this.type = type;
        this.myConnection = c;
        this.config = config;
        start();
    }

    /**
     * Method isDone
     * 
     * @return boolean
     */
    public boolean isDone() {
        return done;
    }

    

    public void start() {
        try {
            byte[] buffer = new byte[4096];
            int saved = 0;
            int len;
            a: for (;;) {

                if (done) {
                    break;
                }

                len = buffer.length;

                // Used to be 1, but if we block it doesn't matter
                // however 1 will break with some servers, including apache
                if (len == 0) {
                    len = buffer.length;
                }
                if (saved + len > buffer.length) {
                    len = buffer.length - saved;
                }
                int len1 = 0;
                while (len1 == 0) {
                    try {
                        len1 = inputStream.read(buffer, saved, len);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        if (done && (saved == 0)) {
                            break a;
                        }
                        len1 = -1;
                        break;
                    }
                }
                len = len1;
                if ((len == -1) && (saved == 0)) {
                    break;
                }
                if (len == -1 || inputStream.available() == 0) {
                    done = true;
                }
                // No matter how we may (or may not) format it, send it
                // on unformatted - we don't want to mess with how its
                // sent to the other side, just how its displayed
                if ((outputStream != null) && (len > 0)) {
                    outputStream.write(buffer, saved, len);
                    response = response + new String(buffer, saved, len);
                    System.out.println("The response is for type:" + type + "<"
                            + response + ">");
                    if (config.isCachingOn()
                            && (response.contains("</soap:Envelope>") || response
                                    .contains("</soapenv:Envelope>"))) {
                        MocktailCache mocktailCache = new MocktailCache(config,
                                response);
                        mocktailCache.saveInMocktailRepository();
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            done = true;
            try {
                if (outputStream != null) {
                    outputStream.flush();
                    if (null != outputStreamSocket) {
                        outputStreamSocket.shutdownOutput();
                    } else {
                        outputStream.close();
                    }
                    outputStream = null;
                }
            } catch (Exception e) {
            }
            try {
                if (inputStream != null) {
                    if (inputStreamSocket != null) {
                        inputStreamSocket.shutdownInput();
                    } else {
                        inputStream.close();
                    }
                    inputStream = null;
                }
            } catch (Exception e) {
            }
            myConnection.wakeUp();
        }
    }
    
 


    public String getPayload() {
        return response;
    }
}
