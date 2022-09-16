package ourmap.demo.repository;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import ourmap.demo.entity.user.Role;
import ourmap.demo.entity.user.User;

@SpringBootTest
@RequiredArgsConstructor
@Transactional
@Rollback(value = false)
public class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;

    @Test
    public void testUser(){
        User user = new User("hyobin","qls0786@naver.com", Role.USER);
        userRepository.save(user);

    }

}
