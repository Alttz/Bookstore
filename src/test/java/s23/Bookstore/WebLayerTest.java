package s23.Bookstore;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;

import s23.Bookstore.domain.BookRepository;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.Mockito.*;
import java.util.Collections;

@SpringBootTest
@AutoConfigureMockMvc
public class WebLayerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private BookRepository repository;

	@Test
	 public void bookListTest() throws Exception {
	     when(repository.findAll()).thenReturn(Collections.emptyList());  // mock the repository response

	     mockMvc.perform(MockMvcRequestBuilders.get("/booklist")
	            .with(user("user").password("password").roles("USER")))  // mock authentication
	           .andExpect(status().isOk())
	           .andExpect(view().name("booklist"))
	           .andExpect(model().attributeExists("books"));
	 }

}
