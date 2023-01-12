package ourmap.demo.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@NoArgsConstructor
public class OldMessage extends Message{

    public OldMessage(Member sender, Member receiver, MessageTypes messageType) {
        super(sender, receiver, messageType);
    }
}
