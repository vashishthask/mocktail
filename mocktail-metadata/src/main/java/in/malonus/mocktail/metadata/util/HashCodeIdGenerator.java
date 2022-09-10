package in.malonus.mocktail.metadata.util;

import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.thoughtworks.xstream.XStream;

public class HashCodeIdGenerator implements UniqueIdGenerator {
    private static final Logger LOGGER = LoggerFactory.getLogger(HashCodeIdGenerator.class);

    @Override
    public int getUniqueId(Object... objects) {
        return Arrays.asList(objects).hashCode();
    }

    @Override
    public int getUniqueId(String methodName, Object... objects) {
        List<Object> list = Arrays.asList(objects);
        XStream xstream = new XStream();
        StringBuilder builder = new StringBuilder();
        builder.append(methodName);
        LOGGER.debug("the method name is" + methodName);
        for (Object object : list) {
            LOGGER.debug("the object is" + object);

            String oXml = "";
            try {
                oXml = xstream.toXML(object);
            } catch (Throwable e) {
                LOGGER.error("Could not find converter for the object " + object
                        + ". This object will be ignored in creating unique name. The trace is:" + e.getMessage(), e);
            }
            builder.append(oXml);
            LOGGER.debug("oXml" + oXml);

        }
        int hashCode = builder.toString().hashCode();
        return hashCode;
    }
}
