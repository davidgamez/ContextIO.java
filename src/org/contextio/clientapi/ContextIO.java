package org.contextio.clientapi;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.scribe.builder.ServiceBuilder;
import org.scribe.model.OAuthRequest;
import org.scribe.model.Response;
import org.scribe.model.Token;
import org.scribe.model.Verb;
import org.scribe.oauth.OAuthService;
import org.scribe.utils.URLUtils;

/**
 * Class to manage Context.IO API access
 * 
 * @author Thomas Taschauer | tomtasche.at
 */
public class ContextIO {

	public static final String ENDPOINT = "api.context.io";
	public static final String API_VERSION = "2.0";
	protected String key;
	protected String secret;
	protected boolean ssl;
	protected boolean saveHeaders;
	protected boolean authHeaders;
	protected ContextIOResponse lastResponse;

	/**
	 * Instantiate a new ContextIO object. Your OAuth consumer key and secret
	 * can be found under the "settings" tab of the developer console
	 * (https://console.context.io/#settings)
	 * 
	 * @param key
	 *            Your Context.IO OAuth consumer key
	 * @param secret
	 *            Your Context.IO OAuth consumer secret
	 */
	public ContextIO(String key, String secret) {
		this.key = key;
		this.secret = secret;
		this.ssl = true;
		this.saveHeaders = false;
	}

	/**
	 * Instantiate a new ContextIO object. Your OAuth consumer key and secret
	 * can be found under the "settings" tab of the developer console
	 * (https://console.context.io/#settings)
	 * 
	 * @param key
	 *            Your Context.IO OAuth consumer key
	 * @param secret
	 *            Your Context.IO OAuth consumer secret
	 * @param ssl
	 *            true if the connection to contextio will be under SSL
	 *            protocol, false otherwise
	 * @param saveHeaders
	 *            true if
	 */
	public ContextIO(String key, String secret, boolean ssl, boolean saveHeaders) {
		this.key = key;
		this.secret = secret;
		this.ssl = ssl;
		this.saveHeaders = saveHeaders;
	}

	/**
	 * Returns the 20 contacts with whom the most emails were exchanged.
	 * 
	 * @link http://context.io/docs/1.1/addresses
	 * @param account
	 *            accountId or email address of the mailbox you want to query
	 * @return ContextIOResponse
	 * @throws Exception 
	 */
	public ContextIOResponse addresses(String account) throws Exception {
		return get(account, "adresses.json", null);
	}

	/**
	 * Returns the 25 most recent attachments found in a mailbox. Use limit to
	 * change that number.
	 * 
	 * @link http://context.io/docs/1.1/allfiles
	 * @param account
	 *            accountId or email address of the mailbox you want to query
	 * @param params
	 *            Query parameters for the API call: since, limit
	 * @return ContextIOResponse
	 * @throws Exception 
	 */
	public ContextIOResponse allFiles(String account, Map<String, String> params) throws Exception {
		params = filterParams(params, new String[] { "since", "limit" });

		return get(account, "allfiles.json", params);
	}

	/**
	 * Returns the 25 most recent attachments found in a mailbox. Use limit to
	 * change that number. This is useful if you're polling a mailbox for new
	 * messages and want all new messages indexed since a given timestamp.
	 * 
	 * @link http://context.io/docs/1.1/allmessages
	 * @param account
	 *            accountId or email address of the mailbox you want to query
	 * @param params
	 *            Query parameters for the API call: since, limit
	 * @return ContextIOResponse
	 * @throws Exception 
	 */
	public ContextIOResponse allMessages(String account,
			Map<String, String> params) throws Exception {
		params = filterParams(params, new String[] { "since", "limit" });

		return get(account, "allmessages.json", params);
	}

	/**
	 * This call returns the latest attachments exchanged with one or more email
	 * addresses
	 * 
	 * @link http://context.io/docs/1.1/contactfiles
	 * @param account
	 *            accountId or email address of the mailbox you want to query
	 * @param params
	 *            Query parameters for the API call: 'email', 'to', 'from',
	 *            'cc', 'bcc', 'limit'
	 * @return ContextIOResponse
	 * @throws Exception 
	 */
	public ContextIOResponse contactFiles(String account,
			Map<String, String> params) throws Exception {
		params = filterParams(params, new String[] { "email", "to", "from",
				"cc", "bcc", "limit" });

		return get(account, "contactfiles.json", params);
	}

	/**
	 * This call returns list of email messages for one or more contacts. Use
	 * the email parameter to get emails where a contact appears in the
	 * recipients or is the sender. Use to, from and cc parameters for more
	 * precise control.
	 * 
	 * @link http://context.io/docs/1.1/contactmessages
	 * @param account
	 *            accountId or email address of the mailbox you want to query
	 * @param params
	 *            Query parameters for the API call: 'email', 'to', 'from',
	 *            'cc', 'bcc', 'limit'
	 * @return ContextIOResponse
	 * @throws Exception 
	 */
	public ContextIOResponse contactMessages(String account,
			Map<String, String> params) throws Exception {
		params = filterParams(params, new String[] { "email", "to", "from",
				"cc", "bcc", "limit" });

		return get(account, "contactmessages.json", params);
	}

	/**
	 * This call search the lists of contacts.
	 * 
	 * @link http://context.io/docs/1.1/contactsearch
	 * @param account
	 *            accountId or email address of the mailbox you want to query
	 * @param params
	 *            Query parameters for the API call: 'search'
	 * @return ContextIOResponse
	 * @throws Exception 
	 */
	public ContextIOResponse contactSearch(String account,
			Map<String, String> params) throws Exception {
		params = filterParams(params, new String[] { "search" });

		return get(account, "contactsearch.json", params);
	}

	/**
	 * Given two files, this will return the list of insertions and deletions
	 * made from the oldest of the two files to the newest one.
	 * 
	 * @link http://context.io/docs/1.1/diffsummary
	 * @param account
	 *            accountId or email address of the mailbox you want to query
	 * @param params
	 *            Query parameters for the API call: 'fileId1', 'fileId2'
	 * @return ContextIOResponse
	 * @throws Exception 
	 */
	public ContextIOResponse diffSummary(String account,
			Map<String, String> params) throws Exception {
		params = filterParams(params, new String[] { "fileId1", "fileId2" });

		params.put("generate", "1");

		return get(account, "diffsummary.json", params);
	}

	/**
	 * Returns the content a given attachment. If you want to save the
	 * attachment to a file, set $saveAs to the destination file name. If
	 * $saveAs is left to null, the function will return the file data. on the
	 * 
	 * @link http://context.io/docs/1.1/downloadfile
	 * @param account
	 *            accountId or email address of the mailbox you want to query
	 * @param params
	 *            Query parameters for the API call: 'fileId'
	 * @param saveAs
	 *            Path to local file where the attachment should be saved to.
	 * @return mixed
	 */
	public void downloadFile(String account, Map<String, String> params,
			File saveAs) {
		throw new UnsupportedOperationException("Not yet implemented, sorry.");
	}

	/**
	 * Returns a list of revisions attached to other emails in the mailbox for
	 * one or more given files (see fileid parameter below).
	 * 
	 * @link http://context.io/docs/1.1/filerevisions
	 * @param account
	 *            accountId or email address of the mailbox you want to query
	 * @param params
	 *            Query parameters for the API call: 'fileId', 'fileName'
	 * @return ContextIOResponse
	 * @throws Exception 
	 */
	public ContextIOResponse fileRevisions(String account,
			Map<String, String> params) throws Exception {
		params = filterParams(params, new String[] { "fileid", "filename" });

		return get(account, "filerevisions.json", params);
	}

	/**
	 * Returns a list of files that are related to the given file. Currently,
	 * relation between files is based on how similar their names are. You must
	 * specify either the fileId of fileName parameter
	 * 
	 * @link http://context.io/docs/1.1/relatedfiles
	 * @param account
	 *            accountId or email address of the mailbox you want to query
	 * @param params
	 *            Query parameters for the API call: 'fileId', 'fileName'
	 * @return ContextIOResponse
	 * @throws Exception 
	 */
	public ContextIOResponse relatedFiles(String account,
			Map<String, String> params) throws Exception {
		params = filterParams(params, new String[] { "fileid", "filename" });

		return get(account, "relatedfiles.json", params);
	}

	/**
	 * 
	 * @link http://context.io/docs/1.1/filesearch
	 * @param account
	 *            accountId or email address of the mailbox you want to query
	 * @param params
	 *            Query parameters for the API call: 'fileName'
	 * @return ContextIOResponse
	 * @throws Exception 
	 */
	public ContextIOResponse fileSearch(String account,
			Map<String, String> params) throws Exception {
		params = filterParams(params, new String[] { "filename" });

		return get(account, "filesearch.json", params);
	}

	/**
	 *
	 * @throws Exception 
	 * @link http://context.io/docs/1.1/imap/accountinfo
	 */
	public ContextIOResponse imap_accountInfo(Map<String, String> params) throws Exception {
		params = filterParams(params, new String[] { "email", "userid" });

		return get("", "imap/accountinfo.json", params);
	}

	/**
	 * @link http://context.io/docs/1.1/imap/addaccount
	 * @param params
	 *            Query parameters for the API call: 'email', 'server',
	 *            'username', 'password', 'oauthconsumername', 'oauthtoken',
	 *            'oauthtokensecret', 'usessl', 'port'
	 * @return ContextIOResponse
	 * @throws Exception 
	 */
	public ContextIOResponse imap_addAccount(Map<String, String> params) throws Exception {
		params = filterParams(params, new String[] { "email", "server",
				"username", "oauthconsumername", "oauthtoken",
				"oauthtokensecret", "password", "usessl", "port", "firstname",
				"lastname" });

		return get("", "imap/addaccount.json", params);
	}

	/**
	 * Attempts to discover IMAP settings for a given email address
	 * 
	 * @link http://context.io/docs/1.1/imap/discover
	 * @param params
	 *            either a string or assoc array with email as its key
	 * @return ContextIOResponse
	 * @throws Exception 
	 */
	public ContextIOResponse imap_discover(Map<String, String> params) throws Exception {
		// TODO: differs from original implementiation
		params = filterParams(params, new String[] { "email" });
		params.put("source_type", "IMAP");

		return get("", "discovery", params);
	}

	/**
	 * Modify the IMAP server settings of an already indexed account
	 * 
	 * @link http://context.io/docs/1.1/imap/modifyaccount
	 * @param params
	 *            Query parameters for the API call: 'credentials', 'mailboxes'
	 * @return ContextIOResponse
	 * @throws Exception 
	 */
	public ContextIOResponse imap_modifyAccount(String account,
			Map<String, String> params) throws Exception {
		params = filterParams(params,
				new String[] { "credentials", "mailboxes" });

		return get(account, "imap/modifyaccount.json", params);
	}

	/**
	 * Remove the connection to an IMAP account
	 * 
	 * @link http://context.io/docs/1.1/imap/removeaccount
	 * @return ContextIOResponse
	 * @throws Exception 
	 */
	public ContextIOResponse imap_removeAccount(String account,
			Map<String, String> params) throws Exception {
		params = filterParams(params, new String[] { "label" });

		return get(account, "imap/removeaccount.json", params);
	}

	/**
	 * When Context.IO can't connect to your IMAP server, the IMAP server gets
	 * flagged as unavailable in our database. Use this call to re-enable the
	 * syncing.
	 * 
	 * @link http://context.io/docs/1.1/imap/resetstatus
	 * @return ContextIOResponse
	 * @throws Exception 
	 */
	public ContextIOResponse imap_resetStatus(String account,
			Map<String, String> params) throws Exception {
		params = filterParams(params, new String[] { "label" });

		return get(account, "imap/resetstatus.json", params);
	}

	/**
	 *
	 * @throws Exception 
	 * @link http://context.io/docs/1.1/imap/oauthproviders
	 */
	public ContextIOResponse imap_deleteOAuthProvider(Map<String, String> params) throws Exception {
		params = filterParams(params, new String[] { "key" });

		params.put("action", "delete");

		return get("", "imap/oauthproviders.json", params);
	}

	/**
	 *
	 * @throws Exception 
	 * @link http://context.io/docs/1.1/imap/oauthproviders
	 */
	public ContextIOResponse imap_setOAuthProvider(Map<String, String> params) throws Exception {
		params = filterParams(params, new String[] { "type", "key", "secret" });

		return get("", "imap/oauthproviders.json", params);
	}

	/**
	 *
	 * @throws Exception 
	 * @link http://context.io/docs/1.1/imap/oauthproviders
	 */
	public ContextIOResponse imap_getOAuthProviders(Map<String, String> params) throws Exception {
		params = filterParams(params, new String[] { "key" });

		return get("", "imap/oauthproviders.json", params);
	}

	/**
	 * Returns the message headers of a message. A message can be identified by
	 * the value of its Message-ID header or by the combination of the date sent
	 * timestamp and email address of the sender.
	 * 
	 * @link http://context.io/docs/1.1/messageheaders
	 * @param account
	 *            accountId or email address of the mailbox you want to query
	 * @param params
	 *            Query parameters for the API call: 'emailMessageId', 'from',
	 *            'dateSent',
	 * @return ContextIOResponse
	 * @throws Exception 
	 */
	public ContextIOResponse messageHeaders(String account,
			Map<String, String> params) throws Exception {
		params = filterParams(params, new String[] { "emailmessageid", "from",
				"datesent" });

		return get(account, "messageheaders.json", params);
	}

	/**
	 * Returns document and contact information about a message. A message can
	 * be identified by the value of its Message-ID header or by the combination
	 * of the date sent timestamp and email address of the sender.
	 * 
	 * @link http://context.io/docs/1.1/messageinfo
	 * @param account
	 *            accountId or email address of the mailbox you want to query
	 * @param params
	 *            Query parameters for the API call: 'emailMessageId', 'from',
	 *            'dateSent', 'server', 'mbox', 'uid'
	 * @return ContextIOResponse
	 * @throws Exception 
	 */
	public ContextIOResponse messageInfo(String account,
			Map<String, String> params) throws Exception {
		params = filterParams(params, new String[] { "emailmessageid", "from",
				"datesent", "server", "mbox", "uid" });

		return get(account, "messageinfo.json", params);
	}

	/**
	 * Returns the message body (excluding attachments) of a message. A message
	 * can be identified by the value of its Message-ID header or by the
	 * combination of the date sent timestamp and email address of the sender.
	 * 
	 * @link http://context.io/docs/1.1/messagetext
	 * @param account
	 *            accountId or email address of the mailbox you want to query
	 * @param params
	 *            Query parameters for the API call: 'emailMessageId', 'from',
	 *            'dateSent','type
	 * @return ContextIOResponse
	 * @throws Exception 
	 */
	public ContextIOResponse messageText(String account,
			Map<String, String> params) throws Exception {
		params = filterParams(params, new String[] { "emailmessageid", "from",
				"datesent", "type" });

		return get(account, "messagetext.json", params);
	}

	/**
	 * Returns message information
	 * 
	 * @link http://context.io/docs/1.1/search
	 * @param account
	 *            accountId or email address of the mailbox you want to query
	 * @param params
	 *            Query parameters for the API call: 'subject', 'limit'
	 * @return ContextIOResponse
	 * @throws Exception 
	 */
	public ContextIOResponse search(String account, Map<String, String> params) throws Exception {
		params = filterParams(params, new String[] { "subject", "limit" });

		return get(account, "search.json", params);
	}

	/**
	 * Returns message and contact information about a given email thread.
	 * 
	 * @link http://context.io/docs/1.1/threadinfo
	 * @param account
	 *            accountId or email address of the mailbox you want to query
	 * @param params
	 *            Query parameters for the API call: 'gmailthreadid'
	 * @return ContextIOResponse
	 * @throws Exception 
	 */
	public ContextIOResponse threadInfo(String account,
			Map<String, String> params) throws Exception {
		params = filterParams(params, new String[] { "gmailthreadid",
				"emailmessageid" });

		return get(account, "threadinfo.json", params);
	}

	public boolean isSsl() {
		return ssl;
	}

	public String getApiVersion() {
		return API_VERSION;
	}

	public boolean isAuthHeaders() {
		return authHeaders;
	}

	/**
	 * Specify whether OAuth parameters should be included as URL query
	 * parameters or sent as HTTP Authorization headers. The default is URL
	 * query parameters.
	 * 
	 * @param authHeadersOn
	 *            Set to true to use HTTP Authorization headers, false to use
	 *            URL query params
	 */
	public void setAuthHeaders(boolean authHeaders) {
		this.authHeaders = authHeaders;
	}

	/**
	 * Returns the ContextIOResponse object for the last API call.
	 * 
	 * @return ContextIOResponse
	 */
	public ContextIOResponse getLastResponse() {
		return lastResponse;
	}

	public String build_baseurl() {
		String url = "http";
		if (ssl) {
			url = "https";
		}

		return url + "://" + ENDPOINT + "/" + API_VERSION + '/';
	}

	public String build_url(String action) {
		return build_baseurl() + action;
	}

	public boolean isSaveHeaders() {
		return saveHeaders;
	}

	public void setSaveHeaders(boolean saveHeaders) {
		this.saveHeaders = saveHeaders;
	}

	public ContextIOResponse[] get(String[] accounts, String action,
			Map<String, String> params) {
		ContextIOResponse[] responses = new ContextIOResponse[accounts.length];
		for (int i = 0; i < accounts.length; i++) {
			responses[i] = doCall(Verb.GET, accounts[i], action, params);
		}

		return responses;
	}

	public ContextIOResponse get(String account, String action,
			Map<String, String> params) throws Exception {
		return doCall(Verb.GET, account, action, params);
	}

	public ContextIOResponse post(String account, String action,
			Map<String, String> params) throws Exception {
		return doCall(Verb.POST, account, action, params);
	}

	public ContextIOResponse doCall(Verb method, String account, String action,
			Map<String, String> params) {
		// TODO: differs from original implementiation

		if (account != null && !account.equals("")) {
			if (params == null) {
				params = new HashMap<String, String>();
			}

			params.put("account", account);
		}

		String baseUrl = build_url(action);
		if (params != null && params.size() > 0 && Verb.GET.equals(method)) {
			baseUrl = URLUtils.appendParametersToQueryString(baseUrl, params);
		}

		OAuthService service = new ServiceBuilder()
				.provider(ContextIOApi.class).apiKey(this.key)
				.apiSecret(this.secret).build();
		OAuthRequest request = new OAuthRequest(method, baseUrl);

		if (params != null && params.size() > 0 && Verb.POST.equals(method)
				|| Verb.PUT.equals(method)) {
			for (Entry<String, String> entry : params.entrySet()) {
				if (entry.getValue() != null) {
					request.addBodyParameter(entry.getKey(), entry.getValue());
				}
			}
		}

		Token nullToken = new Token("", "");
		service.signRequest(nullToken, request);

		Response oauthResponse = request.send();

		lastResponse = new ContextIOResponse(oauthResponse.getCode(),
				request.getHeaders(), oauthResponse.getHeaders(), oauthResponse);
		if (lastResponse.isHasError()) {
			return null;
		} else {
			return lastResponse;
		}
	}

	public Map<String, String> filterParams(Map<String, String> givenParams,
			String[] validParams) {
		Map<String, String> filteredParams = new HashMap<String, String>();

		for (String validKey : validParams) {
			for (String givenKey : givenParams.keySet()) {
				if (givenKey.equalsIgnoreCase(validKey)) {
					filteredParams.put(validKey, givenParams.get(givenKey));
				}
			}
		}

		return filteredParams;
	}
}
