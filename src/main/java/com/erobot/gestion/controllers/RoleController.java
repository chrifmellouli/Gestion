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

/**
 * @author CHRIF MELLOULI
 *
 */
@Controller
public class RoleController extends SecurityController {

	public static final String HOME_PAGE_ROLE = "listRoles";
	public static final String ADD_PAGE_ROLE = "addRole";
	public static final String EDIT_PAGE_ROLE = "editRole";

	public static final String ATTR_ROLE = "role";
	public static final String ATTR_ROLES = "roles";

	protected RoleDao roleDao;

	@Autowired
	public RoleController(UserDao userDao, RoleDao roleDao, PrivilegeDao privilegeDao,
			AuthorizationDao authorizationDao) {
		super(userDao, authorizationDao, privilegeDao);
		this.roleDao = roleDao;
	}

	@GetMapping(value = "/Roles")
	public String listRoles(ModelMap modelMap) {
		if (!checkLogin()) {
			return MainController.LOGIN;
		}
		List<Role> roles = roleDao.findAll();
		Map<String, Object> map = new HashMap<>();
		attributeRightsForCurrentUser(map);
		map.put(MainController.USER, MainController.roleUser);
		map.put(ATTR_ROLES, roles);
		modelMap.addAllAttributes(map);
		return HOME_PAGE_ROLE;
	}

	@GetMapping(value = "/Roles/addRole")
	public String addRole(ModelMap modelMap) {
		if (!checkLogin()) {
			return MainController.LOGIN;
		}
		Map<String, Object> map = new HashMap<>();
		attributeRightsForCurrentUser(map);
		map.put(MainController.USER, MainController.roleUser);
		modelMap.addAllAttributes(map);
		return ADD_PAGE_ROLE;
	}

	@PostMapping(value = "/Roles/addRole/add")
	public String addRole(ServletRequest request, ModelMap modelMap) {
		if (!checkLogin()) {
			return MainController.LOGIN;
		}
		if (hasPermission() == 2) {
			Role role = new Role();
			role.setRoleUser(request.getParameter("roleUser"));
			Map<String, Object> map = new HashMap<>();
			attributeRightsForCurrentUser(map);
			map.put(MainController.USER, MainController.roleUser);
			try {
				roleDao.save(role);
				map.put(ATTR_ROLES, roleDao.findAll());
				modelMap.addAllAttributes(map);
				return HOME_PAGE_ROLE;
			} catch (ConstraintViolationException e) {
				map.put(ATTR_ROLE, role);
				map.put(MainController.ATTR_ERRORS, catchConstraintViolation(e));
				modelMap.addAllAttributes(map);
				return ADD_PAGE_ROLE;
			}
		} else {
			Map<String, Object> map = new HashMap<>();
			attributeRightsForCurrentUser(map);
			modelMap.addAllAttributes(map);
			return MainController.PAGE_403;
		}
	}

	@GetMapping(value = "/Roles/{id}/delete")
	public String deleteRole(@PathVariable int id, ModelMap modelMap) {
		if (!checkLogin()) {
			return MainController.LOGIN;
		}
		if (hasPermission() == 2) {
			String error = new String("");
			try {
				roleDao.deleteById(id);
				error = error.concat("yes");
			} catch (Exception e) {
				error = error.concat("no");
			}
			Map<String, Object> map = new HashMap<>();
			attributeRightsForCurrentUser(map);
			map.put(MainController.USER, MainController.roleUser);
			map.put(MainController.ATTR_ERROR, error);
			map.put(ATTR_ROLES, roleDao.findAll());
			modelMap.addAllAttributes(map);
			return HOME_PAGE_ROLE;
		} else {
			Map<String, Object> map = new HashMap<>();
			attributeRightsForCurrentUser(map);
			modelMap.addAllAttributes(map);
			return MainController.PAGE_403;
		}
	}

	@GetMapping(value = "/Roles/{id}/editRole")
	public String updateRole(@PathVariable int id, ModelMap modelMap) {
		if (!checkLogin()) {
			return MainController.LOGIN;
		}
		Role role = roleDao.findById(id);
		Map<String, Object> map = new HashMap<>();
		attributeRightsForCurrentUser(map);
		map.put(MainController.USER, MainController.roleUser);
		map.put(ATTR_ROLE, role);
		modelMap.addAllAttributes(map);
		return EDIT_PAGE_ROLE;
	}

	@GetMapping(value = "/Roles/{id}/editRole/edit")
	public String updateRole(ServletRequest request, @PathVariable int id, ModelMap modelMap) {
		if (!checkLogin()) {
			return MainController.LOGIN;
		}
		if (hasPermission() == 2) {
			Role role = roleDao.findById(id);
			role.setRoleUser(request.getParameter("roleUser"));
			Map<String, Object> map = new HashMap<>();
			attributeRightsForCurrentUser(map);
			map.put(MainController.USER, MainController.roleUser);
			try {
				roleDao.save(role);
				map.put(ATTR_ROLES, roleDao.findAll());
				modelMap.addAllAttributes(map);
				return HOME_PAGE_ROLE;
			} catch (TransactionSystemException e) {
				map.put(ATTR_ROLE, role);
				map.put(MainController.ATTR_ERRORS,
						catchConstraintViolation((ConstraintViolationException) e.getCause().getCause()));
				modelMap.addAllAttributes(map);
				return EDIT_PAGE_ROLE;
			}
		} else {
			Map<String, Object> map = new HashMap<>();
			attributeRightsForCurrentUser(map);
			modelMap.addAllAttributes(map);
			return MainController.PAGE_403;
		}
	}

}
