/**
 * The controller layer of the application
 */
package com.erobot.gestion.controllers;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.erobot.gestion.dao.AuthorizationDao;
import com.erobot.gestion.dao.CategoryDao;
import com.erobot.gestion.dao.CustomerDao;
import com.erobot.gestion.dao.PrivilegeDao;
import com.erobot.gestion.dao.ProductDao;
import com.erobot.gestion.dao.RoleDao;
import com.erobot.gestion.dao.UserDao;
import com.erobot.gestion.models.User;

/**
 * @author CHRIF MELLOULI
 *
 */
@Controller
public class MainController extends SecurityController {

	public static final String LOGIN = "login";
	public static final String INDEX = "index";
	public static final String REDIRECT = "redirect:/";

	public static final String USER = "user";
	public static final String PAGE_403 = "403";
	public static final String ATTR_ROOT = "Root";
	public static final String ATTR_ERROR = "error";
	public static final String ATTR_ERRORS = "errors";
	public static final String ATTR_PERMISSION = "permission";
	public static final String ATTR_TOKEN = "token";
	public static final String ATTR_PRIVILEGES = "listPrivileges";

	public static final String COUNT_USERS = "usersCount";
	public static final String COUNT_USERS_CONNECTED = "usersConnectedCount";
	public static final String COUNT_CATEGORIES = "categoriesCount";
	public static final String COUNT_PRODCUTS = "productsCount";
	public static final String COUNT_CUSTOMERS = "CustomersCount";

	protected static User roleUser;

	protected CategoryDao categoryDao;
	protected ProductDao productDao;
	protected CustomerDao customerDao;
	protected RoleDao roleDao;

	@Autowired
	public MainController(UserDao userDao, CategoryDao categoryDao, ProductDao productDao, CustomerDao customerDao,
			RoleDao roleDao, PrivilegeDao privilegeDao, AuthorizationDao authorizationDao) {
		super(userDao, authorizationDao, privilegeDao);
		this.categoryDao = categoryDao;
		this.productDao = productDao;
		this.customerDao = customerDao;
		this.roleDao = roleDao;
		this.privilegeDao = privilegeDao;
	}

	@GetMapping(value = "/index")
	public String indexPage(ModelMap modelMap) {
		if (!checkLogin()) {
			return LOGIN;
		}
		Map<String, Object> map = new HashMap<>();
		map.put(ATTR_PRIVILEGES, listPermissions());
		map.put(ATTR_TOKEN, getToken(25));
		map.put(COUNT_USERS, userDao.findAll().size());
		map.put(COUNT_USERS_CONNECTED, userDao.findByIs(1).size());
		map.put(COUNT_CATEGORIES, categoryDao.findAll().size());
		map.put(COUNT_PRODCUTS, productDao.findAll().size());
		map.put(COUNT_CUSTOMERS, customerDao.findAll().size());
		map.put(MainController.USER, MainController.roleUser);
		modelMap.addAllAttributes(map);
		return INDEX;
	}

	@GetMapping(value = "/")
	public String indexOrLoginPage(ModelMap modelMap) {
		if (!checkLogin()) {
			return LOGIN;
		} else {
			Map<String, Object> map = new HashMap<>();
			map.put(ATTR_PRIVILEGES, listPermissions());
			map.put(ATTR_TOKEN, getToken(25));
			map.put(COUNT_USERS, userDao.findAll().size());
			map.put(COUNT_USERS_CONNECTED, userDao.findByIs(1).size());
			map.put(COUNT_CATEGORIES, categoryDao.findAll().size());
			map.put(COUNT_PRODCUTS, productDao.findAll().size());
			map.put(COUNT_CUSTOMERS, customerDao.findAll().size());
			map.put(MainController.USER, MainController.roleUser);
			modelMap.addAllAttributes(map);
			return INDEX;
		}
	}

	@GetMapping(value = "/login")
	public String loginPage(ModelMap modelMap) {
		if (checkLogin()) {
			return REDIRECT + INDEX;
		}
		return LOGIN;
	}

	@GetMapping(value = "/disconnect")
	public synchronized String logoutPage(ServletRequest request, ModelMap modelMap) {
		if (!checkLogin()) {
			return REDIRECT + LOGIN;
		}
		if (request.getParameter(ATTR_TOKEN) == null) {
			return REDIRECT + INDEX;
		}
		User user = userDao.findById(roleUser.getId());
		user.setIs(0);
		userDao.save(user);
		String authentification = "Successefull logout";
		modelMap.addAttribute("authentification", authentification);
		return REDIRECT + LOGIN;
	}

	@PostMapping(value = "/connect")
	public synchronized String indexPage(ServletRequest request, ModelMap modelMap) {
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		boolean iCanAccess;
		User user = userDao.findByUsernameAndPassword(username, password);
		iCanAccess = user != null;
		if (iCanAccess) {
			user.setIs(1);
			userDao.save(user);
			roleUser = user;
			Map<String, Object> map = new HashMap<>();
			map.put(ATTR_PRIVILEGES, listPermissions());
			map.put(COUNT_USERS, userDao.findAll().size());
			map.put(COUNT_USERS_CONNECTED, userDao.findByIs(1).size());
			map.put(COUNT_CATEGORIES, categoryDao.findAll().size());
			map.put(COUNT_PRODCUTS, productDao.findAll().size());
			map.put(COUNT_CUSTOMERS, customerDao.findAll().size());
			map.put(USER, userDao.findById(user.getId()));
			modelMap.addAllAttributes(map);
			return INDEX;
		} else {
			String authentification = "authentification failed";
			modelMap.addAttribute("authentification", authentification);
			return LOGIN;
		}
	}

}
