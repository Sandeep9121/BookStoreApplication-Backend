package com.bridgelabz.bookstore.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.bookstore.entity.Cart;
import com.bridgelabz.bookstore.response.Response;
import com.bridgelabz.bookstore.service.ICartService;

@RestController
@CrossOrigin
public class CartController {

	@Autowired
	private ICartService cartService;
	@PostMapping(value="/bookdetails/{bookId}")
	public ResponseEntity<Response> addBooksToCart(@RequestHeader String token,@PathVariable long bookId) throws Exception {
	    List<Cart> cart = cartService.addBooktoCart(token,bookId);
	    return ResponseEntity.status(HttpStatus.ACCEPTED)
				.body(new Response("book added to cart", 200,cart));
	  	}


}