package ourmap.demo.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class Message {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="sender_id",nullable = false)
    private Member sender;

    @ManyToOne
    @JoinColumn(name="receiver_id",nullable = false)
    private Member receiver;

    @Enumerated(value = EnumType.STRING)
    @Column(name="message_type",nullable = false)
    private MessageTypes messageType;

    private String contents;

    public Message(Member sender, Member receiver, MessageTypes messageType) {
        this.sender = sender;
        this.receiver = receiver;
        this.messageType = messageType;
    }
}
