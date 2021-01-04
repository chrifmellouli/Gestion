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
import com.erobot.gestion.dao.CategoryDao;
import com.erobot.gestion.dao.PrivilegeDao;
import com.erobot.gestion.dao.UserDao;
import com.erobot.gestion.models.Category;

/**
 * @author CHRIF MELLOULI
 *
 */
@Controller
public class CategoryController extends SecurityController {

	public static final String HOME_PAGE_CATEGORIES = "listCategories";
	public static final String ADD_PAGE_CATEGORIES = "addCategory";
	public static final String EDIT_PAGE_CATEGORIES = "editCategory";

	public static final String ATTR_CATEGORIES = "categories";
	public static final String ATTR_CATEGORY = "category";

	protected CategoryDao categoryDao;

	@Autowired
	public CategoryController(UserDao userDao, CategoryDao categoryDao, PrivilegeDao privilegeDao,
			AuthorizationDao authorizationDao) {
		super(userDao, authorizationDao, privilegeDao);
		this.categoryDao = categoryDao;
	}

	@GetMapping(value = "/Categories")
	public String listCategories(ModelMap modelMap) {
		if (!checkLogin()) {
			return MainController.LOGIN;
		}
		List<Category> categories = categoryDao.findAll();
		Map<String, Object> map = new HashMap<>();
		attributeRightsForCurrentUser(map);
		map.put(MainController.USER, MainController.roleUser);
		map.put(ATTR_CATEGORIES, categories);
		modelMap.addAllAttributes(map);
		return HOME_PAGE_CATEGORIES;
	}

	@GetMapping(value = "/Categories/addCategory")
	public String addCategory(ModelMap modelMap) {
		if (!checkLogin()) {
			return MainController.LOGIN;
		}
		Map<String, Object> map = new HashMap<>();
		attributeRightsForCurrentUser(map);
		map.put(MainController.USER, MainController.roleUser);
		modelMap.addAllAttributes(map);
		return ADD_PAGE_CATEGORIES;
	}

	@PostMapping(value = "/Categories/addCategory/add")
	public String addCategory(ServletRequest request, ModelMap modelMap) {
		if (!checkLogin()) {
			return MainController.LOGIN;
		}
		if (hasPermission() == 2) {
			Category category = new Category();
			category.setCode(request.getParameter("code"));
			category.setDesignation(request.getParameter("designation"));
			Map<String, Object> map = new HashMap<>();
			attributeRightsForCurrentUser(map);
			map.put(MainController.USER, MainController.roleUser);
			try {
				categoryDao.save(category);
				map.put(ATTR_CATEGORIES, categoryDao.findAll());
				modelMap.addAllAttributes(map);
				return HOME_PAGE_CATEGORIES;
			} catch (ConstraintViolationException e) {
				map.put(ATTR_CATEGORY, category);
				map.put(MainController.ATTR_ERRORS, catchConstraintViolation(e));
				modelMap.addAllAttributes(map);
				return ADD_PAGE_CATEGORIES;
			}
		} else {
			Map<String, Object> map = new HashMap<>();
			attributeRightsForCurrentUser(map);
			modelMap.addAllAttributes(map);
			return MainController.PAGE_403;
		}
	}

	@GetMapping(value = "/Categories/{id}/delete")
	public String deleteCategory(@PathVariable int id, ModelMap modelMap) {
		if (!checkLogin()) {
			return MainController.LOGIN;
		}
		if (hasPermission() == 2) {
			String error = new String("");
			try {
				categoryDao.deleteById(id);
				error = error.concat("yes");
			} catch (Exception e) {
				error = error.concat("no");
			}
			Map<String, Object> map = new HashMap<>();
			attributeRightsForCurrentUser(map);
			map.put(MainController.USER, MainController.roleUser);
			map.put(MainController.ATTR_ERROR, error);
			map.put(ATTR_CATEGORIES, categoryDao.findAll());
			modelMap.addAllAttributes(map);
			return HOME_PAGE_CATEGORIES;
		} else {
			Map<String, Object> map = new HashMap<>();
			attributeRightsForCurrentUser(map);
			modelMap.addAllAttributes(map);
			return MainController.PAGE_403;
		}
	}

	@GetMapping(value = "/Categories/{id}/editCategory")
	public String updateCategory(@PathVariable int id, ModelMap modelMap) {
		if (!checkLogin()) {
			return MainController.LOGIN;
		}
		Category category = categoryDao.findById(id);
		Map<String, Object> map = new HashMap<>();
		attributeRightsForCurrentUser(map);
		map.put(MainController.USER, MainController.roleUser);
		map.put(ATTR_CATEGORY, category);
		modelMap.addAllAttributes(map);
		return EDIT_PAGE_CATEGORIES;
	}

	@PostMapping(value = "/Categories/{id}/editCategory/edit")
	public String updateCategory(ServletRequest request, @PathVariable int id, ModelMap modelMap) {
		if (!checkLogin()) {
			return MainController.LOGIN;
		}
		if (hasPermission() == 2) {
			Category category = categoryDao.findById(id);
			category.setCode(request.getParameter("code"));
			category.setDesignation(request.getParameter("designation"));
			Map<String, Object> map = new HashMap<>();
			attributeRightsForCurrentUser(map);
			map.put(MainController.USER, MainController.roleUser);
			try {
				categoryDao.save(category);
				map.put(ATTR_CATEGORIES, categoryDao.findAll());
				modelMap.addAllAttributes(map);
				return HOME_PAGE_CATEGORIES;
			} catch (TransactionSystemException e) {
				map.put(ATTR_CATEGORY, category);
				map.put(MainController.ATTR_ERRORS,
						catchConstraintViolation((ConstraintViolationException) e.getCause().getCause()));
				modelMap.addAllAttributes(map);
				return EDIT_PAGE_CATEGORIES;
			}
		} else {
			Map<String, Object> map = new HashMap<>();
			attributeRightsForCurrentUser(map);
			modelMap.addAllAttributes(map);
			return MainController.PAGE_403;
		}
	}
}