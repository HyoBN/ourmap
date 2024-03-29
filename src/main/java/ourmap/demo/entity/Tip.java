package ourmap.demo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.Comparator;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(of={"post","comment"})
public class Tip implements Comparable<Tip> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonIgnore
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "post_id")
    private Post post;
    private String comment;

    public int compareTo(Tip other) {
        return id.compareTo(other.getId());
    }

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "writer_id")
    private Member writer;

    public Tip(Post post, String comment, Member writer) {
        this.post = post;
        this.comment = comment;
        this.writer=writer;
    }
}
