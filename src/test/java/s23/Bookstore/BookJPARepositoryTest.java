package s23.Bookstore;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import s23.Bookstore.domain.BookRepository;
import s23.Bookstore.domain.Book;

@DataJpaTest
class BookJPARepositoryTest {

	@Autowired
	BookRepository bookRepository;

	@Test
	void findAllBooksShouldReturnTwoRows() {
		Iterable<Book> books = bookRepository.findAll();
		assertThat(books).hasSize(2);
	}

	@Test
	void findBookByTitleShouldReturnAtLeastOneBook() {
		Iterable<Book> books = bookRepository.findByTitle("Harry Potter");
		assertThat(books).isNotNull();
	}

	@Test
	public void findByTitleShouldReturnAuthor() {
	List<Book> books = bookRepository.findByTitle("Harry Potter");
	//assertThat(books).hasSize(1);
	assertThat(books.get(0).getAuthor()).isEqualTo("J. K. Rowling");
	}

	@Test
	public void saveBookWithTitleAndAuthorShouldBeOK() {
		Book book = new Book("Da Vinci Code", "Dan Brown");
		bookRepository.save(book);
		assertNotEquals(book.getId(), null);

	}

	@Test
	public void saveEmptyBookShouldReturnError() {
		Book book = new Book();
		bookRepository.save(book);
		assertNotEquals(book.getId(), null);
	}

	@Test
	public void getNonExsistingBookShouldReturnEmpty() {
		Optional<Book> book = bookRepository.findById((long) 100);
		System.out.println("Book is " + book);
		assertThat(book).isEmpty();
	}

	@Test
	public void updateBookShould() {
		Optional<Book> book = bookRepository.findById((long) 1);
		assertNotEquals(book.get().getId(), null);
		book.get().setTitle("testi");
		List<Book> books = bookRepository.findByTitle("testi");
		assertThat(books).hasSize(1);
	}


}