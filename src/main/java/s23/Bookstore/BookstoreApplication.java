package s23.Bookstore;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import s23.Bookstore.domain.Book;
import s23.Bookstore.domain.BookRepository;

@SpringBootApplication
public class BookstoreApplication {

	public static void main(String[] args) {
		SpringApplication.run(BookstoreApplication.class, args);
	}
	
	@Bean
	public CommandLineRunner demoData(BookRepository bookRepository) {
		return (args) -> {			
			bookRepository.save(new Book("Lord of the Rings", "JRR Tolkien", 1950, "123", 50.00));
			bookRepository.save(new Book("Harry Potter", "J. K. Rowling", 1999, "124", 60.00));

		};
	}

}
