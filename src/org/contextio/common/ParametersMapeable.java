/**
 * 
 */
package org.contextio.common;

import java.util.Map;

/**
 * Represents classes that could be converted to a map
 * 
 * @author David
 *
 */
public interface ParametersMapeable {

	/**
	 * 
	 * @return
	 */
	Map<String, String> toParametersMap();
}
