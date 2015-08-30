/**
 * 
 */
package org.contextio.common;

/**
 * @author David
 *
 */
public class ImapConfig {

    private String server;
    private String username;
    private String port;
    private boolean useSsl;
    private boolean oauth;
    
    /**
     * @return the server
     */
    public String getServer() {
        return server;
    }
    
    /**
     * @param server the server to set
     */
    public void setServer(String server) {
        this.server = server;
    }
    
    /**
     * @return the username
     */
    public String getUsername() {
        return username;
    }
    
    /**
     * @param username the username to set
     */
    public void setUsername(String username) {
        this.username = username;
    }
    
    /**
     * @return the port
     */
    public String getPort() {
        return port;
    }
    
    /**
     * @param port the port to set
     */
    public void setPort(String port) {
        this.port = port;
    }
    
    /**
     * @return the useSsl
     */
    public boolean isUseSsl() {
        return useSsl;
    }
    
    /**
     * @param useSsl the useSsl to set
     */
    public void setUseSsl(boolean useSsl) {
        this.useSsl = useSsl;
    }
    
    /**
     * @return the oauth
     */
    public boolean isOauth() {
        return oauth;
    }
    
    /**
     * @param oauth the oauth to set
     */
    public void setOauth(boolean oauth) {
        this.oauth = oauth;
    }
    
}
