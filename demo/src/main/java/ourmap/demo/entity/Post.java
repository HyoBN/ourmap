package ourmap.demo.entity;

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

    private String storeName;
    @Enumerated(value = EnumType.STRING)
    private StoreTypes storeType; // 나중에 enum 으로 바꾸기.

    @OneToMany(mappedBy = "post", cascade = CascadeType.PERSIST)
    private List<Tip> tips = new ArrayList<>();

    public Post(String storeName, StoreTypes storeType) {
        this.storeName = storeName;
        this.storeType = storeType;
    }
}
