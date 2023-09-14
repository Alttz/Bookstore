package s23.Bookstore;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import s23.Bookstore.domain.Book;
import s23.Bookstore.domain.BookRepository;
import s23.Bookstore.domain.Category;
import s23.Bookstore.domain.CategoryRepository;

@SpringBootApplication
public class BookstoreApplication {
	
	private static final Logger log = LoggerFactory.getLogger(BookstoreApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(BookstoreApplication.class, args);
	}
	
	@Bean
	public CommandLineRunner demoData(BookRepository bookRepository, CategoryRepository crepository) {
		return (args) -> {		
			crepository.save(new Category ("Fantasy"));
			crepository.save(new Category ("Comedy"));
			crepository.save(new Category ("Scifi"));

			
			bookRepository.save(new Book("Lord of the Rings", "JRR Tolkien", 1950, "123", 50.00, crepository.findByName("Fantasy").get(0)));
			bookRepository.save(new Book("Harry Potter", "J. K. Rowling", 1999, "124", 60.00, crepository.findByName("Scifi").get(0)));

			for (Book book : bookRepository.findAll()) {
				log.info(book.toString());
			}
			
		};
	}

}
