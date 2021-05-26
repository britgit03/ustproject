package com.mvc.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.model.CartItem;
import com.model.Customer;
import com.model.Product;
import com.model.UserRegistration;
import com.service.CustomerService;
import com.service.ProductService;

import lombok.NonNull;

@Controller
public class ProductController {
	
	@Autowired
	ProductService productService;
	
	@Autowired
	CustomerService customerService;
	
	@Autowired
	JdbcUserDetailsManager jdbcUserDetailsManager;
	
	@RequestMapping("/index")
	public ModelAndView welcome() {
		String username=null;		
		Object principal =SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if(principal instanceof UserDetails) {
			username = ((UserDetails) principal).getUsername();	
			System.out.println("##### username is" +username);
		}	
		ModelAndView view =new ModelAndView();
		view.addObject("username", username);
		view.setViewName("index");
		return view;
	}
	
	@RequestMapping("/")
	public ModelAndView welcome1() {
		System.out.println("##### inside the indix page");
		String username=null;		
		Object principal =SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if(principal instanceof UserDetails) {
			username = ((UserDetails) principal).getUsername();	
			System.out.println("##### username is" +username);
		}	
		System.out.println("##### username is" +username);
		ModelAndView view =new ModelAndView();
		view.addObject("username", username);
		view.setViewName("index");
		return view;
	}
	
	
	
	@RequestMapping("/addProduct")
	public ModelAndView addProduct() {
		System.out.println("inside the addProduct");
		return new ModelAndView("addProduct", "command", new Product());
	}
	
	/*@RequestMapping("/cart/{productId}")
	public ModelAndView cartProduct(@PathVariable("productId") String productId) {
		String username=null;		
		Object principal =SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if(principal instanceof UserDetails) {
			username = ((UserDetails) principal).getUsername();	
			System.out.println("##### username is" +username);
		}
		Customer customer = customerService.getCurrentlyLoggedInCustomer();	
		
		List<CartItem> cartItems = customerService.listCartItems(customer);
		model.addAttribute("cartItems", cartItems);
		//model.addAllAttributes("pageTitle","Shopping Cart");
		return "shopping_cart";
		//List<Product> products = productService.getProducts();
		//return new ModelAndView("cart", "products", products);
		}*/

	@RequestMapping("/updateQuantity")
	public String updateQuantity(Product product) {
		System.out.println(" update product quantity details" +product);
		productService.updateQuantity(product);
		return "redirect:viewAllProduct";
	}
	
	@RequestMapping("/deleteCartProduct/{productId}")
	public ModelAndView deleteCartProduct(@PathVariable("productId") String productId) {
		Integer pid =new Integer(productId);
		productService.deleteProduct(pid);
		ModelAndView view = new ModelAndView();
		view.addObject("productId",productId);
		view.addObject("message", "Product with ProductId  " +productId +"  deleted successfully");
		return new ModelAndView("redirect:/viewAllProduct");
	}
	
	@RequestMapping("/saveProduct")
	public String saveProduct(Product product) {
		productService.saveProduct(product);
		return "success";
	}
	
	@RequestMapping("/searchProductByIdForm")
	public ModelAndView SearchProductById() {
		return new ModelAndView("searchProductByIdForm", "command", new Product());
	}
	
	@RequestMapping("/searchProduct")
	public ModelAndView searchProduct(Product product) {
		ModelAndView modelAndView =new ModelAndView();
		modelAndView.setViewName("searchProductByIdForm");
		Integer productId = product.getProductId();
		if(productService.isProductExists(productId)) {
			Product searchProduct = productService.getProduct(productId);
			modelAndView.addObject("command", searchProduct);
			
		}else {
			modelAndView.addObject("command", new Product());
			modelAndView.addObject("message", "Product with ProductId " +productId +"doesnot exist");
			
		}
		return modelAndView;
		
	}
	
	
	@RequestMapping("/deleteProductByIdForm")
	public ModelAndView deleteProduct(Product product) {
		ModelAndView modelAndView =new ModelAndView();
		modelAndView.setViewName("searchProductByIdForm");
		modelAndView.addObject("command", new Product());
		Integer productId = product.getProductId();
		if(productService.isProductExists(productId)) {
			productService.deleteProduct(productId);
			modelAndView.addObject("message", "Product with ProductId  " +productId +"  deleted successfully");
			
		}else {
			modelAndView.addObject("message", "Product with ProductId  " +productId +"doesnot exist");
			
		}
		return modelAndView;
		
	}
	
	@RequestMapping("/viewAllProduct")
	public ModelAndView viewAllProduct() {
		List<Product> products = productService.getProducts();
		return new ModelAndView("viewAllProducts", "products", products);
	}
	@RequestMapping("editProduct/{productId}")
	public ModelAndView editCustomer(@PathVariable("productId") Integer productId) {
		ModelAndView view =new ModelAndView();
		view.setViewName("editProductForm");
		Product product = productService.getProductById(productId);
		view.addObject("command", product);
		return view;
	}
	
	@RequestMapping("/updateProduct")
	public String updateCustomer(Product product) {
		productService.updateProduct(product);
		return "redirect:/viewAllProduct";
	}
	
	@RequestMapping(value="/login", method = RequestMethod.GET)
	public String login(Model model,String error,String logout) {
	if(error!=null) {
		model.addAttribute("errorMsg", "Your User name and password are incorrect");
	}
	if(logout!=null) {
		model.addAttribute("msg", "You have been successfully logout");
	}
	return "login";
}
	
	@RequestMapping(value="/register", method = RequestMethod.GET)
	public ModelAndView login() {
		
		return new ModelAndView("registration", "user", new Customer());	
	}
	
	
	/*@RequestMapping(value="/register", method = RequestMethod.POST)
	public ModelAndView registerUser(@ModelAttribute("user") UserRegistration  userRegistration) {
		
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
		
		User user = new User(userRegistration.getUsername(),userRegistration.getPassword(),authorities);
		jdbcUserDetailsManager.createUser(user);
		return new ModelAndView("login", "message", "You have been successfully registered.Please Login");
		}*/
	
	@RequestMapping(value="/register", method = RequestMethod.POST)
	public ModelAndView registerUser(@ModelAttribute("user") Customer  customer) {
		
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
		
		User user = new User(customer.getCustomerName(),customer.getPassword(),authorities);
		jdbcUserDetailsManager.createUser(user);		
		customerService.saveCustomer(customer);
		return new ModelAndView("login", "message", "You have been successfully registered.Please Login");
		}


}
