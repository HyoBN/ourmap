package ourmap.demo.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;


@Entity
@NoArgsConstructor
public class NewMessage extends Message{

    public NewMessage(Member sender, Member receiver, MessageTypes messageType) {
        super(sender, receiver, messageType);
    }
}
