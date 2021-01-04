/**
 * The model layer of the application
 */
package com.erobot.gestion.models;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;

/**
 * @author CHRIF MELLOULI
 *
 */
@Entity
@Table(name = "ROLES")
public class Role implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8786946755574330043L;

	private static final String ALPHANUMERIC_PATTERN = "[a-zA-Z0-9\s]*";
	private static final String ALPHANUMERIC_PATTERN_NOT_RESPECTED_MESSAGE = "This field only accepts alphanumeric characters.";
	private static final String LENGTH_PATTERN_NOT_RESPECTED_MESSAGE = "This field length's should be between ";

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID_ROLE")
	private int id;
	@Length(min = 4, max = 50, message = LENGTH_PATTERN_NOT_RESPECTED_MESSAGE + "4 and 50")
	@Pattern(regexp = ALPHANUMERIC_PATTERN, message = ALPHANUMERIC_PATTERN_NOT_RESPECTED_MESSAGE)
	@Column(name = "ROLE_USER")
	private String roleUser;
	@OneToMany(mappedBy = "role")
	private List<User> users;

	public Role() {
		// This is a default constructor
	}

	public Role(
			@Length(min = 4, max = 50) @Pattern(regexp = "[a-zA-Z0-9]", message = "This field only accepts alphanumeric characters.") String roleUser) {
		super();
		this.roleUser = roleUser;
	}

	public Role(int id,
			@Length(min = 4, max = 50) @Pattern(regexp = "[a-zA-Z0-9]", message = "This field only accepts alphanumeric characters.") String roleUser) {
		super();
		this.id = id;
		this.roleUser = roleUser;
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
	 * @return the roleUser
	 */
	public String getRoleUser() {
		return roleUser;
	}

	/**
	 * @param roleUser the roleUser to set
	 */
	public void setRoleUser(String roleUser) {
		this.roleUser = roleUser;
	}

	/**
	 * @return the users
	 */
	public List<User> getUsers() {
		return users;
	}

	/**
	 * @param users the users to set
	 */
	public void setUsers(List<User> users) {
		this.users = users;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, roleUser);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof Role)) {
			return false;
		}
		Role other = (Role) obj;
		return id == other.id && Objects.equals(roleUser, other.roleUser);
	}

	@Override
	public String toString() {
		return "Role [id=" + id + ", roleUser=" + roleUser + "]";
	}

}
