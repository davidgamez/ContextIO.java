/**
 * 
 */
package org.contextio.common;

/**
 * @author David
 *
 */
public class AccountSourceDiscover {

    private boolean found;
    private String email;
    private ImapConfig imap;
    
    /**
     * @return the found
     */
    public boolean isFound() {
        return found;
    }
    
    /**
     * @param found the found to set
     */
    public void setFound(boolean found) {
        this.found = found;
    }
    
    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }
    
    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }
    
    /**
     * @return the imap
     */
    public ImapConfig getImap() {
        return imap;
    }
    
    /**
     * @param imap the imap to set
     */
    public void setImap(ImapConfig imap) {
        this.imap = imap;
    }
 

}
