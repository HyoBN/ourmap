package ourmap.demo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor
@ToString(of={"id","storeName"})
public class Post implements Comparable<Post>{

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="post_id")
    private Long id;

    private String storeName;
    @Enumerated(value = EnumType.STRING)
    private StoreTypes storeType;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "writer_id")
    private Member writer;

    @JsonIgnore
    @OneToMany(mappedBy = "post", cascade = CascadeType.PERSIST)
    private List<Tip> tips = new ArrayList<>();

    @Override
    public int compareTo(Post other) {
        return storeName.compareTo(other.storeName);
    }

    public Post(String storeName, StoreTypes storeType, Member writer) {
        this.storeName = storeName;
        this.storeType = storeType;
        this.writer = writer;
    }

    public Post(Long postId, String storeName, StoreTypes storeType, Member writer) {
        this.id=postId;
        this.storeName = storeName;
        this.storeType = storeType;
        this.writer = writer;
    }
}