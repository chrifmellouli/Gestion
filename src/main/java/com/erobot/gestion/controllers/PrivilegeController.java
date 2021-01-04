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
import com.erobot.gestion.dao.UserDao;
import com.erobot.gestion.models.Privilege;

/**
 * @author CHRIF MELLOULI
 *
 */
@Controller
public class PrivilegeController extends SecurityController {

	public static final String HOME_PAGE_PRIVILEGE = "listPrivilege";
	public static final String ADD_PAGE_PRIVILEGE = "addPrivilege";
	public static final String EDIT_PAGE_PRIVILEGE = "editPrivilege";

	public static final String ATTR_PRIVILEGE = "privilege";
	public static final String ATTR_PRIVILEGES = "privileges";

	@Autowired
	public PrivilegeController(UserDao userDao, PrivilegeDao privilegeDao, AuthorizationDao authorizationDao) {
		super(userDao, authorizationDao, privilegeDao);
	}

	@GetMapping(value = "/Privileges")
	public String listPrivileges(ModelMap modelMap) {
		if (!checkLogin()) {
			return MainController.LOGIN;
		}
		List<Privilege> privileges = privilegeDao.findAll();
		Map<String, Object> map = new HashMap<>();
		attributeRightsForCurrentUser(map);
		map.put(MainController.USER, MainController.roleUser);
		map.put(ATTR_PRIVILEGES, privileges);
		modelMap.addAllAttributes(map);
		return HOME_PAGE_PRIVILEGE;
	}

	@GetMapping(value = "/Privileges/addPrivilege")
	public String addPrivilege(ModelMap modelMap) {
		if (!checkLogin()) {
			return MainController.LOGIN;
		}
		Map<String, Object> map = new HashMap<>();
		attributeRightsForCurrentUser(map);
		map.put(MainController.USER, MainController.roleUser);
		modelMap.addAllAttributes(map);
		return ADD_PAGE_PRIVILEGE;
	}

	@PostMapping(value = "/Privileges/addPrivilege/add")
	public String addPrivilege(ServletRequest request, ModelMap modelMap) {
		if (!checkLogin()) {
			return MainController.LOGIN;
		}
		if (hasPermission() == 2) {
			Privilege privilege = new Privilege();
			privilege.setDesignation(request.getParameter("designation"));
			Map<String, Object> map = new HashMap<>();
			attributeRightsForCurrentUser(map);
			map.put(MainController.USER, MainController.roleUser);
			try {
				privilegeDao.save(privilege);
				map.put(ATTR_PRIVILEGES, privilegeDao.findAll());
				modelMap.addAllAttributes(map);
				return HOME_PAGE_PRIVILEGE;
			} catch (ConstraintViolationException e) {
				map.put(ATTR_PRIVILEGE, privilege);
				map.put(MainController.ATTR_ERRORS, catchConstraintViolation(e));
				modelMap.addAllAttributes(map);
				return EDIT_PAGE_PRIVILEGE;
			}
		} else {
			Map<String, Object> map = new HashMap<>();
			attributeRightsForCurrentUser(map);
			modelMap.addAllAttributes(map);
			return MainController.PAGE_403;
		}
	}

	@GetMapping(value = "/Privileges/{id}/delete")
	public String deletePrivilege(@PathVariable int id, ModelMap modelMap) {
		if (!checkLogin()) {
			return MainController.LOGIN;
		}
		if (hasPermission() == 2) {
			String error = new String("");
			try {
				privilegeDao.deleteById(id);
				error = error.concat("yes");
			} catch (Exception e) {
				error = error.concat("no");
			}
			Map<String, Object> map = new HashMap<>();
			attributeRightsForCurrentUser(map);
			map.put(MainController.USER, MainController.roleUser);
			map.put(MainController.ATTR_ERROR, error);
			map.put(ATTR_PRIVILEGES, privilegeDao.findAll());
			modelMap.addAllAttributes(map);
			return HOME_PAGE_PRIVILEGE;
		} else {
			Map<String, Object> map = new HashMap<>();
			attributeRightsForCurrentUser(map);
			modelMap.addAllAttributes(map);
			return MainController.PAGE_403;
		}
	}

	@GetMapping(value = "/Privileges/{id}/editPrivilege")
	public String updatePrivilege(@PathVariable int id, ModelMap modelMap) {
		if (!checkLogin()) {
			return MainController.LOGIN;
		}
		Privilege privilege = privilegeDao.findById(id);
		Map<String, Object> map = new HashMap<>();
		attributeRightsForCurrentUser(map);
		map.put(MainController.USER, MainController.roleUser);
		map.put(ATTR_PRIVILEGE, privilege);
		modelMap.addAllAttributes(map);
		return EDIT_PAGE_PRIVILEGE;
	}

	@GetMapping(value = "/Privileges/{id}/editPrivilege/edit")
	public String updatePrivilege(ServletRequest request, @PathVariable int id, ModelMap modelMap) {
		if (!checkLogin()) {
			return MainController.LOGIN;
		}
		if (hasPermission() == 2) {
			Privilege privilege = privilegeDao.findById(id);
			privilege.setDesignation(request.getParameter("designation"));
			Map<String, Object> map = new HashMap<>();
			attributeRightsForCurrentUser(map);
			map.put(MainController.USER, MainController.roleUser);
			try {
				privilegeDao.save(privilege);
				map.put(ATTR_PRIVILEGES, privilegeDao.findAll());
				modelMap.addAllAttributes(map);
				return HOME_PAGE_PRIVILEGE;
			} catch (TransactionSystemException e) {
				map.put(ATTR_PRIVILEGE, privilege);
				map.put(MainController.ATTR_ERRORS,
						catchConstraintViolation((ConstraintViolationException) e.getCause().getCause()));
				modelMap.addAllAttributes(map);
				return EDIT_PAGE_PRIVILEGE;
			}
		} else {
			Map<String, Object> map = new HashMap<>();
			attributeRightsForCurrentUser(map);
			modelMap.addAllAttributes(map);
			return MainController.PAGE_403;
		}
	}

}
