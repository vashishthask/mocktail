package com.svashishtha.mocktail.tcpcachenew;

import java.util.ResourceBundle;

public class TcpMessages {
    
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

}
