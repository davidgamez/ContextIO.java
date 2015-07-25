/**
 * 
 */
package org.contextio.common;


/**
 * Represents the email message from ContextIO 2.0 API
 * {@link https://context.io/docs/2.0/accounts/messages}
 * 
 * @author David Gamez
 *
 */
public class EmailMessage {

    // Comments on fields were extracted from ContextIO REST documentation

    /**
     * date: numberUnix timestamp of message date
     */
    private long date;

    /**
     * addresses: objectEmail addresses and names of sender and recipients
     */
    private EmailAddresses addresses;

    /**
     * email_message_id: stringValue of RFC-822 Message-ID header
     */
    private String emailMessageId;

    /**
     * stringUnique and persistent id assigned by Context.IO to the message
     */
    private String messageId;

    /**
     * gmail_message_id: stringMessage id assigned by Gmail (only present if
     * source is a Gmail account)
     */
    private String gmailMessageId;

    /**
     * gmail_thread_id: stringThread id assigned by Gmail (only present if
     * source is a Gmail account)
     */
    private String gmailThreadId;

    /**
     * subject: stringSubject of the message
     */
    private String subject;
    
    /**
     * folders: arrayList of folders (or Gmail labels) this message is found in
     */
    private String[] folders;

    /**
     * @return the date
     */
    public long getDate() {
        return date;
    }

    /**
     * @param date the date to set
     */
    public void setDate(long date) {
        this.date = date;
    }

    /**
     * @return the emailMessageId
     */
    public String getEmailMessageId() {
        return emailMessageId;
    }

    /**
     * @param emailMessageId the emailMessageId to set
     */
    public void setEmailMessageId(String emailMessageId) {
        this.emailMessageId = emailMessageId;
    }

    /**
     * @return the messageId
     */
    public String getMessageId() {
        return messageId;
    }

    /**
     * @param messageId the messageId to set
     */
    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    /**
     * @return the gmailMessageId
     */
    public String getGmailMessageId() {
        return gmailMessageId;
    }

    /**
     * @param gmailMessageId the gmailMessageId to set
     */
    public void setGmailMessageId(String gmailMessageId) {
        this.gmailMessageId = gmailMessageId;
    }

    /**
     * @return the gmailThreadId
     */
    public String getGmailThreadId() {
        return gmailThreadId;
    }

    /**
     * @param gmailThreadId the gmailThreadId to set
     */
    public void setGmailThreadId(String gmailThreadId) {
        this.gmailThreadId = gmailThreadId;
    }

    /**
     * @return the subject
     */
    public String getSubject() {
        return subject;
    }

    /**
     * @param subject the subject to set
     */
    public void setSubject(String subject) {
        this.subject = subject;
    }

    /**
     * @return the folders
     */
    public String[] getFolders() {
        return folders;
    }

    /**
     * @param folders the folders to set
     */
    public void setFolders(String[] folders) {
        this.folders = folders;
    }

    /**
     * @return the addresses
     */
    public EmailAddresses getAddresses() {
        return addresses;
    }

    /**
     * @param addresses the addresses to set
     */
    public void setAddresses(EmailAddresses addresses) {
        this.addresses = addresses;
    }
    
}