package ourmap.demo.service;

import org.springframework.stereotype.Service;
import ourmap.demo.entity.Tip;
import ourmap.demo.repository.TipRepository;

import java.util.List;

@Service
public class TipService {

    private final TipRepository tipRepository;

    public TipService(TipRepository tipRepository) {
        this.tipRepository = tipRepository;
    }

    public Long upload(Tip tip) {
        tipRepository.save(tip);
        return tip.getId();
    }

    public void deleteTip(Long id) {
        tipRepository.deleteById(id);
    }

    public Tip findTipById(Long id) {
            return tipRepository.findById(id).get();
    }

    public Long findPostIdByTipId(Long id) {
        Tip tip = findTipById(id);
        return tip.getPost().getId();
    }
    public List<Tip> findTips(){
        return tipRepository.findAll();
    }

    public List<Tip> findTipOfPost(Long postId) {
        return tipRepository.findByPost_Id(postId);
    }

    public List<Tip> findTipByWriter(Long writerId) {
        return tipRepository.findByWriterId(writerId);
    }
}