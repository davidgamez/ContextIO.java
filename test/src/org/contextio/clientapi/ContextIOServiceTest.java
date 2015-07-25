/**
 * 
 */
package org.contextio.clientapi;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.contextio.common.AccountSource;
import org.contextio.common.EmailMessage;
import org.contextio.common.EmailMessageFilter;
import org.easymock.EasyMock;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.easymock.PowerMock;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

/**
 * Test related to the class {@link ContextIOService}
 * This class will perform unit tests, so no real REST calls will be executed
 * @author David
 *
 */
@RunWith(PowerMockRunner.class)
// The following annotation was added to avoid an exception when trying to sign
// OAUTH requests:
// Exception: com.sun.crypto.provider.HmacSHA1 cannot be cast to
// javax.crypto.MacSpi
@PowerMockIgnore("javax.crypto.*")
public class ContextIOServiceTest {

    private JsonParser parser = new JsonParser();

    /**
     * Test {@link ContextIOService#getAccountSources(String, org.contextio.common.AccountSourceStatus)}
     * Use case: one account is returned by the mocked REST layer  
     * @throws Exception
     */
    @Test
    public void testGetAccountSourcesOneAccount() throws Exception {
	String oneAccountJson = "[{\"server\":\"imap.mail.yahoo.com\",\"label\":\"myemail@yahoo.com::yahoo\",\"username\":\"myemail@yahoo.com\",\"port\":993,\"authentication_type\":\"password\",\"status\":\"OK\",\"use_ssl\":true,\"type\":\"imap\",\"resource_url\":\"https://api.context.io/2.0/accounts/dummyId/sources/myemail@yahoo.com::yahoo\"}]";
	List<AccountSource> sources = getAccountSources(oneAccountJson);
	
	Assert.assertNotNull(sources);
	Assert.assertEquals(1, sources.size());
	Assert.assertEquals("imap.mail.yahoo.com", sources.get(0).getServer());
	Assert.assertEquals("myemail@yahoo.com", sources.get(0).getUsername());
	Assert.assertEquals("993", sources.get(0).getPort());
    }

    /**
     * Test {@link ContextIOService#getAccountSources(String, org.contextio.common.AccountSourceStatus)}
     * Use case: no account is returned by the mocked REST layer   
     * @throws Exception
     */
    @Test
    public void testGetAccountSourcesNoAccount() throws Exception {
	String oneAccountJson = "[]";
	List<AccountSource> sources = getAccountSources(oneAccountJson);
	
	Assert.assertNotNull(sources);
	Assert.assertEquals(0, sources.size());
    }

    /**
     * Test {@link ContextIOService#getAccountSources(String, org.contextio.common.AccountSourceStatus)}
     * Use case: two accounts is returned by the mocked REST layer  
     * @throws Exception
     */
    @Test
    public void testGetAccountSourcesMultipleAccount() throws Exception {
	String oneAccountJson = "[{\"server\":\"imap.mail.yahoo.com\",\"label\":\"myemail@yahoo.com::yahoo\",\"username\":\"myemail@yahoo.com\",\"port\":993,\"authentication_type\":\"password\",\"status\":\"OK\",\"use_ssl\":true,\"type\":\"imap\",\"resource_url\":\"https://api.context.io/2.0/accounts/dummyId/sources/myemail@yahoo.com::yahoo\"},";
	oneAccountJson += "{\"server\":\"imap1.mail.yahoo.com\",\"label\":\"myemail@yahoo.com::yahoo\",\"username\":\"myemail1@yahoo.com\",\"port\":994,\"authentication_type\":\"password\",\"status\":\"OK\",\"use_ssl\":true,\"type\":\"imap\",\"resource_url\":\"https://api.context.io/2.0/accounts/dummyId/sources/myemail@yahoo.com::yahoo\"}]";
	List<AccountSource> sources = getAccountSources(oneAccountJson);
	
	Assert.assertNotNull(sources);
	Assert.assertEquals(2, sources.size());
	
	Assert.assertEquals("imap.mail.yahoo.com", sources.get(0).getServer());
	Assert.assertEquals("myemail@yahoo.com", sources.get(0).getUsername());
	Assert.assertEquals("993", sources.get(0).getPort());
	
	Assert.assertEquals("imap1.mail.yahoo.com", sources.get(1).getServer());
	Assert.assertEquals("myemail1@yahoo.com", sources.get(1).getUsername());
	Assert.assertEquals("994", sources.get(1).getPort());
    }
    
    /**
     * Internal method to mock the REST calls and return the JSON formed as <code>jsonString</code>
     * @param jsonString
     * @return
     * @throws Exception
     */
    private List<AccountSource> getAccountSources(String jsonString) throws Exception {
	ContextIOService service = PowerMock.createPartialMockForAllMethodsExcept(ContextIOService.class,
		"getAccountSources", "getListFromResponse");

	Whitebox.setInternalState(service, Gson.class, new GsonBuilder().setPrettyPrinting().create());

	ContextIOResponse response = PowerMock.createMock(ContextIOResponse.class);
	JsonElement jsonElement = parser.parse(jsonString);;
	EasyMock.expect(response.getJson()).andReturn(jsonElement);
	EasyMock.replay(response);
	
	EasyMock.expect(service.get("dummyId", "accounts/dummyId/sources", new HashMap<String, String>())).andReturn(response);
	EasyMock.replay(service);
	
	return service.getAccountSources("dummyId", null);
    }

    @Test
    public void testGetMessages() throws Exception{
	ContextIOService service = PowerMock.createPartialMockForAllMethodsExcept(ContextIOService.class,
		"getMessages", "getListFromResponse");
	
	Whitebox.setInternalState(service, Gson.class, new GsonBuilder().setPrettyPrinting().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create());
	
	ContextIOResponse response = PowerMock.createMock(ContextIOResponse.class);
	JsonArray jsonArray = (JsonArray) parser.parse(getJSONFromFile("test_messages.json"));;
	EasyMock.expect(response.getJson()).andReturn(jsonArray);
	EasyMock.replay(response);
	
	EasyMock.expect(service.get("dummyId", "accounts/dummyId/messages", new HashMap<String, String>())).andReturn(response);
	EasyMock.replay(service);

	Map<EmailMessageFilter, String> filterParams = new HashMap<EmailMessageFilter, String>();
	List<EmailMessage> messages = service.getMessages("dummyId", filterParams);
	
	Assert.assertNotNull(messages);
	
	assertMessages(messages, jsonArray);
    }
    
    private void assertMessages(List<EmailMessage> messages, JsonArray jsonArray) {
	Assert.assertTrue(messages.size() == jsonArray.size());
//	sorting the lists first to be able to compare later with the same sorting criteria
	Collections.sort(messages, new Comparator<EmailMessage>() {

	    @Override
	    public int compare(EmailMessage o1, EmailMessage o2) {
		return o1.getMessageId().compareTo(o2.getMessageId());
	    }
	});
	
	
	List<JsonElement>  messagesElements = getJsonElementList(jsonArray);
	Collections.sort(messagesElements, new Comparator<JsonElement>() {

	    @Override
	    public int compare(JsonElement o1, JsonElement o2) {
		return o1.getAsJsonObject().get("message_id").getAsString().compareTo(o2.getAsJsonObject().get("message_id").getAsString());
	    }
	});
	
	for (int i = 0; i < messages.size(); i++){
	    EmailMessage message = messages.get(i);
	    JsonElement jsonElement = messagesElements.get(i);
	    
	    Assert.assertTrue(jsonElement instanceof JsonObject);	    
	    JsonObject jsonObject = (JsonObject) jsonElement;
	    Assert.assertEquals(message.getEmailMessageId(), getSafeJsonProperty(jsonObject, "email_message_id"));
	    Assert.assertEquals(message.getDate(), jsonObject.get("date").getAsLong());
	    Assert.assertEquals(message.getGmailMessageId(), getSafeJsonProperty(jsonObject, "gmail_message_id"));
	    Assert.assertEquals(message.getGmailThreadId(), getSafeJsonProperty(jsonObject, "gmail_thread_id"));
	    Assert.assertEquals(message.getMessageId(), getSafeJsonProperty(jsonObject, "message_id"));
	    Assert.assertEquals(message.getSubject(), getSafeJsonProperty(jsonObject, "subject"));
	    
	    Assert.assertEquals(message.getAddresses().getFrom().getEmail(), jsonObject.get("addresses").getAsJsonObject().get("from").getAsJsonObject().get("email").getAsString());
	    Assert.assertEquals(message.getAddresses().getFrom().getName(), jsonObject.get("addresses").getAsJsonObject().get("from").getAsJsonObject().get("name").getAsString());
//	    TODO DGD add cc, bcc and reply_to properties
//	   TODO DGD add asserts on folders
	}
    }

    /**
     * 
     * @param jsonObject instance
     * @param property name
     * @return the String value of the property of null if the property is not set
     */
    private String getSafeJsonProperty(JsonObject jsonObject, String property) {
	return jsonObject.get(property)!= null?jsonObject.get(property).getAsString():null;
    }

    /**
     * 
     * @param jsonArray to convert
     * @return a list with the JsonElements from <code>jsonArray</code>
     */
    private List<JsonElement> getJsonElementList(JsonArray jsonArray) {
	List<JsonElement> result = new ArrayList<JsonElement>();
	if (jsonArray != null){
	    for (int i = 0; i < jsonArray.size(); i++){
		result.add(jsonArray.get(i));
	    }
	}
	return result;
    }

    /**
     * Returns the content of a file that is located in the class path 
     * @param filename name of the file
     * @return a string with the content of the file denoted by <code>filename</code>
     * @throws IOException
     */
    private String getJSONFromFile(String filename) throws IOException{
	InputStream inputStream = this.getClass().getResourceAsStream(filename);
	String result = IOUtils.toString(inputStream);
	IOUtils.closeQuietly(inputStream);
	return result;
    }
}
