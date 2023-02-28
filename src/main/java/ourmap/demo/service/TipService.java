package ourmap.demo.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ourmap.demo.entity.Member;
import ourmap.demo.entity.Tip;
import ourmap.demo.repository.TipRepository;

import java.util.List;

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
}