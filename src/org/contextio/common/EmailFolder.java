/**
 * 
 */
package org.contextio.common;

/**
 * @author David
 *
 */
public class EmailFolder {

//    not supported yet
//    attributes: objectIMAP Attributes of the folder given as a hash, 
//    delim: stringCharacter used to delimit hierarchy in the folder name
    
//    folder name
    private String name;
//    numberNumber of messages found in this folder
    private long nbMessages;
//    numberNumber of unread messages in this folder (present only if include_extended_counts is set to 1
    private Long nb_unseen_messages;
    
    /**
     * @return the name
     */
    public String getName() {
        return name;
    }
    
    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }
    
    /**
     * @return the nbMessages
     */
    public long getNbMessages() {
        return nbMessages;
    }
    
    /**
     * @param nbMessages the nbMessages to set
     */
    public void setNbMessages(long nbMessages) {
        this.nbMessages = nbMessages;
    }
    
    /**
     * @return the nb_unseen_messages
     */
    public Long getNb_unseen_messages() {
        return nb_unseen_messages;
    }
    
    /**
     * @param nb_unseen_messages the nb_unseen_messages to set
     */
    public void setNb_unseen_messages(Long nb_unseen_messages) {
        this.nb_unseen_messages = nb_unseen_messages;
    }
}
