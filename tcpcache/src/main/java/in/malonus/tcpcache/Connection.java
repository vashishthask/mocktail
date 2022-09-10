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

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.net.ConnectException;
import java.net.MalformedURLException;
import java.net.Socket;
import java.net.URL;
import java.net.UnknownHostException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * a connection listens to a single current connection
 */
class Connection extends Thread {
    private static final Logger LOG = LoggerFactory.getLogger(Connection.class);

    /**
     * Field inputText
     */
    Socket inSocket = null;

    /**
     * Field outSocket
     */
    Socket outSocket = null;

    /**
     * Field clientThread
     */
    Thread clientThread = null;

    /**
     * Field serverThread
     */
    Thread serverThread = null;

    /**
     * Field rr1
     */
    SocketRR rr1 = null;

    /**
     * Field rr2
     */
    SocketRR rr2 = null;

    /**
     * Field inputStream
     */
    InputStream inputStream = null;

    /**
     * Field HTTPProxyHost
     */
    String HTTPProxyHost = null;

    /**
     * Field HTTPProxyPort
     */
    int HTTPProxyPort = 80;

    private boolean proxySelected;

    private Configuration config;
    
    private CacheFileInfo cacheFileInfo;

    /**
     * Constructor Connection
     * 
     * @param l
     * @param s
     * @param CacheMode
     * @param objectId
     * @param methodName
     */
    public Connection(Socket s, Configuration config) {
        inSocket = s;
        this.config = config;
        start();
    }

    /**
     * Constructor Connection
     * 
     * @param l
     * @param in
     */
    public Connection(InputStream in) {
        inputStream = in;
        start();
    }

    /**
     * Method run
     */
    public void run() {
        try {
            setProxyConfigIfAvailable();
            cacheFileInfo = new CacheFileInfo(config);
            // FIXME
            InputStream incomingStream = inputStream;
            OutputStream inSocketOutputStream = null;
            if (incomingStream == null) {
                incomingStream = inSocket.getInputStream();
            }
            if (inSocket != null) {
                inSocketOutputStream = inSocket.getOutputStream();
            }
            String bufferedData = null;
            StringBuffer buf = null;
            // FIXME
            if (isProxySelected()) {
                bufferedData = createInputRequestWithProxy(incomingStream);
            } else {
                bufferedData = createInputRequest(incomingStream);
            }
            
            if (CacheMode.PLAYBACK.equals(config.getCacheMode()) && isObjectExistInCache()) {
                writeResponseFromCache(inSocketOutputStream);
            } else {
                try {
                    getResponseFromTargetServer(incomingStream, inSocketOutputStream, bufferedData);
                } catch (ConnectException e) {
                    LOG.error(String.format("Could not connect to target host: %s : %s, Start the service for recording mode",
                                    config.getTargetHost(),
                                    config.getTargetPort()), e.getMessage());
                    return;
                }
            }
            
            
            
/*
            } catch (ConnectException e) {
                e.printStackTrace();
                if (config.isCachingOn()) {
                    System.err.println("Could not connect to target host:"
                            + config.getTargetHost() + ":" + config.getTargetPort()
                            + ", instead trying to get from cache:"
                            + e.getMessage());
                    writeResponseFromCache(inSocketOutputStream);
                }
                return;
            }*/
        } catch (Exception e) {
            StringWriter st = new StringWriter();
            PrintWriter wr = new PrintWriter(st);

            e.printStackTrace(wr);
            wr.close();
            System.out.println(st.toString());
            // halt();
        }
    }

    private void getResponseFromTargetServer(InputStream incomingStream,
                                             OutputStream inSocketOutputStream,
                                             String bufferedData) throws UnknownHostException, IOException,
            UnsupportedEncodingException, InterruptedException {
        InputStream outSocketInputStream;
        OutputStream outSocketOutputStream;
        outSocket = new Socket(config.getTargetHost(), config.getTargetPort());
        outSocketOutputStream = outSocket.getOutputStream();
        byte[] bytes = bufferedData.getBytes();
        System.err.println("The bytes to be written on output socket are:" + new String(bytes, "UTF-8"));
        outSocketOutputStream.write(bytes);

        // sends the request to endpoint
        // this is the channel to the endpoint
        rr1 = new SocketRR(this, inSocket, incomingStream, outSocket, outSocketOutputStream, "request:", config);

        // gets the response from endpoint
        // create the response slow link from the inbound slow link
        // this is the channel from the endpoint
        outSocketInputStream = outSocket.getInputStream();
        rr2 = new SocketRR(this, outSocket, outSocketInputStream, inSocket, inSocketOutputStream, "response:", config);

        // rr1.join();
        // rr2.join();
        System.err.println("rr1 isDone?" + rr1.isDone());
        System.err.println("rr2 isDone?" + rr2.isDone());
    }
    
 


    private String createInputRequest(InputStream incomingStream)
            throws IOException {
        StringBuffer buf;
        byte[] b1 = new byte[1];
        buf = new StringBuffer();
        String lastLine = null;
        String bufferedData = null;
        for (;;) {
            int len;
            len = incomingStream.read(b1, 0, 1);
            if (len == -1) {
                break;
            }
            String s1 = new String(b1);
            buf.append(s1);
            if (b1[0] != '\n') {
                continue;
            }

            // we have a complete line
            String line = buf.toString();
            buf.setLength(0);

            // check to see if we have found Host: header
            if (line.startsWith("Host: ")) {
                // we need to update the hostname to target host
                String newHost = "Host: " + config.getTargetHost() + ":" + config.getLocalPort()
                        + "\r\n";
                bufferedData = bufferedData.concat(newHost);
                break;
            }

            // add it to our headers so far
            if (bufferedData == null) {
                bufferedData = line;
            } else {
                bufferedData = bufferedData.concat(line);
            }

            // failsafe
            if (line.equals("\r\n")) {
                break;
            }
            if ("\n".equals(lastLine) && line.equals("\n")) {
                break;
            }
            lastLine = line;
        }
        return bufferedData;
    }

    private void setProxyConfigIfAvailable() {
        HTTPProxyHost = System.getProperty("http.proxyHost");
        if ((HTTPProxyHost != null) && HTTPProxyHost.equals("")) {
            HTTPProxyHost = null;
        }
        if (HTTPProxyHost != null) {
            String tmp = System.getProperty("http.proxyPort");
            if ((tmp != null) && tmp.equals("")) {
                tmp = null;
            }
            if (tmp == null) {
                HTTPProxyPort = 80;
            } else {
                HTTPProxyPort = Integer.parseInt(tmp);
            }
        }
    }

    private boolean isObjectExistInCache() {
        String location = cacheFileInfo.getCacheFileLocation();
        String objectId = cacheFileInfo.getObjectId();
        DiskObjectRepository objectRepository = new DiskObjectRepository();
        return objectRepository.objectAlreadyExist(objectId, location);
    }

    private void writeResponseFromCache(OutputStream inSocketOutputStream)
            throws IOException {
        String location = cacheFileInfo.getCacheFileLocation();
        System.out.println("The class name is:" + config.getTestClassName());
        String objectId = cacheFileInfo.getObjectId();
        String response = getResponseFromDisk(location, objectId);
        System.err.println("The response is(((((:" + response);

        if ((inSocketOutputStream != null)) {
            inSocketOutputStream.write(response.getBytes());
        }
    }

    private String getResponseFromDisk(String location, String objectId2) {
        DiskObjectRepository objectRepository = new DiskObjectRepository();
        return (String) objectRepository.getObject(objectId2, location);
    }


    private String createInputRequestWithProxy(InputStream tmpIn1)
            throws IOException, MalformedURLException {
        String bufferedData;
        StringBuffer buf;
        // Check if we're a proxy
        byte[] b = new byte[1];
        buf = new StringBuffer();
        String s;
        for (;;) {
            int len;
            len = tmpIn1.read(b, 0, 1);
            if (len == -1) {
                break;
            }
            s = new String(b);
            buf.append(s);
            if (b[0] != '\n') {
                continue;
            }
            break;
        }
        bufferedData = buf.toString();
        if (bufferedData.startsWith("GET ") || bufferedData.startsWith("POST ")
                || bufferedData.startsWith("PUT ")
                || bufferedData.startsWith("DELETE ")) {
            int start, end;
            URL url;
            start = bufferedData.indexOf(' ') + 1;
            while (bufferedData.charAt(start) == ' ') {
                start++;
            }
            end = bufferedData.indexOf(' ', start);
            String urlString = bufferedData.substring(start, end);
            if (urlString.charAt(0) == '/') {
                urlString = urlString.substring(1);
            }
            // FIXME
            if (isProxySelected()) {
                url = new URL(urlString);
                String targetHost = url.getHost();
                int targetPort = url.getPort();
                if (targetPort == -1) {
                    targetPort = 80;
                }
                bufferedData = bufferedData.substring(0, start) + url.getFile()
                        + bufferedData.substring(end);
            } else {
                url = new URL("http://" + config.getTargetHost() + ":" + config.getTargetPort() + "/"
                        + urlString);
                bufferedData = bufferedData.substring(0, start)
                        + url.toExternalForm() + bufferedData.substring(end);
                config.setTargetHost(HTTPProxyHost);
                config.setTargetPort(HTTPProxyPort);
            }
        }
        return bufferedData;
    }

    private boolean isProxySelected() {
        return proxySelected;
    }

    /**
     * Method wakeUp
     */
    synchronized void wakeUp() {
        this.notifyAll();
    }

}
