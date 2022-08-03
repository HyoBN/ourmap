package ourmap.demo.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter @Setter
@Entity
public class Post {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    public String storeName;
    @ElementCollection(targetClass=String.class)
    public List<String> tips;


    public Post() {
    }
}
