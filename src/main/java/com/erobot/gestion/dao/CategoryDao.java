/**
 * The DAO layer of the application
 */
package com.erobot.gestion.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.erobot.gestion.models.Category;
import com.erobot.gestion.models.Product;

/**
 * @author CHRIF MELLOULI
 *
 */
@Repository
public interface CategoryDao extends JpaRepository<Category, Integer> {

	Category findById(int id);

	Category findByCode(String code);

	@Query("select c from Category c where c.designation like ?1%")
	Optional<Category> findByDesignation(String designation);

	@Query("select p from Product p where p.category.id= ?1")
	Optional<Product> listProductsInThisCategory(int id);

}
