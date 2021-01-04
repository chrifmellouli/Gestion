package com.erobot.gestion.models;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "AUTHORIZATIONS")
public class Authorization implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private AuthorizationPK authorizationPK;
	private boolean permission;

	public Authorization() {
		/**
		 * This is a default constructor.
		 */
	}

	public Authorization(AuthorizationPK authorizationPK, boolean permission) {
		super();
		this.authorizationPK = authorizationPK;
		this.permission = permission;
	}

	/**
	 * @return the authorizationPK
	 */
	public AuthorizationPK getAuthorizationPK() {
		return authorizationPK;
	}

	/**
	 * @param authorizationPK the authorizationPK to set
	 */
	public void setAuthorizationPK(AuthorizationPK authorizationPK) {
		this.authorizationPK = authorizationPK;
	}

	/**
	 * @return
	 * @see com.erobot.gestion.models.AuthorizationPK#getRole()
	 */
	public Role getRole() {
		return authorizationPK.getRole();
	}

	/**
	 * @param role
	 * @see com.erobot.gestion.models.AuthorizationPK#setRole(com.erobot.gestion.models.Role)
	 */
	public void setRole(Role role) {
		authorizationPK.setRole(role);
	}

	/**
	 * @return
	 * @see com.erobot.gestion.models.AuthorizationPK#getPrivilege()
	 */
	public Privilege getPrivilege() {
		return authorizationPK.getPrivilege();
	}

	/**
	 * @param privilege
	 * @see com.erobot.gestion.models.AuthorizationPK#setPrivilege(com.erobot.gestion.models.Privilege)
	 */
	public void setPrivilege(Privilege privilege) {
		authorizationPK.setPrivilege(privilege);
	}

	/**
	 * @return the permission
	 */
	public boolean isPermission() {
		return permission;
	}

	/**
	 * @param permission the permission to set
	 */
	public void setPermission(boolean permission) {
		this.permission = permission;
	}

	@Override
	public int hashCode() {
		return Objects.hash(authorizationPK, permission);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof Authorization)) {
			return false;
		}
		Authorization other = (Authorization) obj;
		return Objects.equals(authorizationPK, other.authorizationPK) && permission == other.permission;
	}

	@Override
	public String toString() {
		return "Authorization [authorizationPK=" + authorizationPK + ", permission=" + permission + "]";
	}

}
