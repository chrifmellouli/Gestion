/**
 * The DAO layer of the application
 */
package com.erobot.gestion.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.erobot.gestion.models.PasswordEncryption;

/**
 * @author CHRIF MELLOULI
 *
 */
@Repository
public interface SecurityDao  extends JpaRepository<PasswordEncryption, Integer> {
	@Modifying
	@Query(value = "insert into ENCRYCPTION (ENCRYCPTION) values (:encryption)", nativeQuery = true)
	void insertEncryption(@Param("encryption") String encryption);
}
