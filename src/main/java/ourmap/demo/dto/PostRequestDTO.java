package ourmap.demo.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import ourmap.demo.entity.StoreTypes;
import java.util.List;

@RequiredArgsConstructor
@Getter @Setter
public class PostRequestDTO {
    private String storeName;
    private StoreTypes storeType;
    private List<String> tips;
}