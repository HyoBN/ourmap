package ourmap.demo.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ourmap.demo.controller.PostForm;
import ourmap.demo.controller.TipForm;
import ourmap.demo.entity.Member;
import ourmap.demo.entity.Post;
import ourmap.demo.entity.Tip;
import ourmap.demo.repository.TipRepository;

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
    public List<Tip> findTipOfPost(Long postId) {
        return tipRepository.findByPost_Id(postId);
    }

    public List<Tip> findByWriter(Member writer){return tipRepository.findByWriterId(writer.getId());}

    public boolean isExistTip(TipForm tipForm) {
        try {
            Tip tip = tipRepository.findByComment(tipForm.getComment()).get();

        } catch (NoSuchElementException e) {
            return false;
        }
        return true;
    }

    public boolean isTooLongTip(TipForm tipForm) {
        if(tipForm.getComment().length()>101){
            return true;
        }
        return false;
    }
}