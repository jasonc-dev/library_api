package jason.api.libraryapi.repositories;

import jason.api.libraryapi.domain.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {
}
