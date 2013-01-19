package org.apache.ws.commons.tcpmon;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

/**
 * Allows one to send an arbitrary soap message to a specific url with a specified soap action
 */
class Sender {
    private String targetURL;
	private String inputAction = "";

	public Sender() {
    }

	//FIXME
    public void send() {
        try {
        	//FIXME
//            URL u = new URL(endpointField.getText());
        	URL u = new URL(targetURL);
        	
            URLConnection uc = u.openConnection();
            HttpURLConnection connection = (HttpURLConnection) uc;
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setRequestMethod("POST");
            String action = "\"" + inputAction  + "\"";
            connection.setRequestProperty("SOAPAction", action);
            connection.setRequestProperty("Content-Type", "text/xml");
            connection.setRequestProperty("User-Agent", "Axis/2.0");
            OutputStream out = connection.getOutputStream();
            Writer writer = new OutputStreamWriter(out);
            writer.flush();
            writer.close();
            String line;
            InputStream inputStream = null;
            try {
                inputStream = connection.getInputStream();
            } catch (IOException e) {
                inputStream = connection.getErrorStream();
            }
            StringBuilder outputText = new StringBuilder();
            BufferedReader rd = new BufferedReader(new InputStreamReader(inputStream));
            while ((line = rd.readLine()) != null) {
                outputText.append(line);
            }
        } catch (Exception e) {
            StringWriter w = new StringWriter();
            e.printStackTrace(new PrintWriter(w));
        }
    }
    
    public String prettyXML(String input) throws Exception {
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        try {transformerFactory.setAttribute("indent-number", new Integer(2)); } catch (Exception e){}
        Transformer transformer = transformerFactory.newTransformer();
        try {transformer.setOutputProperty("{http://xml.apache.org/xalan}indent-amount", "2"); } catch (Exception e){}
        transformer.setOutputProperty(OutputKeys.INDENT , "yes");
        StringWriter writer = new StringWriter();
        transformer.transform(new StreamSource(new StringReader(input)), new StreamResult(writer));
        return writer.toString();
    }
}
