package ourmap.demo.entity;

import lombok.Getter;

import javax.persistence.*;


@Entity
public class NewMessage extends Message{

    public NewMessage(Long senderId, Long receiverId, MessageTypes messageType) {
        super(senderId, receiverId, messageType);
    }
}
