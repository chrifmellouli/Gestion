/**
 * The DAO layer of the application
 */
package com.erobot.gestion.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.erobot.gestion.models.Customer;

/**
 * @author CHRIF MELLOULI
 *
 */
@Repository
public interface CustomerDao extends JpaRepository<Customer, Integer> {

	Customer findById(int id);

	@Query("select c from Customer c where c.name like ?1%")
	Optional<Customer> findByName(String name);

	@Query("select c from Customer c where c.lastName like ?1%")
	Optional<Customer> findByLastName(String lastName);

	@Query("select c from Customer c where c.phoneNumber like ?1%")
	Customer findByPhoneNumber(String phoneNumber);

	@Query("select c from Customer c where c.address like ?1%")
	Optional<Customer> findByAddress(String address);

}
