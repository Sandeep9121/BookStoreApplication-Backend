package com.bridgelabz.bookstore.implementation;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bridgelabz.bookstore.entity.Book;
import com.bridgelabz.bookstore.entity.Users;
import com.bridgelabz.bookstore.entity.WishlistBook;
import com.bridgelabz.bookstore.repository.BookImple;
import com.bridgelabz.bookstore.repository.UserRepository;
import com.bridgelabz.bookstore.service.IWishlistService;
import com.bridgelabz.bookstore.util.JwtGenerator;
@Service
public class WishlistImplementation implements IWishlistService {
	@Autowired
	private JwtGenerator generate;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private BookImple bookRepository;

	Users user=new Users();


	@Override
	@Transactional
	public List<WishlistBook> addwishBook(String token, long bookId) {
		Long id;

				id = (long) generate.parseJWT(token);
			
			Users user = userRepository.findById(id).get();
			if(user!=null) {
			Book book = bookRepository.findById(bookId).get();
			if(book!=null) {
			List<Book> books = null;
			for (WishlistBook d : user.getWishlistBook()) {
				books = d.getBooksList();
			}
			if (books == null) {
				Users userdetails = this.wishbooks(book,user);
				return userRepository.save(userdetails).getWishlistBook();
			}
			Optional<Book> wishbook = books.stream()
			.filter(t -> t.getBookId() == bookId).findFirst();
			if (wishbook.isPresent()) {
				throw null;
			} else {
				Users userdetails = this.wishbooks(book,user);
				return userRepository.save(userdetails).getWishlistBook();
			}
			}//book
			
			//write here exception........
			
			}//user
			//write here exception........
			return null;
			
			
	
}
	
	public Users wishbooks(Book book,Users user) {
		
		WishlistBook wishbook =new WishlistBook();
		ArrayList<Book> booklist = new ArrayList<>();
		booklist.add(book);
		wishbook.setWishlistTime(LocalDateTime.now());
		wishbook.setBooksList(booklist);
		user.getWishlistBook().add(wishbook);
		return user;
		
	}

	@Override
	@Transactional
	public List<WishlistBook> getWishlistBooks(String token) {
		Long id;
	
			id = (long) generate.parseJWT(token);
			Users user = userRepository.findById(id).get();
			if(user!=null) {
			List<WishlistBook> wishBooks = user.getWishlistBook();
		     return wishBooks;
			}
			//write here exception........
	
		return null;
	}

	@Override
	@Transactional
	public boolean removeWishBook(String token, Long bookId) {
		
		Long id;
			id = (long) generate.parseJWT(token);
			Users user = userRepository.findById(id).get();
			if(user!=null) {
			Book book = bookRepository.findById(bookId).get();
			if(book!=null) {
	
			for (WishlistBook  wishlist : user.getWishlistBook()) {
				boolean exitsInWishlist = wishlist.getBooksList().stream()
						.noneMatch(books -> books.getBookId().equals(bookId));
				if (!exitsInWishlist) {
					userRepository.save(user);
					wishlist.getBooksList().remove(book);
					wishlist.getBooksList().clear();
					boolean users = userRepository.save(user).getWishlistBook()
					!= null ? true : false;
					if (user != null) {
						return users;
					}
				}
			}}//book
			//book exception
			
			}//user
			//exception user
		
		return false;
	}

	@Override
	@Transactional
	public int getCountOfWishlist(String token) {
		Long id;
			id = (long) generate.parseJWT(token);
         int countOfWishList=0;
		Users user = userRepository.findById(id).get();
		if(user!=null)
		{
		List<WishlistBook> wishlist = user.getWishlistBook();
         for(WishlistBook wishbook:wishlist) {
        	 if(!wishbook.getBooksList().isEmpty()) {
        		 countOfWishList++;
        	 }
         }
		return countOfWishList;
		}
		//write here exception...................
		
		return 0;
		}

	
	
	
	
}