/**
 * The DAO layer of the application
 */
package com.erobot.gestion.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.erobot.gestion.models.Product;

/**
 * @author CHRIF MELLOULI
 *
 */
@Repository
public interface ProductDao extends JpaRepository<Product, Integer> {

	Product findById(int id);

	Product findByCode(String code);

	@Query("select p from Product p where p.designation like ?1%")
	Optional<Product> findByDesignation(String designation);

	@Query("select c from Category c where c.id= ?1")
	Optional<Product> findByCategory(int id);

}
