package s23.Bookstore.web;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import s23.Bookstore.domain.BookRepository;
import s23.Bookstore.domain.Category;
import s23.Bookstore.domain.CategoryRepository;
import s23.Bookstore.domain.Book;

@RestController
public class BookRestController {

	@Autowired
	private BookRepository repository;
	@Autowired
	private CategoryRepository crepository;

	@GetMapping(value = "/books")
	public @ResponseBody List<Book> bookListRest() {
		return (List<Book>) repository.findAll();
	}

	@GetMapping(value = "/book/{id}")
	public @ResponseBody Optional<Book> findBookRest(@PathVariable("id") Long id) {
		return repository.findById(id);
	}
	
	@PutMapping(value = "/book/{id}")
	public Book updateBook(@PathVariable("id") Long id, @RequestBody Book updatedBook) {
	    // Find the existing book by ID
	    Optional<Book> optionalBook = repository.findById(id);
	    
	    if (!optionalBook.isPresent()) {
	        // Handle not found, throw an exception or return a custom response.
	        throw new RuntimeException("Book not found with ID: " + id);
	    }
	    
	    Book existingBook = optionalBook.get();
	    
	    // Update the fields of the existing book with the values from updatedBook
	    existingBook.setTitle(updatedBook.getTitle());
	    existingBook.setAuthor(updatedBook.getAuthor());
	    existingBook.setPublicationYear(updatedBook.getPublicationYear());
	    existingBook.setIsbn(updatedBook.getIsbn());
	    existingBook.setPrice(updatedBook.getPrice());

	    // If the category is provided in the updatedBook, you can also update it.
	    if(updatedBook.getCategory() != null) {
	        String categoryName = updatedBook.getCategory().getName();
	        List<Category> categoryList = crepository.findByName(categoryName);
	        Category categoryToUpdate = categoryList.isEmpty() ? new Category(categoryName) : categoryList.get(0);
	        crepository.save(categoryToUpdate);
	        existingBook.setCategory(categoryToUpdate);
	    }
	    
	    // Save the updated book back to the database
	    return repository.save(existingBook);
	}
	
	@PostMapping(value = "/book")
	public Book addBookWithCategory(@RequestBody Book bookRequest) {
	    String categoryName = bookRequest.getCategory().getName();

	    List<Category> categoryList = crepository.findByName(categoryName);
	    Category existingCategory  = null;
	    
	    if (categoryList != null && !categoryList.isEmpty()) {
	    	existingCategory  = categoryList.get(0);
	    }

	    if (existingCategory  == null) {
	    	existingCategory  = new Category(categoryName);
	        crepository.save(existingCategory );
	    }

	    bookRequest.setCategory(existingCategory );
	    return repository.save(bookRequest);
	} 
	
	/* @PostMapping(value = "/book")
	public Book save(@RequestBody Book book) {
	    return repository.save(book);
	} */



		
}
