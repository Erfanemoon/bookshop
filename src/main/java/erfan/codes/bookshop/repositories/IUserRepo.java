package erfan.codes.bookshop.repositories;

import erfan.codes.bookshop.repositories.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IUserRepo extends JpaRepository<UserEntity, Long> {
}
