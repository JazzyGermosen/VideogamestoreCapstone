package org.yearup.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.yearup.data.CategoryDao;
import org.yearup.data.ProductDao;
import org.yearup.models.Category;
import org.yearup.models.Product;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

// add the annotations to make this a REST controller
// add the annotation to make this controller the endpoint for the following url
    // http://localhost:8080/categories
// add annotation to allow cross site origin requests

@RestController
// request mapping is the first thing that shows up in the url
// also makes it so that we dont have to rewrite it through the rest of the code
@RequestMapping("categories")
// allows information from this controller to be accessed from another program.
@CrossOrigin
public class CategoriesController {

    private CategoryDao categoryDao;
    private ProductDao productDao;


    // create an Autowired controller to inject the categoryDao and ProductDao
    @Autowired
    public CategoriesController(CategoryDao categoryDao, ProductDao productDao){
        this.categoryDao = categoryDao;
        this.productDao = productDao;
    }

    // add the appropriate annotation for a get action
    @GetMapping("")
    @PreAuthorize("permitAll()")
    public List<Category> getAll(
            @RequestParam(name="cat", required = false)Integer categoryId,
            @RequestParam(name="name", required = false)String name,
            @RequestParam(name="description", required = false)String description
    )
    {
        // find and return all categories
      try{
          return categoryDao.getAllCategories();
      }catch (Exception ex){
          throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Oops... our bad.");
      }
    }

    // add the appropriate annotation for a get action
    @GetMapping("{id}")
    @PreAuthorize("permitAll()")
    public Category getById(@PathVariable int id)
    {
        // get the category by id
        try{
            Category category = categoryDao.getById(id);
            if(category == null)
                throw new ResponseStatusException(HttpStatus.NOT_FOUND);
            return category;
        } catch(Exception ex) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Oops... our bad.");
        }
    }

    // the url to return all products in category 1 would look like this
    // https://localhost:8080/categories/1/products
    @GetMapping("{categoryId}/products")
    public List<Product> getProductsById(@PathVariable int categoryId)
    {
        // get a list of product by categoryId
        return productDao.getById(categoryId);
    }

    // add annotation to call this method for a POST action
    // add annotation to ensure that only an ADMIN can call this function
    public Category addCategory(@RequestBody Category category)
    {
        // insert the category
        return null;
    }

    // add annotation to call this method for a PUT (update) action - the url path must include the categoryId
    // add annotation to ensure that only an ADMIN can call this function
    public void updateCategory(@PathVariable int id, @RequestBody Category category)
    {
        // update the category by id
    }


    // add annotation to call this method for a DELETE action - the url path must include the categoryId
    // add annotation to ensure that only an ADMIN can call this function
    public void deleteCategory(@PathVariable int id)
    {
        // delete the category by id
    }
}
