package ourmap.demo.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ourmap.demo.entity.Post;
import ourmap.demo.entity.StoreTypes;
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


    @ModelAttribute("storeTypes")
    private StoreTypes[] storeTypes(){
        return StoreTypes.values();
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