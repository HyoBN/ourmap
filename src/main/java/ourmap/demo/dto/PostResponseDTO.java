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
public class PostResponseDTO {
    @Id
    private long id;
    private String storeName;
    @Enumerated(value = EnumType.STRING)
    private StoreTypes storeType;
    public List<Tip> tips = new ArrayList<>();

    public PostResponseDTO(Post post) {
        this.id=post.getId();
        this.storeName=post.getStoreName();
        this.storeType = post.getStoreType();
    }
}
