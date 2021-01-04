package com.erobot.gestion.models;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Embeddable
public class AuthorizationPK implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8998577212684486548L;
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "ID_ROLE")
	private Role role;

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "ID_PRIVILEGE")
	private Privilege privilege;

	public AuthorizationPK() {
		/**
		 * This is a default constructor.
		 */
	}

	public AuthorizationPK(Role role, Privilege privilege) {
		super();
		this.role = role;
		this.privilege = privilege;
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

	/**
	 * @return the privilege
	 */
	public Privilege getPrivilege() {
		return privilege;
	}

	/**
	 * @param privilege the privilege to set
	 */
	public void setPrivilege(Privilege privilege) {
		this.privilege = privilege;
	}

	@Override
	public int hashCode() {
		return Objects.hash(privilege, role);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof AuthorizationPK)) {
			return false;
		}
		AuthorizationPK other = (AuthorizationPK) obj;
		return Objects.equals(privilege, other.privilege) && Objects.equals(role, other.role);
	}

	@Override
	public String toString() {
		return "AuthorizationPK [role=" + role + ", privilege=" + privilege + "]";
	}

}
