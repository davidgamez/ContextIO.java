/**
 * 
 */
package org.contextio.common;

/**
 * @author David Gamez
 *
 */
public class EmailAddresses {

    private EmailAddress from;
    private EmailAddress[] to;
    private EmailAddress[] cc;
    private EmailAddress[] bcc;
    private EmailAddress replyTo;
    
    /**
     * @return the from
     */
    public EmailAddress getFrom() {
        return from;
    }
    
    /**
     * @param from the from to set
     */
    public void setFrom(EmailAddress from) {
        this.from = from;
    }
    
    /**
     * @return the to
     */
    public EmailAddress[] getTo() {
        return to;
    }
    
    /**
     * @param to the to to set
     */
    public void setTo(EmailAddress[] to) {
        this.to = to;
    }
    
    /**
     * @return the cc
     */
    public EmailAddress[] getCc() {
        return cc;
    }
    
    /**
     * @param cc the cc to set
     */
    public void setCc(EmailAddress[] cc) {
        this.cc = cc;
    }
    
    /**
     * @return the bcc
     */
    public EmailAddress[] getBcc() {
        return bcc;
    }
    
    /**
     * @param bcc the bcc to set
     */
    public void setBcc(EmailAddress[] bcc) {
        this.bcc = bcc;
    }
    
    /**
     * @return the replyTo
     */
    public EmailAddress getReplyTo() {
        return replyTo;
    }
    
    /**
     * @param replyTo the replyTo to set
     */
    public void setReplyTo(EmailAddress replyTo) {
        this.replyTo = replyTo;
    }
    
    
}
