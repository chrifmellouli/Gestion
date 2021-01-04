/**
 * The model layer of the application
 */
package com.erobot.gestion.models;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;

/**
 * @author CHRIF MELLOULI
 *
 */
@Entity
@Table(name = "USERS")
public class User implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1825510597943773003L;

	private static final String ALPHANUMERIC_PATTERN = "[a-zA-Z0-9]*";
	private static final String ALPHANUMERIC_PATTERN_NOT_RESPECTED_MESSAGE = "This field only accepts alphanumeric characters.";
	private static final String LENGTH_PATTERN_NOT_RESPECTED_MESSAGE = "This field length's should be between ";

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID_USER")
	private int id;
	@Length(min = 4, max = 50, message = LENGTH_PATTERN_NOT_RESPECTED_MESSAGE + "4 and 50")
	@Pattern(regexp = ALPHANUMERIC_PATTERN, message = ALPHANUMERIC_PATTERN_NOT_RESPECTED_MESSAGE)
	@Column(name = "USER_NAME")
	private String username;
	@Length(min = 4, max = 50, message = LENGTH_PATTERN_NOT_RESPECTED_MESSAGE + "4 and 50")
	@Pattern(regexp = ALPHANUMERIC_PATTERN, message = ALPHANUMERIC_PATTERN_NOT_RESPECTED_MESSAGE)
	@Column(name = "PASSWORD")
	private String password;
	@Column(name = "ISCONN")
	private int is;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_ROLE")
	private Role role;

	public User() {
		// This is a default constructor
	}

	public User(
			@Length(min = 4, max = 50) @Pattern(regexp = "[a-zA-Z0-9]*", message = "This field only accepts alphanumeric characters.") String username,
			@Length(min = 4, max = 50) @Pattern(regexp = "[a-zA-Z0-9]*", message = "This field only accepts alphanumeric characters.") String password,
			int is, Role role) {
		super();
		this.username = username;
		this.password = password;
		this.is = is;
		this.role = role;
	}

	public User(int id,
			@Length(min = 4, max = 50) @Pattern(regexp = "[a-zA-Z0-9]*", message = "This field only accepts alphanumeric characters.") String username,
			@Length(min = 4, max = 50) @Pattern(regexp = "[a-zA-Z0-9]*", message = "This field only accepts alphanumeric characters.") String password,
			int is, Role role) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
		this.is = is;
		this.role = role;
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
	 * @return the is
	 */
	public int getIs() {
		return is;
	}

	/**
	 * @param is the is to set
	 */
	public void setIs(int is) {
		this.is = is;
	}

	/**
	 * @return the role
	 */
	public Role getRole() {
		return role;
	}

	/**
	 * @param role the role to set
	 */
	public void setRole(Role role) {
		this.role = role;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, is, password, role, username);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof User)) {
			return false;
		}
		User other = (User) obj;
		return id == other.id && is == other.is && Objects.equals(password, other.password)
				&& Objects.equals(role, other.role) && Objects.equals(username, other.username);
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", username=" + username + ", password=" + password + ", is=" + is + ", role=" + role
				+ "]";
	}

}
