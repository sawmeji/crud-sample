package com.example.crudsample.controller;

import com.example.crudsample.dao.CategoryDao;
import com.example.crudsample.dao.ProductDao;
import com.example.crudsample.ds.Category;
import com.example.crudsample.ds.Product;
import com.example.crudsample.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class ProjectController {
    @Autowired
    private ProductService productService;

    @GetMapping("/category")
    public String categoryFrom(Model model){
        model.addAttribute("category", new Category());
        return "category-form";
    }

    @PostMapping("/category")
    public String saveCategory(Category category, BindingResult result){
        if (result.hasErrors()){
            return "category-form";
        }
        productService.saveCategory(category);
        return "redirect:/list-category";
    }

    @GetMapping("/list-category")
    public String listCategory(Model model){
        model.addAttribute("categories",productService.findAllCategory());
        return "list-category";
    }

    //this ways only concern with product-form
    @GetMapping("/product")
    public ModelAndView productForm(@ModelAttribute("categories")List<Category> categories){
        ModelAndView mv = new ModelAndView("product-form","product",new Product());
        mv.addObject("listcategory",categories);
        return mv;
        //return new ModelAndView("product-form","product",new Product());
    }

    @PostMapping("/product")
    @Transactional
    public String saveProduct(Product product,BindingResult result){
        if(result.hasErrors()){
            return "product-form";
        }
        productService.saveProduct(product);
        return "redirect:/list-products";
    }

    @GetMapping("/list-products")
    public String listProducts(Model model){
        model.addAttribute("products",productService.findAllProduct());
        return "list-products";
    }
    @GetMapping("/product/delete")
    public String deteteProduct(int id){
        productService.deleteProduct(id);
        return "redirect:/list-products";
    }

    @ModelAttribute("categories")
    public List<Category> listCategory(){
        return productService.findAllCategory();
    }

    @GetMapping("/category/delete")
    public String deleteCategory(int id){
        productService.deleteCategory(id);
        return "redirect:/list-category";
    }

    @GetMapping("/product/update")
    public String updateProduct(int id,Model model){
        this.pId = id;
        model.addAttribute("product",productService.findProductById(id));
        return "product-update";
    }

    private int pId;

    @PostMapping("/product/update")
    public String saveUpdateProduct(Product product){
        System.out.println("ID ::::::: "+ product.getId());
        product.setId(pId);
        productService.updateProduct(product);
        return "redirect:/list-products";
    }
}
