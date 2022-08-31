package com.svashishtha.mocktail.metadata.util;

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
        System.err.println("HashCodeIdGenerator:getUniqueId:methodName:"+methodName);
        for (Object object : list) {
            String oXml = xstream.toXML(object);
            builder.append(oXml);
            System.err.println("HashCodeIdGenerator:getUniqueId:oXml"+object);
        }
        int hashCode = builder.toString().hashCode();
        return hashCode;
    }
}
