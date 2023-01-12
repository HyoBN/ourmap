package ourmap.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ourmap.demo.entity.NewMessage;

import java.util.List;

public interface NewMessageRepository extends JpaRepository<NewMessage, Long> {

    List<NewMessage> findByReceiverId(Long id);
}