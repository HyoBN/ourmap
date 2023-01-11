package ourmap.demo.entity;

import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class Message {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Column(name="sender_id",nullable = false)
    private Long senderId;

    @Column(name="receiver_id",nullable = false)
    private Long receiverId;

    @Enumerated(value = EnumType.STRING)
    @Column(name="message_type",nullable = false)
    private MessageTypes messageType;

    private String contents;

    public Message(Long senderId, Long receiverId, MessageTypes messageType) {
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.messageType = messageType;
    }
}
