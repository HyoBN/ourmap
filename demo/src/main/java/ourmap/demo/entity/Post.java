package ourmap.demo.entity;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(of={"id","storeName"})
public class Post {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="post_id")
    private Long id;

    private String storeName;
    private String storeType; // 나중에 enum 으로 바꾸기.

    @OneToMany(mappedBy = "post")
    private List<Tip> tips = new ArrayList<>();

    public Post(String storeName) {
        this.storeName = storeName;
    }
}
