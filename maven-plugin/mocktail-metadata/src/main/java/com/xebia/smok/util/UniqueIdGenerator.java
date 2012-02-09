package com.xebia.smok.util;


/**
 * I'll generate a unique id out of list of objects
 *
 */
public interface UniqueIdGenerator {

	public int getUniqueId(Object... objects);
}
