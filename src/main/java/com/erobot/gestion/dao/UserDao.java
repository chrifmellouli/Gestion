/**
 * The DAO layer of the application
 */
package com.erobot.gestion.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.erobot.gestion.models.User;

/**
 * @author CHRIF MELLOULI
 *
 */
@Repository
public interface UserDao extends JpaRepository<User, Integer> {

	User findById(int id);

	@Query("select u from User u where u.username = ?1 and u.password = ?2")
	User findByUsernameAndPassword(String username, String password);

	@Query("select u from User u where u.role.id = ?1")
	User findByRole(int id);

	@Query("select u from User u where u.is = ?1")
	List<User> findByIs(int is);

	User findByUsername(String username);

}
