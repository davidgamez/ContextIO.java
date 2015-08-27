/**
 * 
 */
package org.contextio.clientapi;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.contextio.common.Account;
import org.contextio.common.AccountSource;
import org.contextio.common.AccountSourceStatus;
import org.contextio.common.EmailMessage;
import org.contextio.common.EmailMessageBodyPart;
import org.contextio.common.EmailMessageFilter;
import org.contextio.common.EmailMessageFullBody;

import com.google.gson.FieldNamingPolicy;
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

    private Gson gson = new GsonBuilder().setPrettyPrinting().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create();
    
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
	ContextIOResponse response = get(accountId, action, params);
	
	return getListFromResponse(response, AccountSource.class);
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

    /**
     * 
     * @param accountId account identifier 
     * @param filterParams filters
     * @return the list of messages that match with the filters <code>filterParams</code>
     * @throws Exception
     */
    public List<EmailMessage> getMessages(String accountId, Map<EmailMessageFilter, String> filterParams) throws Exception{
	String action = MessageFormat.format("accounts/{0}/messages", accountId);
	Map<String, String> params = new HashMap<String, String>();
	if (filterParams != null){
	    for (Entry<EmailMessageFilter, String> filterParam: filterParams.entrySet()){
		params.put(filterParam.getKey().getFilterName(), filterParam.getValue());
	    }
	}
	ContextIOResponse response = get(accountId, action, params);
	
	return getListFromResponse(response, EmailMessage.class);
    }

    /**
     * 
     * @param accountId account identifier 
     * @param messageId message identifier
     * @return email message associated with <code>messageId</code>
     * @throws Exception
     */
    public EmailMessage getMessage(String accountId, String messageId, Map<EmailMessageFilter, String> filterParams) throws Exception{
	String action = MessageFormat.format("accounts/{0}/messages/{1}", accountId, messageId);
	Map<String, String> params = new HashMap<String, String>();
	if (filterParams != null){
	    for (Entry<EmailMessageFilter, String> filterParam: filterParams.entrySet()){
		params.put(filterParam.getKey().getFilterName(), filterParam.getValue());
	    }
	}
	ContextIOResponse response = get(accountId, action, params);
	
	JsonElement jsonSource = response.getJson().getAsJsonObject();
	return gson.fromJson(jsonSource, EmailMessage.class);
    }

    public EmailMessageFullBody getEmailFullBody(String accountId, String messageId, String type) throws Exception{
	String action = MessageFormat.format("accounts/{0}/messages/{1}/body", accountId, messageId);
	if (StringUtils.isNotBlank(type)){
	    action += "?type=" + type; 
	}
	ContextIOResponse response = get(accountId, action, null);
	
	List<EmailMessageBodyPart> bodyParts = getListFromResponse(response, EmailMessageBodyPart.class);
//	TODO DGD this is a simplified solution, needs to be reviewed 
	EmailMessageFullBody result = new EmailMessageFullBody();
	Collections.sort(bodyParts, new Comparator<EmailMessageBodyPart>(){
	    @Override
	    public int compare(EmailMessageBodyPart o1, EmailMessageBodyPart o2) {
		return Integer.valueOf(o1.getBodySection()).compareTo(Integer.valueOf(o1.getBodySection()));
	    }
	});
	StringBuffer buffer = new StringBuffer();
	for (EmailMessageBodyPart part: bodyParts){
	    buffer.append(part.getContent());
	}
	result.setContent(buffer.toString());
	if (CollectionUtils.isNotEmpty(bodyParts)){
	    result.setCharset(bodyParts.get(0).getCharset());
	    result.setType(bodyParts.get(0).getType());
	}
	return result;
    }
    
    /**
     * 
     * @param response to extract the list of instances
     * @param t class of the generic parameter
     * @return the list of instances with instances of the given generic class T 
     */
    protected <T> List<T> getListFromResponse(ContextIOResponse response, Class<T> t) {
	List<T> result = new ArrayList<T>();
	JsonArray jsonArray = response.getJson().getAsJsonArray();
	if (!jsonArray.isJsonNull() && jsonArray.size() > 0){
	    for (int i = 0; i < jsonArray.size(); i++){
		JsonElement jsonSource = jsonArray.get(i);
		result.add(gson.fromJson(jsonSource, t));
	    }
	}
	return result;
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
