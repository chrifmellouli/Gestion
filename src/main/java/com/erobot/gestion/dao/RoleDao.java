/**
 * The DAO layer of the application
 */
package com.erobot.gestion.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.erobot.gestion.models.Role;

/**
 * @author CHRIF MELLOULI
 *
 */
@Repository
public interface RoleDao extends JpaRepository<Role, Integer> {

	Role findById(int id);

}
