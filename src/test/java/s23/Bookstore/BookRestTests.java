package s23.Bookstore;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;

import s23.Bookstore.domain.Book;
import s23.Bookstore.domain.Category;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@SpringBootTest
public class BookRestTests {
	
	@Autowired
	private WebApplicationContext webAppContext;
	
	@Autowired
	private ObjectMapper objectMapper; // Used for converting objects to JSON

	private MockMvc mockMvc;

	
	@BeforeEach // JUnit5
	public void setUp() {
		mockMvc = MockMvcBuilders.webAppContextSetup(webAppContext).build();
	}
	
	@Test
	public void statusOk() throws Exception {
		mockMvc.perform(get("/books")).andExpect(status().isOk());
	}
	
	@Test
	public void responseTypeApplicationJson() throws Exception {
		mockMvc.perform(get("/books"))
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				// .andExpect(content().contentType(MediaType.APPLICATION_ATOM_XML_VALUE))
				.andExpect(status().isOk());
	}
	
	@Test
	public void postBookWithCategory() throws Exception {
		// Sample data to be sent in POST request
		Category category = new Category("Fiction");
		Book book = new Book("Test Book", "Test Author");
		book.setCategory(category);

		// Convert Book object to JSON format
		String bookJson = objectMapper.writeValueAsString(book);

		mockMvc.perform(post("/book")
				.contentType(MediaType.APPLICATION_JSON)
				.content(bookJson))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON));
	}
}
