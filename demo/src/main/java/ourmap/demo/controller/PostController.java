package ourmap.demo.controller;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ourmap.demo.entity.Post;
import ourmap.demo.entity.StoreTypes;
import ourmap.demo.entity.Tip;
import ourmap.demo.service.PostService;
import ourmap.demo.service.TipService;

import java.util.List;

@Controller
public class PostController {
    private final PostService postService;
    private final TipService tipService;

    public PostController(PostService postService, TipService tipService) {
        this.postService=postService;
        this.tipService=tipService;
    }


    @ModelAttribute("storeTypes")
    private StoreTypes[] storeTypes(){
        return StoreTypes.values();
    }

    @ModelAttribute("posts")
    private List<Post> posts(){
        return postService.findPosts();
    }

    @GetMapping("/newForm")
    public String newPostPage(Model model) {
        model.addAttribute("post", new Post());
        return "post/newForm";
    }

    @PostMapping("/newForm")
    public String newPost(PostForm form) {
        Post post = new Post(form.getStoreName(), form.getStoreType());
        Tip tip = new Tip(post, form.getTip());
        tipService.upload(tip);
        postService.upload(post);
        return "redirect:/";
    }

    @GetMapping("/newComment")
    public String newComment(){
        return "redirect:/home";
    }
    @PostMapping("/newComment")
    public String newComment(TipForm tip) {

        Tip newTip = new Tip(postService.findPostById(tip.getPostId()), tip.getComment());
        tipService.upload(newTip);
        return "redirect:/home";
    }

    @RequestMapping("/home")
    public String home(){
        return "basic/home";
    }

    @RequestMapping("/test")
    public String test(){
        return "basic/test";
    }

    @RequestMapping("/editForm")
    public String edit(){
        return "post/editForm";
    }


    @GetMapping("/searchByName")
    public String search(String name, Model model) {
        List<Post> searchedPosts = postService.findByNameContains(name);
        // 검색 결과가 없을 시 message 전달, 출력 로직 추가하기.
        model.addAttribute("posts", searchedPosts);
        return "basic/home";
    }


    @GetMapping("/edit/{postId}")
    public String editForm(@PathVariable("postId") Long postId, Model model) {
        Post post = postService.findPostById(postId);
        model.addAttribute("post", post);
        return "post/editForm";
    }

    @PostMapping("/edit/{postId}")
    public String editPost(@PathVariable("postId") Long postId, PostForm form) {
        Post post = new Post(postId, form.getStoreName(), form.getStoreType());
        postService.upload(post);
        return "redirect:/home";
    }

    @PostMapping("/deleteTip/{tipId}")
    public String deleteTip(@PathVariable("tipId") Long tipId) {
        System.out.println("tipId = " + tipId);
        Long postId = tipService.findPostIdByTipId(tipId);
        tipService.deleteTip(tipId);
        return "redirect:/edit/"+postId;
    }
}