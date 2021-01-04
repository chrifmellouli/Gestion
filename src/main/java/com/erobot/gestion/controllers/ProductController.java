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
import com.erobot.gestion.dao.ProductDao;
import com.erobot.gestion.dao.UserDao;
import com.erobot.gestion.models.Category;
import com.erobot.gestion.models.Product;

/**
 * @author CHRIF MELLOULI
 *
 */
@Controller
public class ProductController extends SecurityController {

	public static final String HOME_PAGE_PRODUCTS = "listProducts";
	public static final String ADD_PAGE_PRODUCTS = "addProduct";
	public static final String EDIT_PAGE_PRODUCTS = "editProduct";

	public static final String ATTR_PRODUCTS = "products";
	public static final String ATTR_PRODUCT = "product";

	protected ProductDao productDao;
	protected CategoryDao categoryDao;

	@Autowired
	public ProductController(UserDao userDao, ProductDao productDao, CategoryDao categoryDao, PrivilegeDao privilegeDao,
			AuthorizationDao authorizationDao) {
		super(userDao, authorizationDao, privilegeDao);
		this.productDao = productDao;
		this.categoryDao = categoryDao;
	}

	@GetMapping(value = "/Products")
	public String listProducts(ModelMap modelMap) {
		if (!checkLogin()) {
			return MainController.LOGIN;
		}
		List<Product> products = productDao.findAll();
		Map<String, Object> map = new HashMap<>();
		attributeRightsForCurrentUser(map);
		map.put(MainController.USER, MainController.roleUser);
		map.put(ATTR_PRODUCTS, products);
		modelMap.addAllAttributes(map);
		return HOME_PAGE_PRODUCTS;
	}

	@GetMapping(value = "/Products/addProduct")
	public String addProduct(ModelMap modelMap) {
		if (!checkLogin()) {
			return MainController.LOGIN;
		}
		Map<String, Object> map = new HashMap<>();
		attributeRightsForCurrentUser(map);
		map.put(MainController.USER, MainController.roleUser);
		map.put(CategoryController.ATTR_CATEGORIES, categoryDao.findAll());
		modelMap.addAllAttributes(map);
		return ADD_PAGE_PRODUCTS;
	}

	@PostMapping(value = "/Products/addProduct/add")
	public String addProduct(ServletRequest request, ModelMap modelMap) {
		if (!checkLogin()) {
			return MainController.LOGIN;
		}
		if (hasPermission() == 2) {
			Product product = new Product();
			float priceExcludingVAT = Float.parseFloat(request.getParameter("priceExcludingVAT"));
			float vat = Float.parseFloat(request.getParameter("vat"));
			product.setCode(request.getParameter("code"));
			product.setDesignation(request.getParameter("designation"));
			product.setUnitOfMeasure(request.getParameter("unitOfMeasure"));
			product.setPriceExcludingVAT(priceExcludingVAT);
			product.setVat(vat);
			product.setCategory(categoryDao.findById(Integer.parseInt(request.getParameter("category"))));
			Map<String, Object> map = new HashMap<>();
			attributeRightsForCurrentUser(map);
			map.put(MainController.USER, MainController.roleUser);
			map.put(CategoryController.ATTR_CATEGORIES, categoryDao.findAll());
			try {
				productDao.save(product);
				map.put(ATTR_PRODUCTS, productDao.findAll());
				modelMap.addAllAttributes(map);
				return HOME_PAGE_PRODUCTS;
			} catch (ConstraintViolationException e) {
				map.put(ATTR_PRODUCT, product);
				map.put(MainController.ATTR_ERRORS, catchConstraintViolation(e));
				modelMap.addAllAttributes(map);
				return ADD_PAGE_PRODUCTS;
			}
		} else {
			Map<String, Object> map = new HashMap<>();
			attributeRightsForCurrentUser(map);
			modelMap.addAllAttributes(map);
			return MainController.PAGE_403;
		}
	}

	@GetMapping(value = "/Products/{id}/delete")
	public String deleteProduct(@PathVariable int id, ModelMap modelMap) {
		if (!checkLogin()) {
			return MainController.LOGIN;
		}
		if (hasPermission() == 2) {
			String error = new String("");
			try {
				productDao.deleteById(id);
				error = error.concat("yes");
			} catch (Exception e) {
				error = error.concat("no");
			}
			Map<String, Object> map = new HashMap<>();
			attributeRightsForCurrentUser(map);
			map.put(MainController.USER, MainController.roleUser);
			map.put(MainController.ATTR_ERROR, error);
			map.put(ATTR_PRODUCTS, productDao.findAll());
			modelMap.addAllAttributes(map);
			return HOME_PAGE_PRODUCTS;
		} else {
			Map<String, Object> map = new HashMap<>();
			attributeRightsForCurrentUser(map);
			modelMap.addAllAttributes(map);
			return MainController.PAGE_403;
		}
	}

	@GetMapping(value = "/Products/{id}/editProduct")
	public String updateProduct(@PathVariable int id, ModelMap modelMap) {
		if (!checkLogin()) {
			return MainController.LOGIN;
		}
		Product product = productDao.findById(id);
		List<Category> categories = categoryDao.findAll();
		Map<String, Object> map = new HashMap<>();
		attributeRightsForCurrentUser(map);
		map.put(ATTR_PRODUCT, product);
		map.put(CategoryController.ATTR_CATEGORIES, categories);
		map.put(MainController.USER, MainController.roleUser);
		modelMap.addAllAttributes(map);
		return EDIT_PAGE_PRODUCTS;
	}

	@GetMapping(value = "/Products/{id}/editProduct/edit")
	public String updateProduct(ServletRequest request, @PathVariable int id, ModelMap modelMap) {
		if (!checkLogin()) {
			return MainController.LOGIN;
		}
		if (hasPermission() == 2) {
			Product product = productDao.findById(id);
			float priceExcludingVAT = Float.parseFloat(request.getParameter("priceExcludingVAT"));
			float vat = Float.parseFloat(request.getParameter("vat"));
			product.setCode(request.getParameter("code"));
			product.setDesignation(request.getParameter("designation"));
			product.setUnitOfMeasure(request.getParameter("unitOfMeasure"));
			product.setPriceExcludingVAT(priceExcludingVAT);
			product.setVat(vat);
			product.setCategory(categoryDao.findById(Integer.parseInt(request.getParameter("category"))));
			List<Category> categories = categoryDao.findAll();
			Map<String, Object> map = new HashMap<>();
			attributeRightsForCurrentUser(map);
			map.put(MainController.USER, MainController.roleUser);
			map.put(CategoryController.ATTR_CATEGORIES, categories);
			try {
				productDao.save(product);
				map.put(ATTR_PRODUCTS, productDao.findAll());
				modelMap.addAllAttributes(map);
				return HOME_PAGE_PRODUCTS;
			} catch (TransactionSystemException e) {
				map.put(ATTR_PRODUCT, product);
				map.put(MainController.ATTR_ERRORS,
						catchConstraintViolation((ConstraintViolationException) e.getCause().getCause()));
				modelMap.addAllAttributes(map);
				return EDIT_PAGE_PRODUCTS;
			}
		} else {
			Map<String, Object> map = new HashMap<>();
			attributeRightsForCurrentUser(map);
			modelMap.addAllAttributes(map);
			return MainController.PAGE_403;
		}
	}

}
