package ourmap.demo.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import ourmap.demo.config.auth.MemberForm;
import ourmap.demo.entity.Post;
import ourmap.demo.entity.StoreTypes;
import ourmap.demo.service.PostService;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final PostService postService;
    private final HttpSession httpSession;

    @ModelAttribute("storeTypes")
    private StoreTypes[] storeTypes(){
        return StoreTypes.values();
    }

    @ModelAttribute("posts")
    private List<Post> posts(){
        return postService.findPosts();
    }

    @GetMapping("/mainPage")
    public String mainPage(Model model) {
        MemberForm member = (MemberForm) httpSession.getAttribute("member");
        if(member!=null) {
            model.addAttribute("userName", member.getName());
            return "basic/mainPage";
        }
        else {
            model.addAttribute("loginMessage", "로그인 후 사용가능합니다.");
            return "index";
        }
    }

    @GetMapping("/home")
    public String home(Model model){
        MemberForm member = (MemberForm) httpSession.getAttribute("member");

        if(member!=null) {
            model.addAttribute("userName", member.getName());
            return "basic/home";
        } else {
            model.addAttribute("loginMessage", "로그인 후 사용가능합니다.");
            return "index";
        }
    }

    @RequestMapping("/test")
    public String test(){
        return "basic/test";
    }
}
