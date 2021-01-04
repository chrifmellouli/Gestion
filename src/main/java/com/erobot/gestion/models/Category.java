/**
 * The model layer of the application
 */
package com.erobot.gestion.models;

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
@Table(name = "CATEGORY")
public class Category {

	private static final String ALPHANUMERIC_PATTERN = "[a-zA-Z0-9\s]*";
	private static final String ALPHANUMERIC_PATTERN_NOT_RESPECTED_MESSAGE = "This field only accepts alphanumeric characters.";
	private static final String LENGTH_PATTERN_NOT_RESPECTED_MESSAGE = "This field length's should be between ";

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID_CATEGORY")
	private int id;
	@Length(min = 4, max = 6, message = LENGTH_PATTERN_NOT_RESPECTED_MESSAGE + "4 and 6")
	@Pattern(regexp = ALPHANUMERIC_PATTERN, message = ALPHANUMERIC_PATTERN_NOT_RESPECTED_MESSAGE)
	@Column(name = "CODE")
	private String code;
	@Length(min = 4, max = 50, message = LENGTH_PATTERN_NOT_RESPECTED_MESSAGE + "4 and 50")
	@Pattern(regexp = ALPHANUMERIC_PATTERN, message = ALPHANUMERIC_PATTERN_NOT_RESPECTED_MESSAGE)
	@Column(name = "DESIGNATION")
	private String designation;
	@OneToMany(mappedBy = "category")
	private List<Product> products;

	public Category() {
		// This is a default constructor
	}

	public Category(
			@Length(min = 4, max = 6) @Pattern(regexp = "[a-zA-Z0-9]", message = "This field only accepts alphanumeric characters.") String code,
			@Length(min = 4, max = 50) @Pattern(regexp = "[a-zA-Z0-9]", message = "This field only accepts alphanumeric characters.") String designation,
			List<Product> products) {
		super();
		this.code = code;
		this.designation = designation;
		this.products = products;
	}

	public Category(int id,
			@Length(min = 4, max = 6) @Pattern(regexp = "[a-zA-Z0-9]", message = "This field only accepts alphanumeric characters.") String code,
			@Length(min = 4, max = 50) @Pattern(regexp = "[a-zA-Z0-9]", message = "This field only accepts alphanumeric characters.") String designation,
			List<Product> products) {
		super();
		this.id = id;
		this.code = code;
		this.designation = designation;
		this.products = products;
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
	 * @return the code
	 */
	public String getCode() {
		return code;
	}

	/**
	 * @param code the code to set
	 */
	public void setCode(String code) {
		this.code = code;
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

	/**
	 * @return the products
	 */
	public List<Product> getProducts() {
		return products;
	}

	/**
	 * @param products the products to set
	 */
	public void setProducts(List<Product> products) {
		this.products = products;
	}

	@Override
	public int hashCode() {
		return Objects.hash(code, designation, id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof Category)) {
			return false;
		}
		Category other = (Category) obj;
		return Objects.equals(code, other.code) && Objects.equals(designation, other.designation) && id == other.id;
	}

	@Override
	public String toString() {
		return "Category [id=" + id + ", code=" + code + ", designation=" + designation + "]";
	}

}
