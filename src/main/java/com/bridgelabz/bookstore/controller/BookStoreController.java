package com.bridgelabz.bookstore.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.bridgelabz.bookstore.dto.BookDto;
import com.bridgelabz.bookstore.dto.EditBookDto;
import com.bridgelabz.bookstore.entity.Book;
import com.bridgelabz.bookstore.response.BookResponse;
import com.bridgelabz.bookstore.service.IBookService;

//@RestController
//@CrossOrigin
public class BookStoreController {

	@Autowired
	IBookService bookservice;

	@PostMapping("books/addbook")
	public ResponseEntity<BookResponse> addBook(@RequestBody BookDto information) {
		bookservice.addBooks(information);

		return ResponseEntity.status(HttpStatus.CREATED).body(new BookResponse("The Book details are", information));
	}
	
	
	
	
//	@GetMapping( value = "books/{bookId}/{quantity}")
//	public ResponseEntity<BookResponse> getTotalPriceofBookwithDetails( @PathVariable( value = "bookId") long bookId, 
//			                                                            @PathVariable( value = "quantity") int quantity) {
//		BookInformation info = bookservice.getTotalPriceofBook(bookId, quantity);
//		
//			return ResponseEntity.status(HttpStatus.OK).body( new BookResponse("Book Detail are :", info));
//	}
	
	@GetMapping("books")
	public ResponseEntity<BookResponse> getBooks() {
		List<Book> books = bookservice.getBookInfo();
		return ResponseEntity.status(HttpStatus.ACCEPTED).body(new BookResponse("The Book details are", books ));
 
	}
	@GetMapping("books/unsorting")
	public ResponseEntity<BookResponse> sort(){
		List<Book> list=bookservice.sortGetAllBooks();
		return ResponseEntity.status(HttpStatus.OK).body(new BookResponse("all books",list));
	}

//	@PostMapping("/addandupdatecart")
//	public ResponseEntity<BookResponse> addtocart(@RequestParam("userId") Long userId,
//			@RequestHeader("quantity") int quantity, @RequestParam("bookId") Long bookId) {
//		boolean value = bookservice.addandupdatecart(userId, quantity, bookId);
//		if (value) {
//			return ResponseEntity.status(HttpStatus.OK).body(new BookResponse("Book is added to cart ", quantity));
//		} else
//			return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(new BookResponse("Out of Stock", quantity));
//
//	}

//	@PutMapping("/removefromcart")
//	public ResponseEntity<BookResponse> removelabel(@RequestParam("userId") Long userId, @RequestParam("bookId") Long bookId) {
//		bookservice.removefromcart(userId,bookId);
//		return ResponseEntity.status(HttpStatus.OK).body(new BookResponse("Book has been removed from the cart", bookId));
//	}
	
	@GetMapping("books/sorting")
	public ResponseEntity<BookResponse> sorting(@RequestParam("value") boolean value){
		List<Book> list=bookservice.sorting( value);
		if (value==true) {
			return ResponseEntity.status(HttpStatus.OK).body(new BookResponse("all books",list));
		} else {
			return ResponseEntity.status(HttpStatus.OK).body(new BookResponse("all books",list));
		}

	}
	@GetMapping( value = "books/pagewise/{pagenumber}")
	public ResponseEntity<BookResponse> getBookPagewise( @PathVariable( value = "pagenumber") int pagenumber) {
		List<Book> pageList = bookservice.findAllPageBySize( pagenumber);
	
		return ResponseEntity.status(HttpStatus.ACCEPTED).body(new BookResponse("Successfull", pageList));
	}
	
	@GetMapping( value = "books/{bookId}")
 public ResponseEntity<BookResponse> getBookbyId( @PathVariable("bookId") long bookId) {
		Book info = bookservice.getBookbyId(bookId);	
			return ResponseEntity.status(HttpStatus.OK).body(new BookResponse("The book is",info));
	}
	
	
	@PutMapping("books/editbook")
	public ResponseEntity<BookResponse> editBook(@RequestBody EditBookDto information){
		boolean res =bookservice.editBook(information);
		if(res)
			return ResponseEntity.status(HttpStatus.CREATED).body(new BookResponse("The Book is Edited", information));
		return null;
	}
	
	@DeleteMapping("books/deletebook/{bookId}")
	public ResponseEntity<BookResponse> deleteBook(@PathVariable long bookId){
		boolean res =bookservice.deleteBook(bookId);
		if(res)
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(new BookResponse(202,"The Book is Deleted"));
		return null;
	}
	
	@GetMapping("books/approvedBooks")
	public ResponseEntity<BookResponse> getAllApprovedBooks() {
		List<Book> books = bookservice.getAllAprovedBooks();
		return ResponseEntity.status(HttpStatus.ACCEPTED).body(new BookResponse("The Approved Book details are", books ));
		//List<BookInformation> books = bookservice.getAllAprovedBooks();
//		if(books != null)
//			return ResponseEntity.status(HttpStatus.ACCEPTED).body(new BookResponse("The Approved Book details are", books ));
//		else
//			return ResponseEntity.status(HttpStatus.ACCEPTED).body(new BookResponse(400,"No Approved Books available" ));
	}
	
	@PutMapping("books/{bookId}/{status}")
	public ResponseEntity<BookResponse> editStatus(@PathVariable long bookId,@PathVariable String status){
		boolean res =bookservice.editStatus(bookId,status);
		if(res)
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(new BookResponse(202,"The Book Status is changed sucessfully.."));
		else
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(new BookResponse(400,"The Book Status is not updated.."));
	}
	
//	@GetMapping("books/approvedOnHoldBooks")
//	public ResponseEntity<BookResponse> getAllApprovedAndOnHoldBooks() {
//		//List<Book> books = bookservice.getAllApprovedAndOnHoldBooks();
//		if(books != null)
//			return ResponseEntity.status(HttpStatus.ACCEPTED).body(new BookResponse("The Approved & OnHold Book details are", books ));
//		else
//			return ResponseEntity.status(HttpStatus.ACCEPTED).body(new BookResponse(400,"No Approved & OnHold Books available" ));
//	}
	
//	@GetMapping("books/rejectedBooks")
//	public ResponseEntity<BookResponse> getAllRejectedBooks() {
//		List<BookInformation> books = bookservice.getAllRejectedBooks();
//		if(books != null)
//			return ResponseEntity.status(HttpStatus.ACCEPTED).body(new BookResponse("The Rejected Book details are", books ));
//		else
//			return ResponseEntity.status(HttpStatus.ACCEPTED).body(new BookResponse(400,"No Rejected Books available" ));

	}

//}
