package erfan.codes.bookshop.repositories;

import erfan.codes.bookshop.repositories.entities.BookEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IBookRepo extends JpaRepository<BookEntity, Long> {
}
