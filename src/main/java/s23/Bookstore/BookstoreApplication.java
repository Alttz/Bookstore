package s23.Bookstore;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import io.github.cdimascio.dotenv.Dotenv;


@SpringBootApplication
public class BookstoreApplication {
	
	static {
        Dotenv dotenv = Dotenv.load();
        System.setProperty("spring.datasource.url", dotenv.get("DATABASE_URL"));
        System.setProperty("spring.datasource.username", dotenv.get("DATABASE_USER"));
        System.setProperty("spring.datasource.password", dotenv.get("DATABASE_PASSWORD"));

    }
	
	public static void main(String[] args) {
		SpringApplication.run(BookstoreApplication.class, args);
	}

}
