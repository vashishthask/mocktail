package org.mocktail.util;

import java.util.Arrays;
import java.util.List;

import com.thoughtworks.xstream.XStream;

public class HashCodeIdGenerator implements UniqueIdGenerator {

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
        for (Object object : list) {
            String oXml = xstream.toXML(object);
            builder.append(oXml);
        }
        int hashCode = builder.toString().hashCode();
        System.out.println("The hashcode for method:"+methodName + ":is:"+hashCode);
        return hashCode;
    }
}
