/**
 * 
 */
package org.contextio.clientapi;

import java.util.Map;

/**
 * This class expose some {@link ContextIO} methods using DTOs as parameters instead of {@link Map}
 *  
 * @author David
 *
 */
public class ContextIOService extends ContextIO {

	public ContextIOService(String key, String secret, boolean ssl,
			boolean saveHeaders) {
		super(key, secret, ssl, saveHeaders);
	}

	public ContextIOService(String key, String secret) {
		super(key, secret);
	}

}
