package ourmap.demo.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class OldMessage extends Message{

    public OldMessage(Long senderId, Long receiverId, MessageTypes messageType) {
        super(senderId, receiverId, messageType);
    }
}
