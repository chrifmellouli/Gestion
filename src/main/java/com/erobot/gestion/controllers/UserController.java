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
import com.erobot.gestion.dao.PrivilegeDao;
import com.erobot.gestion.dao.RoleDao;
import com.erobot.gestion.dao.UserDao;
import com.erobot.gestion.models.Role;
import com.erobot.gestion.models.User;

/**
 * @author CHRIF MELLOULI
 *
 */
@Controller
public class UserController extends SecurityController {

	public static final String HOME_PAGE_USER = "listUsers";
	public static final String ADD_PAGE_USER = "addUser";
	public static final String EDIT_PAGE_USER = "editUser";

	public static final String ATTR_USER_TO = "userTo";
	public static final String ATTR_USERS = "users";
	public static final String ATTR_ROLES = "roles";
	
	public static final String REQUEST_USER_NAME = "userName";

	protected RoleDao roleDao;

	@Autowired
	public UserController(UserDao userDao, RoleDao roleDao, PrivilegeDao privilegeDao,
			AuthorizationDao authorizationDao) {
		super(userDao, authorizationDao, privilegeDao);
		this.roleDao = roleDao;
	}

	@GetMapping(value = "/Users")
	public String listUsers(ModelMap modelMap) {
		if (!checkLogin()) {
			return MainController.LOGIN;
		}
		List<User> users = userDao.findAll();
		Map<String, Object> map = new HashMap<>();
		attributeRightsForCurrentUser(map);
		map.put(MainController.USER, MainController.roleUser);
		map.put(ATTR_USERS, users);
		modelMap.addAllAttributes(map);
		return HOME_PAGE_USER;
	}

	@GetMapping(value = "/Users/addUser")
	public String addUser(ModelMap modelMap) {
		if (!checkLogin()) {
			return MainController.LOGIN;
		}
		Map<String, Object> map = new HashMap<>();
		attributeRightsForCurrentUser(map);
		map.put(ATTR_ROLES, roleDao.findAll());
		map.put(MainController.USER, MainController.roleUser);
		modelMap.addAllAttributes(map);
		return ADD_PAGE_USER;
	}

	@PostMapping(value = "/Users/addUser/add")
	public String addUser(ServletRequest request, ModelMap modelMap) {
		if (!checkLogin()) {
			return MainController.LOGIN;
		}
		if (hasPermission() == 2) {
			User user = new User();
			int roleId = Integer.parseInt(request.getParameter("role"));
			if ((userDao.findById(roleId) != null)
					&& (userDao.findById(roleId).getRole().getRoleUser().equalsIgnoreCase("root"))) {
				Map<String, Object> map = new HashMap<>();
				attributeRightsForCurrentUser(map);
				modelMap.addAllAttributes(map);
				return MainController.PAGE_403;
			}
			user.setUsername(request.getParameter(REQUEST_USER_NAME));
			if ((userDao.findById(roleId) != null) && (request.getParameter(REQUEST_USER_NAME).equalsIgnoreCase("root"))) {
				Map<String, Object> map = new HashMap<>();
				attributeRightsForCurrentUser(map);
				modelMap.addAllAttributes(map);
				return MainController.PAGE_403;
			}
			user.setPassword(request.getParameter("password"));
			user.setRole(roleDao.findById(roleId));
			user.setIs(0);
			Map<String, Object> map = new HashMap<>();
			attributeRightsForCurrentUser(map);
			map.put(MainController.USER, MainController.roleUser);
			map.put(ATTR_ROLES, roleDao.findAll());
			map.put(ATTR_USER_TO, user);
			try {
				userDao.save(user);
				map.put(ATTR_USERS, userDao.findAll());
				modelMap.addAllAttributes(map);
				return HOME_PAGE_USER;
			} catch (ConstraintViolationException e) {
				map.put(ATTR_USER_TO, user);
				map.put(MainController.ATTR_ERRORS, catchConstraintViolation(e));
				modelMap.addAllAttributes(map);
				return ADD_PAGE_USER;
			}
		} else {
			Map<String, Object> map = new HashMap<>();
			attributeRightsForCurrentUser(map);
			modelMap.addAllAttributes(map);
			return MainController.PAGE_403;
		}
	}

	@GetMapping(value = "/Users/{id}/delete")
	public String deleteUser(@PathVariable int id, ModelMap modelMap) {
		if (!checkLogin()) {
			return MainController.LOGIN;
		}
		if (hasPermission() == 2) {
			if (userDao.findById(id).getUsername().equalsIgnoreCase("root")) {
				return MainController.PAGE_403;
			}
			String error = new String("");
			if (MainController.roleUser.getId() == id) {
				error = error.concat("current");
			} else {
				try {
					userDao.deleteById(id);
					error = error.concat("yes");
				} catch (Exception e) {
					error = error.concat("no");
				}
			}
			Map<String, Object> map = new HashMap<>();
			attributeRightsForCurrentUser(map);
			map.put(MainController.USER, MainController.roleUser);
			map.put(MainController.ATTR_ERROR, error);
			map.put(ATTR_USERS, userDao.findAll());
			modelMap.addAllAttributes(map);
			return HOME_PAGE_USER;
		} else {
			Map<String, Object> map = new HashMap<>();
			attributeRightsForCurrentUser(map);
			modelMap.addAllAttributes(map);
			return MainController.PAGE_403;
		}
	}

	@GetMapping(value = "/Users/{id}/editUser")
	public String updateUser(@PathVariable int id, ModelMap modelMap) {
		if (!checkLogin()) {
			return MainController.LOGIN;
		}
		if (userDao.findById(id).getUsername().equalsIgnoreCase("root")) {
			Map<String, Object> map = new HashMap<>();
			attributeRightsForCurrentUser(map);
			modelMap.addAllAttributes(map);
			return MainController.PAGE_403;
		}
		User user = userDao.findById(id);
		List<Role> roles = roleDao.findAll();
		Map<String, Object> map = new HashMap<>();
		attributeRightsForCurrentUser(map);
		map.put(ATTR_ROLES, roles);
		map.put(MainController.USER, MainController.roleUser);
		map.put(ATTR_USER_TO, user);
		modelMap.addAllAttributes(map);
		return EDIT_PAGE_USER;
	}

	@GetMapping(value = "/Users/{id}/editUser/edit")
	public String updateUser(ServletRequest request, @PathVariable int id, ModelMap modelMap) {
		if (!checkLogin()) {
			return MainController.LOGIN;
		}
		if (userDao.findById(id).getUsername().equalsIgnoreCase("root")) {
			Map<String, Object> map = new HashMap<>();
			attributeRightsForCurrentUser(map);
			modelMap.addAllAttributes(map);
			return MainController.PAGE_403;
		}

		if (hasPermission() == 2) {
			User user = userDao.findById(id);
			int roleId = Integer.parseInt(request.getParameter("role"));
			if ((userDao.findById(roleId) != null)
					&& (userDao.findById(roleId).getRole().getRoleUser().equalsIgnoreCase("root"))) {
				Map<String, Object> map = new HashMap<>();
				attributeRightsForCurrentUser(map);
				modelMap.addAllAttributes(map);
				return MainController.PAGE_403;
			}
			user.setUsername(request.getParameter(REQUEST_USER_NAME));
			if ((userDao.findById(roleId) != null) && (request.getParameter(REQUEST_USER_NAME).equalsIgnoreCase("root"))) {
				Map<String, Object> map = new HashMap<>();
				attributeRightsForCurrentUser(map);
				modelMap.addAllAttributes(map);
				return MainController.PAGE_403;

			}
			user.setPassword(request.getParameter("password"));
			user.setRole(roleDao.findById(roleId));
			List<Role> roles = roleDao.findAll();
			Map<String, Object> map = new HashMap<>();
			attributeRightsForCurrentUser(map);
			map.put(ATTR_ROLES, roles);
			map.put(MainController.USER, MainController.roleUser);
			try {
				userDao.save(user);
				map.put(ATTR_USERS, userDao.findAll());
				modelMap.addAllAttributes(map);
				return HOME_PAGE_USER;
			} catch (TransactionSystemException e) {
				map.put(ATTR_USER_TO, user);
				map.put(MainController.ATTR_ERRORS,
						catchConstraintViolation((ConstraintViolationException) e.getCause().getCause()));
				modelMap.addAllAttributes(map);
				return EDIT_PAGE_USER;
			}
		} else {
			Map<String, Object> map = new HashMap<>();
			attributeRightsForCurrentUser(map);
			modelMap.addAllAttributes(map);
			return MainController.PAGE_403;
		}
	}

}
