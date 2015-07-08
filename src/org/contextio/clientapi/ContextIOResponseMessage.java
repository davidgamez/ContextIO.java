/**
 * 
 */
package org.contextio.clientapi;

/**
 * @author David
 *
 */
public class ContextIOResponseMessage {

	public String type;
    public Integer code;
    public String value;
    
    @Override
    public String toString() {
        return type + "(" + code + "): " + value;
    }
}
