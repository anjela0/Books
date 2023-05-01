package am.itspace.storebook.repository;

import am.itspace.storebook.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Integer> {

    List<Book> findTop20ByOrderByIdDesc();

}
