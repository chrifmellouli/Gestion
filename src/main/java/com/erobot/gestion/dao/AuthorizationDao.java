/**
 * The DAO layer of the application
 */
package com.erobot.gestion.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.erobot.gestion.models.Authorization;
import com.erobot.gestion.models.AuthorizationPK;

/**
 * @author CHRIF MELLOULI
 *
 */
@Repository
public interface AuthorizationDao extends JpaRepository<Authorization, AuthorizationPK> {

	Optional<Authorization> findById(AuthorizationPK id);

	@Query("select a from Authorization a where a.authorizationPK.role.id = ?1")
	List<Authorization> findByRole(int id);

	@Query("select a from Authorization a where a.authorizationPK.privilege.id = ?1")
	List<Authorization> findByPrivilege(int id);
	
}
