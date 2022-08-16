package ourmap.demo.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import ourmap.demo.entity.Post;
import ourmap.demo.entity.Tip;

import java.util.List;
import java.util.Optional;

@SpringBootTest
@Transactional
//@Rollback(value = false)
public class TipRepositoryTest {

    PostRepository postRepository;
    TipRepository tipRepository;

    //bean 두 개 이상일때.
    @Autowired
    public TipRepositoryTest(PostRepository postRepository,
                             TipRepository tipRepository) {
        this.postRepository=postRepository;
        this.tipRepository=tipRepository;
    }
    @Test
    public void testTip() throws Exception {

        Post post1 = new Post("탐앤탐스");
        Post post2 = new Post(("맥도날드"));

        postRepository.save(post1);
        postRepository.save(post2);

        Tip tip1 = new Tip(post1,"에어컨이 빵빵하다");
        Tip tip2 = new Tip(post1,"공부하기 좋다");
        Tip tip3 = new Tip(post2,"화장실이 깨끗하다");
        Tip tip4 = new Tip(post2,"햄버거 먹고싶다");

        tipRepository.save(tip1);
        tipRepository.save(tip2);
        tipRepository.save(tip3);
        tipRepository.save(tip4);


        List<Tip> tips=tipRepository.findAll();
        for (Tip tip : tips) {
            System.out.println("tip = " + tip);
        }
        List<Tip> post1Tips = tipRepository.findByPostID(post1.getId());
        if(post1Tips==null || post1Tips.isEmpty())
            throw new IllegalArgumentException("It's NULL");
        for (Tip post1Tip : post1Tips) {
            System.out.println("post1Tip = " + post1Tip);
        }


    }
}
