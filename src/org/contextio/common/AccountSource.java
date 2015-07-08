/**
 * 
 */
package org.contextio.common;

import java.util.HashMap;
import java.util.Map;

/**
 * Account source class. 
 * @author David
 *
 */
public class AccountSource implements ParametersMapeable{

	/** Mandatory parameters when adding an account source **/
	private String email;
	private String server;
	private String username;
	private boolean useSsl;
	private String port;
	private AccountSourceType type = AccountSourceType.IMAP;
	
	/** Optional parameters when adding an account source **/
	private String originIp;
	private Boolean expungeOnDeletedFlag;
	private Boolean syncAllFolders;
	private String syncFolders;
	private String syncFlags;
	private String rawFileList;
	private String password;
	private String providerRefreshToken;
	private String providerConsumerKey;
	private String callbackUrl;
	private String statusCallbackUrl;
	
	@Override
	public Map<String, String> toParametersMap() {
		Map<String, String> result = new HashMap<String, String>();
		result.put("email", email);
		result.put("server", server);
		result.put("username", username);
		result.put("use_ssl", useSsl?"1":"0");
		result.put("port", port);
		result.put("type", type.name());
		// TODO DGD add optional parameters
		result.put("password", password);
		return result;
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
	 * @return the type
	 */
	public AccountSourceType getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(AccountSourceType type) {
		this.type = type;
	}

	/**
	 * @return the expungeOnDeletedFlag
	 */
	public Boolean getExpungeOnDeletedFlag() {
		return expungeOnDeletedFlag;
	}

	/**
	 * @param expungeOnDeletedFlag the expungeOnDeletedFlag to set
	 */
	public void setExpungeOnDeletedFlag(Boolean expungeOnDeletedFlag) {
		this.expungeOnDeletedFlag = expungeOnDeletedFlag;
	}

	/**
	 * @return the syncAllFolders
	 */
	public Boolean getSyncAllFolders() {
		return syncAllFolders;
	}

	/**
	 * @param syncAllFolders the syncAllFolders to set
	 */
	public void setSyncAllFolders(Boolean syncAllFolders) {
		this.syncAllFolders = syncAllFolders;
	}

	/**
	 * @return the syncFolders
	 */
	public String getSyncFolders() {
		return syncFolders;
	}

	/**
	 * @param syncFolders the syncFolders to set
	 */
	public void setSyncFolders(String syncFolders) {
		this.syncFolders = syncFolders;
	}

	/**
	 * @return the syncFlags
	 */
	public String getSyncFlags() {
		return syncFlags;
	}

	/**
	 * @param syncFlags the syncFlags to set
	 */
	public void setSyncFlags(String syncFlags) {
		this.syncFlags = syncFlags;
	}

	/**
	 * @return the rawFileList
	 */
	public String getRawFileList() {
		return rawFileList;
	}

	/**
	 * @param rawFileList the rawFileList to set
	 */
	public void setRawFileList(String rawFileList) {
		this.rawFileList = rawFileList;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return the providerRefreshToken
	 */
	public String getProviderRefreshToken() {
		return providerRefreshToken;
	}

	/**
	 * @param providerRefreshToken the providerRefreshToken to set
	 */
	public void setProviderRefreshToken(String providerRefreshToken) {
		this.providerRefreshToken = providerRefreshToken;
	}

	/**
	 * @return the providerConsumerKey
	 */
	public String getProviderConsumerKey() {
		return providerConsumerKey;
	}

	/**
	 * @param providerConsumerKey the providerConsumerKey to set
	 */
	public void setProviderConsumerKey(String providerConsumerKey) {
		this.providerConsumerKey = providerConsumerKey;
	}

	/**
	 * @return the callbackUrl
	 */
	public String getCallbackUrl() {
		return callbackUrl;
	}

	/**
	 * @param callbackUrl the callbackUrl to set
	 */
	public void setCallbackUrl(String callbackUrl) {
		this.callbackUrl = callbackUrl;
	}

	/**
	 * @return the statusCallbackUrl
	 */
	public String getStatusCallbackUrl() {
		return statusCallbackUrl;
	}

	/**
	 * @param statusCallbackUrl the statusCallbackUrl to set
	 */
	public void setStatusCallbackUrl(String statusCallbackUrl) {
		this.statusCallbackUrl = statusCallbackUrl;
	}

	/**
	 * @return the originIp
	 */
	public String getOriginIp() {
		return originIp;
	}

	/**
	 * @param originIp the originIp to set
	 */
	public void setOriginIp(String originIp) {
		this.originIp = originIp;
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

}
