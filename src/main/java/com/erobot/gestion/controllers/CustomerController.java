/**
 * The controller layer of the application
 */
package com.erobot.gestion.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.validation.ConstraintViolationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.erobot.gestion.dao.AuthorizationDao;
import com.erobot.gestion.dao.CustomerDao;
import com.erobot.gestion.dao.PrivilegeDao;
import com.erobot.gestion.dao.UserDao;
import com.erobot.gestion.models.Customer;

/**
 * @author CHRIF MELLOULI
 *
 */
@Controller
public class CustomerController extends SecurityController {

	public static final String HOME_PAGE_CUSTOMERS = "listCustomers";
	public static final String ADD_PAGE_CUSTOMER = "addCustomer";
	public static final String EDIT_PAGE_CUSTOMER = "editCustomer";

	public static final String ATTR_CUSTOMERS = "customers";
	public static final String ATTR_CUSTOMER = "customer";

	protected CustomerDao customerDao;

	@Autowired
	public CustomerController(UserDao userDao, CustomerDao customerDao, PrivilegeDao privilegeDao,
			AuthorizationDao authorizationDao) {
		super(userDao, authorizationDao, privilegeDao);
		this.customerDao = customerDao;
	}

	@GetMapping(value = "/Customers")
	public String listCustomers(ModelMap modelMap) {
		if (!checkLogin()) {
			return MainController.LOGIN;
		}
		Map<String, Object> map = new HashMap<>();
		List<Customer> customers = customerDao.findAll();
		attributeRightsForCurrentUser(map);
		map.put(MainController.USER, MainController.roleUser);
		map.put(ATTR_CUSTOMERS, customers);
		modelMap.addAllAttributes(map);
		return HOME_PAGE_CUSTOMERS;
	}
	/**
	 * 
	 * @param modelMap
	 * @return
	 */
	@GetMapping(value = "/Customers/addCustomer")
	public String addCustomer(ModelMap modelMap) {
		if (!checkLogin()) {
			return MainController.LOGIN;
		}
		Map<String, Object> map = new HashMap<>();
		attributeRightsForCurrentUser(map);
		map.put(MainController.USER, MainController.roleUser);
		modelMap.addAllAttributes(map);
		return ADD_PAGE_CUSTOMER;
	}

	@PostMapping(value = "/Customers/addCustomer/add")
	public String addCustomer(ServletRequest request, ModelMap modelMap) {
		if (!checkLogin()) {
			return MainController.LOGIN;
		}
		if (hasPermission() == 2) {
			Customer customer = new Customer();
			customer.setAddress(request.getParameter("address"));
			customer.setLastName(request.getParameter("lastName"));
			customer.setName(request.getParameter("name"));
			customer.setPhoneNumber(request.getParameter("phoneNumber"));
			Map<String, Object> map = new HashMap<>();
			attributeRightsForCurrentUser(map);
			map.put(MainController.USER, MainController.roleUser);
			try {
				customerDao.save(customer);
				map.put(ATTR_CUSTOMERS, customerDao.findAll());
				modelMap.addAllAttributes(map);
				return HOME_PAGE_CUSTOMERS;
			} catch (ConstraintViolationException e) {
				map.put(ATTR_CUSTOMER, customer);
				map.put(MainController.ATTR_ERRORS, catchConstraintViolation(e));
				modelMap.addAllAttributes(map);
				return ADD_PAGE_CUSTOMER;
			}
		} else {
			Map<String, Object> map = new HashMap<>();
			attributeRightsForCurrentUser(map);
			modelMap.addAllAttributes(map);
			return MainController.PAGE_403;
		}
	}

	@GetMapping(value = "/Customers/{id}/delete")
	public String deleteCustomer(@PathVariable int id, ModelMap modelMap) {
		if (!checkLogin()) {
			return MainController.LOGIN;
		}
		if (hasPermission() == 2) {
			String error = new String("");
			try {
				customerDao.deleteById(id);
				error = error.concat("yes");
			} catch (Exception e) {
				error = error.concat("no");
			}
			Map<String, Object> map = new HashMap<>();
			attributeRightsForCurrentUser(map);
			map.put(MainController.USER, MainController.roleUser);
			map.put(MainController.ATTR_ERROR, error);
			map.put(ATTR_CUSTOMERS, customerDao.findAll());
			modelMap.addAllAttributes(map);
			return HOME_PAGE_CUSTOMERS;
		} else {
			Map<String, Object> map = new HashMap<>();
			attributeRightsForCurrentUser(map);
			modelMap.addAllAttributes(map);
			return MainController.PAGE_403;
		}
	}

	@GetMapping(value = "/Customers/{id}/editCustomer")
	public String updateCustomer(@PathVariable int id, ModelMap modelMap) {
		if (!checkLogin()) {
			return MainController.LOGIN;
		}
		Customer customer = customerDao.findById(id);
		Map<String, Object> map = new HashMap<>();
		attributeRightsForCurrentUser(map);
		map.put(MainController.USER, MainController.roleUser);
		map.put(ATTR_CUSTOMER, customer);
		modelMap.addAllAttributes(map);
		return EDIT_PAGE_CUSTOMER;
	}

	@GetMapping(value = "/Customers/{id}/editCustomer/edit")
	public String updateCustomer(ServletRequest request, @PathVariable int id, ModelMap modelMap) {
		if (!checkLogin()) {
			return MainController.LOGIN;
		}
		if (hasPermission() == 2) {
			Customer customer = customerDao.findById(id);
			customer.setAddress(request.getParameter("address"));
			customer.setLastName(request.getParameter("lastName"));
			customer.setName(request.getParameter("name"));
			customer.setPhoneNumber(request.getParameter("phoneNumber"));
			Map<String, Object> map = new HashMap<>();
			attributeRightsForCurrentUser(map);
			map.put(MainController.USER, MainController.roleUser);
			try {
				customerDao.save(customer);
				map.put(ATTR_CUSTOMERS, customerDao.findAll());
				modelMap.addAllAttributes(map);
				return HOME_PAGE_CUSTOMERS;
			} catch (TransactionSystemException e) {
				map.put(ATTR_CUSTOMER, customer);
				map.put(MainController.ATTR_ERRORS,
						catchConstraintViolation((ConstraintViolationException) e.getCause().getCause()));
				modelMap.addAllAttributes(map);
				return EDIT_PAGE_CUSTOMER;
			}
		} else {
			Map<String, Object> map = new HashMap<>();
			attributeRightsForCurrentUser(map);
			modelMap.addAllAttributes(map);
			return MainController.PAGE_403;
		}
	}

}
