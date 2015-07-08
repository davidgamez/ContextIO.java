/**
 * 
 */
package org.contextio.common;

import java.util.HashMap;
import java.util.Map;

/**
 * @author David
 *
 */
public class Account implements ParametersMapeable{

	private String id;
	private String email;
	private String firstName;
	private String lastName;
	
	@Override
	public Map<String, String> toParametersMap() {
		Map<String, String> result = new HashMap<String, String>();
		if (id != null){
			result.put("", id);			
		}
		result.put("email", email);
		result.put("first_name", firstName);
		result.put("last_name", lastName);
		return result;
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
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
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * @param firstName the firstName to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * @return the lastName
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * @param lastName the lastName to set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
}
