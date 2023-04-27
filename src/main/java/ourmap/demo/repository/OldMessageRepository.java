package ourmap.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ourmap.demo.entity.MessageTypes;
import ourmap.demo.entity.NewMessage;
import ourmap.demo.entity.OldMessage;

import java.util.Optional;

public interface OldMessageRepository extends JpaRepository<OldMessage, Long> {

    Optional<OldMessage> findBySenderIdAndReceiverIdAndMessageType(Long senderId, Long receiverId, MessageTypes messageTypes);

}
