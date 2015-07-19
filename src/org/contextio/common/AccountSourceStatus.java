/**
 * 
 */
package org.contextio.common;

/**
 * @author David
 *
 */
public enum AccountSourceStatus {

    OK,
    
    INVALID_CREDENTIALS, 
    CONNECTION_IMPOSSIBLE, 
    NO_ACCESS_TO_ALL_MAIL, 
    TEMP_DISABLED,
    DISABLED
}
