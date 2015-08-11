/**
 * 
 */
package org.contextio.common;

/**
 * 
 * Represents the full body of an email
 * @author David
 *
 */
public class EmailMessageFullBody {

//    MIME type of message
    private String type;
    
//    encoding of the characters
    private String charset;
    
//    the actual content of the message
    private String content;

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
}
