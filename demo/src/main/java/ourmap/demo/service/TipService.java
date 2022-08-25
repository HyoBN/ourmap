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

    public Long deleteTip(Long id) {
        tipRepository.deleteById(id);
        return id;
    }

    public List<Tip> findTips(){
        return tipRepository.findAll();
    }

    public List<Tip> findTipOfPost(Long postId) {
        return tipRepository.findByPost_Id(postId);
    }
}