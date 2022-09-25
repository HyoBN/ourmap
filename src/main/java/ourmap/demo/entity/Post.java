package ourmap.demo.entity;

import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor
@ToString(of={"id","storeName"})
public class Post {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="post_id")
    private Long id;

    //@NotNull     에러해결하고 나중에 붙이기.
    private String storeName;
    @Enumerated(value = EnumType.STRING)
    private StoreTypes storeType; // 나중에 enum 으로 바꾸기.
    private Long writerId;

    @OneToMany(mappedBy = "post", cascade = CascadeType.PERSIST)
    private List<Tip> tips = new ArrayList<>();

    public Post(String storeName, StoreTypes storeType, Long writerId) {
        this.storeName = storeName;
        this.storeType = storeType;
        this.writerId = writerId;
    }

    public Post(Long postId, String storeName, StoreTypes storeType, Long writerId) {
        this.id=postId;
        this.storeName = storeName;
        this.storeType = storeType;
        this.writerId = writerId;
    }
}
