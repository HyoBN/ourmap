package ourmap.demo.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ourmap.demo.entity.Post;
import ourmap.demo.repository.PostRepository;

import java.util.Optional;

@Controller
@RequiredArgsConstructor
@RequestMapping("/posts")
public class PostController {
    private final PostRepository postRepository;

    public void newStore(Post post) {

    }

    public void addTips(String tip) {

    }

    @GetMapping("/{postId}/edit")
    public String editForm(@PathVariable Long postId, Model model) {
        Optional<Post> post = postRepository.findById(postId);
        model.addAttribute("post", post);
        return "basic/editForm";
    }

    @PostMapping("/{postId}/edit")
    public String edit(@PathVariable Long postId, @ModelAttribute Post post) {
        postRepository.save(post);
        return "redirect:/posts/{postId}";
    }
}