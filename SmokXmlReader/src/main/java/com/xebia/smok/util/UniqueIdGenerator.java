package com.xebia.smok.util;

import java.util.Arrays;

/**
 * I'll generate a unique id out of list of objects
 *
 */
public enum UniqueIdGenerator {
	HASH_CODE_IMPL;

	public int getUniqueId(Object... objects) {
		return Arrays.asList(objects).hashCode();
	}

}
