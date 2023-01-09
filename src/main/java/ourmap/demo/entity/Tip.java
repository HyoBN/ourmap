package ourmap.demo.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(of={"post","comment"})
public class Tip {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "post_id")
    private Post post;
    private String comment;

    private Long writerId;

    public Tip(Post post, String comment, Long writerId) {
        this.post = post;
        this.comment = comment;
        this.writerId=writerId;
    }
}
