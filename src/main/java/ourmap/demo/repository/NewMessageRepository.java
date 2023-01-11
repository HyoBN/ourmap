package ourmap.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ourmap.demo.entity.NewMessage;

public interface NewMessageRepository extends JpaRepository<NewMessage, Long> {

}