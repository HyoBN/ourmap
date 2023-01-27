package ourmap.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ourmap.demo.entity.MessageTypes;
import ourmap.demo.entity.NewMessage;

import java.util.List;
import java.util.Optional;

public interface NewMessageRepository extends JpaRepository<NewMessage, Long> {

    List<NewMessage> findByReceiverId(Long id);

    Optional<NewMessage> findBySenderIdAndReceiverIdAndMessageType(Long senderId, Long receiverId, MessageTypes messageTypes);
}