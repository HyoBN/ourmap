package ourmap.demo.dto;

import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import ourmap.demo.entity.Post;

@Getter @Setter
public class TipForm {
    @NotNull
    private Long postId;
    private String comment;

    public TipForm(Long postId, String comment) {
        this.postId = postId;
        this.comment = comment;
    }
}
