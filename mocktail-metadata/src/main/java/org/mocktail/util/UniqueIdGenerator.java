package org.mocktail.util;


/**
 * I'll generate a unique id out of list of objects
 *
 */
public interface UniqueIdGenerator {

	public int getUniqueId(Object... objects);
	public int getUniqueId(String methodNam, Object... objects);
}
