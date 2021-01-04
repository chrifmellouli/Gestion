/**
 * The DAO layer of the application
 */
package com.erobot.gestion.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.erobot.gestion.models.Privilege;

/**
 * @author CHRIF MELLOULI
 *
 */
@Repository
public interface PrivilegeDao extends JpaRepository<Privilege, Integer> {

	Privilege findById(int id);

	@Query("select p from Privilege p where p.designation like %:designation%")
	List<Privilege> findByDesignation(@Param("designation") String designation);
}
