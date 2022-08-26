package ourmap.demo.controller;

import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;
import ourmap.demo.entity.Post;

@Getter @Setter
public class TipForm {

    @NotNull
    private Long postId;
    private String comment;
}
