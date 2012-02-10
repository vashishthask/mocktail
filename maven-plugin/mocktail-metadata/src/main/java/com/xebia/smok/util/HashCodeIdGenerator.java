package com.xebia.smok.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HashCodeIdGenerator implements UniqueIdGenerator {

	@Override
	public int getUniqueId(Object... objects) {
		return Arrays.asList(objects).hashCode();
	}

	@Override
	public int getUniqueId(String methodNam, Object... objects) {
		List<Object> list = Arrays.asList(objects);
		List<Object> methodList = new ArrayList<Object>();
		methodList.add(methodNam);
		methodList.addAll(list);
		return methodList.hashCode();
	}

}
