/**
 * The model layer of the application
 */
package com.erobot.gestion.models;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Pattern;

@Entity
@Table(name = "PRIVILEGE")
public class Privilege implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6945142103068151971L;
	private static final String ALPHANUMERIC_PATTERN = "[a-zA-Z0-9\s]*";
	private static final String ALPHANUMERIC_PATTERN_NOT_RESPECTED_MESSAGE = "This field only accepts alphanumeric characters.";

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID_PRIVILEGE")
	private int id;

	@Pattern(regexp = ALPHANUMERIC_PATTERN, message = ALPHANUMERIC_PATTERN_NOT_RESPECTED_MESSAGE)
	@Column(name = "DESIGNATION")
	private String designation;

	public Privilege() {
		/*
		 * This is a default constructor.
		 */
	}

	public Privilege(
			@Pattern(regexp = "[a-zA-Z0-9 ]*", message = "This field only accepts alphanumeric characters.") String designation) {
		super();
		this.designation = designation;
	}

	public Privilege(int id,
			@Pattern(regexp = "[a-zA-Z0-9 ]*", message = "This field only accepts alphanumeric characters.") String designation) {
		super();
		this.id = id;
		this.designation = designation;
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
	 * @return the designation
	 */
	public String getDesignation() {
		return designation;
	}

	/**
	 * @param designation the designation to set
	 */
	public void setDesignation(String designation) {
		this.designation = designation;
	}

	@Override
	public int hashCode() {
		return Objects.hash(designation, id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof Privilege)) {
			return false;
		}
		Privilege other = (Privilege) obj;
		return Objects.equals(designation, other.designation) && id == other.id;
	}

	@Override
	public String toString() {
		return "Privilege [id=" + id + ", designation=" + designation + "]";
	}

}
