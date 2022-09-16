package ourmap.demo.entity;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import java.util.List;

@SpringBootTest
@Transactional
@Rollback(value = false)
class PostTest {

    @PersistenceContext
    EntityManager em;

    @Test
    public void testEntity(){
        Post postA=new Post("kanna",StoreTypes.CAFE);
        Post postB=new Post("ello",StoreTypes.CAFE);
        em.persist(postA);
        em.persist(postB);

        System.out.println("postA 아이디 : "+ postA.getId());
        System.out.println("postA 아이디 : "+ postB.getId());
        Tip tip1 = new Tip(postA,"good!11");
        Tip tip2 = new Tip(postA,"cheap!22");
        Tip tip3 = new Tip(postB,"good~~!33");
        Tip tip4 = new Tip(postB,"cheap!44");

        em.persist(tip1);
        em.persist(tip2);
        em.persist(tip3);
        em.persist(tip4);

        //초기화.
        em.flush(); // 강제로 db에 insert 날림.
        em.clear(); // jpa의 영속성 컨텍스트의 캐시를 지워버림. -> 깔끔하게 확인 가능.

        //확인.
        List<Post> posts = em.createQuery("select p from Post p", Post.class)
                .getResultList();
        for (Post post : posts) {
            System.out.println("post = " + post);

        }


        List<Tip> tips = em.createQuery("select t from Tip t", Tip.class)
                .getResultList();
        for (Tip tip : tips) {
            System.out.println("tip = " + tip);
            System.out.println(tip.getPost().getId());
        }
    }
}