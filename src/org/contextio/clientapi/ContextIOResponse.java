package org.contextio.clientapi;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.scribe.model.Response;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

/**
 * 
 * @author Thomas Taschauer | tomtasche.at
 *
 */
public class ContextIOResponse {

    private static final Integer[] SUCCESS_HTTP_RESPONSE_CODES = new Integer[] { 200, 201, 202 };

    private JsonParser parser = new JsonParser();
    private Gson prettyGson = new GsonBuilder().setPrettyPrinting().create();

    private int code;
    private Map<String, String> headers;
    private Map<String, String> requestHeaders;
    private Map<String, String> responseHeaders;
    private String contentType;
    private Response rawResponse;
    private boolean hasError;
    private JsonElement json;
    private List<ContextIOResponseMessage> messages = null;

    public ContextIOResponse(int code, Map<String, String> requestHeaders, Map<String, String> responseHeaders,
	    Response rawResponse) {
	this.code = code;
	this.requestHeaders = requestHeaders;
	this.responseHeaders = responseHeaders;
	this.rawResponse = rawResponse;
	this.contentType = rawResponse.getHeader("Content-Type");
	// TODO: this.headers = ;
    }

    public void decodeResponse() {
	if (!Arrays.asList(SUCCESS_HTTP_RESPONSE_CODES).contains(code) || !contentType.equals("application/json")) {
	    hasError = true;
	}

	json = parser.parse(rawResponse.getBody());
	if (json.isJsonObject() && json.getAsJsonObject().has("messages")
		&& json.getAsJsonObject().get("messages").isJsonArray()
		&& json.getAsJsonObject().get("messages").getAsJsonArray().size() > 0) {
	    hasError = true;
	    messages = new ArrayList<ContextIOResponseMessage>();
	    for (JsonElement message : json.getAsJsonObject().get("messages").getAsJsonArray()) {
		ContextIOResponseMessage msg = prettyGson.fromJson(message, ContextIOResponseMessage.class);
		messages.add(msg);
	    }
	} else if (json.isJsonObject() && json.getAsJsonObject().has("type")
		&& json.getAsJsonObject().get("type").getAsString().equalsIgnoreCase("error")) {
	    ContextIOResponseMessage msg = prettyGson.fromJson(json, ContextIOResponseMessage.class);
	    hasError = true;
	    messages = new ArrayList<ContextIOResponseMessage>();
	    messages.add(msg);
	}
    }

    @Override
    public String toString() {
	return "ContextIOResponse [code=" + code + ", headers=" + headers + ", requestHeaders=" + requestHeaders
		+ ", responseHeaders=" + responseHeaders + ", contentType=" + contentType + ", rawResponse="
		+ rawResponse + ", hasError=" + hasError + ", response=" + rawResponse.getBody() + "]";
    }

    /**
     * @return the code
     */
    public int getCode() {
	return code;
    }

    /**
     * @param code
     *            the code to set
     */
    public void setCode(int code) {
	this.code = code;
    }

    /**
     * @return the headers
     */
    public Map<String, String> getHeaders() {
	return headers;
    }

    /**
     * @param headers
     *            the headers to set
     */
    public void setHeaders(Map<String, String> headers) {
	this.headers = headers;
    }

    /**
     * @return the requestHeaders
     */
    public Map<String, String> getRequestHeaders() {
	return requestHeaders;
    }

    /**
     * @param requestHeaders
     *            the requestHeaders to set
     */
    public void setRequestHeaders(Map<String, String> requestHeaders) {
	this.requestHeaders = requestHeaders;
    }

    /**
     * @return the responseHeaders
     */
    public Map<String, String> getResponseHeaders() {
	return responseHeaders;
    }

    /**
     * @param responseHeaders
     *            the responseHeaders to set
     */
    public void setResponseHeaders(Map<String, String> responseHeaders) {
	this.responseHeaders = responseHeaders;
    }

    /**
     * @return the contentType
     */
    public String getContentType() {
	return contentType;
    }

    /**
     * @param contentType
     *            the contentType to set
     */
    public void setContentType(String contentType) {
	this.contentType = contentType;
    }

    /**
     * @return the rawResponse
     */
    public Response getRawResponse() {
	return rawResponse;
    }

    /**
     * @param rawResponse
     *            the rawResponse to set
     */
    public void setRawResponse(Response rawResponse) {
	this.rawResponse = rawResponse;
    }

    /**
     * @return the hasError
     */
    public boolean isHasError() {
	return hasError;
    }

    /**
     * @param hasError
     *            the hasError to set
     */
    public void setHasError(boolean hasError) {
	this.hasError = hasError;
    }

    /**
     * @return the json
     */
    public JsonElement getJson() {
	return json;
    }

    /**
     * @param json
     *            the json to set
     */
    public void setJson(JsonElement json) {
	this.json = json;
    }

    /**
     * @return the messages
     */
    public List<ContextIOResponseMessage> getMessages() {
	return messages;
    }

    /**
     * @param messages
     *            the messages to set
     */
    public void setMessages(List<ContextIOResponseMessage> messages) {
	this.messages = messages;
    }

}
