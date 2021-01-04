/**
 * The model layer of the application
 */
package com.erobot.gestion.models;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;

/**
 * @author CHRIF MELLOULI
 *
 */
@Entity
@Table(name = "CUSTOMER")
public class Customer {

	private static final String ALPHANUMERIC_PATTERN = "[a-zA-Z0-9\s]*";
	private static final String ALPHANUMERIC_PATTERN_NOT_RESPECTED_MESSAGE = "This field only accepts alphanumeric characters.";
	private static final String NUMERIC_PATTERN_NOT_RESPECTED_MESSAGE = "This field only accepts numeric characters.";
	private static final String NUMERIC_PATTERN = "[0-9]*";
	private static final String LENGTH_PATTERN_NOT_RESPECTED_MESSAGE = "This field length's should be between ";

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID_CUSTOMER")
	private int id;
	@Length(min = 4, max = 50,message = LENGTH_PATTERN_NOT_RESPECTED_MESSAGE+"4 and 50")
	@Pattern(regexp = ALPHANUMERIC_PATTERN, message = ALPHANUMERIC_PATTERN_NOT_RESPECTED_MESSAGE)
	@Column(name = "NAME")
	private String name;
	@Length(min = 3, max = 50,message = LENGTH_PATTERN_NOT_RESPECTED_MESSAGE+"3 and 50")
	@Pattern(regexp = ALPHANUMERIC_PATTERN, message = ALPHANUMERIC_PATTERN_NOT_RESPECTED_MESSAGE)
	@Column(name = "LAST_NAME")
	private String lastName;
	@Length(min = 3, max = 50,message = LENGTH_PATTERN_NOT_RESPECTED_MESSAGE+"3 and 50")
	@Pattern(regexp = NUMERIC_PATTERN, message = NUMERIC_PATTERN_NOT_RESPECTED_MESSAGE)
	@Column(name = "PHONE_NUMBER")
	private String phoneNumber;
	@Length(min = 4, max = 50,message = LENGTH_PATTERN_NOT_RESPECTED_MESSAGE+"4 and 50")
	@Column(name = "ADRESS")
	private String address;

	public Customer() {
		// This is a default constructor
	}

	public Customer(
			@Length(min = 4, max = 50) @Pattern(regexp = "[a-zA-Z0-9]", message = "This field only accepts alphanumeric characters.") String name,
			@Length(min = 3, max = 50) @Pattern(regexp = "[a-zA-Z0-9]", message = "This field only accepts alphanumeric characters.") String lastName,
			@Length(min = 3, max = 50) @Pattern(regexp = "[0-9]", message = "This field only accepts numeric characters.") String phoneNumber,
			@Length(min = 4, max = 50) String address) {
		super();
		this.name = name;
		this.lastName = lastName;
		this.phoneNumber = phoneNumber;
		this.address = address;
	}

	public Customer(int id,
			@Length(min = 4, max = 50) @Pattern(regexp = "[a-zA-Z0-9]", message = "This field only accepts alphanumeric characters.") String name,
			@Length(min = 3, max = 50) @Pattern(regexp = "[a-zA-Z0-9]", message = "This field only accepts alphanumeric characters.") String lastName,
			@Length(min = 3, max = 50) @Pattern(regexp = "[0-9]", message = "This field only accepts numeric characters.") String phoneNumber,
			@Length(min = 4, max = 50) String address) {
		super();
		this.id = id;
		this.name = name;
		this.lastName = lastName;
		this.phoneNumber = phoneNumber;
		this.address = address;
	}

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
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

	/**
	 * @return the phoneNumber
	 */
	public String getPhoneNumber() {
		return phoneNumber;
	}

	/**
	 * @param phoneNumber the phoneNumber to set
	 */
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	/**
	 * @return the address
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * @param address the address to set
	 */
	public void setAddress(String address) {
		this.address = address;
	}

	@Override
	public int hashCode() {
		return Objects.hash(address, id, lastName, name, phoneNumber);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof Customer)) {
			return false;
		}
		Customer other = (Customer) obj;
		return Objects.equals(address, other.address) && id == other.id && Objects.equals(lastName, other.lastName)
				&& Objects.equals(name, other.name) && Objects.equals(phoneNumber, other.phoneNumber);
	}

	@Override
	public String toString() {
		return "Customer [id=" + id + ", name=" + name + ", lastName=" + lastName + ", phoneNumber=" + phoneNumber
				+ ", address=" + address + "]";
	}

}
