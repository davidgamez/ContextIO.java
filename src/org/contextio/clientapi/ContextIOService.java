/**
 * 
 */
package org.contextio.clientapi;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.contextio.common.Account;
import org.contextio.common.AccountSource;
import org.contextio.common.AccountSourceStatus;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

/**
 * This class expose some {@link ContextIO} methods using DTOs as parameters
 * instead of {@link Map}
 * 
 * @author David
 *
 */
public class ContextIOService extends ContextIO {

    private Gson prettyGson = new GsonBuilder().setPrettyPrinting().create();
    
    /**
     * 
     * @param key
     *            of the contextio connection account
     * @param secret
     *            of the contextio connection account
     * @param ssl
     *            true is SSL will be use in the connection, false otherwise
     * @param saveHeaders
     */
    public ContextIOService(String key, String secret, boolean ssl, boolean saveHeaders) {
	super(key, secret, ssl, saveHeaders);
    }

    /**
     * 
     * @param key
     *            of the contextio connection account
     * @param secret
     *            of the contextio connection account
     */
    public ContextIOService(String key, String secret) {
	super(key, secret);
    }

    /**
     * 
     * @param accountId
     *            identifier of the account to associate the source
     * @param accountSource
     *            instance to associate {@link https
     *            ://context.io/docs/2.0/accounts/sources}
     * @throws Exception
     */
    public void addAccountSource(String accountId, AccountSource accountSource) throws Exception {
	post("", "accounts/" + accountId + "/sources", accountSource.toParametersMap());
    }

    /**
     * 
     * @param accountId
     *            identifier of the account
     * @return a List of account sources associated to the account denoted by
     *         <code>accountId</code> {@link https
     *         ://context.io/docs/2.0/accounts/sources}
     * @throws Exception 
     */
    public List<AccountSource> getAccountSources(String accountId, AccountSourceStatus accountSourceStatus) throws Exception {
	Map<String, String> params = new HashMap<String, String>();
	if (accountSourceStatus != null){
	    params.put("status", accountSourceStatus.name());
	}
	String action = MessageFormat.format("accounts/{0}/sources", accountId);
	ContextIOResponse response = get("", action, params);
	
	List<AccountSource> result = new ArrayList<AccountSource>();
	JsonArray jsonArray = response.getJson().getAsJsonArray();
	if (!jsonArray.isJsonNull() && jsonArray.size() > 0){
	    for (int i = 0; i < jsonArray.size(); i++){
		JsonElement jsonSource = jsonArray.get(i);
		result.add(prettyGson.fromJson(jsonSource, AccountSource.class));
	    }
	}
	return result;
    }

    /**
     * 
     * @param account
     *            to create
     * @return the new id created
     * @throws Exception
     */
    public String addAccount(Account account) throws Exception {
	ContextIOResponse response = post("", "accounts", account.toParametersMap());
	// return the id created by contextio for future account references
	return response.getJson().getAsJsonObject().get("id").getAsString();
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.contextio.clientapi.ContextIO#post(java.lang.String,
     * java.lang.String, java.util.Map)
     */
    @Override
    public ContextIOResponse post(String account, String action, Map<String, String> params) throws Exception {
	ContextIOResponse response = super.post(account, action, params);
	checkResponse(response);
	return response;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.contextio.clientapi.ContextIO#post(java.lang.String,
     * java.lang.String, java.util.Map)
     */
    @Override
    public ContextIOResponse get(String account, String action, Map<String, String> params) throws Exception {
	ContextIOResponse response = super.get(account, action, params);
	checkResponse(response);
	return response;
    }

    /**
     * Examines the response for errors and throws an exception if found
     * 
     * @param response
     * @throws Exception
     */
    public void checkResponse(ContextIOResponse response) throws Exception {
	response.decodeResponse();
	if (response.isHasError()) {
	    System.out.println(response.getRawResponse().getBody());
	    String message = "";
	    if (response.getMessages() != null) {
		for (ContextIOResponseMessage msg : response.getMessages()) {
		    message = msg.toString() + "\n";
		}
	    }
	    throw new Exception(message);
	}
    }
}
