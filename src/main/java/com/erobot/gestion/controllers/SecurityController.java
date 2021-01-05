package com.erobot.gestion.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.erobot.gestion.dao.AuthorizationDao;
import com.erobot.gestion.dao.PrivilegeDao;
import com.erobot.gestion.dao.UserDao;
import com.erobot.gestion.models.Authorization;
import com.erobot.gestion.models.AuthorizationPK;
import com.erobot.gestion.models.Privilege;

/**
 * @author chrif
 *
 */
@Controller
public class SecurityController{

	protected UserDao userDao;
	protected AuthorizationDao authorizationDao;
	protected PrivilegeDao privilegeDao;

	@Autowired
	public SecurityController(UserDao userDao, AuthorizationDao authorizationDao, PrivilegeDao privilegeDao) {
		super();
		this.userDao = userDao;
		this.authorizationDao = authorizationDao;
		this.privilegeDao = privilegeDao;
	}

	protected static String getToken(int n) {
		String str = "ABCDEFGHIJKLMNOPQRSTUVWXYZ" + "abcdefghijklmnopqrstuvxyz";

		StringBuilder s = new StringBuilder(n);

		for (int i = 0; i < n; i++) {
			double index = str.length() * Math.random();
			s.append(str.charAt((int) index));
		}
		return s.toString();
	}

	public boolean checkLogin() {
		if (MainController.roleUser != null) {
			return (userDao.findById(MainController.roleUser.getId()).getIs() == 1);
		} else {
			return false;
		}
	}

	protected Map<String, String> catchConstraintViolation(ConstraintViolationException e) {
		Set<ConstraintViolation<?>> violations = e.getConstraintViolations();
		Map<String, String> errors = new HashMap<>();
		int counter = 0;
		for (ConstraintViolation<?> violation : violations) {
			String keyError = violation.getPropertyPath().toString() + Integer.toString(counter);
			String valueError = violation.getMessage();
			errors.put(keyError, valueError);
			counter++;
		}
		return errors;
	}

	protected int hasPermission() {
		int permission = 0;
		String entityName = this.getClass().getSimpleName().replace("Controller", "").toLowerCase();
		List<Privilege> privileges = privilegeDao.findByDesignation(entityName);
		for (Privilege privilege : privileges) {
			Optional<Authorization> authorization = authorizationDao
					.findById(new AuthorizationPK(MainController.roleUser.getRole(), privilege));
			if (authorization.isPresent()) {
				if (authorization.get().getPrivilege().getDesignation().equals("view ".concat(entityName))
						&& (authorization.get().isPermission())) {
					permission = 1;
				}
				if (authorization.get().getPrivilege().getDesignation().equals("manage ".concat(entityName))
						&& (authorization.get().isPermission())) {
					permission = 2;
				}
			}
		}
		return permission;
	}

	protected List<String> listPermissions() {
		List<Authorization> authorizations = authorizationDao.findByRole(MainController.roleUser.getRole().getId());
		List<String> privileges = new ArrayList<>();
		for (Authorization authorization : authorizations) {
			if (authorization.isPermission()) {
				if (authorization.getAuthorizationPK().getPrivilege().getDesignation().contains("manage")) {
					privileges.add(
							authorization.getAuthorizationPK().getPrivilege().getDesignation().replace("manage ", ""));
				}
				if (authorization.getAuthorizationPK().getPrivilege().getDesignation().contains("view")) {
					privileges.add(
							authorization.getAuthorizationPK().getPrivilege().getDesignation().replace("view ", ""));
				}
			}
		}
		return privileges;
	}

	/**
	 * @param map
	 */
	protected void attributeRightsForCurrentUser(Map<String, Object> map) {
		map.put(MainController.ATTR_PRIVILEGES, listPermissions());
		map.put(MainController.ATTR_PERMISSION, hasPermission());
	}

}
