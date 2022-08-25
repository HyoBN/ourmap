package ourmap.demo.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ourmap.demo.entity.Post;
import ourmap.demo.entity.Tip;
import ourmap.demo.service.PostService;
import ourmap.demo.service.TipService;

@Controller
//@RequestMapping("/posts")
public class PostController {
    private final PostService postService;
    private final TipService tipService;

    public PostController(PostService postService, TipService tipService) {
        this.postService=postService;
        this.tipService=tipService;
    }


    @GetMapping("/newForm")
    public String newPostPage(){
        return "post/newForm";
    }

    @PostMapping("/newForm")
    public String newPost(PostForm form) {
        // 나중에 storeType추가하기.
        Post post = new Post(form.getStoreName());

        Tip tip = new Tip(post, form.getTip());

        tipService.upload(tip);
        postService.upload(post);
        // tip 추가하는 것도 구현하기.
        return "redirect:/";
    }

    public void addTips(String tip) {

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



    //---------------------------------

    @GetMapping("/{postId}/edit")
    public String editForm(@PathVariable Long postId, Model model) {
        Post post = postService.findPostById(postId);
        model.addAttribute("post", post);
        return "basic/editForm";
    }

    @PostMapping("/{postId}/edit")
    public String edit(@PathVariable Long postId, @ModelAttribute Post post) {
        postService.upload(post);
        return "redirect:/posts/{postId}";
    }
}