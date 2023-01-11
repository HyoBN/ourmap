package ourmap.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ourmap.demo.entity.OldMessage;

public interface OldMessageRepository extends JpaRepository<OldMessage, Long> {

}
