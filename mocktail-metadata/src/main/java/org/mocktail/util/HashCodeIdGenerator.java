package org.mocktail.util;

import java.util.Arrays;

public class HashCodeIdGenerator implements UniqueIdGenerator {

	@Override
	public int getUniqueId(Object... objects) {
		return Arrays.asList(objects).hashCode();
	}

}
