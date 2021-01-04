/**
 * The controller layer of the application
 */
package com.erobot.gestion.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.ServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.erobot.gestion.dao.AuthorizationDao;
import com.erobot.gestion.dao.PrivilegeDao;
import com.erobot.gestion.dao.RoleDao;
import com.erobot.gestion.dao.UserDao;
import com.erobot.gestion.models.Authorization;
import com.erobot.gestion.models.AuthorizationPK;
import com.erobot.gestion.models.Privilege;
import com.erobot.gestion.models.Role;

/**
 * @author CHRIF MELLOULI
 *
 */
@Controller
public class AuthorizationController extends SecurityController {

	public static final String HOME_PAGE_AUTHORIZATION = "listAuthorizations";

	public static final String ATTR_AUTHORIZATION = "authorization";
	public static final String ATTR_AUTHORIZATIONS = "authorizations";
	public static final String ATTR_ROLES = "roles";
	public static final String ATTR_ACTIVE = "active";

	public static final String STR_MANAGE = "manage";
	public static final String STR_VIEW = "view";

	protected RoleDao roleDao;

	@Autowired
	public AuthorizationController(UserDao userDao, RoleDao roleDao, PrivilegeDao privilegeDao,
			AuthorizationDao authorizationDao) {
		super(userDao, authorizationDao, privilegeDao);
		this.roleDao = roleDao;
		this.privilegeDao = privilegeDao;
		this.authorizationDao = authorizationDao;
	}

	@GetMapping(value = "/Authorizations")
	public String listAuthorizations(ModelMap modelMap) {
		if (!checkLogin()) {
			return MainController.LOGIN;
		}
		Map<String, Object> map = listAuthorizations();
		attributeRightsForCurrentUser(map);
		modelMap.addAllAttributes(map);
		return HOME_PAGE_AUTHORIZATION;
	}

	/**
	 * @return
	 */
	private Map<String, Object> listAuthorizations() {
		List<Privilege> privileges = privilegeDao.findAll(Sort.by("id"));
		List<Role> roles = roleDao.findAll(Sort.by("id"));
		List<Authorization> authorizations = authorizationDao.findAll();
		Map<Privilege, Map<Integer, Integer>> matrixOfAuthorizations = new HashMap<>();
		for (Privilege privilege : privileges) {
			Map<Integer, Integer> privilegeAuthorizationsStatus = new TreeMap<>();
			for (Role role : roles) {
				if (authorizations.contains(new Authorization(
						new AuthorizationPK(roleDao.findById(role.getId()), privilegeDao.findById(privilege.getId())),
						true))) {
					privilegeAuthorizationsStatus.put(role.getId(), 1);
				} else {
					privilegeAuthorizationsStatus.put(role.getId(), 0);
				}
			}
			matrixOfAuthorizations.put(privilege, privilegeAuthorizationsStatus);
		}
		Map<String, Object> map = new HashMap<>();
		map.put(MainController.USER, MainController.roleUser);
		map.put(ATTR_ROLES, roles);
		map.put(ATTR_AUTHORIZATIONS, matrixOfAuthorizations);
		return map;
	}

	@PostMapping(value = "/Authorizations/add")
	public String addAuthorization(ServletRequest request, ModelMap modelMap) {
		if (!checkLogin()) {
			return MainController.LOGIN;
		}
		if (hasPermission() == 2) {
			Role role = roleDao.findById(Integer.parseInt(request.getParameter("role")));
			Privilege privilege = privilegeDao.findById(Integer.parseInt(request.getParameter("privilege")));
			AuthorizationPK authorizationPK = new AuthorizationPK(role, privilege);
			Authorization authorization = new Authorization(authorizationPK, true);
			try {
				if (privilege.getDesignation().contains(STR_MANAGE)) {
					Privilege privilege1 = privilegeDao
							.findByDesignation(privilege.getDesignation().replace(STR_MANAGE, STR_VIEW)).get(0);
					AuthorizationPK authorizationPK1 = new AuthorizationPK(role, privilege1);
					Authorization authorization1 = new Authorization(authorizationPK1, true);
					authorizationDao.save(authorization1);
				}
				authorizationDao.save(authorization);
				Map<String, Object> map = listAuthorizations();
				attributeRightsForCurrentUser(map);
				modelMap.addAllAttributes(map);
				return HOME_PAGE_AUTHORIZATION;
			} catch (Exception e) {
				Map<String, Object> map = listAuthorizations();
				attributeRightsForCurrentUser(map);
				modelMap.addAllAttributes(map);
				return HOME_PAGE_AUTHORIZATION;
			}
		} else {
			Map<String, Object> map = new HashMap<>();
			attributeRightsForCurrentUser(map);
			modelMap.addAllAttributes(map);
			return MainController.PAGE_403;
		}
	}

	@GetMapping(value = "/Authorizations/{id1}/{id2}/delete")
	public String deleteAuthorization(@PathVariable int id1, @PathVariable int id2, ModelMap modelMap) {
		if (!checkLogin()) {
			return MainController.LOGIN;
		}
		if (hasPermission() == 2) {
			String error = new String("");
			try {
				Role role = roleDao.findById(id1);
				Privilege privilege = privilegeDao.findById(id2);
				AuthorizationPK authorizationPK = new AuthorizationPK(role, privilege);
				Authorization authorization = new Authorization(authorizationPK, false);
				if (authorization.getAuthorizationPK().getRole().getRoleUser().equalsIgnoreCase("root")) {
					error = error.concat("You cannot do this for root");
				} else {
					if (privilege.getDesignation().contains(STR_VIEW)) {
						Privilege privilege1 = privilegeDao
								.findByDesignation(privilege.getDesignation().replace(STR_VIEW, STR_MANAGE)).get(0);
						AuthorizationPK authorizationPK1 = new AuthorizationPK(role, privilege1);
						Authorization authorization1 = new Authorization(authorizationPK1, false);
						authorizationDao.save(authorization1);
					}
					authorizationDao.save(authorization);
				}
			} catch (Exception e) {
				error = e.getMessage();
			}
			Map<String, Object> map = listAuthorizations();
			attributeRightsForCurrentUser(map);
			map.put(MainController.ATTR_ERROR, error);
			modelMap.addAllAttributes(map);
			return HOME_PAGE_AUTHORIZATION;
		} else {
			Map<String, Object> map = new HashMap<>();
			attributeRightsForCurrentUser(map);
			modelMap.addAllAttributes(map);
			return MainController.PAGE_403;
		}
	}

}
