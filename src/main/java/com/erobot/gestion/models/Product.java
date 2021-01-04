/**
 * The model layer of the application
 */
package com.erobot.gestion.models;

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
import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;
import org.springframework.format.annotation.NumberFormat;
import org.springframework.format.annotation.NumberFormat.Style;

/**
 * @author CHRIF MELLOULI
 *
 */
@Entity
@Table(name = "PRODUCT")
public class Product {

	private static final String ALPHANUMERIC_PATTERN = "[a-zA-Z0-9\s]*";
	private static final String ALPHANUMERIC_PATTERN_NOT_RESPECTED_MESSAGE = "This field only accepts alphanumeric characters.";
	private static final String MINIMUM_VALUE_NOT_RESPECTED_MESSAGE = "This filed only accepts positive values";
	private static final String RANGE_OF_VALUE_NOT_RESPECTED_MESSAGE = "This filed only accepts values between 0 and 100";
	private static final String LENGTH_PATTERN_NOT_RESPECTED_MESSAGE = "This field length's should be between ";

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID_PRODUCT")
	private int id;
	@Length(min = 4, max = 6, message = LENGTH_PATTERN_NOT_RESPECTED_MESSAGE + "4 and 6")
	@Pattern(regexp = ALPHANUMERIC_PATTERN, message = ALPHANUMERIC_PATTERN_NOT_RESPECTED_MESSAGE)
	@Column(name = "CODE")
	private String code;
	@Length(min = 4, max = 50, message = LENGTH_PATTERN_NOT_RESPECTED_MESSAGE + "4 and 50")
	@Pattern(regexp = ALPHANUMERIC_PATTERN, message = ALPHANUMERIC_PATTERN_NOT_RESPECTED_MESSAGE)
	@Column(name = "DESIGNATION")
	private String designation;
	/*
	 * Price excluding value-added tax
	 */
	@Min(value = 0, message = MINIMUM_VALUE_NOT_RESPECTED_MESSAGE)
	@Column(name = "PRICE_EXCLUDING_VAT")
	private float priceExcludingVAT;
	/*
	 * value-added tax
	 */
	@Range(min = 0, max = 100, message = RANGE_OF_VALUE_NOT_RESPECTED_MESSAGE)
	@Column(name = "VAT")
	private float vat;
	@NumberFormat(style = Style.CURRENCY)
	@Length(min = 1, max = 6, message = LENGTH_PATTERN_NOT_RESPECTED_MESSAGE + "1 and 6")
	@Pattern(regexp = ALPHANUMERIC_PATTERN, message = ALPHANUMERIC_PATTERN_NOT_RESPECTED_MESSAGE)
	@Column(name = "UNIT_OF_MEASURE")
	private String unitOfMeasure;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_CATEGORY")
	private Category category;

	public Product() {
		// This is a default constructor
	}

	public Product(
			@Length(min = 4, max = 6) @Pattern(regexp = "[a-zA-Z0-9]", message = "This field only accepts alphanumeric characters.") String code,
			@Length(min = 4, max = 50) @Pattern(regexp = "[a-zA-Z0-9]", message = "This field only accepts alphanumeric characters.") String designation,
			@Min(value = 0, message = "This filed only accepts positive values") float priceExcludingVAT,
			@Range(min = 0, max = 100, message = "This filed only accepts values between 0 and 100") float vat,
			@Length(min = 1, max = 6) @Pattern(regexp = "[a-zA-Z0-9]", message = "This field only accepts alphanumeric characters.") String unitOfMeasure,
			Category category) {
		super();
		this.code = code;
		this.designation = designation;
		this.priceExcludingVAT = priceExcludingVAT;
		this.vat = vat;
		this.unitOfMeasure = unitOfMeasure;
		this.category = category;
	}

	public Product(int id,
			@Length(min = 4, max = 6) @Pattern(regexp = "[a-zA-Z0-9]", message = "This field only accepts alphanumeric characters.") String code,
			@Length(min = 4, max = 50) @Pattern(regexp = "[a-zA-Z0-9]", message = "This field only accepts alphanumeric characters.") String designation,
			@Min(value = 0, message = "This filed only accepts positive values") float priceExcludingVAT,
			@Range(min = 0, max = 100, message = "This filed only accepts values between 0 and 100") float vat,
			@Length(min = 1, max = 6) @Pattern(regexp = "[a-zA-Z0-9]", message = "This field only accepts alphanumeric characters.") String unitOfMeasure,
			Category category) {
		super();
		this.id = id;
		this.code = code;
		this.designation = designation;
		this.priceExcludingVAT = priceExcludingVAT;
		this.vat = vat;
		this.unitOfMeasure = unitOfMeasure;
		this.category = category;
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
	 * @return the priceExcludingVAT
	 */
	public float getPriceExcludingVAT() {
		return priceExcludingVAT;
	}

	/**
	 * @param priceExcludingVAT the priceExcludingVAT to set
	 */
	public void setPriceExcludingVAT(float priceExcludingVAT) {
		this.priceExcludingVAT = priceExcludingVAT;
	}

	/**
	 * @return the vat
	 */
	public float getVat() {
		return vat;
	}

	/**
	 * @param vat the vat to set
	 */
	public void setVat(float vat) {
		this.vat = vat;
	}

	/**
	 * @return the unitOfMeasure
	 */
	public String getUnitOfMeasure() {
		return unitOfMeasure;
	}

	/**
	 * @param unitOfMeasure the unitOfMeasure to set
	 */
	public void setUnitOfMeasure(String unitOfMeasure) {
		this.unitOfMeasure = unitOfMeasure;
	}

	/**
	 * @return the category
	 */
	public Category getCategory() {
		return category;
	}

	/**
	 * @param category the category to set
	 */
	public void setCategory(Category category) {
		this.category = category;
	}

	@Override
	public int hashCode() {
		return Objects.hash(category, code, designation, id, priceExcludingVAT, unitOfMeasure, vat);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof Product)) {
			return false;
		}
		Product other = (Product) obj;
		return Objects.equals(category, other.category) && Objects.equals(code, other.code)
				&& Objects.equals(designation, other.designation) && id == other.id
				&& Float.floatToIntBits(priceExcludingVAT) == Float.floatToIntBits(other.priceExcludingVAT)
				&& Objects.equals(unitOfMeasure, other.unitOfMeasure)
				&& Float.floatToIntBits(vat) == Float.floatToIntBits(other.vat);
	}

	@Override
	public String toString() {
		return "Product [id=" + id + ", code=" + code + ", designation=" + designation + ", priceExcludingVAT="
				+ priceExcludingVAT + ", vat=" + vat + ", unitOfMeasure=" + unitOfMeasure + ", category=" + category
				+ "]";
	}

}
