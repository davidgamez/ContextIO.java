/**
 * 
 */
package org.contextio.clientapi;

import java.util.Map;

import org.contextio.common.Account;
import org.contextio.common.AccountSource;

/**
 * This class expose some {@link ContextIO} methods using DTOs as parameters instead of {@link Map}
 *  
 * @author David
 *
 */
public class ContextIOService extends ContextIO {

    /**
     * 
	 * @param key of the contextio connection account 
	 * @param secret of the contextio connection account
	 * @param ssl true is SSL will be use in the connection, false otherwise
     * @param saveHeaders
     */
	public ContextIOService(String key, String secret, boolean ssl,
			boolean saveHeaders) {
		super(key, secret, ssl, saveHeaders);
	}

	/**
	 * 
	 * @param key of the contextio connection account 
	 * @param secret of the contextio connection account
	 */
	public ContextIOService(String key, String secret) {
		super(key, secret);
	}

	/**
	 * 
	 * @param accountId identifier of the account to associate the source
	 * @param accountSource instance to associate
	 * @throws Exception
	 */
	public void addAccountSource(String accountId, AccountSource accountSource) throws Exception{
		post("", "accounts/" + accountId + "/sources", accountSource.toParametersMap());
	}
	
	/**
	 * 
	 * @param account to create
	 * @return the new id created
	 * @throws Exception
	 */
	public String addAccount(Account account) throws Exception{
		ContextIOResponse response = post("", "accounts", account.toParametersMap());
		
//		return the id created by contextio for future account references
		return response.getJson().getAsJsonObject().get("id").getAsString();
	}
	
	/* (non-Javadoc)
	 * @see org.contextio.clientapi.ContextIO#post(java.lang.String, java.lang.String, java.util.Map)
	 */
	@Override
	public ContextIOResponse post(String account, String action, Map<String, String> params) throws Exception {
		ContextIOResponse response = super.post(account, action, params);
		checkResponse(response);
		return response;
	}
	
	/**
     * Examines the response for errors and throws an exception if found
     * @param response
     * @throws Exception 
     */
    public void checkResponse(ContextIOResponse response) throws Exception {
        response.decodeResponse();
        if(response.isHasError()) {
        	System.out.println(response.getRawResponse().getBody());
            String message = "";
            if(response.getMessages() != null) {
                for(ContextIOResponseMessage msg : response.getMessages()) {
                    message = msg.toString() + "\n";
                }
            }
            throw new Exception(message);
        }
    }
}
