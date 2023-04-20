package ourmap.demo.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import ourmap.demo.entity.Post;
import ourmap.demo.entity.StoreTypes;
import ourmap.demo.entity.Tip;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
public class PostResponseDTO implements Comparable<PostResponseDTO> {
    @Id
    private Long id;
    private String storeName;
    @Enumerated(value = EnumType.STRING)
    private StoreTypes storeType;
    public List<Tip> tips = new ArrayList<>();

    @Override
    public int compareTo(PostResponseDTO other) {
        return storeName.compareTo(other.getStoreName());
    }

    public PostResponseDTO(Post post) {
        this.id=post.getId();
        this.storeName=post.getStoreName();
        this.storeType = post.getStoreType();
    }
}
