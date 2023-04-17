package ourmap.demo.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ourmap.demo.entity.Member;
import ourmap.demo.entity.Post;
import ourmap.demo.entity.Tip;
import ourmap.demo.repository.TipRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class TipService {
    private final TipRepository tipRepository;
    public void upload(Tip tip) {
        tipRepository.save(tip);
    }
    public void deleteTip(Long id) {
        tipRepository.deleteById(id);
    }
    public Tip findById(Long id) {
        return tipRepository.findById(id).get();
    }
    public List<Tip> findTips(){
        return tipRepository.findAll();
    }

    public int CountTipOfPost(Post post){
        List<Tip> tipsOfPost = tipRepository.findByPost_Id(post.getId());
        return tipsOfPost.size();
    }
    public List<Tip> findByWriter(Member writer){return tipRepository.findByWriterId(writer.getId());}

    public boolean lengthOver(String comment) {
        int len = comment.length();
        if (5 <= len && len <= 100) {
            return false;
        }
        else return true;
    }

    public String tipPostingCheck(String comment) {
        if (lengthOver(comment)) {
            return "tipLengthOver";
        } else return "ok";
    }

}