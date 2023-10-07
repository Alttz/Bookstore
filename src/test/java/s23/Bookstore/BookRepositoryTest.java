package s23.Bookstore;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.List;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import s23.Bookstore.domain.BookRepository;
import s23.Bookstore.domain.Book;
import s23.Bookstore.domain.CategoryRepository;
import s23.Bookstore.domain.Category;
import s23.Bookstore.domain.UserRepository;
import s23.Bookstore.domain.AppUser;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest(classes = BookstoreApplication.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class BookRepositoryTest {

	@Autowired
	private BookRepository repository;
	@Autowired
	private CategoryRepository crepository;
	@Autowired
	private UserRepository urepository;

	// book tests

	@Test
	public void findByTitleShouldReturnBook() {
		List<Book> books = repository.findByTitle("Harry Potter");

		assertThat(books).hasSize(1);
		assertThat(books.get(0).getTitle()).isEqualTo("Harry Potter");
	}

	@Test
	@Order(3)
	public void createNewBook() {
		Category category = new Category("Thriller");
		crepository.save(category);
		Book book = new Book("The Da Vinci Code", "Dan Brown", 2003, "0-385-50420-9", 5.99, category);
		repository.save(book);
		assertThat(book.getId()).isNotNull();
	}

	@Test
	@Order(4)
	public void deleteNewBook() {
		List<Book> books = repository.findByTitle("The Da Vinci Code");
		Book book = books.get(0);
		repository.delete(book);
		List<Book> newBooks = repository.findByTitle("The Da Vinci Code");
		assertThat(newBooks).hasSize(0);
	}

	// category tests

	@Test
	public void findByNameShouldReturnCategory() {
		List<Category> categories = crepository.findByName("Fantasy");

		assertThat(categories).hasSize(1);
		assertThat(categories.get(0).getName()).isEqualTo("Fantasy");
	}

	@Test
	@Order(1)
	public void createNewCategory() {
		Category category = new Category("Horror");
		crepository.save(category);
		assertThat(category.getCategoryid()).isNotNull();
	}

	@Test
	@Order(2)
	public void deleteNewCategory() {
		List<Category> categories = crepository.findByName("Horror");
		Category category = categories.get(0);
		crepository.delete(category);
		List<Category> NewCategories = crepository.findByName("Horror");
		assertThat(NewCategories).hasSize(0);
	}

	// user tests

	@Test
	public void findByUsernameShouldReturnCategory() {
		AppUser appUser = urepository.findByUsername("user");

		assertThat(appUser).isNotNull(); // Check that the returned user is not null
		assertThat(appUser.getUsername()).isEqualTo("user");
	}

	@Test
	@Order(5)
	public void createNewAppUser() {
		AppUser appUser = new AppUser("testuser", "$2a$10$B2SaDmKvTN2APDD31kxa6eUMJsLB1Kkznfs6u9s/PSgzd5Poq1WMS",
				"USER");
		urepository.save(appUser);
		assertThat(appUser.getId()).isNotNull();
	}

	@Test
	@Order(6)
	public void deleteNewAppUser() {
		AppUser appUser = urepository.findByUsername("testuser");

		if (appUser != null) {
			urepository.delete(appUser);
			AppUser deletedAppUser = urepository.findByUsername("testuser");
			assertThat(deletedAppUser).isNull(); // Check that the user is deleted and thus is null now
		} else {
			fail("AppUser to delete was not found.");
		}
	}

}
