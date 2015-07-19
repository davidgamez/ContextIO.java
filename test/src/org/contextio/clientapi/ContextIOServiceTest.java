/**
 * 
 */
package org.contextio.clientapi;

import java.util.HashMap;
import java.util.List;

import org.contextio.common.AccountSource;
import org.easymock.EasyMock;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.easymock.PowerMock;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
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
     * Internal method to mock the call REST calls and return the JSON formed as <code>jsonString</code>
     * @param jsonString
     * @return
     * @throws Exception
     */
    private List<AccountSource> getAccountSources(String jsonString) throws Exception {
	ContextIOService service = PowerMock.createPartialMockForAllMethodsExcept(ContextIOService.class,
		"getAccountSources");

	Whitebox.setInternalState(service, Gson.class, new GsonBuilder().setPrettyPrinting().create());

	ContextIOResponse response = PowerMock.createMock(ContextIOResponse.class);
	JsonElement jsonElement = parser.parse(jsonString);;
	EasyMock.expect(response.getJson()).andReturn(jsonElement);
	EasyMock.replay(response);
	
	EasyMock.expect(service.get("", "accounts/dummyId/sources", new HashMap<String, String>())).andReturn(response);
	EasyMock.replay(service);
	
	return service.getAccountSources("dummyId", null);
    }

}
