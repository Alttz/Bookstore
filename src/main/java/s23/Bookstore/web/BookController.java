package s23.Bookstore.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import s23.Bookstore.domain.Book;
import s23.Bookstore.domain.BookRepository;

@Controller
public class BookController {
	
	@Autowired
	private BookRepository repository; 
	
	private static final Logger log = LoggerFactory.getLogger(BookController.class);
	
	@RequestMapping(value= {"/", "/booklist"})
	public String bookList(Model model) {
	model.addAttribute("books", repository.findAll());
	return "booklist";
	}
	
	@RequestMapping("index")
	@ResponseBody
	public String showMainPage() {
		return "Tämä on pääsivu";
	}
	
	@RequestMapping(value = "/add")
	public String addBook(Model model){
		log.info("lisätään kirja");
	 model.addAttribute("book", new Book());
	 return "addbook";
	}
	
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String save(Book book){
	 repository.save(book);
	 return "redirect:booklist";
	}
	
	@RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
	public String deleteBook(@PathVariable("id") Long id, Model model)
	{ repository.deleteById(id);
	 return "redirect:../booklist";
	}
	
	@RequestMapping(value = "/edit/{id}")
	public String showModBook(@PathVariable("id") Long id, Model model)
	{ model.addAttribute("book", repository.findById(id));
	return "editbook";
	}

}
