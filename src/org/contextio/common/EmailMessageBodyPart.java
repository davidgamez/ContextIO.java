/**
 * 
 */
package org.contextio.common;

/**
 * 
 * Represents a body part of an email
 * @author David
 *
 */
public class EmailMessageBodyPart {

//    MIME type of message
    private String type;
    
//    encoding of the characters
    private String charset;
    
//    the actual content of the message
    private String content;

//    indicating position of the part in the body structure
    private int bodySection;
    
    /**
     * @return the type
     */
    public String getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * @return the charset
     */
    public String getCharset() {
        return charset;
    }

    /**
     * @param charset the charset to set
     */
    public void setCharset(String charset) {
        this.charset = charset;
    }

    /**
     * @return the content
     */
    public String getContent() {
        return content;
    }

    /**
     * @param content the content to set
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * @return the bodySection
     */
    public int getBodySection() {
        return bodySection;
    }

    /**
     * @param bodySection the bodySection to set
     */
    public void setBodySection(int bodySection) {
        this.bodySection = bodySection;
    }
}
